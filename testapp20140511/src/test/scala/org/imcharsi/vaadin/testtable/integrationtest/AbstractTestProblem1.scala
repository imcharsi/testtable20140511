package org.imcharsi.vaadin.testtable.integrationtest

import org.scalatest.{ BeforeAndAfterAll, FlatSpec }
import org.scalatest.selenium.WebBrowser
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebDriver

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
abstract class AbstractTestProblem1(override val buttonName: Option[String], override val testWindowName: Option[String])
    extends FlatSpec with WebBrowser with BeforeAndAfterAll with TestUtil {
  "row 19 in master table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table1.foreach(scrollTable(_, 1000))
    table1.flatMap(findTableRow(_, "19")).foreach(click.on)
    waitUtil()
    scrollLayout(1000)
    table2.flatMap(findTableRow(_, "380"))
  }

  "row 418 in detail table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    table2.flatMap(findTableRow(_, "418")).foreach(click.on)
    waitUtil()
  }

  var scrollTop: Option[String] = None

  "layout's scroll".should("be scrolled up into top").taggedAs(IntegrationTestTag).in {
    scrollLayout(0)
    scrollTop = layoutScrollable.flatMap(_.attribute("scrollTop"))
    table1.foreach(scrollTable(_, 0))
    table1.flatMap(findTableRow(_, "0")).map(click.on)
    waitUtil()
  }

  "row 19 in master table".should("be clicked again").taggedAs(IntegrationTestTag).in {
    table1.foreach(scrollTable(_, 1000))
    table1.flatMap(findTableRow(_, "19")).foreach(click.on)
    waitUtil()
    table2.flatMap(findTableRow(_, "418"))
  }

  "layout's scrollTop".should("be at top").taggedAs(IntegrationTestTag).in {
    layoutScrollable.flatMap(_.attribute("scrollTop")).map(s ⇒ assert(s == "0"))
  }
}

