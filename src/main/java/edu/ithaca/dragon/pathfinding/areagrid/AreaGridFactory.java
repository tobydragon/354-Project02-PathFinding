package edu.ithaca.dragon.pathfinding.areagrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class AreaGridFactory {
    private static Random random = new Random(System.currentTimeMillis());

    public static AreaGrid createPerfectMaze(int numRows, int numColumns){
        return new AreaGrid(createPerfectMazeLocations(numRows, numColumns));
    }

    public static AreaGrid createLoopyMaze(int numRows, int numColumns, double removal_factor){
      List<List<Location>> maze = createPerfectMazeLocations(numRows, numColumns);
      removeRandomWalls(maze, removal_factor);
      return new AreaGrid(maze);
    }

    private static List<List<Location>> createPerfectMazeLocations(int numRows, int numColumns) {
        List<List<Location>> cells = new ArrayList<>();

        for (int x = 0; x < numColumns; ++x) {
          cells.add(new ArrayList<Location>());
          for (int y = 0; y < numRows; ++y) {
            Location c = new Location(x, y);
            cells.get(x).add(c);
            if (x % 2 == 0 || y % 2 == 0) {
              c.setWall(true);
            }
          }
        }

        Location current = cells.get(1).get(1);
        current.setWall(false);
        ArrayList<Location> visited = new ArrayList<Location>();
        Stack<Location> open = new Stack<Location>();

        visited.add(current);
        open.add(current);

        // Create a maze on the grid
        while (open.size() > 0) {
          ArrayList<Location> viable = getViableWalls(cells, current, visited, numColumns, numRows);

          if (viable.size() > 0) {
            Location n = viable.get(random.nextInt(viable.size()));

            Location mid = getMiddle(cells, current, n, numColumns, numRows);
            if (mid != null) {
              mid.setWall(false);
            }

            current = n;
            visited.add(current);
            open.add(current);
          }
          else {
            current = open.pop();
          }
        }

        return cells;
    }

  /**
   * @return a list of walls have not been visited and are valid
   */
  public static ArrayList<Location> getViableWalls(List<? extends List<Location>> grid, Location cell, List<Location> visited, int gridColumns, int gridRows) {
    ArrayList<Location> vwalls = new ArrayList<Location>();

    for (Location c : GridMove.calcValidDoubleNeighbors(grid, cell, gridColumns, gridRows)) {
      if (!visited.contains(c)) {
        int count = 0;
        for (Location v : GridMove.calcValidMainNeighbors(grid, c, gridColumns, gridRows)) {
          if (!v.isWall()) {
            count += 1;
          }
        }
        if (count == 0) {
          vwalls.add(c);
        }
      }
    }
    return vwalls;
  }

  // Returns the cell in the middle of two cells
  // Just for use with cells that are 2 horizontally away
  public static Location getMiddle(List<? extends List<Location>> cells, Location c0, Location c1, int gridColumns, int gridRows) {
    int x = (c0.getX() + c1.getX())/2;
    int y = (c0.getY() + c1.getY())/2;
    if (x > 0 && y > 0 && x < gridColumns - 1 && y < gridRows - 1) {
      return cells.get(x).get(y);
    }
    return null;
  }

    private static void removeRandomWalls(List<List<Location>> cells, double removal_factor){
        if (cells.size() < 5 || cells.get(0).size() < 5){
            throw new IllegalArgumentException("Need to be big enough to have walls to remove");
        }
        else {
          int num_remove_attempts = (int)Math.floor( (cells.size()-1) * (cells.get(0).size()-1) * removal_factor);
          for (int attempt=0; attempt<num_remove_attempts; attempt++){
              int x = random.nextInt(cells.size());
              int y = random.nextInt(cells.get(0).size());
              if (isInnerWall(cells.get(x).get(y), cells.get(0).size(),  cells.size())){
                cells.get(x).get(y).setWall(false);
              }
          }
              
        }
        
    }
    
    public static boolean isInnerWall(Location location, int numColumns, int numRows){
        if (!location.isWall()){
            return false;
        }
        else if (location.getX() < 1 || location.getY() < 1 || location.getY() >= numColumns - 1 || location.getX() >= numRows - 1){
            return false;
        }
        else{
          return true;
        }
    }
}
