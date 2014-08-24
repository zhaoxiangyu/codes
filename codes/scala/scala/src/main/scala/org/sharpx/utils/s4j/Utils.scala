package org.sharpx.utils.s4j

import java.io.{FileInputStream, File}
import java.util.{HashMap, ArrayList}
import org.apache.poi.hwpf.usermodel.CharacterRun
import org.apache.poi.hwpf.HWPFDocument
import org.sharpx.swing.apps.el.beans.{Article,Phrase, NewWord}
import org.sharpx.utils.scala.beans.{Article => Arti, Para => Par, Phrase => Phras, NewWord => NewWor}
import org.sharpx.utils.scala.ArticleParsers
import scala.xml.Elem

class ScalaUtils {
  
	object Converter {
	  
	    def l2al[A,B](
	        l:List[A],f:A => B):ArrayList[B] = {
		  val al = new ArrayList[B]()
		  for(e <- l.reverse) al.add(f(e))
		  al
		}
	    
	    def l2a[A,B](
	        l:List[A],f:A => B):Array[B] = {
		  val al = new ArrayList[B]()
		  for(e <- l.reverse) al.add(f(e))
		  al.toArray().asInstanceOf[Array[B]]
		}
	    
	    def m2hm[A,B,C,D](
	        m:Map[A,B],f:A=>C,f2:B=>D):
	        HashMap[C,D] = {
	      val hm = new HashMap[C,D]()
	      for(e <- m) hm.put(f(e._1),f2(e._2)) 
	      hm
	    }
	    
	}
	
	var f = {nw:NewWor =>
	    new NewWord(nw.spell,nw.docName,nw.partOfSpeech,nw.seqNo,nw.ordi,nw.notes)}
	def f2(arti:Arti):Article = 
	  new Article(null,arti.headTitle,arti.whole, null,
	      c3(arti.phrases),c3(arti.newWords))

	def c1(nwl:List[NewWor]):ArrayList[NewWord] = 
	  Converter.l2al[NewWor,NewWord](nwl,f)
	  
	def c2(nwm:Map[String,List[NewWor]]):HashMap[String,ArrayList[NewWord]] = 
	  Converter.m2hm[String,List[NewWor],String,ArrayList[NewWord]](nwm,{s:String=>s},c1)
	  
	def c3(pl:List[Phras]):Array[Phrase] = {
	  val al = Converter.l2al[Phras,Phrase](pl,{phras => new Phrase(phras.typ,phras.text,phras.offset)})
	  al.toArray(new Array[Phrase](0))
	}
	def c4(pl:List[Phras]):Array[String] = {
	  val al = Converter.l2al[Phras,String](pl,{_.text})
	  al.toArray(Array[String]())
	}
	/*implicit def articleConverter(nw:Arti):Article = null*/
	
	/*Black = "#000000" Green = "#008000" 
	Silver = "#C0C0C0" Lime = "#00FF00" 
	Gray = "#808080" Olive = "#808000" 
	White = "#FFFFFF" Yellow = "#FFFF00" 
	Maroon = "#800000" Navy = "#000080" 
	Red = "#FF0000" Blue = "#0000FF" 
	Purple = "#800080" Teal = "#008080" 
	Fuchsia = "#FF00FF" Aqua = "#00FFFF"*/ 
	
	private val htmlColorTable = Map(0->"Black",1->"Silver",2->"Gray",3->"White",
	    4->"Maroon",5->"Red",6->"Purple",7->"Fuchsia",
	    8->"Green",9->"Lime",10->"Olive",11->"Yellow",
	    12->"Navy",13->"Blue",14->"Teal",15->"Aqua")
	    
	private def colorName(num:Int):String = {
		htmlColorTable.get(num).getOrElse("Unkown Color")
	}
	
	def highlightKeyword(text:String,keyword:String):String = 
		<html>{text}</html>.buildString(false)
		
	def parseArticle(file:File): ArrayList[NewWord] = c1(ArticleParsers.parse(file))
	
	def parseArticle(text:String,docName:String): HashMap[String,ArrayList[NewWord]] =
		c2(ArticleParsers.parse(text,docName))

	def doc2Html(file:File):String = {
		val hwpfDoc = new HWPFDocument(new FileInputStream(file));
		<html><head><title>{
			  val title = hwpfDoc.getSummaryInformation.getTitle
			  Utils.log.info("doc title:"+title)
			  title}
		</title></head><body>{
			val ors = hwpfDoc.getOverallRange();
			for {i <- 0 until ors.numParagraphs
			  prs = ors.getParagraph(i) } yield {
			  <p>{
				   for { j <- 0 until prs.numCharacterRuns()
				     crrs = prs.getCharacterRun(j) } yield charRunRangeToSpanTag(crrs)
			  }</p>
			}
		}</body></html>.toString
	}
		
	def fetchDocSummary(filePath:String):Unit = {
		val hwpfDoc = new HWPFDocument(new FileInputStream(new File(filePath)));
		val si = hwpfDoc.getSummaryInformation
		val title = si.getTitle
		println("title:"+title);
		println("applicationName:"+si.getApplicationName);
		println("charCount:"+si.getCharCount);
		println("author:"+si.getAuthor);
		println("comments:"+si.getComments);
		println("createDateTime:"+si.getCreateDateTime);
		println("editTime:"+si.getEditTime);
		println("keywords:"+si.getKeywords);
		println("lastAuthor:"+si.getLastAuthor);
		println("lastPrinted:"+si.getLastPrinted);
		println("lastSaveDateTime:"+si.getLastSaveDateTime);
		println("pageCount:"+si.getPageCount);
		println("RevNumber:"+si.getRevNumber);
		println("Security:"+si.getSecurity);
		println("Subject:"+si.getSubject);
		println("template:"+si.getTemplate);
		println("wordCount:"+si.getWordCount);
	}
	
	def doc2Article(file:File):Article = {
		val hwpfDoc = new HWPFDocument(new FileInputStream(file));
		val si = hwpfDoc.getSummaryInformation
		val title = si.getTitle
		val wordCount = si.getWordCount 
		val ors = hwpfDoc.getRange();
		
		var (whole:String,paras:List[Par],newWords:List[Phras],phrases:List[Phras],offset:Int) = 
		  ("",Nil,Nil,Nil,0)
		for {i <- 0 until ors.numParagraphs
			prs = ors.getParagraph(i) } {
			  var (pColor,pText,paraText,pOffset) = (0,"","",0)
			  for { j <- 0 until prs.numCharacterRuns()
				crrs = prs.getCharacterRun(j) } {
				  val text = crrs.text
				  if (pColor == crrs.getColor){
				    if (crrs.isHighlighted){
					  newWords ::= new Phras(text,0,offset)
				    }
				    pText += text
				  }else{
				    if (crrs.isHighlighted){
					  newWords ::= new Phras(text,0,offset)
				    }
				    if(pColor != 0)
				    	phrases ::= new Phras(pText,pColor,pOffset)
					pColor = crrs.getColor
					pText = text
					pOffset = offset
				  }
				  offset += text.length
				  whole += text
				  paraText += text
			  }
			  whole += "\n"
			  offset += 1
		      paras ::= toPara(paraText)
		}
		
		var article = f2(Arti(whole,title,paras,newWords,phrases))
		article.setFilePath(file.getPath)
		article.setWordCount(wordCount)
		article
	}
	
	private def toPara(paraText:String) : Par = {
	  null
	}
	
	/*private def toPara(underParas:List[UnderPara]) : Para = {
	  null
	}
	
	private def toUnderParas(text:String) : (List[UnderPara]) = {
	  Nil
	}*/
			
	def charRunRangeToSpanTag(cr:CharacterRun): Elem = {
	  var ret = <span style={"color:"+colorName(cr.getColor%16)}>{cr.text}</span> /*{Some(cr.getColor)}*/
	  Utils.log.info(ret)
	  ret = if (cr.isHighlighted){
		<span style="background:Gray">
		  if (cr.isItalic){
			ret =  <i>
		      if (cr.isBold){
		  	    ret = <b>{ret}</b> 
			}else 
		  	    ret
		    ret
		  </i> 
		  	ret 
		  }else 
		    ret
	    </span>/*;mso-highlight:yellow*/
	  }else ret

	  ret
	}
}