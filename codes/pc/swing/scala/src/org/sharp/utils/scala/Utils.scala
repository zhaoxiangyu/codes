package org.sharp.utils.scala

import java.util.Date

class ScalaUtils {
	def printTime():Unit = {
		println(new Date())
	}
	
	def testClose[A <: {def close():Unit}](o: A){
		o.close
		println(o+" closed.")
	}
	
	/*Black = "#000000" Green = "#008000" 
	Silver = "#C0C0C0" Lime = "#00FF00" 
	Gray = "#808080" Olive = "#808000" 
	White = "#FFFFFF" Yellow = "#FFFF00" 
	Maroon = "#800000" Navy = "#000080" 
	Red = "#FF0000" Blue = "#0000FF" 
	Purple = "#800080" Teal = "#008080" 
	Fuchsia = "#FF00FF" Aqua = "#00FFFF"*/ 
}