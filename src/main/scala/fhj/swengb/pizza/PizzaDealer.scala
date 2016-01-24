package fhj.swengb.pizza


import javafx.animation.AnimationTimer
import javafx.scene.control.ProgressBar
import javafx.scene.image.ImageView

import fhj.swengb.pizza.PizzaDealer.{CraftingBench, Customer}

import scala.util.Random.nextInt


object GameLoop extends AnimationTimer {

  //nur als beispiel wie man einen Customer erstellt
  //var customer = new CustomerAnim()
  //customer.set(obj,2)
  //cashier erzeugen

  //wird nach Bedienung der 4 Kunden um 1 erhöht
  lazy val levelMax: Int = 4
  //wird um 1 verringert falls Zeit abgelaufen
  lazy val timeStatisch: Int = 16
  var level: Int = 1
  //max level
  var lives: Int = 4
  // in sec
  var i: Int = 1
  // falls maxlevel erreicht ist wird die übrige Zeit ZUSÄTZLICH noch um diesen wert verringert     der wert erhöht sich dannach
  var score: Long = 0
  var selectedCustomer:Int = 1
  var cus1: Customer = _
  var cus2: Customer = _
  var cus3: Customer = _
  var cus4: Customer = _
  var craB1: CraftingBench = _
  var craB2: CraftingBench = _
  var craB3: CraftingBench = _
  var craB4: CraftingBench = _
  var timer: Long = _
  var cashier = CashierAnim
  var obj: ImageView = _
  var fps = 100
  var lastFrame = System.nanoTime
  var lastLogicFrame = 0
  var progressbar:ProgressBar = _
  var playerName:String = ""
  var firstRun:Int = 0

  /*
  alle speachbubbles
   */

  def set(cash: ImageView, fps: Int = 100) {
    this.obj = cash
    this.fps = fps
    CashierAnim.set(cash)
  }


  def setEverything(pb:ProgressBar): Unit = {
    progressbar = pb


  }

  createCustomers(level)
  def editProgressbar() = {
    progressbar.setProgress(progressbar.getProgress+calcPorgressbar())
  }
  override def handle(now: Long): Unit = {
    val frameJump: Int = Math.floor((now - lastFrame) / (1000000000 / fps)).toInt //berechne ob genug Zeit vergangen ist um einen neuen Frame anzuzeigen
    if (frameJump > 1) {
      //neuen Frame berechnen
      //sind customers erstellt

      if(checkCustomersExist()==false){
        //createCustomers(this.level)
      }


      if (checkIfGameover == true) { //spiel vorbei?
        //in datenbank speichern
        ScalaJdbcSQL.setHighscoreList(playerName,score.toInt)
        stop()
      }

      //überprüfen ob alle customers bedient worden sind
      //einen customer auf happy setzen

      if(firstRun == 0) {
        firstRun = 1
        createCustomers(level)
        //CustomerSpeechBubbleAnim.setOrder


      }


      if (checkIfCustomersServed == true) {

        score = 4 + level * (System.nanoTime() / 1000000000 - timer) // 4 für customers + level*timeleft
        //reset timer
        level = level + 1
        createCustomers(level)
        //speechbubble neu setzten

        setTimer()
      }

      //



      if (checkTimer == true) {  //timer setzen
        setTimer()
        lives = lives - 1
      } else {
        editProgressbar()
        //setprogressbar
      }











      lastFrame = now
      lastLogicFrame += 1
      //customer.handle(now)

      cashier.handle(now)
    }



    //Nur zum testen des Cashiers:
    if (lastLogicFrame == 50) {
      cashier.setGoTo("Customer1")

    } else if (lastLogicFrame == 100) {
      cashier.setGoTo("Customer4")

    } else if (lastLogicFrame > 150) {
      cashier.setGoTo("Käse")

      lastLogicFrame = 0
    }


    /*
    x   erstellen der 4 Kunden (<- jeweils eine Pizza)
    x   kunden werden abhängig von level erstellt
    x Kunden müssen bestimmten platz bekommen position 1-4
        - positon in Imageview zuweisen (animation) -> Container den man auffüllt
    - Customer number = aussehen zuweisen randomizen //warten auf FXML
    - setzen der cUSTOMERSPEECHBALLONANIM -> sprechblase
    - Timer start
      - falls timer 0   -1 leben  und in das nächste level
      ingridients button
      customer button
      zeit umrechnen 0 - 1 mit timeandlevel function

     */
    /*
    //Nur zum testen des Customers:
    if (lastLogicFrame == 50) {
      customer.setAngry()

    }else  if (lastLogicFrame == 100) {
      customer.setHappy()

    }else if (lastLogicFrame > 150){
      customer.setNeutral()

      lastLogicFrame=0
    }
    */
  }



  //allgemeine setter funktion für alle objekte











  private def checkIfGameover: Boolean = {
    if (this.lives == 0) {
      true
    }
    else {
      false
    }
  }

  private def checkIfCustomersServed(): Boolean = {
    if (cus1.getOrder() == craB1.getAddedIngridients && cus2.getOrder() == craB2.getAddedIngridients && cus3.getOrder() == craB3.getAddedIngridients && cus4.getOrder() == craB4.getAddedIngridients) {
      true
    }
    else {
      false
    }
  }

  private def setTimer() {
    timer = System.nanoTime() / 1000000000

  }

  private def checkTimer(): Boolean = {
    //abgelaufen?  ja?
    if (timer > 0) {
      if (System.nanoTime() / 1000000000 > timer + timeAndLevel()) true
      else false
    } else {
      true
    }
  }

  private def timeLeft():Int = {
    val i = timer - System.nanoTime()/1000000000
    i.toInt
  }

  private def calcPorgressbar():Double = {
    val x = timeAndLevel()

    val y:Float = x/100 // dann haben wir 1 %

    val progressbarChange = y*timeLeft

    progressbarChange
  }

  private def timeAndLevel(): Int = {
    //returns time for timer
    var x = 0
    if (level == levelMax) {
      x = timeStatisch - level - i
      i = i + 1
      if (x <= 0) return 1
    }
    else {
      x = timeStatisch - level
      level = level + 1
    }
    x
  }

  private def checkCustomersExist(): Boolean = {
    try {
      cus1.getOrder()
      true
    }
    catch {
      case e => false
    }
  }



  def ingridientsButton(i:Int,selectedCustomer:Int) = {//add ingridient to working bench
    i match {
      case 1 => {getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("salami")
        CashierAnim.setGoTo("salami")} //salami
      case 2 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("paprika"); CashierAnim.setGoTo("paprika")//paprika
      case 3 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("champignon"); CashierAnim.setGoTo("champignon")
      case 4 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("cheese"); CashierAnim.setGoTo("cheese") //cheese
      case 5 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("onion"); CashierAnim.setGoTo("onion") //onion
      case 6 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("tomato"); CashierAnim.setGoTo("tomato")//tomato
      case 7 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("ham"); CashierAnim.setGoTo("ham") //ham
      case 8 => getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("tuna"); CashierAnim.setGoTo("tuna") //tuna
      case _ => CashierAnim.setGoTo("standard") //back to standardposition
    }

    //set animation cashier??!?!?!?!


  }

  def getCraftingBenchForCustomer(selectedCustomer:Int): CraftingBench = {
    selectedCustomer match {
      case 1 => craB1
      case 2 => craB2
      case 3 => craB3
      case 4 => craB4
    }
  }

  def customerSelected(customer:Int): Unit = {
    customer match {
      case 1 => selectedCustomer=1
      case 2 => selectedCustomer=2
      case 3 => selectedCustomer=3
      case 4 => selectedCustomer=4
    }
  }


  def createCustomers(level: Int) = {
    //val positionAnimation: List[Int] = util.Random.shuffle((1 to 4).toSeq.toList)
    cus1 = new Customer(level)
    println("CUSTOMER 1 FAGGOT"+cus1.getOrder())
    craB1 = new CraftingBench(cus1.getOrder())
    cus2 = new Customer(level)
    println("CUSTOMER 2 DU NUTTE"+cus2.getOrder())
    craB2 = new CraftingBench(cus2.getOrder())
    cus3 = new Customer(level)
    craB3 = new CraftingBench(cus3.getOrder())
    cus4 = new Customer(level)
    craB4 = new CraftingBench(cus4.getOrder())

    /*
    cus1.set(???) //updaten ausehen randomzien
    cus2.set(???) //updaten
    cus3.set(???) //updaten
    cus4.set(???) //updaten
    */

  }
}


object PizzaDealer {


  class Pizza(n: Int) {
    //n = number of ingridients
    val ingridients = List("champignon", "cheese", "ham", "onion", "paprika", "salami", "tomato", "tuna")
    var finalIngridients = List[String]()

    def setPizzaObject(): List[String] = {
      if(n >= 4){
        for(n <- 1 to 4){
          finalIngridients ::= ingridients.toList((math.random*ingridients.size).toInt)
        }
      }else {
        for(n <- 1 to n){
          finalIngridients ::= ingridients.toList((math.random*ingridients.size).toInt)
        }
      }
      finalIngridients
    }
    def getIngridients(): List[String] = finalIngridients
  }


  class Customer(level: Int) {
    val order = new Pizza(GameLoop.level).setPizzaObject()
    println("IM CUSTOMER"+order)
    //+sprechblase setzen
    def getOrder(): List[String] = order.sorted
  }


  class CraftingBench(order: List[String]) {
    var ingridientsAdded = List[String]()

    def addIngridientToCraftingBench(ingridient: String): List[String] = {
      if (order.contains(ingridient)) {
        ingridientsAdded ::= ingridient
        ingridientsAdded
        //if abfrage ob customer served -> customer auf happy setzen
      }
      else {
        println("Nope war net in der Order")
        ingridientsAdded
      }
    }

    def getAddedIngridients: List[String] = ingridientsAdded.sorted
  }

  class CustomerDisplay {
    //get Zutaten

    //set display

  }

  class Player(name: String) {
    def setName = ???
    def getName = ???

  }


}