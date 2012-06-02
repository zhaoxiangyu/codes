package org.sharp.scala

import org.sharp.utils.scala._
import java.io.ByteArrayInputStream
import java.net.Socket
import scala.math._

object ScalaApp extends BaseApp with VersionSupport {
	var loggerName : String = "ScalaApp"
	var versionCode : String = "1.0"
	  
	val i,j,k : Int = 1
	
	def main(args : Array[String]) : Unit = {
	  	/*val ia : Array[Int] = new Array[Int](3)
	  	var tp : Pair[Int,Int] = (1,2)
		
	  	loggerName = "ScalaApp2"
		println("hi"+tp._1)
		println("hi"+ia(0))
		printVersion
		printResult("hi","world",super.concat)
		printResult("hi","world",concat)
		su2.printTime
		println(new BaseApp().instanceCount.value)
		su2.testClose(new ByteArrayInputStream("".getBytes))
		su2.testClose(new Socket(){	override def close() = println("closing socket...")})
	  	 */
	}
	
	override def concat(str1:String,str2:String):String = {
		str1 + "_"+ str2
	}
	
	def area(factor:Float,length:Float,width:Float) = factor * length * width
	
	def ellipse(a: (Float,Float,Float) => Float, l:Float, w:Float) = a(Pi.toFloat/4.0f,l,w)

	def ellipse2(l:Float, w:Float) = area(Pi.toFloat/4.0f,l,w)

	val ellipse3:(Float,Float) => Float = (l,w) => area(Pi.toFloat/4.0f,l,w)
	
	def ellipse4(l:Float, w:Float) = ellipse3(l,w)
}

trait VersionSupport {
	var versionCode : String
	def printVersion : Unit = {
		println(versionCode)
	}
}

class BaseApp {
	
	object instanceCount {
		var value = 0
	}
  
	instanceCount.value += 1

	def printResult(prefix:String, msg: String, f: (String, String) => String) : Unit = {
		println(f(prefix,msg))
	}
	
	protected def concat(str1:String,str2:String):String = {
		str1 + " "+ str2
	}
	
}
