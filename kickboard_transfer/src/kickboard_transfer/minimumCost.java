package kickboard_transfer;
//import java.io.*;
import org.json.simple.*;
import java.util.HashMap;
import java.util.ArrayList;
public class minimumCost {
	
	//this function is for making new json routes
	//and add new jsonarrays to the formal routes
	public static ArrayList<output_to_json> makeMultipleRoute(BrokenLine bl, int cur_loc) {
		//find nearby companies by itself
		//and get the new starting point and the list of the company
		OutList companyOutcome = find_kickboard.getAllCompany(bl, cur_loc);
		String[] company_list = companyOutcome.company_list;
		int cur = companyOutcome.cur_loc;
		
		//make whole list of json files according to the newly found routes
		ArrayList<output_to_json> jsonList = new ArrayList<output_to_json>();
		
		for(int i = 0; i < company_list.length; i++) {
			ArrayList<output_to_json> newjsonlist = repeatedlyFindRoute(company_list[i], bl, cur);
			
			for(int j = 0; j < newjsonlist.size(); j++)
				jsonList.add(newjsonlist.get(j));
		}
		
		//if there's a walking route near the starting point
		//append the JSONArray of walking
		if(cur_loc != cur) {
			output_to_json prevWalking = new output_to_json();
			Transfer_alarm forWalk = new Transfer_alarm();
			int[] outcome = forWalk.walkingRoute(cur_loc, cur, bl.points);
			
			//paste the walking route to the json file prevWalking
			prevWalking.input_transfer_json(outcome[0], outcome[1], "walking", outcome[2], outcome[3]);
			
			//parse the JSONArray to the every possible routes
			for(int i = 0; i < jsonList.size(); i++) 
				jsonList.get(i).wholearray = jsonList.get(i).addJSONArray(jsonList.get(i).wholearray, prevWalking.wholearray);
		}
		return jsonList;
	}
	
	//this function is for repeatedly calling list of near kickboard
	public static ArrayList<output_to_json> repeatedlyFindRoute(String area, BrokenLine bl, int cur_loc) {
		Transfer_alarm kick = new Transfer_alarm(area);
		
		//this file is for saving the outcome of this algorithm
		output_to_json outcome_file = new output_to_json();
		
		//this json file list is for saving the outcome from algorithm after this
		ArrayList<output_to_json> afterFile = new ArrayList<output_to_json>();
		
		//initialize out list
		//each stands for startpoint, endpoint, time, costs
		int[] out = new int[4];
		
		//check whether the kickboard path is inside the given area
		//output the first index of path which is out of the given area
		kick = new Transfer_alarm(area);
		out = kick.checkIsOutArea(bl, cur_loc);
		
		//after the algorithm, parse them to the outcome
		OutList outlist = new OutList(out, area);
		outcome_file.input_transfer_json(outlist.startpoint, outlist.endpoint, outlist.transport, outlist.time, outlist.cost);
		
		//if last point is near to the destination
		//distance about 2 minutes by kickboard
		//let just walk the rest
		if(distance_calculate.time_kickboard_sum(bl.points, outlist.endpoint, bl.n - 1) < 2) {
			out = kick.walkingRoute(outlist.endpoint, bl.n - 1, bl.points);
			outlist.changeOut(out);
			outcome_file.input_transfer_json(outlist.startpoint, outlist.endpoint, "walking", outlist.time, outlist.cost);
		}
		
		if(outlist.endpoint < bl.n - 1) {
			afterFile = makeMultipleRoute(bl, outlist.endpoint);
			
			//append formal wholearray to the wholearrays found after this algorithm
			for(int i = 0; i < afterFile.size(); i++) 
				afterFile.get(i).wholearray = afterFile.get(i).addJSONArray(afterFile.get(i).wholearray, outcome_file.wholearray);
		}
		
		//if all the road finding is done
		//make a new output_to_json list and parse the calculated route
		else {
			afterFile.add(outcome_file);
		}
		return afterFile;
	}
	
	//main code for calling the minimum cost route algorithm
	public static void main(String[] args) {
		int starting = 0;
		ArrayList<output_to_json> outcomeFile = new ArrayList<output_to_json>();
		
		//generate the naver roadfinding api
		//change into usable text path.txt
		Point startP = new Point(127.0488417, 37.50449745);
		Point endP = new Point(127.13088226, 37.51604834);
		
		String path = naverParsing.jsonToText(startP, endP, 0);
		
		//get path polyline from the given text
		DataParser dppath = new DataParser(path);
		BrokenLine bl = dppath.parseBrokenLine();
		
		//this json file is for merging rest of the json files
		//find all possible routes according to the dppath
		outcomeFile = makeMultipleRoute(bl, starting);
		int minCost = Integer.MAX_VALUE;
		int minIndex = -1;
		
		//check the minimum value 
		for(int i = 0; i < outcomeFile.size(); i++) {
			JSONArray thisarray = outcomeFile.get(i).wholearray;
//			System.out.print(thisarray + "\n");
			int wholecost = 0;
			
			for(int j = 0; j < thisarray.size(); j++) {
				JSONObject innerRoute = (JSONObject) thisarray.get(j);
				wholecost += (int) innerRoute.get("travel cost");
			}
			//change the minimum cost and its index
			if(minCost > wholecost) {
				minCost = wholecost;
				minIndex = i;
			}
		}
		//put the minimum outcome to the given outcome.json
		if(minIndex != -1) {
			outcomeFile.get(minIndex).writeJSONObject();
		}
		else {
			System.out.print("no mininum cost value!" + "\n");
			throw new RuntimeException();
		}	
	}
}
