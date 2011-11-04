package com.ww.wjax11.comet.lift

import net.liftweb.http._
import js.jquery.JQuery14Artifacts
import net.liftweb.sitemap._

class Boot extends Bootable {

  override def boot() {
    LiftRules.setSiteMap(siteMap())

    LiftRules.addToPackages("com.ww.wjax11.comet.lift")
    LiftRules.jsArtifacts = JQuery14Artifacts
    LiftRules.htmlProperties.default.set { req: Req => new Html5Properties(req.userAgent) }
    LiftRules.passNotFoundToChain = true
  }
  
  def siteMap() = SiteMap(
    Menu(S ? "Ajax") / "lift" / "ajax",
    Menu(S ? "Comet") / "lift" / "comet"
  )  
}