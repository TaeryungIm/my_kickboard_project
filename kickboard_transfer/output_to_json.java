package kickboard_transfer;
//import java.io.*;
//import java.util.Map;
//import java.util.ArrayList;
//import java.util.HashMap;
import org.json.simple.*;
public class output_to_json {
	JSONObject output;
	
	public output_to_json() {
		JSONObject output_Data = new JSONObject();
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
		this.output = output_Data;
	}
	
	public void input_transfer(int startpoint, int lastpoint, String transport) {
		JSONObject newdata = new JSONObject();
		newdata.put("transportation", transport);
		newdata.put("startpoint", startpoint);
		newdata.put("lastpoint", lastpoint);
		
		JSONArray newarray = new JSONArray();
		newarray.add(newdata);
		
		output.put(transport + " going", newarray);
	}
	
	public static void main(String[] args) {
		output_to_json test_json = new output_to_json();
		
		test_json.input_transfer(0, 10, "airkick");
	}
}
