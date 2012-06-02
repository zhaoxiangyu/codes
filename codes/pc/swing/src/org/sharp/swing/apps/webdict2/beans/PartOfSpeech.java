package org.sharp.swing.apps.webdict2.beans;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.sharp.intf.Converter;
import org.sharp.jdkex.FjUtils;
import org.sharp.jdkex.LangUtils;


public class PartOfSpeech{
	
	private String partofspeech;

	private Meaning[] meaning;

	public void setPartofspeech(String partofspeech) {
		this.partofspeech = partofspeech;
	}

	public String getPartofspeech() {
		return partofspeech;
	}

	public void setMeaning(Meaning[] meaning) {
		this.meaning = meaning;
	}

	public Meaning[] getMeaning() {
		return meaning;
	}

	public String toString(){
		return explString();/*ToStringBuilder.reflectionToString(this,Utils.toStringStyle);*/
	}
	
	public String explString(){
		final StringBuffer ret = new StringBuffer(partofspeech);
		if(meaning != null){
			List<Meaning> meaningsl = Arrays.asList(meaning);
			CollectionUtils.forAllDo(meaningsl, new Closure(){
				public void execute(Object input) {
					ret.append("\n\n").append(((Meaning)input).explString());
				}
			});
		}
		return ret.toString();
	}
	
	public String examplesString(){
		String[] examplesStrings = FjUtils.convertTo(meaning, 
				String[].class, new Converter<Meaning,String>(){
					public String to(Meaning meaning) {
						return meaning.exampleString();
					}
		});
		return LangUtils.join(examplesStrings, "\n\n");
	}

	public String htmlString(){
		final StringBuffer ret = new StringBuffer(partofspeech);
		List<Meaning> meaningsl = Arrays.asList(meaning);
		CollectionUtils.forAllDo(meaningsl, new Closure(){
			public void execute(Object input) {
				ret.append("<br>").append(((Meaning)input).htmlString());
			}
		});
		return ret.toString();
	}
}

