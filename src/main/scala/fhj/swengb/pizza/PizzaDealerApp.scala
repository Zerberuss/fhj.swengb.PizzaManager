package fhj.swengb.pizza

import javafx.animation._
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene._
import javafx.scene.control.ProgressBar
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.AnchorPane
import javafx.scene.shape.{Circle, CubicCurveTo, MoveTo, Path}
import javafx.stage.Stage
import javafx.util.Duration

import scala.util.control.NonFatal


object PizzaDealerApp {
  def main(args: Array[String]) {
    Application.launch(classOf[PizzaDealerApp], args: _*)
  }
}


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
    this.dealerSpriteAnim = new ImageViewSprite(obj, new Image("/fhj/swengb/pizza/sprites/pizza-dealer.png"), 4, 2, 8, frameWidth, frameHeight, 10)
    pathTrans = new PathTransition()
    pathTrans.setDuration(new Duration(speed))
    pathTrans.setNode(this.dealer)
    pathTrans.setAutoReverse(false)
    lastPosition = (dealer.getTranslateX,dealer.getTranslateY)
  }

  private def createPath(pathToTarget: (Int,Int)): Path ={
    var path: Path = new Path()
    //    path.getElements.add(new MoveTo(this.dealer.getTranslateX+frameWidth/2, this.dealer.getTranslateY+frameHeight/2))
    //    path.getElements.add(new CubicCurveTo(this.dealer.getTranslateX+frameWidth/2, this.dealer.getTranslateY+frameHeight/2, xTarget, yTarget, xTarget, yTarget))

    path.getElements.add(new MoveTo(this.lastPosition._1+frameWidth/2, this.lastPosition._2+frameHeight/2))
    path.getElements.add(new CubicCurveTo(this.lastPosition._1+frameWidth/2, this.lastPosition._2+frameHeight/2, pathToTarget._1, pathToTarget._2, pathToTarget._1, pathToTarget._2))
    path
  }

  def goTo(GoToName:String){

    if (GoToName!=lastGoTo) {   //Neue Animation nu dann auführen, wenn sich das Ziel ändert

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



class PizzaDealerApp extends javafx.application.Application {
  val Css = "/fhj/swengb/pizza/PizzaDealer.css"
  val Fxml = "/fhj/swengb/pizza/PizzaDealer.fxml"


  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("Pizza Dealer")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent]) //loads the default scene
      stage.setScene(scene)
      stage.setResizable(false) //window cannot be rescaled
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
}



//controller contains the description of the functionality of the application
class PizzaDealerAppController extends PizzaDealerApp {
  //attributes are being initialized (everything with an ID

  //Main Pane
  @FXML var menu: AnchorPane = _
  //Settings Pane for Muliplayer
  @FXML var highscoresMenu: AnchorPane = _
  //Settings Pane for SinglePlayer
  @FXML var gameMenu: AnchorPane = _
  //game Pane where the game is played (both mp and sp)
  @FXML var gamePane: AnchorPane = _
  //Highscores Pane
  @FXML var highscoresPane: AnchorPane = _


  @FXML var highscoreAvatar: ImageView = _
  @FXML var playerAvatar: ImageView = _

  @FXML var playerName: control.TextField = _


  @FXML var status: control.Label = _
  @FXML var headline: control.Label = _
  @FXML var winPane: AnchorPane = _
  @FXML var winStatus: control.Label = _

  @FXML var progressBarTest: ProgressBar = _
  @FXML var logoAnimationImageView: ImageView = _

  var circles: Seq[Circle] = null





  //initialize function executes the commands at startup for the main scene

  //animation for Menue slide ins and outs
  def animMenuPanes(obj: AnchorPane, slideRight: Boolean, xMitte: Int = 400, yMitte: Int = 720/2): Unit = {  //1280/2

    var xEnde = 1300
    var path: Path = new Path()

    if (slideRight) {
      path.getElements.add(new MoveTo(xMitte, yMitte))
      path.getElements().add(new CubicCurveTo(xMitte + 50, yMitte, xMitte + 200, yMitte, xEnde+xMitte, yMitte))
    } else {
      path.getElements.add(new MoveTo(xEnde+xMitte, yMitte))
      path.getElements().add(new CubicCurveTo(xMitte + 200, yMitte, xMitte + 50, yMitte, xMitte, yMitte))
    }

    var pathTrans: PathTransition = new PathTransition()
    pathTrans.setDuration(new Duration(200))
    pathTrans.setNode(obj)
    pathTrans.setPath(path)
    pathTrans.setAutoReverse(false)
    pathTrans.play()
  }


  def startHighscoresPane(): Boolean = {
    animMenuPanes(highscoresPane, false, 1280/2+5)
    animMenuPanes(highscoresMenu, true)
  true
  }


  def startSinglePlayer(playerName: String): Boolean = {
    status.setText("Play a little game:")
    animMenuPanes(gameMenu, true)
    animMenuPanes(gamePane, false, 1280/2+5)
    true
  }


  def highscoresMenuBack(): Unit = animMenuPanes(highscoresMenu, true)

  //Hier die richtigen User Infos übergeben! -> aus tabelle oda ka wie ihr sie gespeichert habts
  def gameMenuBack(): Unit = animMenuPanes(gameMenu, true)

  def highscoresMenuStart(): Unit = logoAnim.goTo("Customer3") //animMenuPanes(highscoresMenu, false)

  def gameMenuStart(): Unit = logoAnim.goTo("Customer2") //animMenuPanes(gameMenu, false)

  def highscoresStart(): Unit = {
    startHighscoresPane()
  }

  def gameStart(): Unit = {

     startSinglePlayer(playerName.getText)
  }

  def backToMainMenu(): Unit = ???

  def restart(): Unit = {
    menu.getScene().getWindow().hide(); start(new Stage)
  }


//Test um bei begin Animation auszuführen-> FAIL .. reality hits you hard bro!
  //lazy val logoSet = menuDealerLogoAnim.set(logoAnimationImageView)
  //lazy val logoSet = new GameLoop(logoAnimationImageView)
  lazy val logoSet = Cashier
  //lazy val logoAnim = menuDealerLogoAnim
  lazy val logoAnim = logoSet

  def exit(): Unit = {
    logoSet.set(logoAnimationImageView)
    progressBarTest.setProgress(progressBarTest.getProgress + 0.1)
    if (progressBarTest.getProgress > 1) System.exit(1)
    else if (progressBarTest.getProgress % 0.2 < 0.1) logoAnim.goTo("Customer1")
    else  logoAnim.start()
    print(progressBarTest.getProgress % 0.2 + "\n")
  }
}