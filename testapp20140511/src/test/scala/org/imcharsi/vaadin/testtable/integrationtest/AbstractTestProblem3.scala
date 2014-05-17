package org.imcharsi.vaadin.testtable.integrationtest

import org.scalatest.{ BeforeAndAfterAll, FlatSpec }
import org.scalatest.selenium.WebBrowser
import org.openqa.selenium.{ Keys, WebDriver }
import org.openqa.selenium.chrome.ChromeDriver

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
class AbstractTestProblem3(override val buttonName: Option[String], override val testWindowName: Option[String])
    extends FlatSpec with WebBrowser with BeforeAndAfterAll with TestUtil {
  "row 4 in master table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table1.flatMap(findTableRow(_, "4")).foreach(click.on)
    table2.flatMap(findTableRow(_, "80"))
  }

  private var storedScrollTop: Option[String] = None

  "detail table's scrollTop".should("be stored").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    storedScrollTop = table2Scrollable.flatMap(_.attribute("scrollTop"))
  }

  "row 96 in detail table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table2Scrollable.foreach(_.underlying.sendKeys(Keys.PAGE_UP))
    table2.flatMap(findTableRow(_, "96")).foreach(click.on)
    waitUtil()
  }

  "row 4 in master table".should("be clicked again").taggedAs(IntegrationTestTag).in {
    table1.flatMap(findTableRow(_, "3")).foreach(click.on)
    table2.flatMap(findTableRow(_, "60"))
    table1.flatMap(findTableRow(_, "4")).foreach(click.on)
    table2.flatMap(findTableRow(_, "96"))
  }

  "detail table".should("be scrolled into top").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 0))
    table2.flatMap(findTableRow(_, "80"))
  }

  "detail table".should("be scrolled into bottom").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    table2.flatMap(findTableRow(_, "103"))
    table2Scrollable.
      flatMap(_.attribute("scrollTop")).
      foreach(s ⇒ assert(storedScrollTop.get == s))
  }
}

