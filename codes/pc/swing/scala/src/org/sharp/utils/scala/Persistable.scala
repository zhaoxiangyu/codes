package org.sharp.utils.scala
import scala.xml.XML
import scala.xml.Node

trait Persistable[T] {
  def fromFile(fp:String):T
  def toFile(fp:String,o:T):Unit
}

trait XmlPersistable[T] extends Persistable[T]{
  def toXml(o:T):Node
  def fromXml(el:Node):T
  
  def toFile(fp:String,o:T):Unit = {
    XML.saveFull(fp, toXml(o), "UTF-8", true, null)
  }
  def fromFile(fp:String):T = {
    val el = XML.loadFile(fp)
    fromXml(el)
  }
}