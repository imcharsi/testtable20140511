package org.imcharsi.vaadin.testapp20140511

import com.vaadin.data.util.{ BeanItem, BeanContainer }
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
class TestContainer extends BeanContainer[Integer, TestBean](classOf[TestBean]) {

  private class Resolver extends BeanIdResolver[Integer, TestBean] {
    override def getIdForBean(bean: TestBean): Integer = bean.id
  }

  setBeanIdResolver(new Resolver)

  def generateData1(count: Int): Unit = {
    0.until(count).foreach(addItem(_))
  }

  def generateData2(seed: Int, count: Int): Unit = {
    (seed * count).until(count * seed + count + seed).foreach(addItem(_))
  }

  private def addItem(i: Int): BeanItem[TestBean] = {
    addItem(i, TestBean(i, f"name${i}"))
  }
}
