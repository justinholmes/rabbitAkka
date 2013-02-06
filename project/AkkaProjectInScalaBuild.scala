import sbt._
import sbt.Keys._

object AkkaProjectInScalaBuild extends Build {

  lazy val akkaProjectInScala = Project(
    id = "akka-project-in-scala",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Akka Project In Scala",
      organization := "org.example",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.0",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots",
      resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/releases",
      libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.1.0",
//      libraryDependencies += "com.typesafe.akka" %% "akka-camel" % "2.1.0",
//      libraryDependencies += "org.apache.camel" % "camel-jetty" % "2.10.0",
//      libraryDependencies +=   "org.apache.activemq" % "activemq-camel" % "5.7.0",
//      libraryDependencies +=       "org.apache.activemq" % "activemq-core" % "5.7.0",
      libraryDependencies += "com.github.sstone" % "amqp-client_2.10" % "1.1"


  )
  )

}
