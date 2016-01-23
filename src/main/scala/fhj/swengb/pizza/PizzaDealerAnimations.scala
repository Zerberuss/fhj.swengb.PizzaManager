package fhj.swengb.pizza


/**
  * Created by Andreas on 23.01.2016.
  */

import javafx.animation.{PathTransition, AnimationTimer, FadeTransition}
import javafx.scene.image.{ImageView, Image}
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path}
import javafx.util.Duration

import fhj.swengb.pizza.PizzaDealer.Pizza

/** Sprechblasen animieren
  *
  * Setzt die Ingredients, des Orders, in die Sprechblase des Customers
  *
  * set -> Sprechblase erstellen
  * setOrder -> Sprechblase befüllen -> Ingredients als list(String) übergeben
  */
case class CustomerSpeechBubbleAnim(){
  var obj:ImageView = _
  var ingredientsObj:List[ImageView] = List()
  //val centerOfIngredients = (100,100)

  def set(bubbleObj:ImageView,ingredientsObj:List[ImageView],customerPos:(Int,Int)): Unit = {
    this.obj=bubbleObj
    this.obj.setImage(new Image("/fhj/swengb/pizza/customer/bubble.png"))
    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()

    this.ingredientsObj = ingredientsObj
  }

  def setOrder(ingredients:List[String]): Unit ={
    ingredientsObj.foreach(obj=>obj.setVisible(false))
    ingredients.indices.foreach(index => {
      ingredientsObj(index).setImage(new Image("/fhj/swengb/pizza/ingredients/" + ingredients(index) + ".png"))

      val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
      fadetransition.setFromValue(0)
      fadetransition.setToValue(1)
      fadetransition.playFromStart()
      ingredientsObj(index).setVisible(true)
    })
  }
}

/**Animation der Customer
  *
  * set: Neuen Customer erstellen
  *       obj: ImageView aus der FXML
  *       customerPos: (x,y) Koordinate des Customers
  *       customerNr: welches aussehen hat der Customer
  */
case class CustomerPersonAnim(){
  var customerNr = 1
  var obj:ImageView = _
  var customerImageList = List("")
  var customerAnim:ImageViewSprite = _

  def set(obj:ImageView, customerPos:(Int,Int), customerNr:Int) {
    this.obj = obj
    this.customerNr = customerNr
    this.customerImageList = List("/fhj/swengb/pizza/customer/customer"+customerNr+"_angry.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_angry_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_happy_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_neutral.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_neutral_glow.png")

    this.customerAnim = new ImageViewSprite(obj, new Image(customerImageList(3)),1, 1, 1, 100, 200, 1)
    this.obj.setTranslateX(customerPos._1)
    this.obj.setTranslateY(customerPos._2)
    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()
  }

  def setAngry() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(0)), 1, 1, 1, 100, 200, 1)
  }
  def setAngryGlowing() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(1)), 1, 1, 1, 100, 200, 1)
  }
  def setHappyGlowing() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(2)), 1, 1, 1, 100, 200, 1)
  }
  def setNeutral() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(3)), 1, 1, 1, 100, 200, 1)
  }
  def setNeutralGlowing() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(4)), 1, 1, 1, 100, 200, 1)
  }
  def handle(now: Long) = {
    customerAnim.handle(now)
  }
}


/**Animation des Cashiers
  *
  * Lässt den Cashier herumlaufen
  *
  * set:  erstellen des Cashiers
  * setGoTo : lässt ihn zu einem Ziel laufen
  * handle: -> animation ausführen
  */
object CashierAnim {
  val frameWidth = 100
  val frameHeight = 200

  var dealer: ImageView = _
  var dealerSpriteAnim:ImageViewSprite = _
  var pathTrans: PathTransition = _
  var lastPosition = (1:Double,1:Double)
  var lastGoTo = ""
  var isStanding=false
  var speed = 1000

  def set(obj: ImageView,speed:Int = 1000) = {
    this.speed = speed
    this.dealer = obj
    dealerSpriteAnim = getSpriteAnimation(true)
    pathTrans = new PathTransition()
    pathTrans.setDuration(new Duration(speed))
    pathTrans.setNode(this.dealer)
    pathTrans.setAutoReverse(false)
    lastPosition = (dealer.getTranslateX,dealer.getTranslateY)
  }

  private def getSpriteAnimation(goingDown:Boolean):ImageViewSprite  = {
    if(goingDown)  new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/cashier_down.png"), 3, 1, 3, frameWidth, frameHeight, 6)
    else new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/cashier_up.png"), 3, 1, 3, frameWidth, frameHeight, 6)
  }

  private def createPath(pathToTarget: (Int,Int)): Path ={
    var path: Path = new Path()
    path.getElements.add(new MoveTo(this.lastPosition._1+frameWidth/2, this.lastPosition._2+frameHeight/2))
    path.getElements.add(new CubicCurveTo(this.lastPosition._1+frameWidth/2, this.lastPosition._2+frameHeight/2, pathToTarget._1, pathToTarget._2, pathToTarget._1, pathToTarget._2))
    path
  }

  def setGoTo(GoToName:String){

    if (GoToName!=lastGoTo) {   //Neue Pfad Animation nur dann ausführen, wenn sich das Ziel geändert hat
    var targetPos = (0,0)
      GoToName match {
        case "Customer1"    => targetPos =(400,0)
        case "Customer2"    => targetPos =(-400,0)
        case "Customer3"    => targetPos =(500,300)
        case "Customer4"    => targetPos =(-500,-300)

        case "salami"       => targetPos =(100,200)
        case "paprika"      => targetPos =(100,200)
        case "champignon"   => targetPos =(100,200)
        case "cheese"       => targetPos =(100,200)
        case "onion"        => targetPos =(100,200)
        case "tomato"       => targetPos =(100,200)
        case "ham"          => targetPos =(100,200)
        case "tuna"         => targetPos =(100,200)
        case _              => targetPos =(570,360)
      }
      if(targetPos._2 >= lastPosition._2) { dealerSpriteAnim = getSpriteAnimation(true) }  //Sprite Animation ändern, falls sich die laufrichtung ändert -> isGoingDown true
      else{ dealerSpriteAnim = getSpriteAnimation(false)}
      pathTrans.setPath(createPath(targetPos))
      pathTrans.playFromStart()
      lastGoTo=GoToName
    }
  }


  def handle(now: Long): Unit = {
    if(lastPosition != (dealer.getTranslateX,dealer.getTranslateY)){
      this.dealerSpriteAnim.handle(now)
      lastPosition = (dealer.getTranslateX,dealer.getTranslateY)
      isStanding=false
    }else {
      if (!isStanding){       //nur einmal auf stehend zurücksetzen -> Ressourcen sparen, wenn er sich nicht bewegt
        dealerSpriteAnim = new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/cashier-down.png"), 4, 2, 8, frameWidth, frameHeight, 10)
        isStanding= true
      }
    }
  }
}



object menuDealerLogoAnim extends AnimationTimer{
  var dealer: ImageViewSprite = _
  var status: Int = 0
  var obj:ImageView = new ImageView()

  def set(dealer: ImageView) = {
    obj = dealer
    this.dealer = new ImageViewSprite(obj, new Image("/fhj/swengb/pizza/sprites/pizza-dealer.png"), 4, 2, 8, 280, 280, 10)
    status = 0
  }

  override def handle(now: Long): Unit = {
    this.dealer.handle(now)
    if (status != 3) {
      if (status == 0) {
        obj.setTranslateX(obj.getTranslateX - 5)
        if (obj.getTranslateX < -150) status = 1
      } else {
        obj.setTranslateX(obj.getTranslateX + 5)
        if (obj.getTranslateX > 150)  status = 0
      }
    }
  }
}