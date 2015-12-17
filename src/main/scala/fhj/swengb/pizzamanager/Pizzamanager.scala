package fhj.swengb.pizzamanager


import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene._
import javafx.stage.Stage

import scala.util.control.NonFatal


object pizzamanager {
  def main(args: Array[String]) {
    Application.launch(classOf[pizzamanager], args: _*)
  }
}


class pizzamanager extends javafx.application.Application {


  val Css = "/fhj/swengb/pizzamanager/pizzamanager.css"
  val Fxml = "/fhj/swengb/pizzamanager/pizzamanager.fxml"


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

class pizzamanagerController extends pizzamanager {

}