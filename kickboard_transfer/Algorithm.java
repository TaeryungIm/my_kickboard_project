package kickboard_transfer;

import java.io.IOException;

public class Algorithm {
	public static void main(String[] args) {
		String path = "./path1.txt";
		Polygon[] polygons = DataParser.parseAllPolygon(path);
		int n_polygon = polygons.length;

		// DEV
		// System.out.println("Test");
		// System.out.println("Length: " + n_polygon);
		// for (int i = 0; i < n_polygon; ++i) {
		// 	System.out.println(polygons[i].n);
		// }

		// Test if two lines p1q1 and p2q2 intersect
		Point p2 = new Point(37.4945, 126.998);
		Point q2 = new Point(37.5, 126.998);
		System.out.println(GeometryUtils.doIntersect(polygons[0].points[0], polygons[0].points[1], p2, q2));
		System.out.println(GeometryUtils.doIntersect(polygons[0].points[1], polygons[0].points[2], p2, q2));

		// Test if the point is inside a polygon
		Point p;
		p = new Point(37.4945, 126.998);
		System.out.println(GeometryUtils.isInside(polygons[0], p));

		p = new Point(37.5, 126.998);
		System.out.println(GeometryUtils.isInside(polygons[0], p));
	}
}