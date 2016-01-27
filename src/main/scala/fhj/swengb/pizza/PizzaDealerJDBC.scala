
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

  def connectToDatabase: String = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      ""
    } catch {
      case e: Exception => e.printStackTrace.toString
    }
  }

  def getHighscoresForUser(username: String): (String,Int) = {
    val highscoresMap = getHighscores
    try {
      println("hey")
      ("User requested was not found",0)
      //falls user nicht gefunden wurde
      //val valueFromUser:Int = highscoresMap(username.trim)
      //(username,valueFromUser)
    } catch {
      case e:Exception => e.printStackTrace
        ("User requested was not found",0)
    }
  }

  def getHighscores: (String,String) = {
    var nameString:String = ""
    var highscoresString:String = ""
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM highscore ORDER BY highscore_number DESC LIMIT 0,20")
      while (rs.next) {
        val username = rs.getString("username")
        val highscore_number = rs.getInt("highscore_number")
        nameString =nameString + username +  " : " + " \n"
        highscoresString =highscoresString + highscore_number + " \n"
      }
      //println(highscoresString)
      (nameString,highscoresString)
    }
    catch {
      case e => e.printStackTrace
        (nameString,highscoresString)
    }

  }

  def addToHighscoreList(username: String, score: Int) = {
    try {
      val statement = connection.prepareStatement("INSERT INTO highscore (username,highscore_number) VALUES(?,?)") //insert befehl
      statement.setString(1, username)
      statement.setInt(2, score)
      statement.executeUpdate()

    }
    catch {
      case x:Exception => x.printStackTrace()
    }
  }

}