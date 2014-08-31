package org.sharpx.swing.apps.el.beans;

import java.util.Date;

import org.sharpx.swing.beans.ReviewInfo;

public class Article {
	String title;
	int wordCount;
	String filePath;
	Sentence[] sentences;
	Phrase[] phrases;
	String[] tags;
	Phrase[] newWords;
	Tag phraseTag = defaultArticlePhraseTag();
	ReviewInfo ri = new ReviewInfo(new Date());
	String text;
	
	private static Tag defaultArticlePhraseTag(){
		Tag rt = new Tag("..");
		Tag time = new Tag("time");
		Tag people = new Tag("people");
		Tag place = new Tag("place");
		rt.setChildren(new Tag[]{people,time,place});
		return rt;
	}
	
	public Sentence[] getSentences() {
		return sentences;
	}

	public void setSentences(Sentence[] sentences) {
		this.sentences = sentences;
	}

	public Phrase[] getPhrases() {
		return phrases;
	}

	public void setPhrases(Phrase[] phrases) {
		this.phrases = phrases;
	}

	public Article() {
		super();
	}

	public Article(String filePath, String title, String text, Sentence[] sentences, Phrase[] phrases,
			Phrase[] newWords) {
		super();
		this.filePath = filePath;
		this.title = title;
		this.text = text;
		this.sentences = sentences;
		this.phrases = phrases;
		this.newWords = newWords;
	}

	public ReviewInfo getRi() {
		if(ri == null){
			Date now = new Date();
			ri = new ReviewInfo(now);
			ri.touch(now, 0, 0);
		}
		return ri;
	}

	public void setRi(ReviewInfo ri) {
		this.ri = ri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Phrase[] getNewWords() {
		return newWords;
	}

	public void setNewWords(Phrase[] newWords) {
		this.newWords = newWords;
	}

	public Tag getPhraseTag() {
		return phraseTag;
	}

	public void setPhraseTag(Tag phraseTag) {
		this.phraseTag = phraseTag;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		/*result = prime * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + Arrays.hashCode(newWords);
		result = prime * result
				+ ((phraseTag == null) ? 0 : phraseTag.hashCode());
		result = prime * result + Arrays.hashCode(phrases);
		result = prime * result + ((ri == null) ? 0 : ri.hashCode());
		result = prime * result + Arrays.hashCode(sentences);
		result = prime * result + Arrays.hashCode(tags);
		result = prime * result + ((text == null) ? 0 : text.hashCode());*/
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + wordCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		/*if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (!Arrays.equals(newWords, other.newWords))
			return false;
		if (phraseTag == null) {
			if (other.phraseTag != null)
				return false;
		} else if (!phraseTag.equals(other.phraseTag))
			return false;
		if (!Arrays.equals(phrases, other.phrases))
			return false;
		if (ri == null) {
			if (other.ri != null)
				return false;
		} else if (!ri.equals(other.ri))
			return false;
		if (!Arrays.equals(sentences, other.sentences))
			return false;
		if (!Arrays.equals(tags, other.tags))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;*/
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (wordCount != other.wordCount)
			return false;
		return true;
	}
}

class Sentence {
	String text;
}
