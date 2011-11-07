/*
 * Copyright (c) 2011 WeigleWilczek and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.ww.wjax11.comet.atmosphere

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import javax.servlet.ServletConfig
import org.atmosphere.handler.ReflectorServletProcessor
import org.atmosphere.util.XSSHtmlFilter
import org.atmosphere.cpr.{BroadcastFilter, Meteor, AtmosphereServlet}


class CometHandler extends HttpServlet {

  private val filter: List[BroadcastFilter] = List(new XSSHtmlFilter)

  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    import scala.collection.JavaConverters._

    val meteor = Meteor.build(req, filter.asJava, null)
    meteor.suspend(-1, true)
    val writer = meteor.getAtmosphereResource.getResponse.getWriter
    writer.write("\n")

    val thread = new Thread {
      override def run() {
        while (true) {
          // send message
          writer.write("""$$$MESSAGE$$${ "a": "Hello from Server!" }""" + "\n")
          res.flushBuffer()

          Thread.sleep(2000)
        }
      }
    }
    thread.start()
  }

}

class CometAtmosphereServlet extends AtmosphereServlet {

  private val cometHandler = new CometHandler

  protected override def loadConfiguration(sc: ServletConfig) {
    val r = new ReflectorServletProcessor
    r.setServlet(cometHandler)
    addAtmosphereHandler("/*", r)
  }

  override def destroy() {
    super.destroy()
    cometHandler.destroy()
  }

}
