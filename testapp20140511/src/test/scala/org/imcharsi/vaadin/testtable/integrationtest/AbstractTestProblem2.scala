package org.imcharsi.vaadin.testtable.integrationtest

import org.scalatest.{ BeforeAndAfterAll, FlatSpec }
import org.scalatest.selenium.WebBrowser
import org.openqa.selenium.{ Keys, WebDriver }
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
class AbstractTestProblem2(override val buttonName: Option[String], override val testWindowName: Option[String])
    extends FlatSpec with WebBrowser with BeforeAndAfterAll with TestUtil {
  "row 17 in master table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table1.foreach(scrollTable(_, 1000))
    table1.flatMap(findTableRow(_, "name17")).foreach(click.on)
    table2.flatMap(findTableRow(_, "name340"))
  }

  "rows 375,376 in detail table".should("be clicked").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    val idList = "name375" :: "name376" :: Nil
    val elemList = idList.flatMap(i ⇒ table2.flatMap(findTableRow(_, i)))
    val actions = new Actions(webDriver).keyDown(Keys.CONTROL)
    elemList.
      foldLeft(actions)((a, b) ⇒ a.click(b.underlying)).
      perform()
  }

  "detail table".should("be scrolled correctly1").taggedAs(IntegrationTestTag).in {
    assertUtil("300", "name18", "name375", "name376")
  }

  "detail table".should("be scrolled correctly2").taggedAs(IntegrationTestTag).in {
    assertUtil("0", "name19", "name380")
  }

  "detail table".should("be scrolled correctly3").taggedAs(IntegrationTestTag).in {
    assertUtil("300", "name18", "name375", "name376")
  }

  "detail table".should("be scrolled correctly4").taggedAs(IntegrationTestTag).in {
    assertUtil("640", "name17", "name375", "name376")
  }

  "detail table".should("be scrolled correctly5").taggedAs(IntegrationTestTag).in {
    assertUtil("0", "name16", "name320")
  }

  "detail table".should("be scrolled into bottom").taggedAs(IntegrationTestTag).in {
    table2.foreach(scrollTable(_, 1000))
    table2.foreach(findTableRow(_, "name355"))
  }

  "detail table".should("be scrolled into top").taggedAs(IntegrationTestTag).in {
    assertUtil("0", "name15", "name300")
  }

  private def assertUtil(expectedText: String, masterRowText: String, detailRowText2: String*): Unit = {
    scrollLayout(0)
    table1.flatMap(findTableRow(_, masterRowText)).foreach(click.on)
    scrollLayout(1000)
    detailRowText2.foreach(s ⇒ table2.flatMap(findTableRow(_, s)))
    table2Scrollable.flatMap(_.attribute("scrollTop")).
      foreach(s ⇒ assert(s == expectedText))
  }
}
