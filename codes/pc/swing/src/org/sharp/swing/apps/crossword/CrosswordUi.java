package org.sharp.swing.apps.crossword;

import java.awt.Container;
import java.util.Map;
import java.util.Properties;

import javax.swing.JScrollPane;

import org.sharp.intf.Pluggable;

import edu.ncsu.csc517.dpp11.data.AngryPuzzle;

public class CrosswordUi implements Pluggable {

	public Container getUi() {
		Map wordList = new Properties();
		wordList.put("lace", "****d up");
		wordList.put("grovel", "lie or crawl with show of fear or humility");
		wordList.put("cork", "extract **** out of bottle");
		wordList.put("astride", "with one leg on each side");
		wordList.put("dash", "a **** of sand");
		/*wordList.put("prospect", "wide view of landscape");

		wordList.put("smog", "smoke and fog");
		wordList.put("moor", "high land");
		wordList.put("malt", "related grain");
		wordList.put("sprinkle", "scatter in drops or particles");
		wordList.put("don", "****");
		wordList.put("deposit", "save money in bank for interest");*/
		return new JScrollPane(new PuzzleDisplay(new AngryPuzzle(wordList,1)));
	}

	public AppLifeCycle lifeCycle() {
		return null;
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI() {
			public Container getUI() {
				return getUi();
			}
			public String tabName() {
				return "Crossword";
			}
			public String tabDescription() {
				return "Cross word";
			}
		};
	}
}
