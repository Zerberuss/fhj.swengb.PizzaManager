package fhj.swengb.pizza


import javafx.animation.AnimationTimer
import javafx.scene.image.ImageView

import scala.util.Random.nextInt



case class GameLoop(obj : ImageView) extends AnimationTimer{

  //nur als beispiel wie man einen Customer erstellt
  var customer = new CustomerAnim()
  customer.set(obj,2)

  var fps = 100
  var lastFrame = System.nanoTime
  var lastLogicFrame = 0



  override def handle(now: Long): Unit = {
    val frameJump: Int = Math.floor((now - lastFrame) / (1000000000 / fps)).toInt       //berechne ob genug Zeit vergangen ist um einen neuen Frame anzuzeigen
    if (frameJump > 1) {                                                                //neuen Frame berechnen
      lastFrame = now
      lastLogicFrame += 1
      customer.handle(now)
      //menuDealerLogoAnim.handle(now)
    }

    //Nur zum testen des Customers:
    if (lastLogicFrame == 50) {
      customer.setAngry()

    }else  if (lastLogicFrame == 100) {
      customer.setHappy()

    }else if (lastLogicFrame > 150){
      customer.setNeutral()

      lastLogicFrame=0
    }
  }
}


object PizzaDealer {


  var level: Int = 1
  //wird nach Bedienung der 4 Kunden um 1 erhöht
  val levelMax: Int = 8
  //max level
  val lives: Int = 3
  //wird um 1 verringert falls ein fehler gemacht wird
  val timeStatisch: Int = 16
  // in sec
  var i: Int = 1
  // falls maxlevel erreicht ist wird die übrige Zeit ZUSÄTZLICH noch um diesen wert verringert     der wert erhöht sich dannach
  var timeLeft: Int = 0


  def timeAndLevel(): Int = {
    //returns time for timer

    if (level == levelMax) {
      timeLeft = timeStatisch - level - i
      i = i + 1
      if(timeLeft<=0) return 1
    } else {
      timeLeft = timeStatisch - level
      level = level + 1

    }
    timeLeft
  }






  class Pizza(n: Int) {
    //n = number of ingridients
    val ingridients = List("Pilze", "Tomatensoße", "Käse", "Paprika", "Salami", "Schinken", "Thunfisch", "Zwiebel")
    var finalIngridients = List[String]()

    def setPizzaObject(): List[String] = {
      val intList = getRandomNumbers(n, ingridients.length)
      for (i <- 0 to intList.length - 1) {
        finalIngridients ::= ingridients(intList(i))
      }
      finalIngridients ::= "Teig"
      finalIngridients
    }

    def getRandomNumbers(n: Int, maxIng: Int): List[Int] = {
      val intList = Stream.continually(nextInt(maxIng)).take(n).toList
      intList
    }

    def getIngridients(): List[String] = finalIngridients


  }


  class Customer(level: Int) {

    val order = new Pizza(level)
    val workingbench = new CraftingBench

    def setOrder(order: Pizza) = this.order.setPizzaObject() //+sprechblase setzen

    def getOrder(): Pizza = order

    def addIngriedientToBench(ingriedientToAdd: String) {
      if (order.getIngridients contains ingriedientToAdd) {
        workingbench.addIngridientToCraftingBench(ingriedientToAdd)
        true
      } else {
        println("ob du behindert bist kannst du nicht die richtigen ingridents wählen du faggot?")
        false //evt negative rückmeldung an den spieler
      }
    }
    setOrder(order)
  }


  class CraftingBench() {
    var ingridientsAdded = List[String]()


    def addIngridientToCraftingBench(ingridient: String): List[String] = {
      ingridientsAdded ::= ingridient
      ingridientsAdded
    }

    def getAddedIngridients: List[String] = ingridientsAdded

    //add Zutat   getZutaten


  }

  class CustomerDisplay() {
    //get Zutaten
    //set display

  }

  class Cashier {


  }

  class Player  {

  }

  /*
    class Highscore(s:Int) {


      def setHighscore
      def getHighscore
      def addtohighscorelist
      def increaseHighscore
    }
  */
}