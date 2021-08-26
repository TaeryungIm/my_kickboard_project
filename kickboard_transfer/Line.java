package kickboard_transfer;

public class Line extends Shape {
	public Line(Point p1, Point p2) {
		this.n = 2;
		this.points = new Point[2];
		this.points[0] = p1;
		this.points[1] = p2;
	}
}