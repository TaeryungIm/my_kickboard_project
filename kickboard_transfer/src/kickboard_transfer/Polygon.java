package kickboard_transfer;

public class Polygon extends Shape {
	int basic_cost, minute_cost;
	public Polygon(int n, Point[] points, int basic_cost, int minute_cost) {
		this.n = n;
		this.points = points;
		this.basic_cost = basic_cost;
		this.minute_cost = minute_cost;
	}
}