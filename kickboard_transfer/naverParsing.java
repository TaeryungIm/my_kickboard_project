package kickboard_transfer;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class naverParsing {
	public static void main(String[] args) {
		naverParsing.routeParsing("out.json");
	}
	
	public static void routeParsing(String path) {
		String file_name = "./" + path;
		System.out.print(file_name + "\n");
		JSONParser parser = new JSONParser();
		
		try {
			Object json_obj = parser.parse(new FileReader(file_name));
			JSONObject route = (JSONObject) json_obj;
			String[] rt = (String[]) route.get("route");
			for(int i = 0; i < rt.length; i++)
				System.out.print(rt[i]);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
