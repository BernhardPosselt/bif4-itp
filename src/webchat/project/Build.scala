import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "webchat"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      	// Add your project dependencies here,
       	"mysql" % "mysql-connector-java" % "5.1.18",
 		"commons-lang" % "commons-lang" % "2.6"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // coffeescriptOptions := Seq("bare", "native", "coffee -p")
    )



}
