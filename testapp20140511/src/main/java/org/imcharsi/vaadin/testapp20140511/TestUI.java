package org.imcharsi.vaadin.testapp20140511;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.imcharsi.vaadin.testwidget20140511.TestComponent;
import org.imcharsi.vaadin.testwidget20140511.TestTable;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;

/**
 * Created by KangWoo,Lee on 14. 5. 7.
 */
@Push
@Widgetset("org.imcharsi.vaadin.testwidget20140511.TestTableWidgetset")
public class TestUI extends UI {
    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestBean implements Serializable {
        private Integer id;
        private String name;
    }

    public static class TestContainer extends BeanContainer<Integer, TestBean> {
        private class Resolver implements BeanIdResolver<Integer, TestBean> {
            @Override
            public Integer getIdForBean(TestBean bean) {
                return bean.getId();
            }
        }

        public TestContainer() {
            super(TestBean.class);
            setBeanIdResolver(new Resolver());
        }

        public void generateData1(int count) {
            for (int i = 0; i < count; i++) {
                final TestBean testBean = new TestBean(i, "name" + i);
                addItem(testBean.getId(), testBean);
            }
        }

        public void generateData2(int seed, int count) {
            for (int i = (seed * count); i < (seed * count) + count + seed; i++) {
                final TestBean testBean = new TestBean(i, "name" + i);
                addItem(testBean.getId(), testBean);
            }
        }
    }

    private class TestWindow extends Window {
        private TestContainer container1 = new TestContainer();
        private TestContainer container2 = new TestContainer();
        private TestTable table1 = new TestTable();
        private TestTable table2 = new TestTable();
        private VerticalLayout layout = new VerticalLayout();

        private class ListenerOne implements Property.ValueChangeListener {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                final Integer value = (Integer) event.getProperty().getValue();
                container2.removeAllItems();
                if (value != null) {
                    container2.generateData2(value, 20);
                }
                final Set<Integer> set = (Set<Integer>) table2.getValue();
                if (set.isEmpty()) {
                    table2.setCurrentPageFirstItemIndex(0);
                } else {
                    table2.setCurrentPageFirstItemId(set.iterator().next());
                }
            }
        }

        private TestWindow() {
            container1.generateData1(20);
            layout.addComponents(table1, table2);
            table1.setPageLength(5);
            table1.setSelectable(true);
            table1.setImmediate(true);
            table1.setContainerDataSource(container1);
            table2.setPageLength(5);
            table2.setSelectable(true);
            table2.setImmediate(true);
            table2.setMultiSelect(true);
            table2.setContainerDataSource(container2);
            table1.addValueChangeListener(new ListenerOne());
            setContent(layout);
            setHeight("500px");
            setWidth("400px");
        }
    }

    private VerticalLayout layout = new VerticalLayout();
    private Button button = new Button();
    private TestComponent testComponent = new TestComponent();
    private Button buttonTwo = new Button();

    private class ListenerOne implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            final TestWindow window = new TestWindow();
            getUI().addWindow(window);
        }
    }

    private class ListenerTwo implements Button.ClickListener {
        private final Random random = new Random();

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            testComponent.setText(Integer.toString(random.nextInt()));
        }
    }

    @Override
    protected void init(VaadinRequest request) {
//        layout.addComponents(button, testComponent, buttonTwo);
        layout.addComponents(button);
        button.addClickListener(new ListenerOne());
        buttonTwo.addClickListener(new ListenerTwo());
        setContent(layout);
    }
}
