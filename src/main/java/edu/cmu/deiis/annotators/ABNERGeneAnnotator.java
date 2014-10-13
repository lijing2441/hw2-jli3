package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import abner.Tagger;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.ABNERGeneAnnotation;
import edu.cmu.deiis.types.NamedEntityAnnotation;
import edu.cmu.deiis.types.SentenceAnnotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * apply ABNER to improve our prediction quality
 * ABNER is a statistical machine learning system using linear-chain conditional
 * random fields (CRFs) with a variety of orthographic and contextual features.
 * @author Tina
 */
public class ABNERGeneAnnotator extends JCasAnnotator_ImplBase {
  /**
   * Citation:
   * B. Settles (2004). Biomedical Named Entity Recognition Using Conditional 
   * Random Fields and Rich Feature Sets. In Proceedings of the International 
   * Joint Workshop on Natural Language Processing in Biomedicine and its Applications
   *  (NLPBA), Geneva, Switzerland, pages 104-107.
   */
  private static Tagger tagger = null;
  
  /**
   * @see JCasAnnotator_ImplBase#initialize(JCas) The overrided initialize() function that reads all
   *      the path config for gene tagging.
   */
  /**
   * Initializes this resource
   * @throws ResourceInstantiationException
   */
  
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    // System.out.println("TokenNGramAnnotator Initailize In-------------");
    /**
     * call the super method from UIMA
     */
    super.initialize(aContext);
    /**
     * Apply ABNER to help label sequences of words in a text which are gene and protein names.
     */
    tagger = new Tagger();
  }
  
  @Override
  /**
   * @see JCasAnnotator_ImplBase#process(JCas); The overrided process() function that processes jcas
   *      into gene annotations and creates new GeneAnnotation.
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    //Map<String, NamedEntityAnnotation> map = new HashMap<String, NamedEntityAnnotation>();
    /**
     * use a hashset to store all the predicted genes recovered so far
     */
    Set<String> dic = new HashSet<String>();
    
    /**
     * Feature structure index access interface
     */
    
    FSIndex neIndex = aJCas.getAnnotationIndex(NamedEntityAnnotation.type);

    // Iterator to get each sentence annotation
    Iterator neIter = neIndex.iterator();
    while(neIter.hasNext()){
      NamedEntityAnnotation ne = (NamedEntityAnnotation) neIter.next();
      dic.add(ne.getCoveredText());
    }    
    
    FSIndex sentenceIndex = aJCas.getAnnotationIndex(SentenceAnnotation.type);

    // Iterator to get each sentence annotation
    Iterator sentenceIter = sentenceIndex.iterator();
    
    /**
     * Iterate through all the sentence input
     */
    while (sentenceIter.hasNext()) {
      // Get the content of each sentence
      SentenceAnnotation sentence = (SentenceAnnotation) sentenceIter.next();
      String content = sentence.getCoveredText();
      content = content.trim();

      String[] data = null;
      
      /**
       * Check if the annotation match the pattern and extract the data from input
       */
      
      if (!content.contains("|")) {
        data = content.split("\\s");
        String text = content.substring(data[0].length() + 1);
        /**
         * apply ABNER to recover all the predicted genes
         */
        String[] candidates = tagger.getEntities(text, "PROTEIN");
        
        for(int i = 0; i < candidates.length; i++){
          if(dic.contains(candidates[i])){
        	if(candidates[i].contains("The uterine response"))
        		System.out.println(candidates[i]);
            ABNERGeneAnnotation gene = new ABNERGeneAnnotation(aJCas);
            gene.setConfidence(1);
            gene.setCasProcessorId("ANBERGeneAnnotator");
            int begin = sentence.getBegin() + data[0].length() + 1 + text.indexOf(candidates[i]);
            int end = begin + candidates[i].length();          
            gene.setSentence(sentence);
            gene.setBegin(begin);
            gene.setEnd(end);
            gene.addToIndexes();
            gene.setId(data[0]);
          }
        }
      }
    }
  }
}
