package fhj.swengb.pizza

import javafx.animation._
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene._
import javafx.scene.control.{Label, Button, ProgressBar}
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

  @FXML var imgvw_background: ImageView = _
  @FXML var customers: AnchorPane = _
  @FXML var imgvw_customer1: ImageView = _
  @FXML var imgvw_customer2: ImageView = _
  @FXML var imgvw_customer3: ImageView = _
  @FXML var imgvw_customer4: ImageView = _
  @FXML var imgvw_customertable: ImageView = _
  @FXML var pizzas: AnchorPane = _
  @FXML var pizza1: AnchorPane = _
  @FXML var pizza2: AnchorPane = _
  @FXML var pizza3: AnchorPane = _
  @FXML var pizza4: AnchorPane = _
  @FXML var imgvw_pizza1: ImageView = _
  @FXML var imgvw_pizza1_ing1: ImageView = _
  @FXML var imgvw_pizza1_ing2: ImageView = _
  @FXML var imgvw_pizza1_ing3: ImageView = _
  @FXML var imgvw_pizza1_ing4: ImageView = _
  @FXML var imgvw_pizza2: ImageView = _
  @FXML var imgvw_pizza2_ing1: ImageView = _
  @FXML var imgvw_pizza2_ing2: ImageView = _
  @FXML var imgvw_pizza2_ing3: ImageView = _
  @FXML var imgvw_pizza2_ing4: ImageView = _
  @FXML var imgvw_pizza3: ImageView = _
  @FXML var imgvw_pizza3_ing1: ImageView = _
  @FXML var imgvw_pizza3_ing2: ImageView = _
  @FXML var imgvw_pizza3_ing3: ImageView = _
  @FXML var imgvw_pizza3_ing4: ImageView = _
  @FXML var imgvw_pizza4: ImageView = _
  @FXML var imgvw_pizza4_ing1: ImageView = _
  @FXML var imgvw_pizza4_ing2: ImageView = _
  @FXML var imgvw_pizza4_ing3: ImageView = _
  @FXML var imgvw_pizza4_ing4: ImageView = _
  @FXML var imgvw_cashier: ImageView = _
  @FXML var imgvw_ingredientstable: ImageView = _
  @FXML var speechbubbles: AnchorPane = _
  @FXML var speechbubble1: AnchorPane = _
  @FXML var speechbubble2: AnchorPane = _
  @FXML var speechbubble3: AnchorPane = _
  @FXML var speechbubble4: AnchorPane = _
  @FXML var imgvw_speechbubble1: ImageView = _
  @FXML var imgvw_speechbubble1_ing1: ImageView = _
  @FXML var imgvw_speechbubble1_ing2: ImageView = _
  @FXML var imgvw_speechbubble1_ing3: ImageView = _
  @FXML var imgvw_speechbubble1_ing4: ImageView = _
  @FXML var imgvw_speechbubble2: ImageView = _
  @FXML var imgvw_speechbubble2_ing1: ImageView = _
  @FXML var imgvw_speechbubble2_ing2: ImageView = _
  @FXML var imgvw_speechbubble2_ing3: ImageView = _
  @FXML var imgvw_speechbubble2_ing4: ImageView = _
  @FXML var imgvw_speechbubble3: ImageView = _
  @FXML var imgvw_speechbubble3_ing1: ImageView = _
  @FXML var imgvw_speechbubble3_ing2: ImageView = _
  @FXML var imgvw_speechbubble3_ing3: ImageView = _
  @FXML var imgvw_speechbubble3_ing4: ImageView = _
  @FXML var imgvw_speechbubble4: ImageView = _
  @FXML var imgvw_speechbubble4_ing1: ImageView = _
  @FXML var imgvw_speechbubble4_ing2: ImageView = _
  @FXML var imgvw_speechbubble4_ing3: ImageView = _
  @FXML var imgvw_speechbubble4_ing4: ImageView = _
  @FXML var ingredients: AnchorPane = _
  @FXML var imgvw_salami: ImageView = _
  @FXML var imgvw_paprika: ImageView = _
  @FXML var imgvw_champignon: ImageView = _
  @FXML var imgvw_cheese: ImageView = _
  @FXML var imgvw_onion: ImageView = _
  @FXML var imgvw_tomato: ImageView = _
  @FXML var imgvw_ham: ImageView = _
  @FXML var imgvw_tuna: ImageView = _
  @FXML var btn_restartGame: Button = _
  @FXML var progressBar_game: ProgressBar = _
  @FXML var lbl_highscore: Label = _




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

  def highscoresMenuStart(): Unit = animMenuPanes(highscoresMenu, false)

  def gameMenuStart(): Unit = animMenuPanes(gameMenu, false)

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
  //lazy val logoAnim = menuDealerLogoAnim.set(logoAnimationImageView)
  //lazy val logoAnim = new GameLoop(logoAnimationImageView)
  //lazy val logoAnim = CashierAnim
  //lazy val logoAnim = menuDealerLogoAnim
  //lazy val logoAnim = logoSet
  lazy val logoAnim = GameLoop()

  def exit(): Unit = {
    logoAnim.set(logoAnimationImageView)
    progressBarTest.setProgress(progressBarTest.getProgress + 0.1)
    if (progressBarTest.getProgress > 1) System.exit(1)
    else if (progressBarTest.getProgress % 0.2 < 0.1) logoAnim.stop()
    else  logoAnim.start()
    print(progressBarTest.getProgress % 0.2 + "\n")
  }
}