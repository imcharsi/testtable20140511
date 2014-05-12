package org.imcharsi.vaadin.testwidget20140511.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by KangWoo,Lee on 14. 5. 12.
 */
public class TestComponentWidget extends Label {
    private class ListenerOne implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            final Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "hi");
        }
    }

    public TestComponentWidget() {
        addClickHandler(new ListenerOne());
    }
}
