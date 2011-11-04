package com.ww.wjax11.comet.lift.comet

import net.liftweb.actor.LiftActor
import net.liftweb.http._

class Chat extends CometActor with CometListener {

  private var msgs = Vector.empty[String]

  def registerWith = ChatServer

  override def lowPriority = {
    case v: Vector[String] => msgs = v; reRender()
  }

  def render = "#messages *" #> msgs.map(msg => <li>{msg}</li>)
}


object ChatServer extends LiftActor with ListenerManager {

  private var msgs = Vector.empty[String]

  def createUpdate = msgs

  override def lowPriority = {
    case s: String => msgs :+= s; updateListeners()
  }
}

