package kickboard_transfer;

public class distance_calculate { //function of this class is static
	//longitude 1 degree in latitude of 37 degrees is estimated to 88.6km
	//latitude of 1 degree is estimated to 111km
	//also, calculates kickboard and walking time
	//particularly, the average speed if kickboard is 15km/h
	//speed of walking is basically 5km/h
	
	public static double distance(Point p1, Point p2) { //the parameter is kilometer
		double dis, x_dis, y_dis;
		
		x_dis = Math.abs(p1.x - p2.x) * 88.6;
		y_dis = Math.abs(p1.y - p2.y) * 111.1;
		dis = Math.pow(Math.pow(x_dis, 2) + Math.pow(y_dis, 2), 0.5);
		dis = Double.parseDouble(String.format("%.3f", dis));
		if(dis < 0)
			return -1;
		else
			return dis;
	}
	
	public static double[] distance_list(BrokenLine path) {
		int n = path.n;
		Point[] point_list = path.points;
		double[] list = new double[n-1];
		
		for(int i = 0; i < n-1; i++) {
			list[i] = distance(point_list[i], point_list[i + 1]);
		}
		return list;
	}
	
	public static double distance_list_sum(BrokenLine path, int start, int end) {
		double sum = 0;
		double[] list = distance_list(path);
		for(int i = start; i < end; i++)
			sum += list[i];
		return sum;
	}
	
	public static double time_kickboard(Point p1, Point p2) { //the parameter is minute
		double dis = distance(p1, p2);
		double total_T = dis * 4.0;
		
		return total_T;
	}
	
	public static int time_kickboard_sum(Point[] list, int start, int end) {
		double sum = 0;
		for(int i = start; i < end; i++) //sum to just before end point
			sum += time_kickboard(list[i], list[i + 1]);
		return (int) Math.round(sum);
	}
	
	public static double time_walk(Point p1, Point p2) { //the parameter is minute
		double dis = distance(p1, p2);
		double total_T = dis * 12.0;
		
		return total_T;
	}
	
	public static int time_walk_sum(Point[] list, int start, int end) {
		double sum = 0;
		for(int i = start; i < end; i++) //sum to just before end point
			sum += time_walk(list[i], list[i + 1]);
		return (int) Math.round(sum);
	}
	
	public static int kickboard_cost(int basic_cost, int minute_cost, int time) { //cost_counter
		int whole_cost;
		whole_cost = basic_cost + minute_cost * time;
		return whole_cost;
	}
}
