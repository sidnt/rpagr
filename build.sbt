val scalaV = "2.13.4"
val zioV = "1.0.4-2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "release-pager",
    version := "0.1.0",
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioV
    )
  )
