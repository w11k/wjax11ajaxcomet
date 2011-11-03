package com.ww.wjax11.comet

import javax.servlet.FilterConfig
import com.google.inject.{Guice, Injector}
import com.google.inject.servlet.GuiceFilter

class Filter extends GuiceFilter {

  private var injector: Injector = _

  override def init(filterConfig: FilterConfig) {
    injector = Guice.createInjector(new Module)
    super.init(filterConfig)
  }

  override def destroy() {
    super.destroy()
  }

}
