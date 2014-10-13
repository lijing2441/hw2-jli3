package edu.cmu.deiis.cas_consumers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.ProcessTrace;

import edu.cmu.deiis.types.ABNERGeneAnnotation;
import edu.cmu.deiis.types.SentenceAnnotation;

/**
 * An Evaluator annotator will evaluator all the score of each answer and display the result in
 * standard output.
 */

public class GeneTaggerEvaluator extends CasConsumer_ImplBase {
  /**
   * Class member, a static variable that refers to the question number.
   */
  private JCas jcas;

  //private HashSet<String> trueGeneSet;

  private HashSet<String> labeledGeneSet;

  private Map<String, Integer> res;

  private String outputPath = null;

  /**
   * Initializes this resource
   * 
   * @throws ResourceInstantiationException
   */
  public void initialize() throws ResourceInitializationException {
    outputPath = (String) getConfigParameterValue("OutputPath");
    //trueGeneSet = new HashSet<String>();
    labeledGeneSet = new HashSet<String>();
    res = new HashMap<String, Integer>();
    
    /*
    BufferedReader br;

    try {
      br = new BufferedReader(new FileReader(((String) getConfigParameterValue("GSPath"))));
      String line = br.readLine();
      while (line != null) {
        line = line.trim();
        if(line != null){
          String[] data = line.split("\\|");
          trueGeneSet.add(data[2]);
        }
        
        line = br.readLine();
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    */

  }

  /**
   * @see JCasAnnotator_ImplBase#process(JCas); The overrided process function that processes jcas
   *      into sentence annotations
   */
  @Override
  public void processCas(CAS aCas) throws ResourceProcessException {
    // TODO Auto-generated method stub
    try {
      jcas = aCas.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    /**
     * mark the position of our prediction - output
     */
    FSIndex labeledIndex = jcas.getAnnotationIndex(ABNERGeneAnnotation.type);
    // Iterator to get each labeled gene annotation
    Iterator labeledIter = labeledIndex.iterator();

    while (labeledIter.hasNext()) {
      // Get the content of each sentence
      ABNERGeneAnnotation labeledGene = (ABNERGeneAnnotation) labeledIter.next();
      labeledGeneSet.add(labeledGene.getCoveredText());

      /**
       * record the position and id of our result
       */
      int base = labeledGene.getSentence().getBegin() + labeledGene.getId().length() + 1;
      SentenceAnnotation sentence = labeledGene.getSentence();
      
      int start = labeledGene.getId().length() + 1;
      
      String labelS = labeledGene.getCoveredText();
      char[] cs = labelS.toCharArray();
      
      int beginOff = 0;
      int endOff = 0;
      for(int i = 0; i < cs.length; i++){
    	  if(cs[i] == ' ')
    		  beginOff++;
    	  else
    		  break;
      }
      
      for(int i = cs.length - 1; i >= 0; i--){
    	  if(cs[i] == ' ')
    		  endOff++;
    	  else
    		  break;
      }
      
      labeledGene.setBegin(labeledGene.getBegin() + beginOff);
      labeledGene.setEnd(labeledGene.getEnd() - endOff);
      
      String offsetS = sentence.getCoveredText().substring(start, labeledGene.getBegin() - labeledGene.getSentence().getBegin());
      
      
      if(offsetS.length() != 0){
    	  base += offsetS.split("\\s").length;
    	  
          char c = offsetS.toCharArray()[offsetS.length() - 1];
          if(c != ' ')
        	  base -= 1;
      }
      
      int begin = labeledGene.getBegin() - base;
      int end = labeledGene.getEnd() - base - labeledGene.getCoveredText().split("\\s").length;
      String s = labeledGene.getId() + "|" + begin + " " + end + "|" + labeledGene.getCoveredText();
      //System.out.println(s);
      res.put(s, base);
    }
    return;
  }

  @Override
  /**
   * @see JCasAnnotator_ImplBase#process(JCas); The overrided process function that processes jcas
   *      into sentence annotations
   */
  public void collectionProcessComplete(ProcessTrace arg0) throws ResourceProcessException,
          IOException {

    super.collectionProcessComplete(arg0);

    printResult();
  }

  /**
   * print the result out based on the performance
   */
  private void printResult() throws FileNotFoundException, UnsupportedEncodingException {
    List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
            res.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
      public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        return e1.getValue() - e2.getValue();
      }
    });

    PrintWriter writer = new PrintWriter(outputPath, "UTF-8");
    for (int i = 0; i < list.size(); i++){
    	//System.out.println(list.get(i).getKey());
    	writer.println(list.get(i).getKey());
    }
      
    writer.close();
    
    /*
    int bingo = 0;
    int totalP = labeledGeneSet.size();
    int totalR = trueGeneSet.size();

    for (String s : labeledGeneSet) {
      if (trueGeneSet.contains(s))
        bingo++;
    }

    double recall = (double) bingo / totalR;
    double precision = (double) bingo / totalP;
    double f_measure = (double) 2 * recall * precision / (recall + precision);

    System.out.println("----------------------------------------------------");
    System.out.println("****************************************************");
    System.out.println("----------------------------------------------------");
    System.out.println("System Performance Evaluation:");
    System.out.println("Precision: " + precision);
    System.out.println("Recall: " + recall);
    System.out.println("F_measure: " + f_measure);
    System.out.println();
    */
  }
}
