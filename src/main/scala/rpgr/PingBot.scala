package rpgr

import zio._
import console._

import sttp.client3._
import sttp.model._
import sttp.client3.asynchttpclient.zio._

import botConfig._

object PingBot extends App {

  val botBaseUrl = "https://api.telegram.org/bot"
  def getMeUri(botBaseUrl: String, botToken: String, methodName: String) = Uri.parse(botBaseUrl + botToken + "/" + methodName)

  val p0 = for {
    botToken      <-  accessBotToken
    getMeUri      <-  ZIO.fromEither(getMeUri(botBaseUrl, botToken, "getMe"))
    getMeRequest  =   basicRequest.get(getMeUri).response(asString)
    response      <-  send(getMeRequest)
    _             <-  console.putStrLn(s"Got response code: ${response.code}")
    _             <-  console.putStrLn(response.body.toString)
  } yield ()
  
  val program = p0.provideCustomLayer(AsyncHttpClientZioBackend.layer() ++ getBotTokenFromConsole)

  def run(args: List[String]): zio.URIO[zio.ZEnv,ExitCode] = program.exitCode

}
