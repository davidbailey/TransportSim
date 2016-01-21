organization := "com.github.davidbailey"
name := "TransportSim"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.7"
libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" %"0.9.0.0",
  "org.apache.spark" %% "spark-core" % "1.5.1"
)
