public class Perceptron{
  
  public static void main(String [] args){
    int [] i1 = {0,1,1,1,0,1,0,0,0,1,1,0,0,0,1,1,0,0,0,1,0,1,1,1,0};
    int o1 = 0;
    for (int i = 0;i< i1.length -1; i++){
      o1 += i1[i];
    }
    System.out.println(o1);
  }
}