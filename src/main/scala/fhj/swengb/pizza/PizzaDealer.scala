package fhj.swengb.pizza


import javafx.animation.AnimationTimer
import javafx.scene.control
import javafx.scene.control.{Label, ProgressBar}
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
  var selectedCustomer: Int = 1
  var cus1: Customer = _
  var cus2: Customer = _
  var cus3: Customer = _
  var cus4: Customer = _
  var craB1: CraftingBench = _
  var craB2: CraftingBench = _
  var craB3: CraftingBench = _
  var craB4: CraftingBench = _
  var timer: Long = _
  var fps = 100
  var lastFrame = System.nanoTime
  var lastLogicFrame = 0


  //STUFF TO SET
  var progressbar: ProgressBar = _
  var cashier1: ImageView = _
  var cashier = CashierAnim
  //pizza1
  var imgvw_pizza1: ImageView = _
  var imgvw_pizza1_ing1: ImageView = _
  var imgvw_pizza1_ing2: ImageView = _
  var imgvw_pizza1_ing3: ImageView = _
  var imgvw_pizza1_ing4: ImageView = _
  //pizza2
  var imgvw_pizza2: ImageView = _
  var imgvw_pizza2_ing1: ImageView = _
  var imgvw_pizza2_ing2: ImageView = _
  var imgvw_pizza2_ing3: ImageView = _
  var imgvw_pizza2_ing4: ImageView = _
  //pizza3
  var imgvw_pizza3: ImageView = _
  var imgvw_pizza3_ing1: ImageView = _
  var imgvw_pizza3_ing2: ImageView = _
  var imgvw_pizza3_ing3: ImageView = _
  var imgvw_pizza3_ing4: ImageView = _
  //pizza4
  var imgvw_pizza4: ImageView = _
  var imgvw_pizza4_ing1: ImageView = _
  var imgvw_pizza4_ing2: ImageView = _
  var imgvw_pizza4_ing3: ImageView = _
  var imgvw_pizza4_ing4: ImageView = _
  //Customer 1-4
  var imgvw_customer1: ImageView = _
  var imgvw_customer2: ImageView = _
  var imgvw_customer3: ImageView = _
  var imgvw_customer4: ImageView = _
  //speachbubble1
  var imgvw_speachbubble1: ImageView = _
  var imgvw_speachbubble1_ing1: ImageView = _
  var imgvw_speachbubble1_ing2: ImageView = _
  var imgvw_speachbubble1_ing3: ImageView = _
  var imgvw_speachbubble1_ing4: ImageView = _
  //speachbubble2
  var imgvw_speachbubble2: ImageView = _
  var imgvw_speachbubble2_ing1: ImageView = _
  var imgvw_speachbubble2_ing2: ImageView = _
  var imgvw_speachbubble2_ing3: ImageView = _
  var imgvw_speachbubble2_ing4: ImageView = _
  //speachbubble3
  var imgvw_speachbubble3: ImageView = _
  var imgvw_speachbubble3_ing1: ImageView = _
  var imgvw_speachbubble3_ing2: ImageView = _
  var imgvw_speachbubble3_ing3: ImageView = _
  var imgvw_speachbubble3_ing4: ImageView = _
  //speachbubble4
  var imgvw_speachbubble4: ImageView = _
  var imgvw_speachbubble4_ing1: ImageView = _
  var imgvw_speachbubble4_ing2: ImageView = _
  var imgvw_speachbubble4_ing3: ImageView = _
  var imgvw_speachbubble4_ing4: ImageView = _
  //ingrediants
  var imgvw_salami: ImageView = _
  var imgvw_paprika: ImageView = _
  var imgvw_champignon: ImageView = _
  var imgvw_cheese: ImageView = _
  var imgvw_onion: ImageView = _
  var imgvw_tomato: ImageView = _
  var imgvw_ham: ImageView = _
  var imgvw_tuna: ImageView = _
  //highscore && playername
  var highscore: Label = _
  var playerName: control.TextField = _

  /*
  alle speachbubbles
   */

  def set(cash: ImageView, pb: ProgressBar, pizzaList1: (ImageView, List[ImageView]), pizzaList2: (ImageView, List[ImageView]),
          pizzaList3: (ImageView, List[ImageView]), pizzaList4: (ImageView, List[ImageView]), customer: List[ImageView],
          speachbubble1: (ImageView, List[ImageView]), speachbubble2: (ImageView, List[ImageView]), speachbubble3: (ImageView, List[ImageView]),
          speachbubble4: (ImageView, List[ImageView]), ingrediants: List[ImageView], highscore: Label, playername: control.TextField) {
    //cashier
    this.cashier1 = cash
    CashierAnim.set(cash)
    //progressbar
    progressbar = pb
    //set pizza1
    imgvw_pizza1 = pizzaList1._1
    imgvw_pizza1_ing1 = pizzaList1._2(0)
    imgvw_pizza1_ing2 = pizzaList1._2(1)
    imgvw_pizza1_ing3 = pizzaList1._2(2)
    imgvw_pizza1_ing4 = pizzaList1._2(3)
    //set pizza2
    imgvw_pizza2 = pizzaList2._1
    imgvw_pizza2_ing1 = pizzaList2._2(0)
    imgvw_pizza2_ing2 = pizzaList2._2(1)
    imgvw_pizza2_ing3 = pizzaList2._2(2)
    imgvw_pizza2_ing4 = pizzaList2._2(3)
    //set pizza3
    imgvw_pizza3 = pizzaList3._1
    imgvw_pizza3_ing1 = pizzaList3._2(0)
    imgvw_pizza3_ing2 = pizzaList3._2(1)
    imgvw_pizza3_ing3 = pizzaList3._2(2)
    imgvw_pizza3_ing4 = pizzaList3._2(3)
    //set pizza4
    imgvw_pizza4 = pizzaList4._1
    imgvw_pizza4_ing1 = pizzaList4._2(0)
    imgvw_pizza4_ing2 = pizzaList4._2(1)
    imgvw_pizza4_ing3 = pizzaList4._2(2)
    imgvw_pizza4_ing4 = pizzaList4._2(3)
    //set customer
    imgvw_customer1 = customer(0)
    imgvw_customer2 = customer(1)
    imgvw_customer3 = customer(2)
    imgvw_customer4 = customer(3)
    //set speachbubble1
    imgvw_speachbubble1 = speachbubble1._1
    imgvw_speachbubble1_ing1 = speachbubble1._2(0)
    imgvw_speachbubble1_ing2 = speachbubble1._2(1)
    imgvw_speachbubble1_ing3 = speachbubble1._2(2)
    imgvw_speachbubble1_ing4 = speachbubble1._2(3)
    //set speachbubble2
    imgvw_speachbubble2 = speachbubble2._1
    imgvw_speachbubble2_ing1 = speachbubble2._2(0)
    imgvw_speachbubble2_ing2 = speachbubble2._2(1)
    imgvw_speachbubble2_ing3 = speachbubble2._2(2)
    imgvw_speachbubble2_ing4 = speachbubble2._2(3)
    //set speachbubble3
    imgvw_speachbubble3 = speachbubble3._1
    imgvw_speachbubble3_ing1 = speachbubble3._2(0)
    imgvw_speachbubble3_ing2 = speachbubble3._2(1)
    imgvw_speachbubble3_ing3 = speachbubble3._2(2)
    imgvw_speachbubble3_ing4 = speachbubble3._2(3)
    //set speachbubble4
    imgvw_speachbubble4 = speachbubble4._1
    imgvw_speachbubble4_ing1 = speachbubble4._2(0)
    imgvw_speachbubble4_ing2 = speachbubble4._2(1)
    imgvw_speachbubble4_ing3 = speachbubble4._2(2)
    imgvw_speachbubble4_ing4 = speachbubble4._2(3)
    //set ingrediants
    imgvw_salami = ingrediants(0)
    imgvw_paprika = ingrediants(1)
    imgvw_champignon = ingrediants(2)
    imgvw_cheese = ingrediants(3)
    imgvw_onion = ingrediants(4)
    imgvw_tomato = ingrediants(5)
    imgvw_ham = ingrediants(6)
    imgvw_tuna = ingrediants(7)
    //highscore setzen
    this.highscore = highscore
    //setzen des namen des spielers
    this.playerName = playerName
  }


  createCustomers(level)

  def editProgressbar() = {
    progressbar.setProgress(progressbar.getProgress + calcPorgressbar())
  }

  def resetProgressbar() = {
    progressbar.setProgress(1.0)
  }

  override def handle(now: Long): Unit = {
    val frameJump: Int = Math.floor((now - lastFrame) / (1000000000 / fps)).toInt //berechne ob genug Zeit vergangen ist um einen neuen Frame anzuzeigen
    if (frameJump > 1) {
      //neuen Frame berechnen
      //sind customers erstellt

      if (checkCustomersExist() == false) {
        createCustomers(level)
        //CustomerSpeechBubbleAnim.setOrder
      }


      if (checkIfGameover == true) {
        //spiel vorbei?
        //in datenbank speichern
        //ScalaJdbcSQL.setHighscoreList(playerName,score.toInt)
        stop()
      }

      //überprüfen ob alle customers bedient worden sind
      //einen customer auf happy setzen


      if (checkIfCustomersServed == true) {

        score = 4 + level * (System.nanoTime() / 1000000000 - timer) // 4 für customers + level*timeleft
        //reset timer
        level = level + 1
        createCustomers(level)
        //speechbubble neu setzten

        setTimer()
      }




      if (checkTimer == true) {
        //timer setzen
        setTimer()
        lives = lives - 1
        resetProgressbar()
      } else {
        editProgressbar()
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

  private def timeLeft(): Int = {
    val i = timer - System.nanoTime() / 1000000000
    i.toInt
  }

  private def calcPorgressbar(): Double = {
    val x = timeAndLevel()
    val y: Float = x / 100 // dann haben wir 1 %
    val progressbarChange = y * timeLeft
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
    } catch {
      case e => false
    }
  }


  def ingridientsButton(i: Int, selectedCustomer: Int) = {
    //add ingridient to working bench
    i match {
      case 1 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("salami")
        CashierAnim.setGoTo("salami")} //salami
      case 2 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("paprika")
        CashierAnim.setGoTo("paprika")} //paprika
      case 3 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("champignon")
        CashierAnim.setGoTo("champignon")}
      case 4 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("cheese")
        CashierAnim.setGoTo("cheese")} //cheese
      case 5 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("onion")
        CashierAnim.setGoTo("onion")} //onion
      case 6 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("tomato")
        CashierAnim.setGoTo("tomato")} //tomato
      case 7 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("ham")
        CashierAnim.setGoTo("ham")} //ham
      case 8 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("tuna")
        CashierAnim.setGoTo("tuna")} //tuna
      case _ => CashierAnim.setGoTo("standard") //back to standardposition
    }

    //set animation cashier??!?!?!?!


  }

  def getCraftingBenchForCustomer(selectedCustomer: Int): CraftingBench = {
    selectedCustomer match {
      case 1 => craB1
      case 2 => craB2
      case 3 => craB3
      case 4 => craB4
    }
  }

  def customerSelected(customer: Int): Unit = {
    customer match {
      case 1 => selectedCustomer = 1
      case 2 => selectedCustomer = 2
      case 3 => selectedCustomer = 3
      case 4 => selectedCustomer = 4
    }
  }


  def createCustomers(level: Int) = {
    //val positionAnimation: List[Int] = util.Random.shuffle((1 to 4).toSeq.toList)
    cus1 = new Customer(level)
    //println("CUSTOMER 1 FAGGOT" + cus1.getOrder())
    craB1 = new CraftingBench(cus1.getOrder())
    cus2 = new Customer(level)
    //println("CUSTOMER 2 DU NUTTE" + cus2.getOrder())
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
      if (n >= 4) {
        for (n <- 1 to 4) {
          finalIngridients ::= ingridients.toList((math.random * ingridients.size).toInt)
        }
      } else {
        for (n <- 1 to n) {
          finalIngridients ::= ingridients.toList((math.random * ingridients.size).toInt)
        }
      }
      finalIngridients
    }

    def getIngridients(): List[String] = finalIngridients
  }


  class Customer(level: Int) {
    val order = new Pizza(GameLoop.level).setPizzaObject()
    println("IM CUSTOMER" + order)

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