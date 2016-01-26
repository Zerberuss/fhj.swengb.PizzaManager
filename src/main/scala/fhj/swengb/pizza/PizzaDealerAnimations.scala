package fhj.swengb.pizza


/**
  * Created by Andreas on 23.01.2016.
  */

import javafx.animation.{AnimationTimer, FadeTransition, PathTransition}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path}
import javafx.util.Duration


/** Pizza erstellen und belegen
  *
  * set: ImageViews setzen
  * showPizza: Pizza anzeigen
  *
  * addIngredient Neuen Belag hinzufügen
  *
  * reset: alles ausblenden
  */

case class PizzaAnim(){
  var obj:ImageView = _
  var ingredientsAdded: Int = 0
  var ingredientsObj:List[ImageView] = List()

  def set (pizzaObj:ImageView,ingredientsObj:List[ImageView]): Unit = {
    this.obj = pizzaObj
    this.obj.setImage(new Image("/fhj/swengb/pizza/images/gamePane_pizza.png"))
    this.obj.setVisible(false)

    this.ingredientsObj = ingredientsObj
    ingredientsObj.foreach( ing => ing.setVisible(false))
    reset()
  }

  def showPizza(): Unit ={
    if(!ingredientsObj(ingredientsAdded).isVisible) ingredientsObj(ingredientsAdded).setVisible(true)
    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()
  }

  def addIngredient(ingredient:String): Unit ={
    if(!ingredientsObj(ingredientsAdded).isVisible) ingredientsObj(ingredientsAdded).setVisible(true)

    ingredientsObj(ingredientsAdded).setImage(new Image("/fhj/swengb/pizza/images/ingredients/" + ingredient + ".png"))

    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), ingredientsObj(ingredientsAdded))
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()

    ingredientsAdded+=1
  }

  def reset(): Unit ={
    ingredientsObj.indices.foreach(index => {
      val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), ingredientsObj(index))
      fadetransition.setFromValue(1)
      fadetransition.setToValue(0)
      fadetransition.playFromStart()
  })
    ingredientsAdded = 0

    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
    fadetransition.setFromValue(1)
    fadetransition.setToValue(0)
    fadetransition.playFromStart()
  }



}

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

  def set(bubbleObj:ImageView,ingredientsObj:List[ImageView]): Unit = {
    this.obj=bubbleObj
    this.obj.setImage(new Image("/fhj/swengb/pizza/images/speechbubble.png"))
    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()

    this.ingredientsObj = ingredientsObj
  }

  def setOrder(ingredients:List[String]): Unit ={
    ingredientsObj.foreach(obj=>obj.setVisible(false))
    ingredients.indices.foreach(index => {
      ingredientsObj(index).setImage(new Image("/fhj/swengb/pizza/images/ingredients/" + ingredients(index) + ".png"))

      val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), ingredientsObj(index))
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
  *       customerNr: welches aussehen hat der Customer
  */
case class CustomerPersonAnim(){
  var customerNr = 1
  var obj:ImageView = _
  var customerImageList = List("")
  var customerAnim:ImageViewSprite = _
  var status = "normal"
  var isGlowing = false

  def set(obj:ImageView, customerNr:Int) {
    this.obj = obj
    this.customerNr = customerNr
    this.customerImageList = List("/fhj/swengb/pizza/customer/customer"+customerNr+"_angry.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_angry_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_happy_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_neutral.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_neutral_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_happy.png")

    this.customerAnim = new ImageViewSprite(obj, new Image(customerImageList(3)),1, 1, 1, 100, 200, 1)

    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), this.obj)
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()
  }

  private def setStatus(): Unit ={
    if (!isGlowing) {
      status match {
        case "normal" =>   customerAnim = new ImageViewSprite(obj, new Image(customerImageList(3)), 1, 1, 1, 100, 200, 1)
        case "angry" =>    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(0)), 1, 1, 1, 100, 200, 1)
        case "happy" =>     customerAnim = new ImageViewSprite(obj, new Image(customerImageList(5)), 1, 1, 1, 100, 200, 1) // noch falsch!
      }
    }
    else{
      status match {
        case "normal" =>   customerAnim = new ImageViewSprite(obj, new Image(customerImageList(4)), 1, 1, 1, 100, 200, 1)
        case "angry" =>    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(1)), 1, 1, 1, 100, 200, 1)
        case "happy" =>    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(2)), 1, 1, 1, 100, 200, 1)
      }
    }
  }

  def setVisible(visible:Boolean) {
    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), this.obj)
    if (visible){
      fadetransition.setFromValue(0)
      fadetransition.setToValue(1)
    }else{
      fadetransition.setFromValue(1)
      fadetransition.setToValue(0)
    }
    fadetransition.playFromStart()
  }

  def setGlowing(glow:Boolean){
    isGlowing=glow
    setStatus()
  }

  def setAngry() {
   status = "angry"
    setStatus()
  }
  def setHappy() {
   status = "happy"
    setStatus()
  }
  def setNeutral() {
    status = "normal"
    setStatus()
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
        case "Customer1"    => targetPos =(-239,50)
        case "Customer2"    => targetPos =(-37,50)
        case "Customer3"    => targetPos =(155,50)
        case "Customer4"    => targetPos =(360,50)

        case "salami"       => targetPos =(-460,120)
        case "paprika"      => targetPos =(-330,120)
        case "champignon"   => targetPos =(-180,120)
        case "cheese"       => targetPos =(-20,120)
        case "onion"        => targetPos =(150,120)
        case "tomato"       => targetPos =(300,120)
        case "ham"          => targetPos =(450,120)
        case "tuna"         => targetPos =(600,120) //600,120
        case _              => targetPos =(60,100)
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
        dealerSpriteAnim = new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/cashier_down.png"), 3, 1, 3, frameWidth, frameHeight, 6)
        isStanding= true
      }
    }
  }
}


/**Logo Animation
  *
  *
  */
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
