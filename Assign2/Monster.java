import java.util.Random;

public class Monster{
  
  private int x;
  private int y;
  private int [] [] creatures;
  private int nearest_creature_x;
  private int nearest_creature_y;
  private boolean creature_found = false;
  
  public Monster(int W){
    Random r = new Random();
    x = r.nextInt(W);
    y = r.nextInt(W);
  }
  
  public Monster(int lx, int ly){
    x = lx;
    y = ly;
  }
  
  public int GetX(){
    return x;
  }
  
  public int GetY(){
    return y;
  }
  
  public void Set_World_Info(int [] [] creat){
    creatures = creat;
  }
  
  public void Move(){
    Find_Creat();
    if (creature_found && nearest_creature_x != 0 || nearest_creature_y != 0){
      if (nearest_creature_x * nearest_creature_x >= nearest_creature_y * nearest_creature_y){
        if (nearest_creature_x > 0){
          this.x ++;
        }else if (nearest_creature_x < 0){
          this.x --;
        }
      }if (nearest_creature_x * nearest_creature_x < nearest_creature_y * nearest_creature_y){
        if (nearest_creature_y > 0){
          this.y ++;
        }else if (nearest_creature_y < 0){
          this.y --;
        }
      }
      
    }else {
      Random_Move();
    }
    return;
    
  }

  
  public void Find_Creat(){
    int vd = 2; // sets the view distance
    int cx = 0;
    int cy = 0;
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
  
  public void Random_Move(){
    Random r = new Random();
    if (r.nextInt(2) == 0){
      if (r.nextInt(2) == 0 && x < creatures[this.GetY()].length -1){
        this.x ++;
      }else if (x > 0){
        this.x --;
      }
    }else{
      if (r.nextInt(2) == 0 && y < creatures.length - 1){
        this.y ++;
      }else if (y > 0){
        this.y --;
      }
    }
  }
}