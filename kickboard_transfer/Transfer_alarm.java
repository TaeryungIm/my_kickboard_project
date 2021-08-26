package kickboard_transfer;

public class Transfer_alarm { //full merge every necessary function here
	String area;
	static int startPoint, stopPoint; //this is static, use it carefully
	
	public Transfer_alarm(String area) {
		this.area = area;
	}
	
	public int checkIsOutArea(DataParser dppath, BrokenLine bl, int startP) { //get the first point out from the given area
		startPoint = startP;
		int area_num = -1;
		boolean bool = true;
		
		Polygon[] pg = DataParser.parseAllPolygon(area);
		
		for(int i = 0; i < pg.length; i++) {
			if(GeometryUtils.isInside(pg[i], bl.points[0])) {
				area_num  = i;
				break;
			}
		}
		//if last point in the same area, do not transfer
		if(GeometryUtils.isInside(pg[area_num], bl.points[bl.n-1])) {
			return bl.n - 1;
		}
		
		try {
			for(int i = startP; i < bl.n; i++) {
				if(bool) {
					bool = GeometryUtils.isInside(pg[area_num], bl.points[i]);
				}
				else {
					stopPoint = i-1; //the very first point out from the area
					return stopPoint;
				}
			}
		}catch(ArrayIndexOutOfBoundsException e) { //in case when area_num = -1
			System.out.print("Not in right companay's area");
		}
		return bl.n; //if done, return the maximum number
	}

	public static double[] dist_cal(BrokenLine bl) {
		double[] statistics = new double[3]; //dist_list_sum, kick_time, walk_time 
		statistics[0] = distance_calculate.distance_list_sum(statistics, startPoint, stopPoint);
		statistics[1] = distance_calculate.time_kickboard_sum(bl.points, startPoint, stopPoint);
		statistics[2] = distance_calculate.time_walk_sum(bl.points, startPoint, stopPoint);
		return statistics;
	}
}
