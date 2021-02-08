package rpgr

import zio._
import console._

object ZMain extends App {

  val hiZ = putStrLn("hi")

  def run(args: List[String]) = hiZ.exitCode
  
}