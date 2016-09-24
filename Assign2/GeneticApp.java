
import java.util.Scanner;
import java.util.Random;

public class GeneticApp{
  
  static int W = 10; // Sets the dimensions of the world size
  static int T = 15; // Sets the time for each generation
  static int [] [] world_array = new int[W] [W];
  static int [] [] strawb_array = new int[W] [W];
  static int [] [] mush_array = new int[W] [W];
  static Creature [] creatures = new Creature[W];
  static int [][] creature_locations = new int[W][W];
  static Monster [] monsters = new Monster[W/2];
  static int [][] monster_locations = new int[W][W];
  static Creature [] baby_creatures;
  static int generation = 0;
  static int F = 3; // how many steps for a monster move
  
  public static void main(String[] args){
    int step = 0;
    create_world();
    Scanner scan = new Scanner(System.in);
    boolean loop = true;
    String input = "";
    print_world(step);
    while (loop){
      input = scan.nextLine();
      if (input.equals("s")) {
        loop = false;        
      }else if (input.equals("c")){
        for (Creature c : creatures){
          c.Get_Stats();
        }
      }else if (input.equals("")){
        step ++;
        move_timestep(step);
        print_world(step);
      }else if (input.equals("k")){
        step ++;
        move_timestep(step);
        System.out.println(" Gen - " + generation + " Step - " + step + " Number inside ( ) is no. creatures on tile, no. before hyphen is food type 2 = strawberry, 3 = mushroom");
        System.out.println("number after hyphen is the amount of food there, number inside [ ] is how many monsters if any");
      }
    }
    scan.close();
  }
  
  // Prints out the grid to see whats going on
  public static void print_world(int step){
    int x = 0;
    int y = 0;
    for (int q = 0; q < W; q ++){
        System.out.print("________");
      }
    System.out.println();
    for (int [] i: world_array){
      for (int j: i){
        int count = 0;
        if (world_array[y][x] == 2){
          count = strawb_array[y][x];
        }else if(world_array[y][x] == 3){
          count = mush_array[y][x];
        }
        if (monster_locations[y][x] > 0){
          System.out.print("|[" + monster_locations[y][x] + "]" + j + "-" + count + " ");
        }else if (creature_locations[y][x] > 0){
          System.out.print("|(" + creature_locations[y][x] + ")" + j + "-" + count + " ");
        }else{
          System.out.print("|   " + j + "-" + count + " ");
        }
        x++;
      }
      System.out.print("|\n|");
      for (int q = 0; q < W; q ++){
        System.out.print("_______|");
      }
      x = 0;
      System.out.println();
      y++;
    }
    System.out.println(" Gen - " + generation + " Step - " + step + " Number inside ( ) is no. creatures on tile, no. before hyphen is food type 2 = strawberry, 3 = mushroom");
    System.out.println("number after hyphen is the amount of food there, number inside [ ] is how many monsters if any");
  }
  
  //creates the world filled with food, poison, creatures and monsters
  public static void create_world(){
    int y = 0;
    for (int [] i:strawb_array){
      for (int x = 0; x < i.length; x++){
        Random r = new Random();
        int chance = r.nextInt(100);
        if ( chance < 20){
          if (world_array[y][x] == 0){
            world_array[y][x] = 2; 
            strawb_array[y][x] = r.nextInt(5) + 1;
          }
        }
      }
      y++;
    }
    y = 0;
    for (int [] i:mush_array){
      for (int x = 0; x < i.length; x++){
        Random r = new Random();
        int chance = r.nextInt(100);

        if ( chance < 20){
          if (world_array[y][x] == 0){
            world_array[y][x] = 3; 
            mush_array[y][x] = r.nextInt(5) + 1;
          }
        }
        x ++;
      }
      y++;
    }
    y= 0;
    if (generation == 0){
      for (int i = 0; i < creatures.length; i++){
        creatures[i] = new Creature(i,1);
        creature_locations[creatures[i].GetY()][creatures[i].GetX()] += 1;
      }
      for (int i = 0; i < monsters.length; i++){
        monsters[i] = new Monster(i+2, world_array.length -3);
        monster_locations[monsters[i].GetY()][monsters[i].GetX()] += 1;
      }
    }
  }
  
  public static void move_timestep(int step){
    // creatures perform their action
    if (step % 15 == 0){
      generation++;
      Reproduce();
      create_world();
      step ++;
      //creature representations are updated
      for (int i = 0; i < creature_locations.length; i++){
        for (int j = 0; j < creature_locations[i].length; j++){
          creature_locations[i][j] = 0;
        }
      }
      for (int i = 0; i < creatures.length; i++){
        creature_locations[creatures[i].GetY()][creatures[i].GetX()] += 1;
      }
      return;
    }
    //makes the creatures that still live do stuff
    for (int i = 0; i < creatures.length; i++){
      if (creatures[i].Get_Energy() > 0){
        creatures[i].Set_World_Info(strawb_array, mush_array, monster_locations, creature_locations);
        creatures[i].Move();
        if (creatures[i].Has_Ate_Straw()){
          strawb_array[creatures[i].GetY()][creatures[i].GetX()] --;
        }
        if (creatures[i].Has_Ate_Mush()){
          mush_array[creatures[i].GetY()][creatures[i].GetX()] --;
        }
      }
      
    }
    //creature representations are updated
    for (int i = 0; i < creature_locations.length; i++){
      for (int j = 0; j < creature_locations[i].length; j++){
           creature_locations[i][j] = 0;
      }
    }
    for (int i = 0; i < creatures.length; i++){
      if (creatures[i].GetY() > world_array.length - 1){
        creatures[i].SetY(world_array.length -1);
      }else if (creatures[i].GetY() < 0){
        creatures[i].SetY(0);
      }
      if (creatures[i].GetX() > world_array[creatures[i].GetY()].length - 1){
        creatures[i].SetX(world_array[creatures[i].GetY()].length -1);
      }else if (creatures[i].GetX() < 0){
        creatures[i].SetX(0);
      }
      creature_locations[creatures[i].GetY()][creatures[i].GetX()] += 1;
    }
    //monsters perform their action
    if (step % F == 0){
      for (int i = 0; i < monsters.length; i ++){
        monsters[i].Set_World_Info(creature_locations);
        monsters[i].Move();
      }
      //monster info is updated
      for (int i = 0; i < monster_locations.length; i++){
        for (int j = 0; j < monster_locations[i].length; j++){
          monster_locations[i][j] = 0;
        }
      }
      for (int i = 0; i < monsters.length; i++){
        monster_locations[monsters[i].GetY()][monsters[i].GetX()] += 1;
      }
      // resources grow by 1 unit
      if (step % 2*F == 0){
      for (int i = 0; i < world_array.length; i++){
        for (int j = 0; j < world_array[i].length; j++){
          if (world_array[i][j] == 2){
            if (strawb_array[i][j] < 9){
              strawb_array[i][j] ++;
            }
          }else if (world_array[i][j] == 3){
            if (mush_array[i][j] < 9){
              mush_array[i][j] ++;
            }
          }
        }
      }
      }
    }
    
    
    
  }
  
  //iterates trhough the creature array creating the same number of baby creatures
  // this method manages the selection of parents
  // the creature constructors manage the crossing over and mutations
  public static void Reproduce(){
    baby_creatures = new Creature[creatures.length];
    Random r = new Random();
    for (int k = 0; k < baby_creatures.length; k++){
      int parent1 = 0;
      int parent2 = 0;
      int energy_aggregate = 0;
      for (int i = 0; i < creatures.length; i++){
        energy_aggregate += creatures[i].Get_Energy();
      }
      int energy_roulette = r.nextInt(energy_aggregate);
      for (int i = 0; i < creatures.length; i++){
        energy_roulette -= creatures[i].Get_Energy();
        if (energy_roulette < 0){
          parent1 = i;
          i = creatures.length;
        }
      }
      energy_roulette = r.nextInt(energy_aggregate);
      for (int i = 0; i < creatures.length; i++){
        energy_roulette -= creatures[i].Get_Energy();
        if (energy_roulette < 0){
          parent2 = i;
          i = creatures.length;
        }
      }
      baby_creatures[k] = new Creature(creatures[parent1].Get_Chromosome(), creatures[parent2].Get_Chromosome(), k, 1);
    }
    creatures = baby_creatures;
  }
  
}//end class