package com.ww.wjax11.comet.wicket

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.model.Model
import org.apache.wicket.markup.html.form.{Form, TextField}
import org.apache.wicket.ajax.markup.html.form.AjaxButton

class WicketPage extends WebPage {

  private val form = new Form("form")
  add(form)

  private val wordInput = new TextField("word",  new Model("WJAX 2011"))
  wordInput.setOutputMarkupId(true)
  form.add(wordInput)

  form.add(new AjaxButton("reverse") {
    def onSubmit(target: AjaxRequestTarget, form: Form[_]) {
      resultContent.setObject(wordInput.getModelObject.reverse)
      target.add(result)
    }
    def onError(target: AjaxRequestTarget, form: Form[_]) {}
  })

  private val resultContent = new Model[String]
  private val result = new Label("result", resultContent)
  result.setOutputMarkupId(true)
  form.add(result)
}
