package org.imcharsi.vaadin.testtable.integrationtest

import org.scalatest.{ BeforeAndAfterAll, FlatSpec }
import org.scalatest.selenium.WebBrowser
import org.openqa.selenium.WebDriver
import org.scalatest.time.{ Milliseconds, Millisecond, Span, Second }
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
trait TestUtil extends FlatSpec with WebBrowser with BeforeAndAfterAll {
  implicit var webDriver: WebDriver = null
  val buttonName: Option[String]
  val testWindowName: Option[String]
  val host = "http://localhost:8080/testapp20140511"
  var testWindow: Option[Element] = None
  var table1: Option[Element] = None
  var table2: Option[Element] = None
  var table1Scrollable: Option[Element] = None
  var table2Scrollable: Option[Element] = None
  var layout: Option[Element] = None
  var layoutScrollable: Option[Element] = None

  def scrollLayout(position: Int): Unit = {
    layout.flatMap(_.attribute("id")).
      map(s ⇒ f"document.getElementById('$s').parentNode").
      map(s ⇒ f"$s.scrollTop = $position").
      foreach(executeScript(_))
  }

  def scrollTable(element: Element, position: Int): Unit = {
    element.attribute("id").
      map(s ⇒ f"document.getElementById('$s').getElementsByClassName('v-scrollable')").
      map(s ⇒ f"$s[0].scrollTop = $position").
      foreach(executeScript(_))
  }

  def findTableRow(element: Element, text: String): Option[Element] = {
    element.attribute("id").
      map(s ⇒ xpath(f"//*[@id='$s']//div/div/table/tbody/tr[contains(., '$text')]")).
      flatMap(find(_))
  }

  def waitUtil(): Unit = {
    this.synchronized(wait(50))
  }

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    //    webDriver = new ChromeDriver()
    webDriver = new FirefoxDriver()
    implicitlyWait(Span(1500, Milliseconds))
    goTo(host)
    buttonName.flatMap(s ⇒ find(id(s))).foreach(click.on)
    testWindow = testWindowName.map(id).flatMap(find(_))
    table1 = find(id("table1"))
    table2 = find(id("table2"))
    layout = find(id("layout"))
    layoutScrollable = layout.flatMap(_.attribute("id")).
      map(s ⇒ xpath(f"//*[@id='$s']/..")).
      flatMap(find)
    table1Scrollable = table1.flatMap(_.attribute("id")).
      map(s ⇒ xpath(f"//*[@id='$s']//*[contains(@class, 'v-scrollable')]")).
      flatMap(find)
    table2Scrollable = table2.flatMap(_.attribute("id")).
      map(s ⇒ xpath(f"//*[@id='$s']//*[contains(@class, 'v-scrollable')]")).
      flatMap(find)
    assert(testWindow.isDefined)
    assert(table1.isDefined)
    assert(table2.isDefined)
    assert(table1Scrollable.isDefined)
    assert(table2Scrollable.isDefined)
    assert(layout.isDefined)
    assert(layoutScrollable.isDefined)
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    webDriver.quit()
  }
}
