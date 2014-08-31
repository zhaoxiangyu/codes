package org.sharpx.swing.apps.el.beans.term.beans;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.sharpx.utils.intf.Converter;
import org.sharpx.utils.FjUtils;
import org.sharpx.utils.LangUtils;


public class PartOfSpeech{
	
	private String partofspeech;
	private String ukPro;
	private String usPro;

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
	
	public String proString(){
		return "BrE: "+getUkPro()+" NAmE: "+getUsPro();
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

	public String getUkPro() {
		return ukPro;
	}

	public void setUkPro(String ukPro) {
		this.ukPro = ukPro;
	}

	public String getUsPro() {
		return usPro;
	}

	public void setUsPro(String usPro) {
		this.usPro = usPro;
	}

}

