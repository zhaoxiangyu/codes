<%@ page 
	import="org.sharp.parser.antlr.*,java.net.URLDecoder"
	contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%! 
	JpVocabulary jpVocabulary = new JpVocabulary();
%>
<%
	//request.setCharacterEncoding("UTF-8");
	String jpvoc = request.getParameter("jpvoc");
	out.println("jpvoc:"+jpvoc);
	jpvoc = URLDecoder.decode(jpvoc,"UTF-8");
	/* jpvoc = "ばんりのちょうじょう（万里の長城 〔专〕 万里长城"; */
	Vocabulary voc = new Vocabulary();
	JpVocabulary.parseJpVocx(jpvoc, voc);
	
	out.println("jpvoc:"+jpvoc);
	out.println("pronun:"+voc.pronun);
	out.println("writing:"+voc.writing);
	out.println("partOfSpeech:"+voc.partOfSpeech);
	out.println("expl:"+voc.expl);
%>