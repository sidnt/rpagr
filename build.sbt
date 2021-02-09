val scalaV = "2.13.4"
val zioV = "1.0.4-2"
val sttpV = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "release-pager",
    version := "0.1.0",
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioV,
      "com.softwaremill.sttp.client3" %% "core" % sttpV,
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % sttpV
    )
  )
