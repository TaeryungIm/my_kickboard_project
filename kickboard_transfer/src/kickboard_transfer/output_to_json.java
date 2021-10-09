package kickboard_transfer;
import java.io.*;
import org.json.simple.*;
public class output_to_json {
	JSONObject output;
//	JSONObject wholeobject;
	JSONArray wholearray;
	
	public output_to_json() {
		//this is for having whole array
//		wholeobject = new JSONObject();
		wholearray = new JSONArray();
	}
	
	//this is for scripting partial route for each possible route
	public void input_transfer_json(int startpoint, int lastpoint, String transport, double time, int cost) {
		JSONObject newdata = new JSONObject();
		newdata.put("startpoint", startpoint);
		newdata.put("lastpoint", lastpoint);
		newdata.put("travel time", time);
		newdata.put("travel cost", cost);
		
		if(transport == "walking")
			newdata.put("transportation", transport);
		else {
			String n_trans = transport.substring(2, transport.length() - 4);
			newdata.put("transportation", n_trans);
		}
		
		wholearray.add(newdata);
	}
	
	public void input_transfer_json_multiAPI(int startpoint, int lastpoint, String transport, double time, int cost, String path) {
		JSONObject newdata = new JSONObject();
		newdata.put("startpoint", startpoint);
		newdata.put("lastpoint", lastpoint);
		newdata.put("travel time", time);
		newdata.put("travel cost", cost);
		newdata.put("path file", path);
		
		if(transport == "walking")
			newdata.put("transportation", transport);
		else {
			String n_trans = transport.substring(2, transport.length() - 4);
			newdata.put("transportation", n_trans);
		}
		
		wholearray.add(newdata);
	}
	
	//this method is for finally write the jsonobject down
	public void writeJSONObject() {
		//initialize the output json file
		try {
			FileWriter writer = new FileWriter("./outcome.json");
			String jsonstring = wholearray.toJSONString();
			writer.write(jsonstring);
			
			writer.close();
		}catch(Exception e){
			System.out.print("json file isn't made");
			throw new RuntimeException();
		}
	}
	
	public JSONArray getJSONArray() {
		return wholearray;
	}
		
	//put the JSONObject from the bePut to the beingPut
	public JSONArray addJSONArray(JSONArray beingPut, JSONArray bePut) {
		for(int i = 0; i < bePut.size(); i++) {
			JSONObject innerdata = (JSONObject) bePut.get(i);
			beingPut.add(innerdata);
		}
		return beingPut;
	}
	
//	public static void main(String[] args) {
//		output_to_json test_json = new output_to_json();
//		
//		test_json.input_transfer_json(0, 10, "airkick", 12.5, 1200);
//		test_json.input_transfer_json(10, 20, "demokick", 6.5, 400);
//		System.out.print(test_json.wholearray.size() + "\n");
//		System.out.print(test_json.wholearray);
		
//	}
}
