package org.sharpx.swing.beans;

import java.lang.reflect.Array;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

public class RecentItems {
	public static class Item {
		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public Date getVisitTime() {
			return visitTime;
		}

		public void setVisitTime(Date visitTime) {
			this.visitTime = visitTime;
		}

		String item;
		Date visitTime;
		
		public Item() {
			super();
		}

		public Item(String item, Date visitTime) {
			super();
			this.item = item;
			this.visitTime = visitTime;
		}
		
		public String toString(){
			return FilenameUtils.getBaseName(item);
		}
	}

	Item[] items =(Item[]) Array.newInstance(Item.class, 0)/*= new Item<T>[0]()*/;
	int maxLength;
	
	public void addItem(String o){
		if(items.length > maxLength){
			items = (Item[]) ArrayUtils.remove(items, 0);
		}
		
		if(items.length>=1){
			Item lastItem = items[items.length-1];
			if(lastItem.getItem().equals(o))
				lastItem.setVisitTime(new Date());
			else
				items = (Item[]) ArrayUtils.add(items, new Item(o,new Date()));
		}else{
			items = (Item[]) ArrayUtils.add(items, new Item(o,new Date()));
		}
	}

	public Item[] fetchItems() {
		Item[] re = (Item[]) ArrayUtils.clone(items);
		ArrayUtils.reverse(re);
		return re;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public RecentItems(int maxLength) {
		super();
		this.maxLength = maxLength;
	}

	public RecentItems() {
		super();
	}
	
}
