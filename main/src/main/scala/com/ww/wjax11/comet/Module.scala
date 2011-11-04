package com.ww.wjax11.comet

import com.google.inject.servlet.ServletModule
import plain.PlainServlet
import org.apache.wicket.protocol.http.WicketFilter
import wicket.WicketApplication

class Module extends ServletModule {

  override def configureServlets() {
    import scala.collection.JavaConverters._

    bind(classOf[PlainServlet]).asEagerSingleton()
    serve("/plain/servlet").`with`(classOf[PlainServlet])

    val wicketInitParams = Map(
      "applicationClassName" -> classOf[WicketApplication].getName,
      WicketFilter.FILTER_MAPPING_PARAM -> "/wicket/*"
    )

    bind(classOf[WicketFilter]).asEagerSingleton()
    filter("/wicket/*").through(classOf[WicketFilter], wicketInitParams.asJava)

  }

}
