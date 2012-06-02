package org.sharp.swing.apps.el.beans.term.beans;

import java.util.Arrays;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.sharp.jdkex.LangUtils;

public class Meaning {
	private String no="";
	private String grammar="";
	private String[] inflections;
	
	private String definition="";
	private String[] examples;

	public String toString(){
		return LangUtils.reflectionToString(this);
	}
	
	public String explString(){
		final StringBuffer ret = new StringBuffer(getNo() + " " +
				getGrammar() + " " + getDefinition() + "\n\n");
		/*CollectionUtils.forAllDo(Arrays.asList(
			(String[])ObjectUtils.defaultIfNull(getExamples(),new String[0])),
			new Closure() {
				public void execute(Object input) {
					ret.append(input+"\n\n");
				}
			});*/
		return ret.toString();
	}

	public String exampleString() {
		return LangUtils.join(examples, "\n\n");
	}

	public String htmlString(){
		final StringBuffer ret = new StringBuffer(getNo() + " " + getDefinition() + "<br>");
		CollectionUtils.forAllDo(Arrays.asList(
			(String[])ObjectUtils.defaultIfNull(getExamples(),new String[0])),
			new Closure() {
				public void execute(Object input) {
					ret.append(input+"<br>");
				}
			});
		return ret.toString();
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		return no;
	}

	public void setGrammar(String grammar) {
		this.grammar = grammar;
	}

	public String getGrammar() {
		return grammar;
	}

	public void setInflections(String[] inflections) {
		this.inflections = inflections;
	}

	public String[] getInflections() {
		return inflections;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getDefinition() {
		return definition;
	}

	public void setExamples(String[] examples) {
		this.examples = examples;
	}

	public String[] getExamples() {
		return examples;
	}

}
