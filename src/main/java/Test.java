import abner.Tagger;

public class Test {
  public static void main(String[] args) {
      String s = "Comparison with alkaline phosphatases and 5-nucleotidase";
      Tagger tagger = new Tagger();
      String[] ss = tagger.getEntities(s, "PROTEIN");
      
      for(int i = 0; i < ss.length; i++){
        System.out.println(ss[i]);
      }
  }
}
