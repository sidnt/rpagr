package rpgr

import zio._
import console._
import java.io._

object botConfig {

  type BotConfig = Has[botConfig.Service]

  trait Service {
    val botApiKey: String
  }

  val accessBotApiKey = ZIO.access[BotConfig](_.get.botApiKey)
  
  /**
    * kind of important concept to assimilate that 
    * the effect to gather api key is performed during layer construction
    * and not in the domain logic program
    */
  val getBotApiKeyFromConsole = ZLayer.fromEffect(
    for {
      apiKey <- putStrLn("enter the bot's api key correctly>") *> getStrLn
    } yield new botConfig.Service {
      val botApiKey = apiKey
    }
  )

}

object botConfigTest extends App {

  import botConfig._

  val p0 = for {
    key <- accessBotApiKey
    _ <- putStrLn(s"got api key>\n$key")
  } yield ()

  val program = p0.provideCustomLayer(getBotApiKeyFromConsole).exitCode

  def run(args: List[String]): zio.URIO[zio.ZEnv,ExitCode] = program

}
