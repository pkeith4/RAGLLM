ThisBuild / scalaVersion := "3.5.1"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % "3.3.6",
  "org.apache.hadoop" % "hadoop-common" % "3.3.6",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.3.6",
  "org.apache.hadoop" % "hadoop-aws" % "3.3.6",

  "org.apache.lucene" % "lucene-core" % "9.10.0",
  "org.apache.lucene" % "lucene-analysis-common" % "9.10.0",

  "org.apache.pdfbox" % "pdfbox" % "2.0.31",
  "com.softwaremill.sttp.client3" %% "core" % "3.9.5",
  "com.softwaremill.sttp.client3" %% "circe" % "3.9.5",
  "io.circe" %% "circe-generic" % "0.14.9",
  "io.circe" %% "circe-parser" % "0.14.9",
  "io.circe" %% "circe-core" % "0.14.9",

  "com.typesafe" % "config" % "1.4.3",

  "org.slf4j" % "slf4j-api" % "1.7.36",
  "org.slf4j" % "slf4j-simple" % "1.7.36" % Runtime,

  "org.scalatest" %% "scalatest" % "3.2.19" % Test
)

assembly / assemblyExcludedJars := {
  val cp = (assembly / fullClasspath).value
  cp.filter { jar =>
    val name = jar.data.getName
    name.startsWith("hadoop-") || name.startsWith("slf4j-simple")
  }
}

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "services", xs @ _*) => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case "application.conf" => MergeStrategy.concat
  case x if x.endsWith(".properties") => MergeStrategy.concat
  case _ => MergeStrategy.first
}

Compile / mainClass := Some("src.Main")
assembly / mainClass := Some("src.parallelizer.ChunkJob")