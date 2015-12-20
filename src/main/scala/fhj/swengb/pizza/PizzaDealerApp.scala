package fhj.swengb.pizza

import javafx.animation._
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene._
import javafx.scene.control.ProgressBar
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path}
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
  //attributes are being initialized (everything with an ID)

  //Main Pane
  @FXML var menu: AnchorPane = _
  //Settings Pane for Muliplayer
  @FXML var highscoresPane: AnchorPane = _
  //Settings Pane for SinglePlayer
  @FXML var gameMenu: AnchorPane = _
  //game Pane where the game is played (both mp and sp)
  @FXML var gamePane: AnchorPane = _


  @FXML var highscoreAvatar: ImageView = _
  @FXML var playerAvatar: ImageView = _

  @FXML var playerName: control.TextField = _


  @FXML var status: control.Label = _
  @FXML var headline: control.Label = _
  @FXML var winPane: AnchorPane = _
  @FXML var winStatus: control.Label = _

  @FXML var progressBarTest: ProgressBar = _





  //initialize function executes the commands at startup for the main scene

  //animation for Menue slide ins and outs
  def animMenuPanes(obj: AnchorPane, slideRight: Boolean, xMitte: Int = 300, yMitte: Int = 400): Unit = {

    var xEnde = 1200
    var path: Path = new Path()

    if (slideRight) {
      path.getElements.add(new MoveTo(xMitte, yMitte))
      path.getElements().add(new CubicCurveTo(xMitte + 50, yMitte, xMitte + 200, yMitte, xMitte + xEnde, yMitte))
    } else {
      path.getElements.add(new MoveTo(xMitte + xEnde, yMitte))
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


    animMenuPanes(gameMenu, true, 300)
    animMenuPanes(gamePane, false, 356)

  true
  }



  def startSinglePlayer(playerName: String): Boolean = {


    status.setText("Play against the bot:")

    animMenuPanes(gameMenu, true, 300)
    animMenuPanes(gamePane, false, 356)
    true
  }



  def highscoresMenuBack(): Unit = animMenuPanes(highscoresPane, true)

  //Hier die richtigen User Infos übergeben! -> aus tabelle oda ka wie ihr sie gespeichert habts
  def gameMenuBack(): Unit = animMenuPanes(gameMenu, true)

  def highscoresMenuStart(): Unit = animMenuPanes(highscoresPane, false)

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



  def exit(): Unit = progressBarTest.setProgress(progressBarTest.getProgress+0.1)
}