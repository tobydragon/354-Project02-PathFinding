package edu.ithaca.dragon.pathfinding.pathfinder;

import edu.ithaca.dragon.pathfinding.areagrid.Location;

public interface PathFinder {

    Location chooseMove(LocalSensor localSensor);
}
