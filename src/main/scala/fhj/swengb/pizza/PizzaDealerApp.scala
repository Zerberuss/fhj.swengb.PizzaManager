package fhj.swengb.assignments.ttt.aschneider

import javafx.animation._
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene._
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path}
import javafx.stage.Stage
import javafx.util.Duration

import scala.util.control.NonFatal


object TicTacToeApp {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeApp], args: _*)
  }
}


class TicTacToeApp extends javafx.application.Application {


  val Css = "/fhj/swengb/pizza/PizzaDealer.css"
  val Fxml = "/fhj/swengb/pizza/PizzaDealer.fxml"


  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("TicTacJoe")
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
class TicTacToeAppController extends TicTacToeApp {
  //attributes are being initialized (everything with an ID)
  @FXML var menu: AnchorPane = _
  //Main Pane
  @FXML var mpMenu: AnchorPane = _
  //Settings Pane for Muliplayer
  @FXML var spMenu: AnchorPane = _
  //Settings Pane for SinglePlayer
  @FXML var gamePane: AnchorPane = _
  //game Pane where the game is played (both mp and sp)
  @FXML var mpAvatar: ImageView = _

  @FXML var spAvatar: ImageView = _

  @FXML var mpName1: control.TextField = _
  @FXML var mpName2: control.TextField = _
  @FXML var spName: control.TextField = _


  @FXML var status: control.Label = _
  @FXML var headline: control.Label = _
  @FXML var winPane: AnchorPane = _
  @FXML var winStatus: control.Label = _





  var playingInSpMode: Boolean = true
  var CPUplaying:Boolean = false

  var playerAPlaying:Boolean = true



  




  //initialize function executes the commands at startup for the main scene

  def anim(obj: AnchorPane, slideRight: Boolean, xMitte: Int = 300, yMitte: Int = 400): Unit = {

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




  def startMultiPlayer(playerName1: String, playerName2: String): Boolean = {

    playingInSpMode=false

    var player1IsStarting: Boolean = false
    if(false){
      player1IsStarting = true
      status.setText("It's " + playerName1 + "'s turn:")
    }
    else {
      player1IsStarting = false
      status.setText("It's " + playerName2 + "'s turn:")
    }
    anim(mpMenu, true, 300)
    anim(gamePane, false, 356)

    if (player1IsStarting)
       true
    else
       false
  }



  def startSinglePlayer(playerName: String): Boolean = {

    playingInSpMode = true

    var playerIsStarting: Boolean = false
    if (scala.util.Random.nextInt(100) >= 50) {
      playerIsStarting = true
    }
    else {
      playerIsStarting = false
    }
    status.setText("Play against the bot:")
    mpName2.setText(spName.getText())
    mpName1.setText("Angry Bot")

    anim(spMenu, true, 300)
    anim(gamePane, false, 356)
    if (playerIsStarting)
       true
    else
       false
  }



  def mpMenuBack(): Unit = anim(mpMenu, true)

  //Hier die richtigen User Infos übergeben! -> aus tabelle oda ka wie ihr sie gespeichert habts
  def spMenuBack(): Unit = anim(spMenu, true)

  def mpStartMenu(): Unit = anim(mpMenu, false)

  def spStartMenu(): Unit = anim(spMenu, false)

  def mpStart(): Unit = {
    startMultiPlayer(mpName1.getCharacters.toString, mpName2.getCharacters.toString)

  }

  def spStart(): Unit = {

    playerAPlaying = startSinglePlayer(spName.getCharacters.toString)
  }

  def backToMainMenu(): Unit = ???

  def restart(): Unit = {
    menu.getScene().getWindow().hide(); start(new Stage)
  }




  def exit(): Unit = System.exit(1)
}