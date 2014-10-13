package edu.cmu.deiis.annotators;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.utils.PosTagNamedEntityRecognizer;
import edu.cmu.deiis.types.NamedEntityAnnotation;
import edu.cmu.deiis.types.SentenceAnnotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class NamedEntityAnnotator extends JCasAnnotator_ImplBase {
  /**
   * Citation:
   * Manning, Christopher D., Surdeanu, Mihai, Bauer, John, Finkel, Jenny, Bethard, Steven J., 
   * and McClosky, David. 2014. The Stanford CoreNLP Natural Language Processing Toolkit. 
   * In Proceedings of 52nd Annual Meeting of the Association for Computational Linguistics: 
   * System Demonstrations, pp. 55-60.
   */
  private StanfordCoreNLP pipeline;
  
  /**
   * @see JCasAnnotator_ImplBase#initialize(JCas) The overrided initialize() function that reads all
   *      the path config for gene tagging.
   */
  /**
   * Initializes this resource
   * @throws ResourceInstantiationException
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    /**
     * call the super method from UIMA
     */
    super.initialize(aContext);
    // Get config. parameter values
    /**
     * Apply the StanfordCoreNLP NER to help label sequences of words
     * in a text which are gene and protein names.
     */
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos");
    pipeline = new StanfordCoreNLP(props);
  }
  
  /**
   * @see JCasAnnotator_ImplBase#process(JCas); The overrided process() function that processes jcas
   *      into gene annotations and creates new GeneAnnotation.
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    // TODO Auto-generated method stub
    // Get the input annotations from jcas
    /**
     * Feature structure index access interface
     */
    FSIndex sentenceIndex = aJCas.getAnnotationIndex(SentenceAnnotation.type);

    // Iterator to get each sentence annotation
    Iterator sentenceIter = sentenceIndex.iterator();

    while (sentenceIter.hasNext()) {
      // Get the content of each sentence
      SentenceAnnotation sentence = (SentenceAnnotation) sentenceIter.next();
      String content = sentence.getCoveredText();
      content = content.trim();
      
      /**
       * Check if the annotation match the pattern and extract the data from input
       */
      
      if (!content.contains("|")) {
        String[] data = content.split("\\s");
        
        /**
         * Calculate the start and end, obtaining the data from sentences
         */
        String text = content.substring(data[0].length() + 1);
        /**
         * apply the PosTagNamedEntityRecognizer to get the gene spans
         */
        Map<Integer, Integer> map = PosTagNamedEntityRecognizer.getGeneSpans(pipeline, text);
        
        /**
         * to iterate through all the entries and record down the prediction of NER
         */
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry pairs = (Map.Entry) it.next();

          NamedEntityAnnotation nn = new NamedEntityAnnotation(aJCas);
          int begin = sentence.getBegin() + data[0].length() + (Integer) pairs.getKey();
          int end = sentence.getBegin() + data[0].length() + (Integer) pairs.getValue();
          nn.setBegin(begin + 1);
          nn.setEnd(end + 1);
          nn.setConfidence(1);
          nn.setCasProcessorId("NamedEntityAnnotator");
          nn.setSentence(sentence);
          nn.setId(data[0]);
          nn.addToIndexes();
          it.remove();
        }
      }
    }
  }
}
