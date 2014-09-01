package org.sharpx.scala

class 数字(value : Int) {
  val v = value
  def 加(num : 数字):数字 = {
    new 数字(v+num.v)
  }
  
  def 等于(num : 数字):Boolean = {
    v == num.v
  }
  
}

trait 数字运算{
  
  val 零 = new 数字(0)
  val 一 = new 数字(1)
}

object ChineseCalc extends 数字运算 {
  
  def 说(){
    println("零 加 一 等于 一:"+(零 加 一 等于 一))
    println("零 加 零等于 一:"+(零 加 一 等于 零))
  }
  
  def main(args : Array[String]) : Unit = {
    说
  }
}