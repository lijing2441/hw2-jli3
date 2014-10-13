

/* First created by JCasGen Sat Sep 27 00:12:40 EDT 2014 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Sep 27 10:44:34 EDT 2014
 * XML source: /Users/Tina/Documents/11791/hw2-jli3/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class Gene extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Gene.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Gene() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Gene(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Gene(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Gene(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: geneId

  /** getter for geneId - gets This feature marks the id of genes.
   * @generated
   * @return value of the feature 
   */
  public String getGeneId() {
    if (Gene_Type.featOkTst && ((Gene_Type)jcasType).casFeat_geneId == null)
      jcasType.jcas.throwFeatMissing("geneId", "edu.cmu.deiis.types.Gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Gene_Type)jcasType).casFeatCode_geneId);}
    
  /** setter for geneId - sets This feature marks the id of genes. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGeneId(String v) {
    if (Gene_Type.featOkTst && ((Gene_Type)jcasType).casFeat_geneId == null)
      jcasType.jcas.throwFeatMissing("geneId", "edu.cmu.deiis.types.Gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((Gene_Type)jcasType).casFeatCode_geneId, v);}    
   
    
  //*--------------*
  //* Feature: Sentence

  /** getter for Sentence - gets This feature describes which sentence this gene in.
   * @generated
   * @return value of the feature 
   */
  public Sentence getSentence() {
    if (Gene_Type.featOkTst && ((Gene_Type)jcasType).casFeat_Sentence == null)
      jcasType.jcas.throwFeatMissing("Sentence", "edu.cmu.deiis.types.Gene");
    return (Sentence)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Gene_Type)jcasType).casFeatCode_Sentence)));}
    
  /** setter for Sentence - sets This feature describes which sentence this gene in. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentence(Sentence v) {
    if (Gene_Type.featOkTst && ((Gene_Type)jcasType).casFeat_Sentence == null)
      jcasType.jcas.throwFeatMissing("Sentence", "edu.cmu.deiis.types.Gene");
    jcasType.ll_cas.ll_setRefValue(addr, ((Gene_Type)jcasType).casFeatCode_Sentence, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    