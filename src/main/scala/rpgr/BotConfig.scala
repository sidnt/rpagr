package rpgr

import zio._
import console._
import java.io._

object botConfig {

  type BotConfig = Has[botConfig.Service]

  trait Service {
    val botToken: String
  }

  val accessBotToken = ZIO.access[BotConfig](_.get.botToken)
  
  /**
    * kind of important concept to assimilate that 
    * the effect to gather api key is performed during layer construction
    * and not in the domain logic program
    */
  val getBotTokenFromConsole = ZLayer.fromEffect(
    for {
      token <- putStrLn("enter the bot's token correctly>") *> getStrLn
    } yield new botConfig.Service {
      val botToken = token
    }
  )

}

object botConfigTest extends App {

  import botConfig._

  val p0 = for {
    token <- accessBotToken
    _ <- putStrLn(s"got api key>\n$token")
  } yield ()

  val program = p0.provideCustomLayer(getBotTokenFromConsole).exitCode

  def run(args: List[String]): zio.URIO[zio.ZEnv,ExitCode] = program

}
