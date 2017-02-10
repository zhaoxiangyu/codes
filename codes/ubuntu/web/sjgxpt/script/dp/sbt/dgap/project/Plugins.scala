import sbt._
import Keys._

object Plugins extends Build {
  lazy val root = Project("root", file(".")).settings(
    addSbtPlugin("com.github.shivawu" % "sbt-maven-plugin" % "0.1.2")
  )
}
