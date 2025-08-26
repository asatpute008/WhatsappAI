package Project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import io.opentelemetry.api.trace.Span;

public class SmartChatBot {
	 private StanfordCoreNLP pipeline;

	    public SmartChatBot() {
	        // Configure NLP pipeline
	        Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");
	        pipeline = new StanfordCoreNLP(props);
	    }

	    public String getReply(String userInput) {
	        CoreDocument doc = new CoreDocument(userInput);
	        pipeline.annotate(doc);

	        // Get sentiment of first sentence
	        String sentiment = doc.sentences().get(0).sentiment();
	        
	        System.out.println("sentiment: "+ sentiment);

	        // Get named entities
	        List<String> entities = new ArrayList<>();
	        doc.tokens().forEach(token -> {
	            String ner = token.ner();
	            if (!ner.equals("O")) {
	                entities.add(token.word() + " (" + ner + ")");
	            }
	        });
	        
	        return sentiment;

	    }

}
