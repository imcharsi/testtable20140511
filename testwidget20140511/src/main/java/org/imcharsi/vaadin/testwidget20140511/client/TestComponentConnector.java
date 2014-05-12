package org.imcharsi.vaadin.testwidget20140511.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import org.imcharsi.vaadin.testwidget20140511.TestComponent;
import org.imcharsi.vaadin.testwidget20140511.client.shared.TestComponentState;

/**
 * Created by KangWoo,Lee on 14. 5. 12.
 */
@Connect(TestComponent.class)
public class TestComponentConnector extends AbstractComponentConnector {
    @Override
    public TestComponentWidget getWidget() {
        return (TestComponentWidget) super.getWidget();
    }

    @Override
    protected TestComponentWidget createWidget() {
        return GWT.create(TestComponentWidget.class);
    }

    @Override
    public TestComponentState getState() {
        return (TestComponentState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getWidget().setText(getState().text);
    }
}
