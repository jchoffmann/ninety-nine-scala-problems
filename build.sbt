name := "ninety-nine-scala-problems"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % Test

scalacOptions ++= Seq("-language:implicitConversions,higherKinds")
scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")
