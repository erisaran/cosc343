import java.util.Scanner;
import java.util.Random;

public class GeneticApp{
  
  static int [] [] world_array = new int[10] [10];
  static int [] [] strawb_array = new int[10] [10];
  static int [] [] mush_array = new int[10] [10];
  
  public static void main(String[] args){
    int step = 1;
    create_world();
    Scanner scan = new Scanner(System.in);
    boolean loop = true;
    String input = "";
    while (loop){
      print_world();
      input = scan.nextLine();
      if (input.equals("s")) {
        loop = false;        
      }else if (input.equals("")){
        loop = true;
      }
    }
//    while (step < 10){
//      print_world();
//      step++;
//      try{
//        
//        Thread.sleep(1000);
//      }catch(Exception e)
//      {
//        System.out.println("Exception caught");
//      }
    
  }
  
  public static void print_world(){
    for (int [] i: world_array){
      for (int j: i){
        System.out.print(j + " ");
      }
      System.out.println();
    }
    System.out.println(" ");
  }
  
  public static void create_world(){
//    for (int [] i:world_array){
//      for (int j: i){
//        j = 0;
//      }
//    }
//    for (int [] i:strawb_array){
//      for (int j: i){
//        j = 0;
//      }
//    }
//    for (int [] i:mush_array){
//      for (int j: i){
//        j = 0;
//      }
//    }
    int q = 0;
    int p = 0;
    for (int [] i:strawb_array){
      for (int j: i){
        Random r = new Random();
        int chance = r.nextInt(100);
        i = r.nextInt(10);
        if ( chance < 10){
          if (world_array[p][q] == 0){
            world_array[p][q] = 2;
          }
        }
        p ++;
      }
      p = 0;
      q++;
    }
    q = 0;
    p = 0;
    for (int [] i:mush_array){
      for (int j: i){
        Random r = new Random();
        int chance = r.nextInt(100);
        if ( chance < 10){
          if (world_array[p][q] == 0){
            world_array[p][q] = 3;
          }
        }
        p ++;
      }
      p = 0;
      q++;
    }
    q= 0;
  }
}