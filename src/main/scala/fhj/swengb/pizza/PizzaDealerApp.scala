package fhj.swengb.pizza

import javafx.animation._
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene._
import javafx.scene.control.{Button, Label, ProgressBar}
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path}
import javafx.scene.text.{Text, TextFlow}
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
  //Gameover Overlay
  @FXML var gameOverPane: AnchorPane = _


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
  @FXML var lbl_lives:Label = _



  //Buttons
  @FXML var salamibtn:Button = _
  @FXML var paprikabtn:Button = _
  @FXML var champignon:Button = _
  @FXML var cheesebtn:Button = _
  @FXML var onionbtn:Button = _
  @FXML var tomatobtn:Button = _
  @FXML var hambtn:Button = _
  @FXML var tunabtn:Button = _

  @FXML var customer1:Button = _
  @FXML var customer2:Button = _
  @FXML var customer3:Button = _
  @FXML var customer4:Button = _


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

  @FXML var gameOver_score: control.Label = _
  @FXML var gameOver_error: control.Label = _

  @FXML var highscore_error: control.Label = _
  @FXML var highscore_scoreTextFlow: TextFlow = _
  @FXML var highscore_nameTextFlow: TextFlow = _





  def logobtn():Unit = {
    menuDealerLogoAnim.set(logoAnimationImageView)
    menuDealerLogoAnim.start()
  }


  def callSalamibtn():Unit = GameLoop.ingridientsButton(1,GameLoop.selectedCustomer)
  def callPaprikabtn():Unit = GameLoop.ingridientsButton(2,GameLoop.selectedCustomer)
  def callChampignonbtn():Unit = GameLoop.ingridientsButton(3,GameLoop.selectedCustomer)
  def callCheesebtn():Unit = GameLoop.ingridientsButton(4,GameLoop.selectedCustomer)
  def callOnionbtn():Unit = GameLoop.ingridientsButton(5,GameLoop.selectedCustomer)
  def callTomatobtn():Unit = GameLoop.ingridientsButton(6,GameLoop.selectedCustomer)
  def callHambtn():Unit = GameLoop.ingridientsButton(7,GameLoop.selectedCustomer)
  def callTunabtn():Unit = GameLoop.ingridientsButton(8,GameLoop.selectedCustomer)

  def callCustomer1():Unit = GameLoop.customerSelected(1)
  def callCustomer2():Unit = GameLoop.customerSelected(2)
  def callCustomer3():Unit = GameLoop.customerSelected(3)
  def callCustomer4():Unit = GameLoop.customerSelected(4)




  //initialize function executes the commands at startup for the main scene

  //animation for Menue slide ins and outs



  def startHighscoresPane(): Boolean = {
    animMenuPanes.play(highscoresPane, false, 1280/2+8)
    animMenuPanes.play(highscoresMenu, true)
    true
  }


  def startSinglePlayer(playerName: String): Boolean = {
    animMenuPanes.play(gameMenu, true)
    animMenuPanes.play(gamePane, false, 1280/2+8)
    setNewGame()
    true
  }


  def highscoresMenuBack(): Unit = animMenuPanes.play(highscoresMenu, true)

  //Hier die richtigen User Infos übergeben! -> aus tabelle oda ka wie ihr sie gespeichert habts
  def gameMenuBack(): Unit = animMenuPanes.play(gameMenu, true)



  def gameMenuStart(): Unit = animMenuPanes.play(gameMenu, false)

  def highscoresStart(): Unit = {
    startHighscoresPane()
  }

  def gameStart(): Unit = {
    startSinglePlayer(playerName.getText)
  }

  def backToMainMenu(): Unit = animMenuPanes.play(highscoresPane, true, 1300)

  def restart(): Unit = {
    animMenuPanes.play(gameOverPane,true) //1280/2-187
    mainLoop.player.stop()
    menuLoop.player.stop()
    menu.getScene().getWindow().hide(); start(new Stage)
  }




  /** Erstellt ein neues Game und setzt die Variablen der FXML Datei
    *
    *gesetzt wird: alles!
    *
    * @return
    */
  def setNewGame() {
    val pizzaList1 = (
      imgvw_pizza1,
      List (
        imgvw_pizza1_ing1,
        imgvw_pizza1_ing2,
        imgvw_pizza1_ing3,
        imgvw_pizza1_ing4))
    val pizzaList2 =  (
      imgvw_pizza2,
      List (
        imgvw_pizza2_ing1,
        imgvw_pizza2_ing2,
        imgvw_pizza2_ing3,
        imgvw_pizza2_ing4))
    val pizzaList3=  (
      imgvw_pizza3,
      List (
        imgvw_pizza3_ing1,
        imgvw_pizza3_ing2,
        imgvw_pizza3_ing3,
        imgvw_pizza3_ing4))
    val pizzaList4 = (
      imgvw_pizza4,
      List (
        imgvw_pizza4_ing1,
        imgvw_pizza4_ing2,
        imgvw_pizza4_ing3,
        imgvw_pizza4_ing4
      ))

    val cashier = imgvw_cashier


    val customersList = List (
      imgvw_customer1,
      imgvw_customer2,
      imgvw_customer3,
      imgvw_customer4
    )

    val speachbubble1 = (
      imgvw_speechbubble1,
      List(
        imgvw_speechbubble1_ing1,
        imgvw_speechbubble1_ing2,
        imgvw_speechbubble1_ing3,
        imgvw_speechbubble1_ing4
      ))
    val speachbubble2 = (
      imgvw_speechbubble2,
      List(
        imgvw_speechbubble2_ing1,
        imgvw_speechbubble2_ing2,
        imgvw_speechbubble2_ing3,
        imgvw_speechbubble2_ing4
      ))
    val speachbubble3 = (
      imgvw_speechbubble3,
      List(
        imgvw_speechbubble3_ing1,
        imgvw_speechbubble3_ing2,
        imgvw_speechbubble3_ing3,
        imgvw_speechbubble3_ing4
      ))
    val speachbubble4 = (
      imgvw_speechbubble4,
      List(
        imgvw_speechbubble4_ing1,
        imgvw_speechbubble4_ing2,
        imgvw_speechbubble4_ing3,
        imgvw_speechbubble4_ing4
      ))

    val ingrediants = List(
      imgvw_salami,
      imgvw_paprika,
      imgvw_champignon,
      imgvw_cheese,
      imgvw_onion,
      imgvw_tomato,
      imgvw_ham,
      imgvw_tuna
    )

    val progressBar =  progressBar_game
    val highscore =  lbl_highscore
    val playername = this.playerName
    val lives = lbl_lives

    menuLoop.player.stop()
    mainLoop.player.play()

    GameLoop.set(cashier,progressBar,pizzaList1,pizzaList2,pizzaList3,pizzaList4,customersList,speachbubble1,speachbubble2,speachbubble3,speachbubble4,ingrediants,highscore,playername,lives,gameOverPane,gameOver_score,gameOver_error)
    GameLoop.start()
  }


  def highscoresMenuStart(): Unit = {
    highscore_nameTextFlow.getChildren().clear()
    highscore_scoreTextFlow.getChildren().clear()
    
    animMenuPanes.play(highscoresPane, false, 1280/2+8)
    highscore_error.setText(ScalaJdbcSQL.connectToDatabase)
    var highscoreNameList = new Text(ScalaJdbcSQL.getHighscores._1)
    highscoreNameList.setFill(Color.ORANGE)
    highscore_nameTextFlow.getChildren().add(highscoreNameList)  //ScalaJdbcSQL.getHighscores
    var highscoreScoreList = new Text(ScalaJdbcSQL.getHighscores._2)
    highscoreScoreList.setFill(Color.ORANGE)
    highscore_scoreTextFlow.getChildren().add(highscoreScoreList)


  }


  def exit(): Unit = {
    System.exit(1)
  }


  menuLoop.set()
  menuLoop.player.setVolume(0.5)
  mainLoop.set()
  mainLoop.player.setVolume(0.3)
  buttonEffect.set()
  pizzaEffect.set()
  puffEffect.set()

  menuLoop.player.play()

}


object animMenuPanes {  //1280/2

  def play(obj: AnchorPane, slideRight: Boolean, xMitte: Int = 400, yMitte: Int = 720/2): Unit ={

    buttonEffect.player.stop()
    buttonEffect.player.play()

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
}