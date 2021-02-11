package edu.ithaca.dragon.pathfinding.areagrid;

import java.util.ArrayList;
import java.util.List;

public enum GridMove {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    LEFT_UP(-1, 1),
    RIGHT_UP(-1, -1),
    LEFT_DOWN(-1, 1),
    RIGHT_DOWN(1, 1),
    UP_UP(0, -2),
    DOWN_DOWN(0, 2),
    LEFT_LEFT(-2, 0),
    RIGHT_RIGHT(2, 0),
    
    NO_MOVE(0,0);

    private final int x;
    private final int y;
    
    private GridMove(int x, int y){
        this.x = x;
        this.y = y;
    }

    // All 4 directions starting right going clockwise
    public static final GridMove[] MAIN_DIRECTIONS = {
        RIGHT, DOWN, LEFT, UP
    };

    // All 4 direction 2 away
    public static final GridMove[] DOUBLE_DIRECTIONS = {
        RIGHT_RIGHT, DOWN_DOWN, LEFT_LEFT, UP_UP
    };

    // All 8 directions
    public static final GridMove[] ALL_DIRECTIONS = {
        RIGHT, DOWN, LEFT, UP, RIGHT_DOWN, LEFT_DOWN, LEFT_UP, RIGHT_UP
    };
    
    /**
     * @return a new location from the grid, or the same location if the move was out-of-bounds
     */
    public static Location calcNewLocation(List<? extends List<Location>> grid,  Location current, GridMove move, int numColumns, int numRows){
        int newX = current.getX() + move.x;
        int newY = current.getY() + move.y;
        if (newX > 0 && newY > 0 && newX < numColumns-1 && newY < numRows-1) {
            return grid.get(newX).get(newY);
        }
        else {
            return current;
        }
    }

    public static List<Location> calcAllValidNeighborsFromMoves(List<? extends List<Location>> grid,  Location current, GridMove[] moves, int numColumns, int numRows) {
        List<Location> n = new ArrayList<Location>();
        for (GridMove move : moves) {
            Location potentialNeighbor = calcNewLocation(grid, current, move, numColumns, numRows);
            if (!potentialNeighbor.equals(current)){
                n.add(potentialNeighbor);
            }
        }
        return n;
    }

    public static List<Location> calcValidMainNeighbors(List<? extends List<Location>> grid,  Location current, int numColumns, int numRows) {
        return calcAllValidNeighborsFromMoves(grid, current, MAIN_DIRECTIONS, numColumns, numRows);
    }

    public static List<Location> calcValidDoubleNeighbors(List<? extends List<Location>> grid,  Location current, int numColumns, int numRows) {
        return calcAllValidNeighborsFromMoves(grid, current, DOUBLE_DIRECTIONS, numColumns, numRows);
    }

    public static List<Location> calcOpenMainNeighbors(List<? extends List<Location>> grid,  Location current, int numColumns, int numRows) {
        List<Location> validNeighbors =  calcAllValidNeighborsFromMoves(grid, current, MAIN_DIRECTIONS, numColumns, numRows);
        List<Location> openNeighbors = new ArrayList<>();
        for (Location neighbor : validNeighbors){
            if (!neighbor.isWall()){
                openNeighbors.add(neighbor);
            }
        }
        return openNeighbors;
    }

    /**
     * a method to help figure out where a given move will take you, if it is a valid move
     * @return the new location resulting from taking the given move, if it is one of the allowedLocations, else the current location 
     */
    public static Location calcNewLocationOrStay(Location current, GridMove move, List<Location> allowedLocations){
        int newX = current.getX() + move.x;
        int newY = current.getY() + move.y;
        for (Location allowedLocation : allowedLocations){
            if (allowedLocation.getX() == newX && allowedLocation.getY() == newY){
                return allowedLocation;
            }
        }
        return current;
    }
 
    
}
