package org.sharp.swing.apps.cslideingblock;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JScrollPane;

public class ChineseSlidingBlock {

	public Container getUI(){
		return new JScrollPane(new Board());
	}
	
	public static class Board extends Component{

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;

			g2.setColor(Color.green);
	        g2.fill(new Rectangle(10,10,300,200));
			AlphaComposite ac =
	               AlphaComposite.getInstance(AlphaComposite.SRC,0.0f);
			g2.setComposite(ac);
			g2.setColor(Color.cyan);
			g2.fill(new Rectangle(50,50,50,50));
		}
		
	}
}