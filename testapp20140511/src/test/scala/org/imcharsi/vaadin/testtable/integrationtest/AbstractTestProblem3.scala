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
    table1.flatMap(findTableRow(_, "name4")).foreach(click.on)
    table2.flatMap(findTableRow(_, "name80"))
  }

  private var storedScrollTop: Option[String] = None

  "detail table's scrollTop".should("be stored").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    storedScrollTop = table2Scrollable.flatMap(_.attribute("scrollTop"))
  }

  "row 96 in detail table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table2Scrollable.foreach(_.underlying.sendKeys(Keys.PAGE_UP))
    table2.flatMap(findTableRow(_, "name96")).foreach(click.on)
  }

  "row 4 in master table".should("be clicked again").taggedAs(IntegrationTestTag).in {
    table1.flatMap(findTableRow(_, "name3")).foreach(click.on)
    table2.flatMap(findTableRow(_, "name60"))
    table1.flatMap(findTableRow(_, "name4")).foreach(click.on)
    table2.flatMap(findTableRow(_, "name96"))
  }

  "detail table".should("be scrolled into top").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 0))
    table2.flatMap(findTableRow(_, "name80"))
  }

  "detail table".should("be scrolled into bottom").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    table2.flatMap(findTableRow(_, "name103"))
    table2Scrollable.
      flatMap(_.attribute("scrollTop")).
      foreach(s ⇒ assert(storedScrollTop.get == s))
  }
}

