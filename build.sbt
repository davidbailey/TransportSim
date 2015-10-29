name := "transportSim"

version := "0.1"

scalaVersion := "2.11.7"

sbtPlugin := true

coverageEnabled := true

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "io.plasmap" %% "geow" % "0.3.6-SNAPSHOT"

