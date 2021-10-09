package kickboard_transfer;

public class GeometryUtils {

	public static int COLINEAR = 0;
	public static int CW = 1;
	public static int CCW = 2;


	// Define infinite (Using INT_MAX caused overflow problems)
	static double INF = 1000;

	// Given three colinear points p, q, r, the function checks if point q lies
	// on line segment 'pr'
	public static boolean onSegment(Point p, Point q, Point r) {
		return (q.x <= Math.max(p.x, r.x) &&
				q.x >= Math.min(p.x, r.x) &&
				q.y <= Math.max(p.y, r.y) &&
				q.y >= Math.min(p.y, r.y));
	}
 
	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0: p, q and r are colinear
	// 1: Clockwise
	// 2: Counterclockwise
	public static int orientation(Point p, Point q, Point r)
	{
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
 
		if (val == 0) {
			return COLINEAR; // colinear
		}
		return (val > 0) ? CW : CCW; // clock or counterclock wise
	}
 
	// The function that returns true if line segment 'p1q1' and 'p2q2' intersect.
	public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
		// Find the four orientations needed for general and special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);
 
		// General case
		if (o1 != o2 && o3 != o4)
			return true;
 
		// Special Cases
		// p1, q1 and p2 are colinear and
		// p2 lies on segment p1q1
		if (o1 == COLINEAR && onSegment(p1, p2, q1))
			return true;
 
		// p1, q1 and p2 are colinear and
		// q2 lies on segment p1q1
		if (o2 == COLINEAR && onSegment(p1, q2, q1))
			return true;
 
		// p2, q2 and p1 are colinear and
		// p1 lies on segment p2q2
		if (o3 == COLINEAR && onSegment(p2, p1, q2))
			return true;
 
		// p2, q2 and q1 are colinear and
		// q1 lies on segment p2q2
		if (o4 == COLINEAR && onSegment(p2, q1, q2))
			return true;
 
		// Doesn't fall in any of the above cases
		return false;
	}
  
	// Returns true if the broken line and the polygon intersect
	public static boolean doIntersect(BrokenLine brokenLine, Polygon polygon)
	{
		boolean result = false;
		int n_polygon = polygon.n;
		int n_brokenLine = brokenLine.n;
		for (int i = 0, j = n_polygon - 1; i < n_polygon; j = i++) {
			for (int k = 0; k < n_brokenLine - 1; ++k) {
				result = result || doIntersect(
					polygon.points[i],
					polygon.points[j],
					brokenLine.points[k],
					brokenLine.points[k + 1]);
			}
		}
		return result;
	}

	// Returns true if the point p lies inside the polygon[] with n vertices
	public static boolean isInside(Polygon polygonWrapper, Point p)
	{
		Point[] polygon = polygonWrapper.points;
		int n = polygonWrapper.n;
		// There must be at least 3 vertices in polygon[]
		if (n < 3)
			return false;
 
		// Create a point for line segment from p to infinite
		Point extreme = new Point(INF, p.y);
 
		// Count intersections of the above line
		// with sides of polygon
		int count = 0, i = 0;
		do {
			int next = (i + 1) % n;
 
			// Check if the line segment from 'p' to
			// 'extreme' intersects with the line
			// segment from 'polygon[i]' to 'polygon[next]'
			if (doIntersect(polygon[i], polygon[next], p, extreme))
			{
				// If the point 'p' is colinear with line
				// segment 'i-next', then check if it lies
				// on segment. If it lies, return true, otherwise false
				if (orientation(polygon[i], p, polygon[next]) == COLINEAR)
				{
					return onSegment(polygon[i], p,
									polygon[next]);
				}
 
				count++;
			}
			i = next;
		} while (i != 0);
 
		// Return true if count is odd, false otherwise
		return (count % 2 == 1); // Same as (count%2 == 1)
	}

	// Get the distance between a point and a straight line
	public static double getPointToLineDist(Point p, Point a, Point b) {
		double ABx = b.x - a.x;
		double ABy = b.y - a.y;
		double APx = p.x - a.x;
		double APy = p.y - a.y;
 
		double AB_AP = ABx * APx + ABy * APy;
		double distAB2 = ABx * ABx + ABy * ABy;
 
		double Dx = a.x, Dy = a.y;
		if (distAB2 != 0) {
			double t = AB_AP / distAB2;
			if (t >= 1) {
				Dx = b.x;
				Dy = b.y;
			} else if (t > 0) {
				Dx = a.x + ABx * t;
				Dy = a.y + ABy * t;
			} else {
				Dx = a.x;
				Dy = a.y;
			}
		}
		double PDx = Dx - p.x, PDy = Dy - p.y;

		return Math.sqrt(PDx * PDx + PDy * PDy);
	}
 
	// Get the shortest distance between a point and a polygon
	public static double getPointToPolygonDist(Point p, Polygon polygon) {
		double dist = INF;
		int n = polygon.n;
		for (int i = 0, j = n - 1; i < n; j = i++) {
			dist = Math.min(dist, getPointToLineDist(p, polygon.points[i], polygon.points[j]));
		}

		return dist;
	}

}
 
