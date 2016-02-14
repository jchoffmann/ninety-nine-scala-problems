name := "ninety-nine-scala-problems"

scalaVersion := "2.11.7"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test

scalacOptions ++= Seq("-language:implicitConversions,higherKinds")
scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")
