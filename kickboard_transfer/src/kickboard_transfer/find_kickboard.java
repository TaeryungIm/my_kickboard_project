package kickboard_transfer;
import java.io.*;
import java.util.HashMap;
public class find_kickboard {
	
	public static OutList getAllCompany(BrokenLine bl, int cur_loc) {
		//get nearby kickboard company names by this algorithm
		HashMap<String, Point> nearby_kickboard;
		nearby_kickboard = find_nearby_kickboard(bl, cur_loc);
		
		//if there are still no near kickboard
		//throw an exception
		if(nearby_kickboard.containsKey("no near kickboard"))
			throw new RuntimeException();
		
		int counter = 0;
		//if there's no near kickboard
		//increase the starting point and find again
		while(nearby_kickboard.containsKey("no near kickboard")) {
			counter++;
			nearby_kickboard = find_nearby_kickboard(bl, cur_loc + counter);
		}
		
		String[] company_list = new String[nearby_kickboard.size()];
		Point[] point_list = new Point[nearby_kickboard.size()];
		for(String key: nearby_kickboard.keySet()) {
			company_list[counter] = key;
//			System.out.print("Key: " + key + "\n");
			point_list[counter] = nearby_kickboard.get(key);
			counter++;
		}
		
		OutList outlist = new OutList(cur_loc + counter, company_list, point_list);
		return outlist;
	}
	
	public static HashMap<String, Point> find_nearby_kickboard(BrokenLine bl, int cur_loc) {
		BufferedReader br;
		//output the near kickboard information by HashMap
		//HashMap will contain the nearby kickboard company's name and the distances
		HashMap<String, Point> company_info = new HashMap<String, Point>();
		
		//if there's no kickboard is near, return this hashmap
		HashMap<String, Point> no_near_company = new HashMap<String, Point>();
		no_near_company.put("no near kickboard", null);
		
		//put the details of nearest one to this ArrayList
		//ArrayList <String> nearest_ones = new ArrayList<String>();
		boolean isNearKickboard = false;
		
		try { 
			//assume that we get location of kickboard through nearKickboard file
			String kickboardTxtFile = nearKickboard_info.getNearKickboardInfo();
			File file = new File(kickboardTxtFile);
			FileReader reader = new FileReader(file);
			br = new BufferedReader(reader);
			
			//first line is the number of the whole file
			int kickboard_num = Integer.parseInt(br.readLine());
			
			//check the nearest one through a certain algorithm
			//let's assume that the info will be like below
			//demokick 127.14323 37.23414
			for(int i = 0; i < kickboard_num; i++) {
				String[] kick_coor = br.readLine().split(" ");
				
				//assume that the first one is long, and the second one is lat
				String company_name = "./" + kick_coor[0] + ".txt";
				Point kickboard = new Point(Double.parseDouble(kick_coor[1]), Double.parseDouble(kick_coor[2]));
				double dist_kick = distance_calculate.distance(kickboard, bl.points[cur_loc]);
				
				//let's put the every company's closest kickboard to the list
				//kickboard have to be within 500m
				//if already in the hashmap, replace with the minimum value
				if(dist_kick <= 0.5) {
					isNearKickboard = true;
					if(company_info.containsKey(company_name)) {
						Point formal_value = company_info.get(company_name);
						company_info.replace(company_name, kickboard);
					}
					else {
						company_info.put(company_name, kickboard);
					}
				}
			}
			br.close();
		}catch(Exception e) {}
		
		//for testing
//		if(!company_info.isEmpty()) {
//			for(String keyVal: company_info.keySet()) {
//				System.out.print("company key: " + keyVal + "\n");
//				System.out.print("key value: " + company_info.get(keyVal) + "\n");
//			}
//		}
//		else {
//			System.out.print("no possible kickboards" + "\n");
//		}
		
		//if no kickboard within 500m
		if(!isNearKickboard) 
			return no_near_company;
		
		return company_info;
	}
	
	public static void main(String[] args) {
//		String path = naverParsing.jsonToText();
//		
//		DataParser dppath = new DataParser(path);
//		BrokenLine bl = dppath.parseBrokenLine();
//		
//		HashMap<String, String> newMap = new HashMap <String, String>();
//		System.out.print("current location coordinate is (" + bl.points[46].x + "," + bl.points[46].y + ")\n");
//		newMap = find_nearby_kickboard(bl, 46);
//		
//		System.out.print(newMap.size() + "\n");
//		for(String keyval: newMap.keySet()) {
//			System.out.print(keyval + " "  + newMap.get(keyval)+ "\n");
//		}
	}
}
