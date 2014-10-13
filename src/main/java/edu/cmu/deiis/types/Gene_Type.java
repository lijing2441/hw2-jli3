
/* First created by JCasGen Sat Sep 27 00:12:40 EDT 2014 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Sat Sep 27 10:44:34 EDT 2014
 * @generated */
public class Gene_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Gene_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Gene_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Gene(addr, Gene_Type.this);
  			   Gene_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Gene(addr, Gene_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Gene.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.Gene");
 
  /** @generated */
  final Feature casFeat_geneId;
  /** @generated */
  final int     casFeatCode_geneId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGeneId(int addr) {
        if (featOkTst && casFeat_geneId == null)
      jcas.throwFeatMissing("geneId", "edu.cmu.deiis.types.Gene");
    return ll_cas.ll_getStringValue(addr, casFeatCode_geneId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGeneId(int addr, String v) {
        if (featOkTst && casFeat_geneId == null)
      jcas.throwFeatMissing("geneId", "edu.cmu.deiis.types.Gene");
    ll_cas.ll_setStringValue(addr, casFeatCode_geneId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Sentence;
  /** @generated */
  final int     casFeatCode_Sentence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSentence(int addr) {
        if (featOkTst && casFeat_Sentence == null)
      jcas.throwFeatMissing("Sentence", "edu.cmu.deiis.types.Gene");
    return ll_cas.ll_getRefValue(addr, casFeatCode_Sentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentence(int addr, int v) {
        if (featOkTst && casFeat_Sentence == null)
      jcas.throwFeatMissing("Sentence", "edu.cmu.deiis.types.Gene");
    ll_cas.ll_setRefValue(addr, casFeatCode_Sentence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Gene_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_geneId = jcas.getRequiredFeatureDE(casType, "geneId", "uima.cas.String", featOkTst);
    casFeatCode_geneId  = (null == casFeat_geneId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_geneId).getCode();

 
    casFeat_Sentence = jcas.getRequiredFeatureDE(casType, "Sentence", "edu.cmu.deiis.types.Sentence", featOkTst);
    casFeatCode_Sentence  = (null == casFeat_Sentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Sentence).getCode();

  }
}



    