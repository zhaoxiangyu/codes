import sbt._
import Keys._

object DgapBuild extends Build {

  override lazy val settings = super.settings ++
    Seq(
      resolvers := Seq()
    )

//  lazy val root = RootProject(
//    id = "dgap-web",
//    base = file("../../../../udev/codes/dgap/sofn-dgap-web/"),
//    settings = Seq(
//    ))
}
