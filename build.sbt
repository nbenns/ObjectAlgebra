name := "FPObjAlgebra"

version := "0.1"

scalaVersion := "3.1.2"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "utf8",
  "-feature",
  "literal-types",
  "-Xfatal-warnings",
  "-Yexplicit-nulls",
  "-source:future"
)

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  "dev.zio"  %% "zio" % "2.0.0",
  "org.typelevel" %% "cats-core" % "2.6.1"
)
