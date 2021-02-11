package edu.ithaca.dragon.pathfinding.areagrid;

public class Location {

    private int x;
    private int y;
    private boolean isWall;

    public Location(int x, int y){
      this(x, y, false);
    }
  
    public Location(int x, int y, boolean isWall) {
      this.x = x;
      this.y = y;
      this.isWall = isWall;
    }

    public Location(Location toCopy) {
      this(toCopy.x, toCopy.y, toCopy.isWall);
    }

    public boolean isWall() {
      return isWall;
    }
  
    public void setWall(boolean wall) {
      isWall = wall;
    }
  
    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Location)) {
        return false;
      }
      Location otherLoc = (Location) obj;
      return otherLoc.x == x && otherLoc.y == y;
    }
  
    @Override
    public String toString() {
      return  "(" + x + ", " + y + ")";
    }

    public int getX(){
      return x;
    }

    public int getY(){
      return y;
    }

    public String buildDisplayString(){
        if (isWall()){
            return "\u2588";
        }
        else {
            return " ";
        }
    }

    //to make json IO functional, should not be used genrally
    public Location(){}

    public void setX(int x){
      this.x = x;
    }
    public void setY(int y){
      this.y = y;
    }

  }