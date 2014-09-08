package org.sharpx.swing.apps.el.beans;

import java.util.Arrays;

import org.sharpx.utils.LangUtils;
import org.sharpx.swing.beans.ReviewInfo;

public class NewWord {
	String spell;
	String docName;
	int seqNo;
	int pos;

	String partOfSpeech;
	String notes;
	String[] tags;
	private boolean learnPronun;
	private boolean learnSpell;
	private boolean learnExpl;

	int termEntryNo;
	String termName;
	ReviewInfo ri = new ReviewInfo();

	public NewWord() {

	}
	
	public void addTag(String tag){
		if(!LangUtils.contains(tags, tag)){
			tags = LangUtils.add(tags, tag);
			Arrays.sort(tags);
		}
	}

	public void addTags(String[] tags){
		for (String tag : tags) {
			addTag(tag);
		}
	}

	public NewWord(String spell, String docName, String partOfSpeech,
			int seqNo,int pos, String notes) {
		super();
		this.spell = spell;
		this.docName = docName;
		this.partOfSpeech = partOfSpeech;
		this.seqNo = seqNo;
		this.pos = pos;
		this.notes = notes;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public boolean removeTag(String tag) {
		if(LangUtils.contains(tags, tag)){
			tags = LangUtils.remove(tags, tag);
			return true;
		}else
			return false;
	}

	public void setLearnPronun(boolean selected) {
		learnPronun = selected;
	}
	
	public boolean getLearnPronun(){
		return learnPronun;
	}

	public int getTermEntryNo() {
		return termEntryNo;
	}

	public void setTermEntryNo(int termEntryNo) {
		this.termEntryNo = termEntryNo;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public ReviewInfo getRi() {
		return ri;
	}

	public void setRi(ReviewInfo ri) {
		this.ri = ri;
	}

	public boolean isLearnSpell() {
		return learnSpell;
	}

	public void setLearnSpell(boolean learnSpell) {
		this.learnSpell = learnSpell;
	}

	public boolean isLearnExpl() {
		return learnExpl;
	}

	public void setLearnExpl(boolean learnExpl) {
		this.learnExpl = learnExpl;
	}
}
