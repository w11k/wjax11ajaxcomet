package com.ww.wjax11.comet.lift.snippet

import net.liftweb.http.js.JsCmds.SetValById
import com.ww.wjax11.comet.lift.comet.ChatServer
import net.liftweb.http.SHtml

object Chat {

  def render = SHtml.onSubmit(s => {
    ChatServer ! s
    SetValById("chat_in", "")
  })
}
