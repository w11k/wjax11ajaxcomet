package com.ww.wjax11.comet.s1

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class S1Servlet extends HttpServlet {

  override def doPost(req: HttpServletRequest, res: HttpServletResponse) {
    res.setContentType("application/json")
    res.setCharacterEncoding("utf-8")
    
    val writer = res.getWriter
    val word = req.getParameter("word")
    writer.println("\"" + word.reverse + "\"")
    writer.close()
  }

}
