package org.imcharsi.vaadin.testapp20140511

import com.vaadin.server.VaadinServlet
import javax.servlet.annotation.WebServlet
import com.vaadin.annotations.VaadinServletConfiguration

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
@WebServlet(urlPatterns = Array[String]("/*"), asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = classOf[TestUI])
class TestServlet extends VaadinServlet
