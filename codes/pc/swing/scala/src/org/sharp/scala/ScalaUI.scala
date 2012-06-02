package org.sharp.scala

import scala.swing._
import java.awt.Dimension

object MainUI extends SimpleSwingApplication {
	def top = new MainFrame {
		title = "Greetings"
		preferredSize = new Dimension(200,100)
		val label = new Label {
			text = "Hello World!"
			font = new java.awt.Font("Verdana",
				java.awt.Font.BOLD,22)
		}

		contents = new GridBagPanel {
			var c = new Constraints
			c.gridwidth = java.awt.GridBagConstraints.REMAINDER
			add(label, c)
			background = java.awt.Color.yellow
			border = Swing.EmptyBorder(15, 15, 15, 15)
		}
	}
}