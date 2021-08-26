package kickboard_transfer;
import java.io.*;
public class read_shell {
	public static void read_mapfind(String path) {
		ProcessBuilder processBuilder = new ProcessBuilder(path);
        processBuilder.redirectErrorStream(true);

        try {
            // Run script
            Process process = processBuilder.start();

            // Read output
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            System.out.println(output.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}
}
