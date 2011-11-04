package com.ww.wjax11.comet.wicket

import org.apache.wicket.protocol.http.WebApplication

class WicketApplication extends WebApplication {

    override def getHomePage = classOf[WicketPage]

}
