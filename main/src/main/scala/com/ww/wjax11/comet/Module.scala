package com.ww.wjax11.comet

import com.google.inject.servlet.ServletModule
import s1.S1Servlet

class Module extends ServletModule {

  override def configureServlets() {
    bind(classOf[S1Servlet]).asEagerSingleton()
    serve("/s1/servlet").`with`(classOf[S1Servlet])


  }

}
