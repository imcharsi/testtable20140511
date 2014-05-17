package org.imcharsi.vaadin.testapp20140511

import com.vaadin.ui._
import com.vaadin.server.VaadinRequest
import com.vaadin.annotations.{ Widgetset, Push }
import com.vaadin.data.util.BeanContainer
import org.imcharsi.vaadin.testwidget20140511.{ TestComponent, TestTable }
import com.vaadin.data.Property
import java.util
import scala.collection.JavaConversions

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
@Push
@Widgetset("org.imcharsi.vaadin.testwidget20140511.TestTableWidgetset")
class TestUI extends UI {

  class TestWindow extends Window {

    private class ListenerOne extends Property.ValueChangeListener {
      override def valueChange(event: Property.ValueChangeEvent): Unit = {
        container2.removeAllItems()
        Option(event.getProperty.getValue.asInstanceOf[Integer]).
          foreach(container2.generateData2(_, 20))

        val head = JavaConversions.
          asScalaSet(table2.getValue.asInstanceOf[util.Set[Integer]]).
          headOption
        head.map(table2.setCurrentPageFirstItemId(_))
        if (head.isEmpty) {
          table2.setCurrentPageFirstItemIndex(0)
        }
      }
    }

    private def init(): Unit = {

      setCaption("test")
      setId("testWindow")
      table1.setId("table1")
      table2.setId("table2")
      layout.setId("layout")
      container1.generateData1(20)
      layout.addComponents(table1, table2)
      table1.setCaption("master")
      table2.setCaption("detail")
      table1.setPageLength(5)
      table1.setSelectable(true)
      table1.setImmediate(true)
      table1.setContainerDataSource(container1)
      table2.setPageLength(5)
      table2.setSelectable(true)
      table2.setImmediate(true)
      table2.setMultiSelect(true)
      table2.setContainerDataSource(container2)
      table1.addValueChangeListener(new ListenerOne())
      setContent(layout)
      setHeight("200px")
      setWidth("400px")
    }

    val container1: TestContainer = new TestContainer
    val container2: TestContainer = new TestContainer
    val table1: TestTable = new TestTable
    val table2: TestTable = new TestTable
    val layout: VerticalLayout = new VerticalLayout()
    init()
  }

  class TestWindow2 extends Window {

    private class ListenerOne extends Property.ValueChangeListener {
      override def valueChange(event: Property.ValueChangeEvent): Unit = {
        container2.removeAllItems()
        Option(event.getProperty.getValue.asInstanceOf[Integer]).
          foreach(container2.generateData2(_, 20))

        val head = JavaConversions.
          asScalaSet(table2.getValue.asInstanceOf[util.Set[Integer]]).
          headOption
        head.map(table2.setCurrentPageFirstItemId(_))
        if (head.isEmpty) {
          table2.setCurrentPageFirstItemIndex(0)
        }
      }
    }

    private def init(): Unit = {
      setCaption("original")
      setId("testWindow2")
      table1.setId("table1")
      table2.setId("table2")
      layout.setId("layout")
      container1.generateData1(20)
      layout.addComponents(table1, table2)
      table1.setCaption("master")
      table2.setCaption("detail")
      table1.setPageLength(5)
      table1.setSelectable(true)
      table1.setImmediate(true)
      table1.setContainerDataSource(container1)
      table2.setPageLength(5)
      table2.setSelectable(true)
      table2.setImmediate(true)
      table2.setMultiSelect(true)
      table2.setContainerDataSource(container2)
      table1.addValueChangeListener(new ListenerOne())
      setContent(layout)
      setHeight("200px")
      setWidth("400px")
    }

    val container1: TestContainer = new TestContainer
    val container2: TestContainer = new TestContainer
    val table1: Table = new Table()
    val table2: Table = new Table()
    val layout: VerticalLayout = new VerticalLayout()
    init()
  }

  private class ListenerOne(f: Unit ⇒ Window) extends Button.ClickListener {
    override def buttonClick(event: Button.ClickEvent): Unit = {
      getUI().addWindow(f())
    }
  }

  val layout: VerticalLayout = new VerticalLayout()
  val button1: Button = new Button("test")
  val button2: Button = new Button("original")
  val testComponent: TestComponent = new TestComponent

  override def init(p1: VaadinRequest): Unit = {
    layout.addComponents(button1, button2)
    button1.setId("button1")
    button2.setId("button2")
    button1.addClickListener(new ListenerOne(_ ⇒ new TestWindow))
    button2.addClickListener(new ListenerOne(_ ⇒ new TestWindow2))
    setContent(layout)
  }
}

