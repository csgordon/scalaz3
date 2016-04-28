val org = "edu.drexel"

lazy val z3jar = file("external/z3bin/lib/com.microsoft.z3.jar")

val libs = Seq(
  "org.scala-lang.modules" %% "scala-async"     % "0.9.2",
  "com.typesafe.akka"      %% "akka-actor"      % "2.3.4",
  "com.typesafe.akka"      %% "akka-slf4j"      % "2.3.4",
  "org.scalaz"             %% "scalaz-core"     % "7.1.0",
  "org.scalacheck"         %% "scalacheck"      % "1.12.5" % Test,
  "org.scalatest"          %% "scalatest"       % "2.2.4"  % Test,
  "org.specs2"             %% "specs2"          % "2.4"    % Test
)

lazy val z3Task = taskKey[Unit]("Fetch and build Z3 and its Java bindings")

z3Task := {
    val s: TaskStreams = streams.value
    val exists = z3jar.exists()
    if (!exists)
        s.log.info("Z3 is missing.... rebuilding...");
    else
        s.log.info("Found existing Z3")
    exists || (("bash fetch.sh" !) == 0)
}


lazy val root = (project in file(".")).
    settings(
        name := "scalaz3",
        version := "0.1alpha",
        scalaVersion := "2.11.8",
        libraryDependencies ++= libs,
        compile <<= (compile in Compile) dependsOn (z3Task),
        unmanagedBase <<= baseDirectory { base => base / "external/z3bin/lib" }
    )
