package kickboard_transfer;

public class Transfer_alarm { //full merge every necessary function here
	String area;
//	double startPoint, stopPoint;
	int[] outcome;
	
	public Transfer_alarm() {
		outcome = new int[4]; //contains startindex, lastindex, travel time, travel costs
	}
	
	public Transfer_alarm(String area) {
		this.area = area;
		outcome = new int[4]; //contains startindex, lastindex, travel time, travel costs
	}
	
	public int[] checkIsOutArea(BrokenLine bl, int startP) {
		outcome[0] = startP;
		int area_num = -1;
		boolean bool = true;
		
		Polygon[] pg = DataParser.parseAllPolygon(area);
		
		//find the right area in text list of the company
		for(int i = 0; i < pg.length; i++) {
			if(GeometryUtils.isInside(pg[i], bl.points[0])) {
				area_num  = i;
				break;
			}
		}
		
		//if last point or near from there in the same area, do not transfer
		int near_point = bl.n - 1; //initial value
		
		//find point as near as 2 minute by kickboard to the end point
		for(int i = bl.n - 1;;i--) {
			if(distance_calculate.time_kickboard_sum(bl.points, i, bl.n - 1) < 2)
				near_point = i;
			else
				break;
		}
		
		//if one of the near point is in the same initial area
		//return the algorithm
		for(int i = near_point; i < bl.n - 1; i++) {
			if(GeometryUtils.isInside(pg[area_num], bl.points[bl.n-1])) {
				outcome[1] = i;
				outcome[2] = distance_calculate.time_kickboard_sum(bl.points, startP, i);
				outcome[3] = distance_calculate.kickboard_cost
						(pg[area_num].basic_cost, pg[area_num].minute_cost, outcome[2]);
				return outcome;
			}
		}
		
		//check whether the path is out of the given area
		try {
			for(int i = startP; i < bl.n; i++) {
				if(bool) {
					bool = GeometryUtils.isInside(pg[area_num], bl.points[i]);
				}
				else {
					outcome[1] = i-1; //the very first point out from the area
					outcome[2] = distance_calculate.time_kickboard_sum(bl.points, startP, i-1);
					outcome[3] = distance_calculate.kickboard_cost
							(pg[area_num].basic_cost, pg[area_num].minute_cost, outcome[2]);
					return outcome;
				}
				
				//exception: if the path is just little away from the area
				if(!bool) {
					int howFarAway = areaAwayMiddleOfPath(i, pg[area_num], bl);
					
					//jump to the point inside the area
					//and the loop goes on
					if(howFarAway >= 0) {
						i += howFarAway;
						bool = true;
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException e) { //in case when area_num = -1
			System.out.print("Not in right companay's area");
		}
		
		//if the process get to the final point
		outcome[1] = bl.n - 1;
		outcome[2] = distance_calculate.time_kickboard_sum(bl.points, startP, bl.n - 1);
		outcome[3] = distance_calculate.kickboard_cost
				(pg[area_num].basic_cost, pg[area_num].minute_cost, outcome[2]);
		return outcome; //if done, return the maximum number
	}
	
	//this function is only usable through checkIsOutArea function
	private int areaAwayMiddleOfPath(int startp, Polygon area, BrokenLine bl) {
		int middle_away = -1;
		
		//if kickboard is back to the area again, proceed
		for(int i = startp + 1; i < bl.n - 1; i++) {
			if(GeometryUtils.isInside(area, bl.points[i])) {
				middle_away = i - startp;
				break;
			}
		}
		
		//if it's way out of the line, just transfer to another
		return middle_away;
	}

	//this is for merging all walking routes
	public int[] walkingRoute(int startpoint, int endpoint, Point[] pathline) {		
		outcome[0] = startpoint;
		outcome[1] = endpoint;
		outcome[2] = distance_calculate.time_walk_sum(pathline, startpoint, endpoint);
		outcome[3] = 0; //no money cost
		return outcome;
	}
}
