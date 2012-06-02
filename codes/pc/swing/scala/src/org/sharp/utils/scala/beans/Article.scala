package org.sharp.utils.scala.beans
import java.util.ArrayList
import org.sharp.utils.scala.XmlPersistable
import scala.collection.immutable.WrappedString
import scala.xml.Node

class Text
case class Article(whole:String,headTitle:String,paras:List[Para],
    newWords:List[Phrase],phrases:List[Phrase]) extends Text {
  def getNewWords() : ArrayList[String] = {
    val wl:ArrayList[String] = new ArrayList[String]()
    for (e <- newWords) wl.add(e.text)
    wl
  }
  def getPhrases(showNp:Boolean,showVp:Boolean,
      showAdjp:Boolean,showAdvp:Boolean,showNewwords:Boolean) : ArrayList[Phrase] = {
    val phs:ArrayList[Phrase] = new ArrayList[Phrase]()
    for (e <- phrases.reverse if (e.shouldShow(showNp,showVp,showAdjp,showAdvp))) 
      phs.add(e)
      
    if(showNewwords)
      for(e <- phrases.reverse if (e.containWords(newWords))) phs.add(e)
      
    phs
  }
  def getPhrasesText(showNp:Boolean,showVp:Boolean,
      showAdjp:Boolean,showAdvp:Boolean) : String = {
    var str = ""
    for (p <- phrases.reverse) str += {
    	if (p.shouldShow(showNp,showVp,showAdjp,showAdvp)) p.text +"\n"
        else "" }
    str
  }
  def getPhrasesText() : String = {
	getPhrasesText(true,true,true,true)
  }
  def getMaskedArticleText(showNp:Boolean,showVp:Boolean,
      showAdjp:Boolean,showAdvp:Boolean) : String = {
    var str = ""
    for (i <- 0 to whole.length -1) {
      str += (if (phrases.exists(
          _.shouldShowChar(i,showNp,showVp,showAdjp,showAdvp))) whole.charAt(i) 
        else if (phrases.exists(
          _.includeChar(i))) if(whole(i)==' ')  ' ' else '_' 
        else whole(i)) }
    str
  }
  def getPhraseSamples() = {
    var str = ""
    var showedCate = Set[Int]()
    for (p <- phrases) {
      str += (if (!showedCate.contains(p.cate)) 
      {showedCate += p.cate;"cate:"+p.cate+" text:"+p.text+"\n"} else "")
    }
    str
  }
}
case class Para extends Text
case class Section(whole:String,offset:Int) extends Para
case class Paragraph(whole:String,sentences:List[Sentence],offset:Int) extends Para
case class Title(whole:String,offset:Int) extends Para
case class UnderPara extends Text
case class Sentence(whole:String,sentParts:List[SentPart],offset:Int) extends UnderPara
case class SentPart(whole:String,offset:Int) extends UnderPara
case class Phrase(text:String,cate:Int,offset:Int) extends UnderPara {
  def isNoun() = cate == 4 //green
  def isVerb() = cate == 2 //blue
  def isAdj() = cate == 7  //orange
  def isAdv() = cate == 12 //purple
  def typ() = cate match {
    case 4 => "noun"
    case 2 => "verb"
    case 7 => "adj"
    case 12 => "adv"
    case _ => "unkown"
  }
  def includeChar(pos:Int) = pos >=offset && pos < offset +text.length
  def shouldShowChar(pos:Int,showNoun:Boolean,showVerb:Boolean,
      showAdj:Boolean,showAdv:Boolean) = {
    includeChar(pos) && shouldShow(showNoun,showVerb,showAdj,showAdv)
  }
  def shouldShow(showNoun:Boolean,showVerb:Boolean,
      showAdj:Boolean,showAdv:Boolean) = {
   ((isNoun && showNoun) ||
        (isVerb && showVerb) ||
        (isAdj && showAdj) ||
        (isAdv && showAdv)) 
  }
  def containWords(words:List[Phrase]) = {
    var exist = false
   	for(w <- words) {
   	  if(text.indexOf(w.text)>=0)
   	    exist = true
   	}
   	exist
  }
  override def toString() = text
}
case class NewWord(docName:String,spell:String,
    partOfSpeech:String,seqNo:Int,ordi:Int,notes:String) extends XmlPersistable[NewWord]{
  
  def toXml(o : NewWord):Node =
    <nw>
	  <docName>{docName}</docName>
      <spell>{spell}</spell>
      <partOfSpeech>{partOfSpeech}</partOfSpeech>
      <seqNo>{seqNo}</seqNo>
      <notes>{notes}</notes>
	</nw>

  def fromXml(el:Node) = 
    NewWord((el \ "docName").text,
        (el \ "spell").text,
        (el \ "partOfSpeech").text,
        new WrappedString((el \ "seqNo").text).toInt,
        1,
        (el \ "notes").text )

}