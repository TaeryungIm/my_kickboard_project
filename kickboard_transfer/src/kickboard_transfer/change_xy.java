package kickboard_transfer;
import java.io.*;

public class change_xy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileReader fr = new FileReader("./nearKickboard.txt");
			BufferedReader br = new BufferedReader(fr);
			BufferedWriter wr = new BufferedWriter(new FileWriter("./nearKickboard1.txt"));
		
			int num = Integer.parseInt(br.readLine());
			wr.write(num + "\n");
			System.out.print(num + "\n");
			for(int i = 0; i < num; i++) {
				String line = br. readLine();
				String coors[] = line.split(" ");
				System.out.print(coors.length + "\n");
				String x = coors[1];
				String y = coors[2];
			
				wr.write(coors[0] + " " + y + " " + x + "\n");
			}
			
			br.close();
			wr.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
