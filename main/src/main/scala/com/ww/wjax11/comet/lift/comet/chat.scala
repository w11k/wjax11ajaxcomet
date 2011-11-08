package com.ww.wjax11.comet.lift.comet

import net.liftweb.actor.LiftActor
import net.liftweb.http._

class Chat extends CometActor with CometListener {

  private var messages = List.empty[String]

  def registerWith = ChatServer

  override def lowPriority = {
    case v: List[String] => messages = v; reRender()
  }

  def render = "#messages *" #> messages.map(msg => <li>{msg}</li>)
}


object ChatServer extends LiftActor with ListenerManager {

  private var messages = List.empty[String]

  def createUpdate = messages

  override def lowPriority = {
    case s: String => messages :+= s; updateListeners()
  }
}

