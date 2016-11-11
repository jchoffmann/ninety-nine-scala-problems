name := "ninety-nine-scala-problems"

scalaVersion := "2.12.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % Test

scalacOptions ++= Seq("-language:implicitConversions,higherKinds")
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
