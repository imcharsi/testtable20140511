package org.imcharsi.vaadin.testapp20140511;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Created by KangWoo,Lee on 14. 5. 12.
 */
@WebServlet(urlPatterns = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = TestUI.class)
public class TestServlet extends VaadinServlet {
}
