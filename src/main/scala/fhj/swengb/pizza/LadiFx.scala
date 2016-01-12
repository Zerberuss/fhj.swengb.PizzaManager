package net.ladstatt.fx.animations

import java.net.URL
import java.util.ResourceBundle
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.fxml._
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.collection.immutable.IndexedSeq
import scala.util.Random
import scala.util.control.NonFatal

/**
  * AnimationTimer Example
  *
  * Shows an example how to use the JavaFX AnimationTimer class
  */
object FxAnimations {
  def main(args: Array[String]) {
    Application.launch(classOf[FxAnimationsApp], args: _*)
  }
}

class FxAnimationsApp extends javafx.application.Application {

  val loader = new FXMLLoader(getClass.getResource("FxAnimations.fxml"))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("FxAnimations App")
      loader.load[Parent]()
      stage.setScene(new Scene(loader.getRoot[Parent]))
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}



case class CircleAnimation(circles : Seq[Circle]) extends AnimationTimer {

  // every tick this method is called - you are free to do whatever you want
  // in this method. maybe animate something, maybe something else ...
  override def handle(now: Long): Unit = {
    circles.foreach {
      case c =>
        c.setCenterX(c.getCenterX + Random.nextDouble * 4 - 2)
        c.setCenterY(c.getCenterY + Random.nextDouble * 4 - 2)
    }

  }
}


class FxAnimationsController extends Initializable {

  @FXML var canvasAnchorPane: AnchorPane = _

  def randColor(): Color = Color.color(Random.nextDouble, Random.nextDouble(), Random.nextDouble)

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    val width  = canvasAnchorPane.getMinWidth
    val height = canvasAnchorPane.getMinHeight

    val circles: IndexedSeq[Circle] =
      for (i <- 1 to 2000) yield {
        mkCircle(width.toInt, height.toInt, Random.nextInt(20) +1 )
      }

    canvasAnchorPane.getChildren.addAll(circles: _*)

    CircleAnimation(circles).start()
  }


  def mkCircle(width: Int, height: Int, maxRadius: Int): Circle = {
    val c = new Circle(Random.nextInt(width), Random.nextInt(height), Random.nextInt(maxRadius))
    c.setFill(randColor())
    c
  }

}

