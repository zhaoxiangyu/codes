package org.sharp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.sharp.intf.ContentParser;
import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;
import org.tartarus.snowball.ext.EnglishStemmer;

public class Lucene {
	
	private IndexWriter indexWriter;

	public Lucene(){
		
	}
	
	public void closeIndexWriter(){
		try {
			if(indexWriter != null)
				indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class TextHighlighter {

		public static String[] highlight(String keyword, String text) {
			return highlight(keyword, text, 10, newSentenceFragmenter(),
					newSnowballAnalyzer("English"), new SimpleHTMLFormatter(
							"<span style='background:yellow;color:blue'>",
							"</span>"));
		}

		private static String[] highlight(String keyword, String text,
				int maxNumFragments, Fragmenter fragmenter, Analyzer analyzer,
				Formatter formatter) {
			if (analyzer == null)
				throw new IllegalArgumentException(
						"Analyzer analyzer can not be null.");
			if (formatter == null)
				throw new IllegalArgumentException(
						"Formatter formatter can not be null.");
			if(text == null)
				return new String[0];
			if(LangUtils.isEmpty(keyword))
				return new String[0];

			String[] ret = new String[0];

			String kw = englishStem(keyword).toLowerCase();
			Utils.log.debug("keyword:"+kw);
			/*if(Character.isUpperCase(keyword.charAt(0))){
				kw = LangUtils.capitalize(kw);
			}*/
			TermQuery query = new TermQuery(new Term("field", kw));
			Scorer scorer = new QueryScorer(query);

			Highlighter highlighter = new Highlighter(formatter, scorer);
			highlighter.setTextFragmenter(fragmenter);
			TokenStream tokenStream = analyzer.tokenStream("field",
					new StringReader(text));
			try {
				ret = highlighter.getBestFragments(tokenStream, text,
						maxNumFragments);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
		}
		
		private static Fragmenter newSentenceFragmenter(){
			return new Fragmenter() {
				String ot;
				TokenStream ts;
				public void start(String originalText, TokenStream tokenStream) {
					ot = originalText;
					ts = tokenStream;
					Utils.log.debug("Fragmenter.start");
				}
				public boolean isNewFragment() {

					String term = "";
					if(ts.hasAttribute(CharTermAttribute.class)){
						char[] buffer = ts.getAttribute(CharTermAttribute.class).buffer();
						term = new String(buffer,0,buffer.length);
					}
					if(!ts.hasAttribute(OffsetAttribute.class))
						return false;
					OffsetAttribute oa = ts.getAttribute(OffsetAttribute.class);
					int so = oa.startOffset();
					so = so >=2? so:2;
					Character c1 = ot.charAt(so-1);
					Character c2 = ot.charAt(so-2);
					if((c2 == '.' && !Character.isLetterOrDigit(c1)) || c1 == '.' || c1 == '\n'){
						return true;
					}
					return false;
				}
			};
		}
	}

	public static Analyzer newSnowballAnalyzer(String language) {
		return new SnowballAnalyzer(Version.LUCENE_31, language);																 
	}

	private IndexWriter newIndexWriter(String indexPath)
			throws CorruptIndexException, LockObtainFailedException,
			IOException {
		if(indexWriter!=null){
			return indexWriter;
		}

		Directory dir = FSDirectory.open(new File(indexPath));
		Analyzer analyzer = newSnowballAnalyzer("English");
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31,
				analyzer);

		boolean createOrAppend = false;
		if (createOrAppend) {
			// Create a new index in the directory, removing any
			// previously indexed documents:
			iwc.setOpenMode(OpenMode.CREATE);
		} else {
			// Add new documents to an existing index:
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		// Optional: for better indexing performance, if you
		// are indexing many documents, increase the RAM
		// buffer. But if you do this, increase the max heap
		// size to the JVM (eg add -Xmx512m or -Xmx1g):
		//
		// iwc.setRAMBufferSizeMB(256.0);

		IndexWriter writer = new IndexWriter(dir, iwc);

		// NOTE: if you want to maximize search performance,
		// you can optionally call optimize here. This can be
		// a costly operation, so generally it's only worth
		// it when your index is relatively static (ie you're
		// done adding documents to it):
		//
		// writer.optimize();
		// writer.close();
		indexWriter = writer;
		return writer;
	}

	public static int termFreqVector(String indexPath,String filePath) throws IOException{

		Directory dir;
		dir = FSDirectory.open(new File(indexPath));
		IndexReader ir = IndexReader.open(dir);
		/*for(int i = 0; i< ir.maxDoc();i++){
			Document doc = ir.document(i);
			String fp = doc.get(Indexable.FN_ID);
			if(FilenameUtils.normalize(fp).equals(FilenameUtils.normalize(filePath))){
				TermFreqVector tfv = ir.getTermFreqVector(i, IndexableFile.FN_CONTENTS);
				List<Pair> list = new ArrayList<Pair>();
				for(int j=0;j< tfv.size();j++){
					list.add(new Pair(tfv.getTerms()[j],tfv.getTermFrequencies()[j]));
				}
				Collections.sort(list);
				for (Pair pair : list) {
					System.out.println("term:"+pair.str+
							",frequency:"+pair.i);
				}
			}
		}*/
		TermEnum terms = ir.terms();
		while(terms.next()){
			Term t = terms.term();
			TermPositions tps = ir.termPositions(t);
			while(tps.next()){
				Document doc = ir.document(tps.doc());
				String fp = doc.get(Indexable.FN_ID);
				if(FilenameUtils.normalize(fp).equals(FilenameUtils.normalize(filePath))){
					int i=1;
					while(i++ <= tps.freq()){
						int pn = tps.nextPosition();
						Utils.log.debug("position:"+pn+",term:"+t.text()+",field:"+t.field());
						if(tps.isPayloadAvailable()){
							byte[] pl =tps.getPayload(new byte[0], 0);
							Utils.log.debug("payload:"+new String(pl));
						}
					}
				}
			}
		}
		return 1;
	}
	
	public static interface Indexable {
		public final String FN_ID = "path";
		public final String FN_TYPE = "type";
		public final static String FN_MODIFIED = "modified";
		public final static String FN_CONTENTS = "contents";
		public final static String FN_NW_EXPL = "expl";
		public final static String FN_NW_TAG = "tags";
		public final static String DT_FILE = "file";
		Document toDocument();
		void close();
		String id();
		String docType();
	}

	public static class IndexableFile implements Indexable {
		private File file;
		private InputStream contentStream;

		public IndexableFile(File file) {
			this.file = file;
			try {
				contentStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public IndexableFile(File file, ContentParser contentParser) {
			this.file = file;
			if (contentParser != null)
				contentStream = contentParser.contentStream(file);
		}

		public void close() {
			try {
				contentStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public Document toDocument() {
			if (file.canRead() && !file.isDirectory()) {
				try {
					Document doc = new Document();

					NumericField modifiedField = new NumericField(FN_MODIFIED);
					modifiedField.setLongValue(file.lastModified());
					doc.add(modifiedField);

					// Add the contents of the file to a field named "contents".
					// Specify a Reader,
					// so that the text of the file is tokenized and indexed,
					// but not stored.
					// Note that FileReader expects the file to be in UTF-8
					// encoding.
					// If that's not the case searching for special characters
					// will fail.

					/*
					 * doc.add(new Field(FN_CONTENTS, new BufferedReader( new
					 * InputStreamReader(contentStream, "UTF-8"))));
					 */
					doc.add(new Field(FN_CONTENTS, IOUtils
							.toString(contentStream), Field.Store.YES,
							Field.Index.ANALYZED,
							Field.TermVector.WITH_POSITIONS_OFFSETS ));
					return doc;

				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}
			return null;
		}

		public String id() {
			return file.getPath();
		}

		public String docType() {
			return DT_FILE;
		}
	}

	public static String englishStem(String wd) {
		EnglishStemmer es = new EnglishStemmer();
		es.setCurrent(wd);
		es.stem();
		return es.getCurrent();
	}
	
	public static String genDocId(String type,String id){
		return type+"::"+id;
	}

	public static Pair<String, String> parseDocId(String id){
		int p = id.indexOf("::");
		if(p<0)
			return null;
		else
			return new ImmutablePair<String, String>(id.substring(0,p), 
				id.substring(p+2,id.length()));
	}

	public void deleteAll(String indexPath){
		if (indexPath == null || "".equals(indexPath.trim()))
			return;
		try {
			IndexWriter writer;
			writer = newIndexWriter(indexPath);
			
			writer.deleteAll();
			//writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void indexDoc(String indexPath, Indexable item){
		Utils.log.info("indexDoc");
		if (indexPath == null || "".equals(indexPath.trim()))
			return;
		try {
			IndexWriter writer;
			writer = newIndexWriter(indexPath);
			
			Document doc = item.toDocument();
			Field idField = new Field(Indexable.FN_ID, genDocId(item.docType(),item.id()),
					Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
			idField.setOmitTermFreqAndPositions(true);
			doc.add(idField);

			Field typeField = new Field(Indexable.FN_TYPE, item.docType(),
					Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
			idField.setOmitTermFreqAndPositions(true);
			doc.add(typeField);

			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				writer.addDocument(doc);
			} else {
				writer.updateDocument(
						new Term(Indexable.FN_ID, genDocId(item.docType(),item.id())), doc);
			}
			item.close();

			writer.commit();
			//writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static interface SearchResult {
		TopDocs hits();

		Document doc(int docID);

		void close();
	}

	public static SearchResult queryDocs(String indexPath, String docType,
			String queryString,	String field) throws ParseException, IOException {

		final IndexSearcher searcher = new IndexSearcher(
				FSDirectory.open(new File(indexPath)));
		Analyzer analyzer = newSnowballAnalyzer("English");
		QueryParser parser = new QueryParser(Version.LUCENE_31, field, analyzer);
		Query query = parser.parse(queryString);

		BooleanQuery q = new BooleanQuery();
		q.add(new TermQuery(new Term(
				Indexable.FN_TYPE,docType)), 
			Occur.MUST);
		q.add(query, Occur.MUST);

		final TopDocs results = searcher.search(q, 100);

		Utils.log.info("Searched for: " + query.toString() + ","
				+ results.totalHits + " hits.");
		return new SearchResult() {

			public TopDocs hits() {
				return results;
			}

			public Document doc(int docID) {
				try {
					return searcher.doc(docID);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			public void close() {
				try {
					searcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public static SearchResult queryDocs(String indexPath, String docType,
			String queryString,	String[] fields) throws ParseException, IOException {

		final IndexSearcher searcher = new IndexSearcher(
				FSDirectory.open(new File(indexPath)));
		Analyzer analyzer = newSnowballAnalyzer("English");
		BooleanQuery kq = new BooleanQuery();
		for (String field : fields) {
			QueryParser parser = new QueryParser(Version.LUCENE_31, field, analyzer);
			Query query = parser.parse(queryString);
			kq.add(query, Occur.SHOULD);
		}
		
		BooleanQuery q = new BooleanQuery();
		q.add(new TermQuery(new Term(
				Indexable.FN_TYPE,docType)), 
			Occur.MUST);
		q.add(kq, Occur.MUST);

		final TopDocs results = searcher.search(q, 100);

		Utils.log.info("Searched for: " + queryString + ","
				+ results.totalHits + " hits.");
		return new SearchResult() {

			public TopDocs hits() {
				return results;
			}

			public Document doc(int docID) {
				try {
					return searcher.doc(docID);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			public void close() {
				try {
					searcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private SearchResult queryDocs(String indexPath, Query query) throws ParseException, IOException {

		final IndexSearcher searcher = new IndexSearcher(
				FSDirectory.open(new File(indexPath)));
		final TopDocs results = searcher.search(query, 100);

		Utils.log.info("Searched for: " + query.toString() + ","
				+ results.totalHits + " hits.");
		return new SearchResult() {

			public TopDocs hits() {
				return results;
			}

			public Document doc(int docID) {
				try {
					return searcher.doc(docID);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			public void close() {
				try {
					searcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public static void close(Lucene lucene) {
		if(lucene!=null)
			lucene.closeIndexWriter();
	}
}
