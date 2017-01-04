import java.util.*;

public class Dice {

  public static int numSamples = 10000;
  public static int dieSize = 6;
  
    public static void main(String[] args) {        

        for (int p1 = 1; p1 < 7; p1++) {
          for (int p2 = p1; p2 < 7; p2++) {
            compete(p1,p2);
          }
        }
    }

  public static void compete(int p1, int p2) {
      int p1Wins = 0;
      int ties = 0;
      for (int s = 0; s < numSamples; s++) {
          int r1 = nDmDBA(p1,dieSize);
          int r2 = nDmDBA(p2,dieSize);
          if (r1 > r2)
            p1Wins++;
          else if (r1 == r2)
            ties++;
      }
      System.out.println("after "+numSamples+" trials "+p1+"D"+dieSize+" beats/ties/loses to "+p2+"D"+dieSize+" "+((double)p1Wins/numSamples)+"/"+((double)ties/numSamples)+"/"+((double)(numSamples-(p1Wins+ties))/numSamples));
  }
  
   public static int nDmDBA(int n,int m) {
    //System.out.println("nDmKiller "+n+" "+m);
    
    int total = 0;
    
    Random rand = new Random(); 
    
    int value = rand.nextInt(m-1) + 1 + n;
    
    return value;
  }
  
  public static int nDmKiller(int n,int m) {
    //System.out.println("nDmKiller "+n+" "+m);
    
    int total = 0;
    
    Random rand = new Random(); 
    
    for (int i = 0; i < n; i++) {
       int value = rand.nextInt(m-1) + 1;
       if (value > total)
         total += value;
       //System.out.println("roll = "+value+" total "+total);
    }
    
    return total;
  }
  
  public static int nDm(int n,int m) {
    //System.out.println("nDm "+n+" "+m);
    
    int total = 0;
    
    Random rand = new Random(); 
    
    for (int i = 0; i < n; i++) {
       int value = rand.nextInt(m-1) + 1; 
       total += value;
       //System.out.println("roll = "+value+" total "+total);
    }
    
    return total;
  }
  
  
}
