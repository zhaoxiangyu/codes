package org.sharpx.utils.scala
import scala.util.parsing.combinator._
import java.io.File
import java.util.ArrayList
import org.sharpx.utils.scala.beans.NewWord
import java.util.HashMap
import org.apache.poi.hwpf.extractor.WordExtractor
import java.io.FileInputStream

object ArticleParsers extends RegexParsers {
	def segments = rep(segment) ^^ 
		{ case segs => /*JdkUtils.log.info("segments:"+segs);*/ Segments(segs) }
	def segment = (word | noneword | wordlist | failure("error when parsing.")) ^^
		{ case s => s}
	def wordlist : Parser[Segment] = 
		"[[" ~ word ~ "]" ~ rep(opt("""\s+""".r)~>word<~opt("""\s+""".r)) ~ "]" ^^
		{ case "[["~pos~"]"~wl~"]" => WordList(pos,wl) }
	def word : Parser[Phrase] = 
	    """[\-\w]+""".r ^^ 
	    { case w => Phrase(w) }
	/*def word : Parser[Phrase] = {
		val p = """[\-\w]+""".r
		val q = p ^^ 
		  { case w =>Phrase(w) }
		p match {
		  case r@Success(_,_) =>
		}
		q}*/
	def noneword : Parser[Phrase] = ("""[[^\[\]]&&\W]+""".r) ^^ 
		{ case nw => Phrase(nw) }
	
	def parse(file:File) : List[NewWord] = {
		val text = new WordExtractor(new FileInputStream(file)).getText();
		var ret = List[NewWord]()

		parse(text,file.getName,{nw => ret ::= nw})
		ret
	}

	def parse(text:String,docName:String) : Map[String,List[NewWord]] = {
		var ret = Map[String,List[NewWord]]()
		
		parse(text,docName,{nw => ret.get(nw.partOfSpeech) match {
		    case None => ret += (nw.partOfSpeech-> List(nw))
		    case Some(l:List[NewWord]) => ret += (nw.partOfSpeech -> (l :+ nw))
		  }})
		ret
	}

	def parse(text:String,docName:String,f: (NewWord => Unit)) : Unit = {
		val pr = parseAll(segments,text)
		var ordi = 0;
		pr match {
		  case s @Success(out,in1) => pr.get.segs.foreach{
			  case WordList(Phrase(x),y:List[Phrase]) =>
			    var seqNo = 0;
			    y.foreach{ case Phrase(p) => seqNo += 1; ordi += 1;
			      	f(NewWord(docName,p,x,seqNo,ordi,""))/*;println(p)*/ }
			  case _ => }
		  case f :Failure => println(f)
		  case e :Error => println(e)
		}
	}
}

sealed trait Segment
case class Segments(segs: List[Segment]) extends Segment
case class Phrase(text: String) extends Segment
case class WordList(pos: Phrase, wl: List[Phrase]) extends Segment
