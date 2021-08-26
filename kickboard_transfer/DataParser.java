package kickboard_transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NumberFormatException;

/*
Class for parsing the formatted data
 - First line: total number of the shapes, namely N
 - Below the first line, N data is given sequentially
   - First line of each data: the number of the points that consists the shape, namely M
   - Below it, M pairs of x and y coordinates of the each point are given sequentially
 */
public class DataParser {
	public FileReader fileReader;
	public BufferedReader bufferReader;
	public int n;

	public DataParser(String path) {
		File file = new File(path);
		try {
			this.fileReader = new FileReader(file);
			this.bufferReader = new BufferedReader(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.n = readN();
	}

	int readN() {
		int n = -1;
		String line = "-1";
		try {
			line = bufferReader.readLine();
			// System.out.println(line);
			n = Integer.parseInt(line);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
				System.out.println("alt: " + line);
				n = Integer.parseInt(line.substring(1, line.length()));	
			}
		return n;
	}

	Point readPoint() { 
		try {
			String line = bufferReader.readLine();
			//System.out.println(line);
			String args[] = line.split(" ");
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			return new Point(x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	Point[] readPoints(int n) { 
		Point[] points = new Point[n];
		for (int i = 0; i < n; ++i) {
			points[i] = readPoint();
		}
		return points;
	}

	/*
	Get the next Polygon from the data file
	 */
	public Polygon parsePolygon() {
		int basic_cost, minute_cost;
		try {
			basic_cost = Integer.parseInt(bufferReader.readLine());
			minute_cost = Integer.parseInt(bufferReader.readLine());
		}catch (IOException e) { //if exception, initialize the cost to recognize;
			basic_cost = 0;
			minute_cost = 0;
		}
		
		int n = readN(); //with multiple coordinates
		Point[] points = readPoints(n);
		return new Polygon(n, points, basic_cost, minute_cost);
	}

	/*
	Get the next BrokenLine from the data file
	 */
	public BrokenLine parseBrokenLine() {
		//int n = readN(); // without multiple coordinates
		Point[] points = readPoints(n);
		return new BrokenLine(n, points);
	}

	public static Polygon[] parseAllPolygon(String path) { 
		DataParser dataParser = new DataParser(path);

		int n = dataParser.n;
		Polygon[] polygons = new Polygon[n];

		for (int i = 0; i < n; ++i) {
			polygons[i] = dataParser.parsePolygon();
		}

		return polygons;
	}

	public static BrokenLine[] parseAllBrokenLine(String path) { 
		DataParser dataParser = new DataParser(path);

		int n = dataParser.n;
		BrokenLine[] brokenLines = new BrokenLine[n];

		for (int i = 0; i < n; ++i) {
			brokenLines[i] = dataParser.parseBrokenLine();
		}

		return brokenLines;
	}
}
