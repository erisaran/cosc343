import java.util.Random;

public class Creature{
  
  private int energy_level;
  private int x;
  private int y;
  private int [] chromosome = new int [13];
  private int [] [] monsters;
  private int [] [] creatures;
  private int [] [] strawberries;
  private int [] [] mushrooms;
  private boolean ate_strawberry = false;
  private boolean ate_mushroom = false;
  private int nearest_mushroom_x = 0; //these store coordinates for items in the creatures local sensory range
  private int nearest_mushroom_y = 0;
  private int nearest_strawberry_x = 0;
  private int nearest_strawberry_y = 0;
  private int nearest_creature_x = 0;
  private int nearest_creature_y = 0;
  private int nearest_monster_x = 0;
  private int nearest_monster_y = 0;
  private boolean mushroom_found = false; // indicates if there is a specific item in the creatures range
  private boolean strawberry_found = false;
  private boolean creature_found = false;
  private boolean monster_found = false;
  private int E_DROP = 7; // the amount of energy lost per standard operation
  
  //constructor to place creatures randomly throughout the world
  public Creature(int W){
    Random r = new Random();
    energy_level = 100;
    x = r.nextInt(W);
    y = r.nextInt(W);
    chromosome[0] = r.nextInt(2);
    chromosome[1] = r.nextInt(2);
    for (int i = 2; i < 6; i ++){
      chromosome[i] = r.nextInt(4);
    }
    chromosome[6] = r.nextInt(5);
    for (int i = 7; i<chromosome.length; i ++){
      chromosome[i] = r.nextInt(6);
    }
  }
  
  //constructor to place creatures in a specified position
  public Creature(int lx, int ly){
    Random r = new Random();
    energy_level = 100;
    x = (lx);
    y = (ly);
    chromosome[0] = r.nextInt(2);
    chromosome[1] = r.nextInt(3) % 2;
    for (int i = 2; i < 7; i ++){
      chromosome[i] = r.nextInt(4);
    }
    chromosome[7] = r.nextInt(5);
    for (int i = 8; i<chromosome.length; i ++){
      chromosome[i] = r.nextInt(6);
    }
  }
  
  // constructor for a baby creature
  public Creature(int [] parent1, int [] parent2,int lx, int ly){
    energy_level = 100;
    x = lx;
    y = ly;
    Random r = new Random();
    int x_point = r.nextInt(parent1.length);
    //adds the first parents genes until the cross over point
    for (int i = 0; i < x_point; i++){
      this.chromosome[i] = parent1[i];
    }
    //adds the second parents genes after the cross over point
    for (int i = x_point; i < chromosome.length; i++){
      this.chromosome[i] = parent2[i];
    }
    // randomly mutates points on the chromosome
    for (int i = 0; i < chromosome.length; i++){
      int m_chance = r.nextInt(100);
      if (m_chance < 10){ // the number here is the percentage chance for mutation
        if (i < 2){
          chromosome[i] = r.nextInt(2);
        }else if (i >= 2 && i < 6){
          chromosome[i] = r.nextInt(4);
        }else if (i == 6){
          chromosome[i] = r.nextInt(5);
        }else if (i > 6){
          chromosome[i] = r.nextInt(6);
        }
      }
    }
  }
  
  //returns the x coordinate
  public int GetX(){
    return x;
  }
  
  //returns the y coordinate
  public int GetY(){
    return y;
  }
  
  public int Get_Energy(){
    return energy_level;
  }
  
  public int[] Get_Chromosome(){
    return chromosome;
  }
  
  //prints out the chromosome
  public void Print_Chromosome(){
    for (int i = 0; i < chromosome.length; i++){
      System.out.print(chromosome[i] + " ");
      if (i == 6){
        System.out.print("  ");
      }
    }
    System.out.println();
  }
  
  public void Get_Stats(){
    System.out.print("Energy = " + this.energy_level + " Position = (" + this.x + "," + this.y + ") Chromosome = ");
    Print_Chromosome();
  }
  
  //returns true if the creature has just eaten a strawberry
  public boolean Has_Ate_Straw(){
    if (ate_strawberry == true){
      return true;
    }
    return false;
  }
  
  //returns true if the creature has just eaten a mushroom
  public boolean Has_Ate_Mush(){
    if (ate_mushroom == true){
      ate_mushroom = false;
      return true;
    }
    return false;
  }
  
  public void SetX(int lx){
    this.x = lx;
  }
  
  public void SetY(int ly){
    this.y = ly;
  }
  
  // sets the locations of objects to simulate the creatures senses
  public void Set_World_Info(int [] [] straw, int [] [] mush, int [] [] mons, int [] [] creat){
    monsters = mons;
    mushrooms = mush;
    strawberries = straw;
    creatures = creat;
  }
  
  
  //
  // this part is about the creature finding things
  //
  //assigns the location of the closest mushroom to the mushroom loaction coordinates of the object
  public void Find_Mush(){
    int vd = 2;
    int cx = vd;
    int cy = vd;
    for (int i = -vd; i <= vd; i ++){
      if (i + GetY() >= 0 && i + GetY() < mushrooms.length){
        for (int j = -vd; j <= vd;j ++){
          if (j + GetX() >= 0 && j + GetX() < mushrooms[GetY() + i].length){
            int p = 0;
            int q = 0;
            if (mushrooms[GetY() + i][GetX() + j] > 0){
              mushroom_found = true;
              if (j < 0){
                p = -j;
              }else{
                p = j;
              }
              if (i < 0){
                q = -i;
              }else{
                q = i;
              }
              if ((p + q) < (cx + cy)){
                cx = p;
                cy = q;
              }
            }
          }
        }
      }
    }
    nearest_mushroom_x = cx;
    nearest_mushroom_y = cy;
  }
  
  //finds the closest strawberry
  public void Find_Straw(){
    int vd = 2;
    int cx = vd;
    int cy = vd;
    for (int i = -vd; i <= vd; i ++){
      if (i + GetY() >= 0 && i + GetY() < strawberries.length){
        for (int j = -vd; j <= vd;j ++){
          if (j + GetX() >= 0 && j + GetX() < strawberries[GetY() + i].length){
            int p = 0;
            int q = 0;
            if (strawberries[GetY() + i][GetX() + j] > 0){
              strawberry_found = true;
              if (j < 0){
                p = -j;
              }else{
                p = j;
              }
              if (i < 0){
                q = -i;
              }else{
                q = i;
              }
              if ((p + q) < (cx + cy)){
                cx = p;
                cy = q;
              }
            }
          }
        }
      }
    }
    nearest_strawberry_x = cx;
    nearest_strawberry_y = cy;
  }
  
  //finds the closest creature
  public void Find_Creat(){
    int vd = 2;
    int cx = vd;
    int cy = vd;
    for (int i = -vd; i <= vd; i ++){
      if (i + GetY() >= 0 && i + GetY() < creatures.length){
        for (int j = -vd; j <= vd;j ++){
          if (j + GetX() >= 0 && j + GetX() < creatures[GetY() + i].length){
            int p = 0;
            int q = 0;
            if (creatures[GetY() + i][GetX() + j] > 0){
              creature_found = true;
              if (j < 0){
                p = -j;
              }else{
                p = j;
              }
              if (i < 0){
                q = -i;
              }else{
                q = i;
              }
              if ((p + q) < (cx + cy)){
                cx = p;
                cy = q;
              }
            }
          }
        }
      }
    }
    nearest_creature_x = cx;
    nearest_creature_y = cy;
  }
  
  //finds the nearest monster
  public void Find_Mon(){
    int vd = 2;
    int cx = vd;
    int cy = vd;
    for (int i = -vd; i <= vd; i ++){
      if (i + GetY() >= 0 && i + GetY() < monsters.length){
        for (int j = -vd; j <= vd;j ++){
          if (j + GetX() >= 0 && j + GetX() < monsters[GetY() + i].length){
            int p = 0;
            int q = 0;
            if (monsters[GetY() + i][GetX() + j] > 0){
              monster_found = true;
              if (j < 0){
                p = -j;
              }else{
                p = j;
              }
              if (i < 0){
                q = -i;
              }else{
                q = i;
              }
              if ((p + q) < (cx + cy)){
                cx = p;
                cy = q;
              }
            }
          }
        }
      }
    }
    nearest_monster_x = cx;
    nearest_monster_y = cy;
  }
  
  
  
  //
  // this part is about the creature doing things
  //
  //chooses and performs an action
  public void Move(){
    if (monsters[this.GetY()][this.GetX()] > 0){
      energy_level = 0;
      return;
    }
    ate_strawberry = false;
    for (int i = 5; i >= 0; i --){
      if (chromosome[8] == i && chromosome[1] != 1){
        if (strawberries[this.GetY()][this.GetX()] > 0){
            ate_strawberry = true;
            energy_level += 35;
            if (energy_level > 100){
              energy_level = 100;
            }
            return;
        }
      }
      if (chromosome[7] == i && chromosome[0] != 1 && mushrooms[this.GetY()][this.GetX()] > 0){
        ate_mushroom = true;
        energy_level = 0;
        return;
      }
      if (chromosome[9] == i && chromosome[2] != 3){ // search for a mushroom
        nearest_mushroom_x = 0;
        nearest_mushroom_y = 0;
        mushroom_found = false;
        Find_Mush();
        if (mushroom_found && nearest_mushroom_x != 0 || nearest_mushroom_y != 0){
          if (chromosome[2] == 0){ //move toward
            if (nearest_mushroom_x * nearest_mushroom_x >= nearest_mushroom_y * nearest_mushroom_y){
              if (nearest_mushroom_x > 0){
                this.x ++;
              }else if (nearest_mushroom_x < 0){
                this.x --;
              }
            }else if (nearest_mushroom_x * nearest_mushroom_x < nearest_mushroom_y * nearest_mushroom_y){
              if (nearest_mushroom_y > 0){
                this.y ++;
              }else if (nearest_mushroom_y < 0){
                this.y --;
              }
            }
          }else if (chromosome[2] == 1){ // move away
            if (nearest_mushroom_x * nearest_mushroom_x <= nearest_mushroom_y * nearest_mushroom_y){
              if (nearest_mushroom_x < 0 && this.x < mushrooms[GetY()].length -1){
                this.x ++;
              }else if (nearest_mushroom_x > 0 && x > 0){
                this.x --;
              }
            }else if (nearest_mushroom_x * nearest_mushroom_x > nearest_mushroom_y * nearest_mushroom_y){
              if (nearest_mushroom_y < 0 && this.y < mushrooms.length -1){
                this.y ++;
              }else if (nearest_mushroom_y > 0 && this.y > 0){
                this.y --;
              }
            }
          }else if (chromosome[2] == 2){
            Random_Move();
          }
          this.energy_level -= E_DROP;
          mushroom_found = false;
          return;
        }
        
      }
      if (chromosome[10] == i && chromosome[3] != 3){ // search for a strawberry
        nearest_strawberry_x = 0;
        nearest_strawberry_y = 0;
        strawberry_found = false;
        Find_Straw();
        if (strawberry_found && nearest_strawberry_x != 0 || nearest_strawberry_y != 0){
          if (chromosome[3] == 0){ //move towards
            if (nearest_strawberry_x * nearest_strawberry_x >= nearest_strawberry_y * nearest_strawberry_y){
              if (nearest_strawberry_x > 0){
                this.x ++;
              }else if (nearest_strawberry_x < 0){
                this.x --;
              }
            }if (nearest_strawberry_x * nearest_strawberry_x < nearest_strawberry_y * nearest_strawberry_y){
              if (nearest_strawberry_y > 0){
                this.y ++;
              }else if (nearest_strawberry_y < 0){
                this.y --;
              }
            }
          }else if (chromosome[3] == 1){ // move away
            if (nearest_strawberry_x * nearest_strawberry_x <= nearest_strawberry_y * nearest_strawberry_y){
              if (nearest_strawberry_x < 0 && this.x < strawberries[GetY()].length -1){
                this.x ++;
              }else if (nearest_strawberry_x > 0 && this.x > 0){
                this.x --;
              }
            }if (nearest_strawberry_x * nearest_strawberry_x > nearest_strawberry_y * nearest_strawberry_y){
              if (nearest_strawberry_y < 0 && this.y < strawberries.length -1){
                this.y ++;
              }else if (nearest_strawberry_y > 0 && this.y > 0){
                this.y --;
              }
            }
          }else if (chromosome[3] == 2){
            Random_Move();
          }
        }
        this.energy_level -= E_DROP;
        strawberry_found = false;
        return;
      }
      if (chromosome[11] == i && chromosome[4] != 3){ // search for a creature
        nearest_creature_x = 0;
        nearest_creature_y = 0;
        creature_found = false;
        Find_Creat();
        if (creature_found && nearest_creature_x != 0 || nearest_creature_y != 0){
          if (chromosome[4] == 0){ //move towards
            if (nearest_creature_x * nearest_creature_x >= nearest_creature_y * nearest_creature_y){
              if (nearest_creature_x > 0 && this.x < creatures[GetY()].length -1){
                this.x ++;
              }else if (nearest_creature_x < 0 && this.x > 0){
                this.x --;
              }
            }if (nearest_creature_x * nearest_creature_x < nearest_creature_y * nearest_creature_y){
              if (nearest_creature_y > 0 && this.y < creatures.length -1){
                this.y ++;
              }else if (nearest_creature_y < 0 && this.y > 0){
                this.y --;
              }
            }
          }else if (chromosome[4] == 1){ // move away
            if (nearest_creature_x * nearest_creature_x <= nearest_creature_y * nearest_creature_y){
              if (nearest_creature_x < 0 && this.x < creatures[GetY()].length -1){
                this.x ++;
              }else if (nearest_creature_x > 0 && this.x > 0){
                this.x --;
              }
            }else if (nearest_creature_x * nearest_creature_x > nearest_creature_y * nearest_creature_y){
              if (nearest_creature_y < 0 && this.y < creatures.length -1){
                this.y ++;
              }else if (nearest_creature_y > 0 && this.y > 0){
                this.y --;
              }
              
            }
          }else if (chromosome[4] == 2){
            Random_Move();
          }
        }
        this.energy_level -= E_DROP;
        return;
      }
      if (chromosome[12] == i && chromosome[5] != 3){ // search for a monster
        nearest_monster_x = 0;
        nearest_monster_y = 0;
        monster_found = false;
        Find_Mon();
        if (monster_found && nearest_monster_x != 0 || nearest_monster_y != 0){
          if (chromosome[5] == 0){ //move towards
            if (nearest_monster_x * nearest_monster_x >= nearest_monster_y * nearest_monster_y){
              if (nearest_monster_x > 0 && this.x < monsters[GetY()].length -1){
                this.x ++;
              }else if (nearest_monster_x < 0 && this.x > 0){
                this.x --;
              }
            }else if (nearest_monster_x * nearest_monster_x < nearest_monster_y * nearest_monster_y){
              if (nearest_monster_y > 0 && this.y < monsters.length -1){
                this.y ++;
              }else if (nearest_monster_y < 0 && this.y > 0){
                this.y --;
              }
            }
          }else if (chromosome[5] == 1){ // move away
            if (nearest_monster_x * nearest_monster_x <= nearest_monster_y * nearest_monster_y){
              if (nearest_monster_x < 0 && this.x < monsters[GetY()].length -1){
                this.x ++;
              }else if (nearest_monster_x > 0 && this.x > 0){
                this.x --;
              }
            }else if (nearest_monster_x * nearest_monster_x > nearest_monster_y * nearest_monster_y){
              if (nearest_monster_y < 0 && this.y < monsters.length -1){
                this.y ++;
              }else if (nearest_monster_y > 0 && this.y > 0){
                this.y --;
              }
            }
          }else if (chromosome[5] == 2){
            Random_Move();
          }
          this.energy_level -= E_DROP;
          monster_found = false;
          return;
        }
      }
    }
    Random_Move();
    this.energy_level -= E_DROP;
  }
  
  
  
  //creates a random movement
  public void Random_Move(){
    Random r = new Random();
    if (r.nextInt(2) == 0){
      if (r.nextInt(2) == 0 && x < creatures[this.GetY()].length -1){
        this.x ++;
      }else if (x > 0){
        this.x --;
      }
    }else{
      if (r.nextInt(2) == 0 && y < creatures.length -1 ){
        this.y ++;
      }else if (y > 0){
        this.y --;
      }
    }
  }
}
    