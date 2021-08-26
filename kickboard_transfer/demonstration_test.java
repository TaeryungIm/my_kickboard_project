package kickboard_transfer;
import java.io.*;
import java.util.HashMap;
//import java.util.Map;
public class demonstration_test {
	public static void main(String[] args) {
//		Point point1 = new Point(127.0275413, 37.49794079);
//		Point point2 = new Point(127.07358008, 37.66086742);
//		
//		distance_calculate discal = new distance_calculate();
//		double dis = discal.distance(point1, point2);
//		double time = discal.time_kickboard(point1, point2);
//		
//		System.out.print("distance is " + dis + "km, and elapsed time is " + time + "min");
		int curindex = 0;
		int outindex = 0;
		int counter = 1;
		Transfer_alarm kick;
		String path = "./path.txt";
		String area;
		output_to_json path_file = new output_to_json();
		
		DataParser dppath = new DataParser(path);
		BrokenLine bl = dppath.parseBrokenLine();
		find_kickboard f_area = new find_kickboard();
		
		double[] list = distance_calculate.distance_list(bl);
		
		//without kickboard finding algorithm, this is temporary measure
		area = "./airkick.txt";
		kick = new Transfer_alarm(area);
		outindex = kick.checkIsOutArea(dppath, bl, outindex);
		path_file.input_transfer(curindex, outindex, area);
		System.out.print(counter + " out index: " + outindex + "\n");
		counter++;
		curindex = outindex;
		if(distance_calculate.time_kickboard_sum(bl.points, outindex, bl.n - 1) < 2.0) {
			path_file.input_transfer(curindex + 1, bl.n - 1, area);
		}
		
		while (outindex < bl.n - 1) { //when it's out of the given area
			area = f_area.find_nearby_kickboard(bl);
			kick = new Transfer_alarm(area);
			outindex = kick.checkIsOutArea(dppath, bl, outindex);
			path_file.input_transfer(curindex, outindex, area);
			System.out.print(counter + " out index: " + outindex + "\n");
			counter++;
			curindex = outindex;
			if(distance_calculate.time_kickboard_sum(bl.points, outindex, bl.n - 1) < 2.0) {
				path_file.input_transfer(outindex + 1, bl.n - 1, area);
			}
		}
		
//		String cmd = "sh /Users/fbdxo/킥보드/Allkick_Algorithm-develop/kickboard_transfer/mapfind.sh";
//		read_shell.read_mapfind(cmd);
	}
	
}
