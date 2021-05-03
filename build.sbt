name := "FPObjAlgebra"

version := "0.1"

scalaVersion := "2.13.5"

val zioVersion = "1.0.6"

libraryDependencies ++= Seq(
  "dev.zio"  %% "zio" % zioVersion,
  "org.typelevel" %% "cats-core" % "2.3.0",
  "dev.zio"  %% "zio-interop-cats" % "2.4.1.0"
)
