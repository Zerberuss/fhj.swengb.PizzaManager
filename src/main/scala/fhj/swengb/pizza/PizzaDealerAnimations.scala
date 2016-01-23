package fhj.swengb.pizza


/**
  * Created by Andreas on 23.01.2016.
  */

import javafx.animation.{PathTransition, AnimationTimer, FadeTransition}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path}
import javafx.util.Duration


case class CustomerAnim(){
  var customerNr = 1
  var obj:ImageView = _
  var customerImageList = List("")
  var customerAnim:ImageViewSprite = _

  def set(obj:ImageView, customerNr:Int) {
    this.obj = obj
    this.customerNr = customerNr
    this.customerImageList = List("/fhj/swengb/pizza/customer/customer"+customerNr+"_angry.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_angry_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_happy_glow.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_neutral.png",
      "/fhj/swengb/pizza/customer/customer"+customerNr+"_neutral_glow.png")

    this.customerAnim = new ImageViewSprite(obj, new Image(customerImageList(4)),1, 1, 1, 100, 200, 1)

    val fadetransition: FadeTransition = new FadeTransition(Duration.millis(100), obj)
    fadetransition.setFromValue(0)
    fadetransition.setToValue(1)
    fadetransition.playFromStart()
  }

  def setAngry() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(0)), 1, 1, 1, 100, 200, 1)
  }
  def setHappy() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(2)), 1, 1, 1, 100, 200, 1)
  }
  def setNeutral() {
    customerAnim = new ImageViewSprite(obj, new Image(customerImageList(3)), 1, 1, 1, 100, 200, 1)
  }
  def animate(now: Long) = {
    customerAnim.handle(now)
  }
}




object Cashier extends AnimationTimer{
  val frameWidth = 280
  val frameHeight = 280

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
    if(goingDown)  new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/pizza-dealer.png"), 4, 2, 8, frameWidth, frameHeight, 10)
    else new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/pizza-dealer2.png"), 4, 2, 8, frameWidth, frameHeight, 10)
  }

  private def createPath(pathToTarget: (Int,Int)): Path ={
    var path: Path = new Path()
    path.getElements.add(new MoveTo(this.lastPosition._1+frameWidth/2, this.lastPosition._2+frameHeight/2))
    path.getElements.add(new CubicCurveTo(this.lastPosition._1+frameWidth/2, this.lastPosition._2+frameHeight/2, pathToTarget._1, pathToTarget._2, pathToTarget._1, pathToTarget._2))
    path
  }

  def goTo(GoToName:String){

    if (GoToName!=lastGoTo) {   //Neue Pfad Animation nur dann ausführen, wenn sich das Ziel geändert hat
    var targetPos = (0,0)
      GoToName match {
        case "Customer1"    => targetPos =(400,0)
        case "Customer2"    => targetPos =(-400,0)
        case "Customer3"    => targetPos =(500,300)
        case "Customer4"    => targetPos =(-500,-300)

        case "Pilze"        => targetPos =(100,200)
        case "Tomatensoße"  => targetPos =(100,200)
        case "Käse"         => targetPos =(100,200)
        case "Paprika"      => targetPos =(100,200)
        case "Salami"       => targetPos =(100,200)
        case "Schinken"     => targetPos =(100,200)
        case "Thunfisch"    => targetPos =(100,200)
        case "Zwiebel"      => targetPos =(100,200)
        case "Teig"         => targetPos =(100,200)
      }
      if(targetPos._2 >= lastPosition._2) { dealerSpriteAnim = getSpriteAnimation(true) }  //Sprite Animation ändern, falls sich die laufrichtung ändert -> isGoingDown true
      else{ dealerSpriteAnim = getSpriteAnimation(false)}
      pathTrans.setPath(createPath(targetPos))
      pathTrans.playFromStart()
      lastGoTo=GoToName
    }
  }


  override def handle(now: Long): Unit = {
    if(lastPosition != (dealer.getTranslateX,dealer.getTranslateY)){
      this.dealerSpriteAnim.handle(now)
      lastPosition = (dealer.getTranslateX,dealer.getTranslateY)
      isStanding=false
    }else {
      if (!isStanding){       //nur einmal auf stehend zurücksetzen -> Ressourcen sparen, wenn er sich nicht bewegt
        dealerSpriteAnim = new ImageViewSprite(dealer, new Image("/fhj/swengb/pizza/sprites/pizza-dealer.png"), 4, 2, 8, frameWidth, frameHeight, 10)
        isStanding= true
      }
    }
  }
}


case class GameLoop(obj : ImageView) extends AnimationTimer{
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
      customer.animate(now)
      //menuDealerLogoAnim.handle(now)
    }

    //Nur zum testen:
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
