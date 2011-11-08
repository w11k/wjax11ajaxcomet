package com.ww.wjax11.comet

import atmosphere.{CometAtmosphereServlet, CometHandler}
import com.google.inject.servlet.ServletModule
import plain.PlainServlet
import org.apache.wicket.protocol.http.WicketFilter
import wicket.WicketApplication
import net.liftweb.http.LiftFilter
import lift.Boot
import org.atmosphere.cpr.AtmosphereServlet
import org.directwebremoting.servlet.DwrServlet

class Module extends ServletModule {

  override def configureServlets() {
    import scala.collection.JavaConverters._

    bind(classOf[PlainServlet]).asEagerSingleton()
    serve("/plain/servlet").`with`(classOf[PlainServlet])

    val wicketInitParams = Map(
      "applicationClassName" -> classOf[WicketApplication].getName,
      WicketFilter.FILTER_MAPPING_PARAM -> "/wicket/*"
    )
    
    val liftInitParams = Map("bootloader" -> classOf[Boot].getName)

    bind(classOf[WicketFilter]).asEagerSingleton()
    filter("/wicket/*").through(classOf[WicketFilter], wicketInitParams.asJava)
    
    bind(classOf[LiftFilter]).asEagerSingleton()
    filter("/*").through(classOf[LiftFilter], liftInitParams.asJava)

    val meteorParams = Map(
      "org.atmosphere.servlet" -> classOf[CometHandler].getName,
      AtmosphereServlet.WEBSOCKET_SUPPORT -> "false",
      AtmosphereServlet.PROPERTY_NATIVE_COMETSUPPORT -> "true"
      ).asJava
    bind(classOf[CometAtmosphereServlet]).asEagerSingleton()
    serve("/cometAtmosphereServlet*").`with`(classOf[CometAtmosphereServlet], meteorParams)

  }

}
