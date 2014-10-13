package edu.cmu.deiis.annotators;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.GeneTrueAnswerAnnotation;
import edu.cmu.deiis.types.SentenceAnnotation;

public class GeneTrueAnswerAnnotator extends JCasAnnotator_ImplBase {

  /**
   * generate the true answer for the prediction
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    /**
     * Feature structure index access interface
     */
    FSIndex sentenceIndex = aJCas.getAnnotationIndex(SentenceAnnotation.type);
    
    // Iterator to get each sentence annotation
    Iterator sentenceIter = sentenceIndex.iterator();
    /**
     * from sentence content to get the golden standard to evaluate our prediction later
     */
    while (sentenceIter.hasNext()) {
      // Get the content of each sentence
      SentenceAnnotation sentence = (SentenceAnnotation) sentenceIter.next();
      String content = sentence.getCoveredText();
      
      /**
       * trim the preceding or tailing whitespace
       */
      content = content.trim();
      
      if (content.contains("|")) {
        String[] data = content.split("\\|");
        
        /**
         * count the offset of the gene
         */
        int offset = sentence.getBegin() + data[0].length() + 1 + data[1].length() + 1;
        GeneTrueAnswerAnnotation gene = new GeneTrueAnswerAnnotation(aJCas);
        int start = offset;
        int end = offset + data[2].length();
        
        /**
         * get all the parameters to our output
         */
        gene.setBegin(start);
        gene.setEnd(end);
        gene.setCasProcessorId("GeneTrueAnswerAnnotator");
        gene.setId(data[0]);
        gene.setConfidence(1);
        gene.setId(data[0]);
        gene.addToIndexes();
      } 
    }
  }

}
