package org.sharpx.swing.apps.el.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.sharpx.utils.LangUtils;
import org.sharpx.swing.apps.el.beans.Phrase;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX;

public class PhrasesUtils {

	public static JTableUtilsX.ColumnsSupportX<Phrase> briefView(){
		return new JTableUtilsX.ColumnsSupportX<Phrase>(){
			public Object[] columnValues(Phrase phr) {
				return new Object[]{phr.getText(),ArrayUtils.toString(phr.getTags(), ","),
						phr.getOffset()};
			}
			public String[] columnNames() {
				return new String[]{"Text","Tags","Offset"};
			}
			public Integer[] columnWidths() {
				return new Integer[]{10*15,10*3,5*1};
			}
			public int defaultSortColumn() {
				return 2;
			}
			public boolean defaultAsc() {
				return true;
			}};
	}

	public static void addTag(Phrase phr, String tagName) {
		if(LangUtils.contains(phr.getTags(),tagName))
			return;
		phr.setTags(LangUtils.add(phr.getTags(),tagName));
		Arrays.sort(phr.getTags());
	}
	
	public static void removeTag(Phrase phrs, String tagName){
		String[] tags = phrs.getTags();
		if(LangUtils.contains(tags, tagName)){
			tags = LangUtils.remove(tags, tagName);
			phrs.setTags(tags);
		}
	}

	public static void removeTag(Phrase[] phrs, String tagName){
		for (Phrase phrase : phrs) {
			removeTag(phrase,tagName);
		}
	}

	public static List<Phrase> filterWords(List<Phrase> wl,final String[] tags, final boolean include){
		return (List<Phrase>) CollectionUtils.select(wl, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof Phrase){
					String[] pts = ((Phrase)o).getTags();
					ArrayList<String> ptsL = new ArrayList<String>();
					if(pts!=null)
						CollectionUtils.addAll(ptsL, pts);
					else
						return !include;
					ArrayList<String> tagsL = new ArrayList<String>();
					if(tags!=null)
						CollectionUtils.addAll(tagsL, tags);
					return ptsL.containsAll(tagsL)?include:!include;
				}else
					return false;
			}
		});
	}

}
