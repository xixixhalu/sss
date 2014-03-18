import play.Project._

import com.github.play2war.plugin._

name := "sss"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.26"
)     

play.Project.playJavaSettings

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"


