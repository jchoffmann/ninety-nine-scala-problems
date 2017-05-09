name := "ninety-nine-scala-problems"

scalaVersion := "2.12.2"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test

scalacOptions ++= Seq("-language:implicitConversions,higherKinds")
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
