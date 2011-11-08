import sbt._
import Keys._
import com.github.siasia._
import WebappPlugin.webappSettings

object BuildSettings {
  val buildOrganization = "com.weiglewilczek"
  val buildVersion      = "1.0.0"
  val buildScalaVersion = "2.9.1"

  val buildSettings =
    Defaults.defaultSettings ++
    // Seq(scalacOptions ++= Seq("-unchecked", "-Xfatal-warnings")) ++
    Seq(
      organization := buildOrganization,
      version      := buildVersion,
      scalaVersion := buildScalaVersion,

      // workaround for sbt issue #206 (remove 'watchTransitiveSources' when sbt 0.11.1 is released)
      // https://github.com/harrah/xsbt/issues/206
      watchTransitiveSources <<=
        Defaults.inDependencies[Task[Seq[File]]](
          watchSources.task, const(std.TaskExtra.constant(Nil)), aggregate = true, includeRoot = true) apply {
            _.join.map(_.flatten)
        }
    )

  val publishSettings = Seq(
    publishTo := Some(Resolver.file("Local Test Repository", Path fileProperty "java.io.tmpdir" asFile)),
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
  )


}

object Dependencies {
  val specs2 = "org.specs2" %% "specs2" % "1.6.1" % "test" withSources()

  def servletApi = "org.mortbay.jetty" % "servlet-api" % "2.5-20081211" % "provided"

  def jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.v20100331" % "container" withSources()

  def rhino = "rhino" % "js" % "1.7R2" withSources()

  def sjson = "net.debasishg" %% "sjson" % "0.15" withSources()

  def freemarker = "org.freemarker" % "freemarker" % "2.3.18" withSources()

  def atmosphere_version = "0.7.1"

  def atmosphere_runtime = "org.atmosphere" % "atmosphere-runtime" % atmosphere_version withSources()

  def logback_classic = "ch.qos.logback" % "logback-classic" % "0.9.24"

  def logback_core = "ch.qos.logback" % "logback-core" % "0.9.24"

  //def slf4s = "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.2" withSources

  //def slf4jLog4j(scope: String) = "org.slf4j" % "slf4j-log4j12" % "1.6.1" % scope

  def guice = "com.google.inject" % "guice" % "3.0" withSources()

  def guiceServlet = "com.google.inject.extensions" % "guice-servlet" % "3.0" withSources()

  def mysql = "mysql" % "mysql-connector-java" % "5.1.16"

  def h2database = "com.h2database" % "h2" % "1.3.155" % "test" withSources()

  def snakeYaml = "org.yaml" % "snakeyaml" % "1.8" withSources()

  def casbah_core = "com.mongodb.casbah" %% "casbah-core" % "2.1.5-1" withSources()

  def commonsBeanutils = "commons-beanutils" % "commons-beanutils" % "1.8.3" withSources()

  def commonsCollections = "commons-collections" % "commons-collections" % "3.2.1" withSources()

  def commonsLogging = "commons-logging" % "commons-logging" % "1.1.1" withSources()

  def jerichoHtml = "net.htmlparser.jericho" % "jericho-html" % "3.2" withSources()

  def wicket = "org.apache.wicket" % "wicket-core" % "1.5.2"

  def dwr = "org.directwebremoting" % "dwr" % "3.0.M1"

  def lift = "net.liftweb" %% "lift-webkit"% "2.4-M4" withSources()
}

object LeonBuild extends Build {
  import BuildSettings._
  import Dependencies._

  resolvers ++= Seq(
    "Sonatype OSS Repo" at "http://oss.sonatype.org/content/repositories/releases",
    "Scala Tools Releases Repo" at "http://scala-tools.org/repo-releases",
    "Official Maven2 Repo" at "http://repo2.maven.org/maven2")

  val coreDeps = Seq(
    specs2,
    logback_classic,
    logback_core,
    servletApi,
    freemarker,
    rhino,
    atmosphere_runtime,
    guice,
    guiceServlet,
    sjson,
    snakeYaml,
    casbah_core,
    commonsBeanutils,
    commonsCollections,
	commonsLogging,
    jerichoHtml,
    h2database,
    wicket,
    lift,
  dwr)

  lazy val parent = Project(
    "parent",
    file("."),
    settings = buildSettings ++
    Seq(libraryDependencies += jetty7) ++
    container.deploy(
      "/" -> main
    )
    ) aggregate(main)

  lazy val main = Project(
    "main",
    file("main"),
    settings = buildSettings ++ webappSettings ++ publishSettings ++
      Seq(libraryDependencies ++= coreDeps)
  )

  lazy val container = Container("container")
}
