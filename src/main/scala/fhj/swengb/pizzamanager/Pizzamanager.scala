package fhj.swengb.pizzamanager


import javafx.animation.{ScaleTransition, PathTransition}
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.geometry.Pos
import javafx.scene._
import javafx.scene.control.{ProgressIndicator, ProgressBar, Label}
import javafx.scene.layout.{VBox, HBox, AnchorPane}
import javafx.scene.shape.{CubicCurveTo, MoveTo, Path, Rectangle}
import javafx.stage.Stage
import javafx.util.Duration

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

  @FXML var  progressBar:Rectangle = _



//     var root: Group = new Group();
  //   var scene:Scene = new Scene(root, 300, 150);
    //scene.getStylesheets().add("progresssample/Style.css");

  var values: Array[Float]  = Array(-1.0f, 0f, 0.6f, 1.0f)

  //var  labels: Array[Label] = Array(values.length);
  var  pbs: ProgressBar = new ProgressBar(values.length);
  var  pins: ProgressIndicator = new ProgressIndicator()
  var  hbs :HBox = new HBox (values.length);

  var i:Int = 0

  while (i < values.size) {
    animProgressBar(i, pbs, pins, hbs,values)
    i+=1
  }

  def animProgressBar(i:Int, pbs: ProgressBar, pins: ProgressIndicator,hbs :HBox, values: Array[Float]): Unit = {


    var label: Label = new Label();
    //        label.setText("progress:" + values(i))

    var pb: ProgressBar = new ProgressBar()
    pb.setProgress(values(i))

    var pin: ProgressIndicator = new ProgressIndicator();
    pin.setProgress(values(i));
    var hb: HBox = new HBox();
    hb.setSpacing(5);
    hb.setAlignment(Pos.CENTER);
    hb.getChildren().addAll(label, pb, pin);

    var vb: VBox = new VBox();
    vb.setSpacing(5);
    vb.getChildren().addAll(hbs);


  }


  def animProgressBar(obj: Rectangle, percentage: Int, xyMitte: Int = 300, yyMitted: Int = 400):Rectangle = {
    //rect.setArcHeight(50);
    //rect.setArcWidth(50);

    var st: ScaleTransition = new ScaleTransition(Duration.millis(2000), obj)
    st.setByX(0.5f)
    st.setByY(1)
    st.setCycleCount(4)
    st.setNode(obj)
    st.setAutoReverse(true)

    st.play()

    var xMitte = 100
    var yMitte = 100

    var path: Path = new Path()
    path.getElements.add(new MoveTo(xMitte, yMitte))
    path.getElements().add(new CubicCurveTo(xMitte + 200, yMitte, xMitte + 50, yMitte, xMitte, yMitte))

    var pathTrans: PathTransition = new PathTransition()
    pathTrans.setDuration(new Duration(200))
    pathTrans.setNode(obj)
    pathTrans.setPath(path)
    pathTrans.setAutoReverse(false)
    pathTrans.play()


    obj
  }



  def animMenue(obj: AnchorPane, slideRight: Boolean, xMitte: Int = 300, yMitte: Int = 400): Unit = {

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

  progressBar = animProgressBar(progressBar, 100, 100, 100);



}