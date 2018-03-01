// (5) shadow sbt-scalajs' crossProject and CrossType until Scala.js 1.0.0 is released
import sbtcrossproject.{crossProject, CrossType}
import org.scalajs.sbtplugin.Stage.FullOpt

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
      "com.lihaoyi"    %%% "fansi"       % "0.2.5",
      "org.scalatest"  %%% "scalatest"   % "3.0.4" % Test
    ),
    scalaVersion := "2.12.4",
    /* Scala comes with an optimizer, so we use that to get some free performance. */
    scalacOptions ++=
      Seq(
        "-deprecation",
        "-opt:l:inline",
        "-opt-inline-from:**",
        "-opt:closure-invocations",
        "-opt:copy-propagation",
        "-opt:box-unbox",
        "-opt:unreachable-code",
        "-opt:redundant-casts",
        "-opt-warnings"
      )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi"  %%% "scalatags"   % "0.6.7",
      "org.scala-js" %%% "scalajs-dom" % "0.9.2"
    ),
    scalaJSUseMainModuleInitializer := true,
    /* `FullOpt` means to optimize java with google closure compiler */
    scalaJSStage in Compile := FullOpt
  )

lazy val coreJvm = core.jvm
lazy val coreJs  = core.js
