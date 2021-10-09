package kickboard_transfer;

public class OutList {
	int startpoint, endpoint, time, cost, cur_loc;
	String transport;
	String[] company_list;
	Point[] distance_list;
	
	public OutList(int[] list, String transport) {
		startpoint = list[0];
		endpoint = list[1];
		time = list[2];
		cost = list[3];
		this.transport = transport;
	}
	
	public OutList(int cur_loc, String[] company_list, Point[] distance_list) {
		this.cur_loc = cur_loc;
		this.company_list = company_list;
		this.distance_list = distance_list; // currently not using this one
	}
	
	public void changeOut(int[] list) {
		this.startpoint = list[0];
		this.endpoint = list[1];
		this.time = list[2];
		this.cost = list[3];
	}
	
	public void changeTransport(String transport) {
		this.transport = transport;
	}
}
