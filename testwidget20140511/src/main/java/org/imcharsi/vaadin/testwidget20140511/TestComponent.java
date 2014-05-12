package org.imcharsi.vaadin.testwidget20140511;

import com.vaadin.ui.AbstractComponent;
import org.imcharsi.vaadin.testwidget20140511.client.shared.TestComponentState;

/**
 * Created by KangWoo,Lee on 14. 5. 12.
 */
public class TestComponent extends AbstractComponent {
    @Override
    protected TestComponentState getState() {
        return (TestComponentState) super.getState();
    }

    public void setText(String s) {
        getState().text = s;
    }
}
