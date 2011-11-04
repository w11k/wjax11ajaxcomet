package com.ww.wjax11.comet.lift.snippet

import net.liftweb.http._
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._

import scala.xml.Text

class Ajax {
  
  def render = {
    def reverse(str: String) = {
      SetHtml("result", Text(str.reverse))
    }

    "#reverse" #> <a href="#" onclick={ SHtml.ajaxCall(JsRaw("$('#word').val()"), reverse _)._2.toJsCmd }>Reverse</a>
  }
}