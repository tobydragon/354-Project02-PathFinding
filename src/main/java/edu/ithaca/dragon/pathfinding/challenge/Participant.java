package edu.ithaca.dragon.pathfinding.challenge;

import edu.ithaca.dragon.pathfinding.areagrid.Location;
import edu.ithaca.dragon.pathfinding.pathfinder.LocalSensor;
import edu.ithaca.dragon.pathfinding.pathfinder.PathFinder;

public class Participant {
    private Location location;
    private PathFinder pathChooser;
	
	public Participant(Location location, PathFinder pathChooser) {
		this.location = location;
		this.pathChooser = pathChooser;
	}

    public Location getLocation() {
		return location;
	}
    
	public void setLocation(Location location) {
		this.location = location;
	}

    public Location chooseMove(LocalSensor localSensor){
        return pathChooser.chooseMove(localSensor);
    }

}
