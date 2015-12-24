package fhj.swengb.pizza

//http://stackoverflow.com/questions/10708642/javafx-2-0-an-approach-to-game-sprite-animation


/*

import javafx.animation.AnimationTimer
import javafx.geometry.Rectangle2D
import javafx.scene.image.{Image, ImageView}

class ImageViewSprite extends AnimationTimer {
  private var imageView: ImageView = null
  private var totalFrames: Int = 0
  private var fps: Float = 0
  private var cols: Int = 0
  private var rows: Int = 0
  private var frameWidth: Int = 0
  private var frameHeight: Int = 0
  private var currentCol: Int = 0
  private var currentRow: Int = 0
  private var lastFrame: Long = 0

  def ImageViewSprites(imageView: ImageView, image: Image, columns: Int, rows: Int, totalFrames: Int, frameWidth: Int, frameHeight: Int, framesPerSecond: Float) {
    //var imageView:ImageView = new ImageView()
    this.imageView = imageView
    imageView.setImage(image)
    imageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight))

    cols = columns
    this.rows = rows
    this.totalFrames = totalFrames
    this.frameWidth = frameWidth
    this.frameHeight = frameHeight
    fps = framesPerSecond
    lastFrame = System.nanoTime
  }
    override def handle(now: Long) {
    val frameJump: Int = Math.floor((now - lastFrame) / (1000000000 / fps)).toInt
    if (frameJump >= 1) {
      lastFrame = now
      val addRows: Int = Math.floor(frameJump.toFloat / cols.toFloat).toInt
      val frameAdd: Int = frameJump - (addRows * cols)
      if (currentCol + frameAdd >= cols) {
        currentRow += addRows + 1
        currentCol = frameAdd - (cols - currentCol)
      }
      else {
        currentRow += addRows
        currentCol += frameAdd
      }
      currentRow = if ((currentRow >= rows)) currentRow - (Math.floor(currentRow.toFloat / rows).toInt * rows) else currentRow
      if ((currentRow * cols) + currentCol >= totalFrames) {
        currentRow = 0
        currentCol = Math.abs(currentCol - (totalFrames - (Math.floor(totalFrames.toFloat / cols) * cols).toInt))
      }
      imageView.setViewport(new Rectangle2D(currentCol * frameWidth, currentRow * frameHeight, frameWidth, frameHeight))
    }
  }
}
*/