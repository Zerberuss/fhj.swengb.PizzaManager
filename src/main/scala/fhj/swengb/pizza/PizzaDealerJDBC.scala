
/**
  * Created by timo on 22.01.16.
  */

package fhj.swengb.pizza

import java.sql.{Connection, DriverManager}

import scala.collection.mutable.Map

/**
  * A Scala JDBC connection example by Alvin Alexander,
  * <a href="http://alvinalexander.com" title="http://alvinalexander.com">http://alvinalexander.com</a>
  */
object ScalaJdbcSQL {

  // connect to the database named "mysql" on port 8889 of localhost
  val url = "jdbc:mysql://socialproject.mynetgear.com:5474/db_pizzadealer"
  val driver = "com.mysql.jdbc.Driver"
  val username = "pizzadealer"
  val password = "Pa$$w0rd"
  var connection: Connection = _


  def closeConnection = connection.close

  def connectToDatabase = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      println("i think its working")
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

  def getHighscoresForUser(username: String): (String,Int) = {
    val highscoresMap = getHighscores
    try {
      //falls user nicht gefunden wurde
      val valueFromUser:Int = highscoresMap(username.trim)
      (username,valueFromUser)
    } catch {
      case e:Exception => e.printStackTrace
        ("User requested was not found",0)
    }
  }

  def getHighscores: Map[String, Int] = {
    val highscoresMap = Map[String, Int]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM highscore")
      while (rs.next) {
        val username = rs.getString("username")
        val highscore_number = rs.getInt("highscore_number")
        println("Username = %s, Highscore = %s".format(username, highscore_number))
        highscoresMap += username -> highscore_number
      }
      //mutable.LinkedHashMap(highscoresMap.toSeq.sortBy(_._1):_*) Sort  geht nicht  furz kack scheiß
      highscoresMap
    }
    catch {
      case e => e.printStackTrace
        highscoresMap
    }

  }

  def setHighscoreList(username: String, score: Int) = {
    try {
      val statement = connection.prepareStatement("INSERT INTO highscore (username,highscore_number) VALUES(?,?)") //insert befehl
      println("bis daher gehts")
      statement.setString(1, username)
      statement.setInt(2, score)
      println("do gehts a no\n")
      statement.executeUpdate()
      print("HEYYYYYYYYYYYYYYYYYY BROTHER")
    }
    catch {
      case x:Exception => x.printStackTrace
    }
  }

}