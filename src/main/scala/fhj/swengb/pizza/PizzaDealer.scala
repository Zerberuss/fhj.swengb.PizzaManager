package fhj.swengb.pizza

/*
TO-DO

in DB schreiben
Overlay für Game Over

checkCustomerSelected function OPTIMIEREN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


BUGS

#1

Wenn man ins nöchste level kommt verschwindet die Speachbubble vom selectedCustomer -> poof effekt falsch gesetzt?


#2
Es lass sich grafisch mehrere Ingredients auf zur Pizza hinzufügen obwohl!!! die Zutat schon oben liegt.
Iwie überprüfen welche Zutaten oben sind?  Get Funktion?



 */


import javafx.animation.AnimationTimer
import javafx.scene.control
import javafx.scene.control.{Label, ProgressBar}
import javafx.scene.image.ImageView

import fhj.swengb.pizza.PizzaDealer.{CraftingBench, Customer}


object GameLoop extends AnimationTimer {

  //nur als beispiel wie man einen Customer erstellt
  //var customer = new CustomerAnim()
  //customer.set(obj,2)
  //cashier erzeugen
  var myNow: Int = 0
  //wird nach Bedienung der 4 Kunden um 1 erhöht
  lazy val levelMax: Int = 4
  //wird um 1 verringert falls Zeit abgelaufen
  lazy val timeStatisch: Int = 32
  var level: Int = 1
  //max level
  var lives: Int = 3
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


  //var timer = Null
  var fps = 30
  var lastFrame = System.nanoTime
  var lastLogicFrame:Long = System.nanoTime()
  var name: String = ""
  var customer1Served: Boolean = false
  var customer2Served: Boolean = false
  var customer3Served: Boolean = false
  var customer4Served: Boolean = false
  var timer: Long = _
  //STUFF TO SET
  var progressbar: ProgressBar = _
  var cashier1: ImageView = _
  var cashier = CashierAnim
  //pizza1
  var imgvw_pizza1: (ImageView, List[ImageView]) = _
  //pizza2
  var imgvw_pizza2: (ImageView, List[ImageView]) = _
  //pizza3
  var imgvw_pizza3: (ImageView, List[ImageView]) = _
  //pizza4
  var imgvw_pizza4: (ImageView, List[ImageView]) = _
  //Customer 1-4
  var imgvw_customer1: ImageView = _
  var imgvw_customer2: ImageView = _
  var imgvw_customer3: ImageView = _
  var imgvw_customer4: ImageView = _
  //speachbubble1+
  var imgvw_speachbubble1: (ImageView, List[ImageView]) = _
  //speachbubble2
  var imgvw_speachbubble2: (ImageView, List[ImageView]) = _
  //speachbubble3
  var imgvw_speachbubble3: (ImageView, List[ImageView]) = _
  //speachbubble4
  var imgvw_speachbubble4: (ImageView, List[ImageView]) = _

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
  var lbl_lives: Label = _

  def set(cash: ImageView, pb: ProgressBar, pizzaList1: (ImageView, List[ImageView]), pizzaList2: (ImageView, List[ImageView]),
          pizzaList3: (ImageView, List[ImageView]), pizzaList4: (ImageView, List[ImageView]), customer: List[ImageView],
          speachbubble1: (ImageView, List[ImageView]), speachbubble2: (ImageView, List[ImageView]), speachbubble3: (ImageView, List[ImageView]),
          speachbubble4: (ImageView, List[ImageView]), ingrediants: List[ImageView], highscore: Label, playername: control.TextField, lives: Label) {
    //cashier
    this.cashier1 = cash
    CashierAnim.set(cash)
    //progressbar
    progressbar = pb
    //set pizza1
    imgvw_pizza1 = pizzaList1
    //set pizza2
    imgvw_pizza2 = pizzaList2
    //set pizza3
    imgvw_pizza3 = pizzaList3
    //set pizza4
    imgvw_pizza4 = pizzaList4
    //set customer
    imgvw_customer1 = customer(0)
    imgvw_customer2 = customer(1)
    imgvw_customer3 = customer(2)
    imgvw_customer4 = customer(3)
    //set speachbubble1
    imgvw_speachbubble1 = speachbubble1
    //set speachbubble2
    imgvw_speachbubble2 = speachbubble2
    //set speachbubble3
    imgvw_speachbubble3 = speachbubble3
    //set speachbubble4
    imgvw_speachbubble4 = speachbubble4

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
    this.lbl_lives = lives
  }


  override def handle(now: Long): Unit = {
    val frameJump: Int = Math.floor((now - lastFrame) / (1000000000 / fps)).toInt //berechne ob genug Zeit vergangen ist um einen neuen Frame anzuzeigen

    if (frameJump >= 1) {
      lastFrame = now


      var nowTemp:Long = System.nanoTime()
      myNow = myNow + math.floor(((System.nanoTime()-lastLogicFrame) / 1E6 )%1000).toInt
      lastLogicFrame = nowTemp

      //println("this is now: " + myNow) // ausgabe in milisek
      if (myNow > timeAndLevel() || myNow <= 0) //timer läuft noch
      {
        setTimer() //Timer neu setzen
        this.lives = this.lives - 1 //leben um 1 verringern da Zeit abgelaufen
        lbl_lives.setText("lives: "+this.lives.toString)
        createCustomers(this.level)
        resetProgressbar() //Progressbar wird wieder auf den Standardwert gesetzt (1.0)

      } else {
        editProgressbar(myNow)
      }



      //neuen Frame berechnen
      //sind customers erstellt

      if (checkCustomersExist() == false) {
        setTimer()
        createCustomers(level)
        lbl_lives.setText("lives: "+lives.toString)
      }




      if (checkIfGameover == true) {
        //spiel vorbei?
        //in datenbank speichern
        writeToDatabase()

        stop()
      }

      //überprüfen ob alle customers bedient worden sind
      //einen customer auf happy setzen

      checkIfSelectedCustomerServed()
      if (checkIfCustomersServed == true) {

        score = score + 4 * level*2 // 4 für customers + level*timeleft
        //println("in der if   ---- "+level)

        highscore.setText("Highscore: " + score.toString)
        //reset timer
        level = level + 1
        print(level)
        createCustomers(this.level)
        setTimer()
      }

      lastLogicFrame += 1


      cus1.appearence.handle(now)
      cus2.appearence.handle(now)
      cus3.appearence.handle(now)
      cus4.appearence.handle(now)

      cashier.handle(now)
    }



    //Nur zum testen des Cashiers:
    if (lastLogicFrame == 50) {

      lastLogicFrame = 0
    }


  }


  //allgemeine setter funktion für alle objekte

  private def writeToDatabase():Unit = {
    try {
      ScalaJdbcSQL.connectToDatabase
      ScalaJdbcSQL.setHighscoreList(this.playerName.getText, this.score.toInt)
      ScalaJdbcSQL.closeConnection
    }
    catch {
      case noConnection => println("Could not connect to Database")
    }

  }


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
      cus1.poof()
      cus2.poof()
      cus3.poof()
      cus4.poof()
      true
    }
    else {
      false
    }
  }


  private def timeAndLevel(): Long = {
    //returns time for timer
    val x= timeStatisch - level
    x * 1000 //in milisec
  }

  private def checkCustomersExist(): Boolean = {
    try {
      cus1.getOrder()
      true
    } catch {
      case e => false
    }
  }

  private def editProgressbar(myNow: Double) = {

    progressbar.setProgress(1 - calcPorgressbar(myNow))


  }

  private def calcPorgressbar(myNow: Double): Double = {

    myNow / timeAndLevel()

  }


  private def setTimer() {
    myNow = 0
  }


  private def resetProgressbar() = {
    progressbar.setProgress(1.0)
  }

  def createCustomers(level: Int) = {

    cus1 = new Customer(level)
    craB1 = new CraftingBench(cus1.getOrder())
    cus2 = new Customer(level)
    craB2 = new CraftingBench(cus2.getOrder())
    cus3 = new Customer(level)
    craB3 = new CraftingBench(cus3.getOrder())
    cus4 = new Customer(level)
    craB4 = new CraftingBench(cus4.getOrder())

    cus1.setPizza(1)
    cus2.setPizza(2)
    cus3.setPizza(3)
    cus4.setPizza(4)

    cus1.setSpeachBubble(1)
    cus2.setSpeachBubble(2)
    cus3.setSpeachBubble(3)
    cus4.setSpeachBubble(4)

    cus1.showPizza()
    cus2.showPizza()
    cus3.showPizza()
    cus4.showPizza()

    cus1.setCustomerAppearence(1)
    cus2.setCustomerAppearence(2)
    cus3.setCustomerAppearence(3)
    cus4.setCustomerAppearence(4)

    customer1Served = false
    customer2Served = false
    customer3Served = false
    customer4Served = false
  }

  def ingridientsButton(i: Int, selectedCustomer: Int) = {
    //add ingridient to working bench
    i match {
      case 1 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("salami")
        cashier.setGoTo("salami", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("salami")
      } //salami
      case 2 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("paprika")
        cashier.setGoTo("paprika", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("paprika")
      } //paprika
      case 3 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("champignon")
        cashier.setGoTo("champignon", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("champignon")
      }
      case 4 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("cheese")
        cashier.setGoTo("cheese", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("cheese")
      } //cheese
      case 5 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("onion")
        cashier.setGoTo("onion", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("onion")
      } //onion
      case 6 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("tomato")
        cashier.setGoTo("tomato", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("tomato")
      } //tomato
      case 7 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("ham")
        cashier.setGoTo("ham", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("ham")
      } //ham
      case 8 => {
        getCraftingBenchForCustomer(selectedCustomer).addIngridientToCraftingBench("tuna")
        cashier.setGoTo("tuna", "customer" + selectedCustomer)
        customerSelected(selectedCustomer).addIngridedientsToPizza("tuna")/*FOR JOE
        HappyCustomer ohne Glowing
        Overlay 4 Gameover*/
      } //tuna
      case _ => {
        cashier.setGoTo("standard", "customer" + selectedCustomer)
      } //back to standardposition
    }
  }

  def getCraftingBenchForCustomer(selectedCustomer: Int): CraftingBench = {
    selectedCustomer match {
      case 1 => craB1
      case 2 => craB2
      case 3 => craB3
      case 4 => craB4
    }
  }

  def customerSelected(customer: Int): Customer = {
    customer match {
      case 1 => {
        if (customer1Served) {
          cus1.setGlowing(false)
          cus1.setHappy()
        } else {
          cus1.setGlowing(true)

        }
        cus2.setGlowing(false)
        cus3.setGlowing(false)
        cus4.setGlowing(false)
        cus2.setNeutral()
        cus3.setNeutral()
        cus4.setNeutral()
        selectedCustomer = 1
        cus1.setHappy()

        cus1
      }
      case 2 => {
        if (customer2Served) {

          cus2.setGlowing(false)
          cus2.setHappy()
        } else {

          cus2.setGlowing(true)
        }
        cus1.setGlowing(false)
        cus3.setGlowing(false)
        cus4.setGlowing(false)
        cus1.setNeutral()
        cus3.setNeutral()
        cus4.setNeutral()
        selectedCustomer = 2
        cus2.setHappy()
        cus2
      }
      case 3 => {
        if (customer3Served) {

          cus3.setGlowing(false)
          cus3.setHappy()
        } else {

          cus3.setGlowing(true)

        }
        cus1.setGlowing(false)
        cus2.setGlowing(false)
        cus4.setGlowing(false)
        cus2.setNeutral()
        cus1.setNeutral()
        cus4.setNeutral()
        selectedCustomer = 3
        cus3.setHappy()
        cus3
      }
      case 4 => {
        if (customer4Served) {

          cus4.setGlowing(false)
          cus4.setHappy()
        } else {

          cus4.setGlowing(true)

        }
        cus1.setGlowing(false)
        cus2.setGlowing(false)
        cus3.setGlowing(false)
        cus1.setNeutral()
        cus2.setNeutral()
        cus3.setNeutral()
        selectedCustomer = 4
        cus4.setHappy()
        cus4
      }
    }
  }

  def checkIfSelectedCustomerServed(): Boolean = {
    selectedCustomer match {
      case 1 => {
        if (cus1.getOrder() == craB1.getAddedIngridients) {
          customer1Served = true
          cus1.setGlowing(false)
          cus1.poof()
          cus1.removeSpeachBubble(1)
          true
        }
        else false
      }
      case 2 => {
        if (cus2.getOrder() == craB2.getAddedIngridients) {
          customer2Served = true
          cus2.setGlowing(false)
          cus2.poof()
          cus2.removeSpeachBubble(2)
          true
        }
        else false
      }
      case 3 => {
        if (cus3.getOrder() == craB3.getAddedIngridients) {
          customer3Served = true
          cus3.setGlowing(false)
          cus3.poof()
          cus3.removeSpeachBubble(3)
          true
        }
        else false
      }
      case 4 => {
        if (cus4.getOrder() == craB4.getAddedIngridients) {
          customer4Served = true
          cus4.setGlowing(false)
          cus4.poof()
          cus4.removeSpeachBubble(4)
          true
        }
        else false
      }
    }
  }
}


object PizzaDealer {


  class Pizza(n: Int) {
    //n = number of ingridients
    val ingredients = List("champignon", "cheese", "ham", "onion", "paprika", "salami", "tomato", "tuna")
    var finalIngredients = List[String]()

    def setPizzaObject(): List[String] = {
      val shuffeld = scala.util.Random.shuffle(ingredients)
      if (n >= 4) {
        for (n <- 1 to 4) {
          finalIngredients ::= shuffeld(n-1)
        }
      } else {
        for (n <- 1 to n) {
          finalIngredients ::= shuffeld(n-1)
        }
      }
      finalIngredients
    }

    def getIngridients(): List[String] = finalIngredients
  }


  class Customer(level: Int) {
    val order = new Pizza(GameLoop.level).setPizzaObject()
    val bubble = new CustomerSpeechBubbleAnim
    val appearence = new CustomerPersonAnim
    val pizzaAnim = new PizzaAnim
    //println("IM CUSTOMER" + order)

    def getOrder(): List[String] = order.sorted

    def setSpeachBubble(i: Int): Unit = {
      i match {
        case 1 => {
          this.bubble.set(GameLoop.imgvw_speachbubble1._1, GameLoop.imgvw_speachbubble1._2)
          this.bubble.setOrder(order)
        }
        case 2 => {
          this.bubble.set(GameLoop.imgvw_speachbubble2._1, GameLoop.imgvw_speachbubble2._2)
          this.bubble.setOrder(order)
        }
        case 3 => {
          this.bubble.set(GameLoop.imgvw_speachbubble3._1, GameLoop.imgvw_speachbubble3._2)
          this.bubble.setOrder(order)
        }
        case 4 => {
          this.bubble.set(GameLoop.imgvw_speachbubble4._1, GameLoop.imgvw_speachbubble4._2)
          this.bubble.setOrder(order)
        }
      }
    }

    def removeSpeachBubble(i:Int):Unit ={
      this.bubble.goAway()
    }


    def setCustomerAppearence(i: Int): Unit = {
      //i ist der customer        zufallszahl
      val positionAnimation: List[Int] = util.Random.shuffle((1 to 3).toSeq.toList)
      i match {
        case 1 => {
          this.appearence.set(GameLoop.imgvw_customer1, positionAnimation(0))
          this.appearence.setNeutral()
        }
        case 2 => {
          this.appearence.set(GameLoop.imgvw_customer2, positionAnimation(1))
          this.appearence.setNeutral()
        }
        case 3 => {
          this.appearence.set(GameLoop.imgvw_customer3, positionAnimation(2))
          this.appearence.setNeutral()
        }
        case 4 => {
          this.appearence.set(GameLoop.imgvw_customer4, positionAnimation(0))
          this.appearence.setNeutral()
        }
      }
    }

    def poof() = this.appearence.setGone()

    def setAngry() = this.appearence.setAngry()

    def setHappy() = this.appearence.setHappy()

    def setGlowing(bool: Boolean) = this.appearence.setGlowing(bool)

    def setNeutral() = this.appearence.setNeutral()

    def resetPizza() = this.pizzaAnim.reset()

    def setPizza(i: Int) = {
      i match {
        case 1 => this.pizzaAnim.set(GameLoop.imgvw_pizza1._1, GameLoop.imgvw_pizza1._2)
        case 2 => this.pizzaAnim.set(GameLoop.imgvw_pizza2._1, GameLoop.imgvw_pizza2._2)
        case 3 => this.pizzaAnim.set(GameLoop.imgvw_pizza3._1, GameLoop.imgvw_pizza3._2)
        case 4 => this.pizzaAnim.set(GameLoop.imgvw_pizza4._1, GameLoop.imgvw_pizza4._2)
      }

    }

    def showPizza() = this.pizzaAnim.showPizza()

    def addIngridedientsToPizza(ingredient: String) = {
      if (order.contains(ingredient)) {

        this.pizzaAnim.addIngredient(ingredient)
      }
    }

  }


  class CraftingBench(order: List[String]) {
    var ingridientsAdded = List[String]()

    def addIngridientToCraftingBench(ingredient: String): List[String] = {
      if (order.contains(ingredient)) {
        //wird auf Happy gesetzt
        GameLoop.customerSelected(GameLoop.selectedCustomer).setHappy()
        ingridientsAdded ::= ingredient
        ingridientsAdded
      }
      else {
        //Customer auf Angry
        GameLoop.customerSelected(GameLoop.selectedCustomer).setAngry()
        ingridientsAdded
      }
    }

    def getAddedIngridients: List[String] = ingridientsAdded.sorted
  }

}
