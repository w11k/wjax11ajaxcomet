package com.ww.wjax11.comet.dwr

import org.directwebremoting.WebContextFactory

class DwrCometChatServer {
  def update(message: String): Unit = {

    var utilAll: org.directwebremoting.proxy.dwr.Util = null

    val wctx= WebContextFactory.get
    System.out.println("wctx = " + wctx)
    val currentPage = wctx.getCurrentPage
    System.out.println("currentPage = " + currentPage)
    val sessions = wctx.getScriptSessionsByPage(currentPage)
    System.out.println("sessions = " + sessions)

    if(utilAll==null)
      utilAll = new org.directwebremoting.proxy.dwr.Util(sessions)

    System.out.println("utilAll = " + utilAll)

    utilAll.addRows("messageTable", Array[Array[String]] ( Array(message) ))

			// for filter, so the message is just sent to specific script session and no to be based on page
			/*
			System.out.println("filterSession "+filter);
			*/
  }

}