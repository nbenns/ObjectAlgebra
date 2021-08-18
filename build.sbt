name := "FPObjAlgebra"

version := "0.1"

scalaVersion := "2.13.6"

scalacOptions ++= Seq(
  "-Xsource:3"
)

libraryDependencies ++= Seq(
  "dev.zio"  %% "zio" % "2.0.0-M1",
  "org.typelevel" %% "cats-core" % "2.6.1"
)
