package org.imcharsi.vaadin.testapp20140511

import scala.beans.BeanProperty

/**
 * Created by KangWoo,Lee on 14. 5. 16.
 */
case class TestBean(@BeanProperty val id: Integer, @BeanProperty val name: String)
