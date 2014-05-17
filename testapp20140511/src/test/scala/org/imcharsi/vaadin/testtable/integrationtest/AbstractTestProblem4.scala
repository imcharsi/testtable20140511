package org.imcharsi.vaadin.testtable.integrationtest

import org.scalatest.{ BeforeAndAfterAll, FlatSpec }
import org.scalatest.selenium.WebBrowser
import org.openqa.selenium.{ Keys, WebDriver }
import org.openqa.selenium.chrome.ChromeDriver

/**
 * Created by KangWoo,Lee on 14. 5. 17.
 */
class AbstractTestProblem4(override val buttonName: Option[String], override val testWindowName: Option[String])
    extends FlatSpec with WebBrowser with BeforeAndAfterAll with TestUtil {
  "row 0 in master table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table1.flatMap(findTableRow(_, "0")).foreach(click.on)
    table2.flatMap(findTableRow(_, "0")).foreach(click.on)
  }

  "detail table".should("be scrolled into bottom").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    table2.flatMap(findTableRow(_, "19"))
  }

  private var scrollTop4End: Option[String] = None
  private var scrollTop4PageDown: Option[String] = None

  "detail table's scrollTop".should("be stored").taggedAs(IntegrationTestTag).in {
    scrollTop4End = table2Scrollable.flatMap(_.attribute("scrollTop"))
    scrollTop4End.map(_.toInt).
      foreach(i ⇒ table2.foreach(scrollTable(_, i - 1)))
    scrollTop4PageDown = table2Scrollable.flatMap(_.attribute("scrollTop"))
  }

  "row 0 in master table".should("be clicked again").taggedAs(IntegrationTestTag).in {
    table1.flatMap(findTableRow(_, "1")).foreach(click.on)
    table2.flatMap(findTableRow(_, "20"))
    table1.flatMap(findTableRow(_, "0")).foreach(click.on)
    table2.flatMap(findTableRow(_, "0"))
  }

  "detail table's scrollTop".should("be scrolled into bottom through PageDown key navigation").taggedAs(IntegrationTestTag).in {
    scrollLayout(1000)
    0.until(4).foreach { i ⇒
      table2Scrollable.map(_.underlying).foreach(_.sendKeys(Keys.PAGE_DOWN))
      this.synchronized(wait(300))
    }
    table2.foreach(findTableRow(_, "19"))
    assert((table2Scrollable.flatMap(_.attribute("scrollTop")).get == scrollTop4PageDown.get)
      || (table2Scrollable.flatMap(_.attribute("scrollTop")).get == scrollTop4End.get))
  }

  "detail table's scrollTop".should("be scrolled into top through PageUp key navigation").taggedAs(IntegrationTestTag).in {
    0.until(4).foreach { i ⇒
      table2Scrollable.map(_.underlying).foreach(_.sendKeys(Keys.PAGE_UP))
      this.synchronized(wait(300))
    }
    table2.foreach(findTableRow(_, "0"))
    assert(table2Scrollable.flatMap(_.attribute("scrollTop")).get == "0")
  }

  "detail table's scrollTop".should("be scrolled into bottom through End key navigation").taggedAs(IntegrationTestTag).in {
    table2Scrollable.map(_.underlying).foreach(_.sendKeys(Keys.END))
    this.synchronized(wait(300))
    table2.foreach(findTableRow(_, "19"))
    assert(table2Scrollable.flatMap(_.attribute("scrollTop")).get == scrollTop4End.get)
  }

  "detail table's scrollTop".should("be scrolled into top through Home key navigation").taggedAs(IntegrationTestTag).in {
    table2Scrollable.map(_.underlying).foreach(_.sendKeys(Keys.HOME))
    this.synchronized(wait(300))
    table2.foreach(findTableRow(_, "0"))
    assert(table2Scrollable.flatMap(_.attribute("scrollTop")).get == "0")
  }
}
