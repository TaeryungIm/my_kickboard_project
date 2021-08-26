package kickboard_transfer;
import java.io.*;

public class change_xy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileReader fr = new FileReader("./demokick.txt");
			BufferedReader br = new BufferedReader(fr);
			BufferedWriter wr = new BufferedWriter(new FileWriter("./demokick1.txt"));
		
			int num = Integer.parseInt(br.readLine());
			wr.write(num + "\n");
			for(int i = 0; i < num; i++) {
				String line = br. readLine();
				String coors[] = line.split(" ");
				String x = coors[0];
				String y = coors[1];
			
				wr.write(y + " " + x + "\n");
			}
			
			br.close();
			wr.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
