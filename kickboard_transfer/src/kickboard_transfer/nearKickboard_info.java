package kickboard_transfer;

public class nearKickboard_info {
	static String kickboard_file;
	static String txt = ".txt";
	
	public static String getNearKickboardInfo(/*object such as current position coord can be added*/) {
		//TODO: this method is to get information of the nearby kickboard
		//and put that info to the kickboard_file
		//the format of the data that we might get from the kickboard company is JSON
		//this function need to change that data in to this formation
		
		/*****************************************/
//		6
//		airkick 127.0458603037813 37.50361694160942
//		demokick 127.047662720938 37.50292752629602
//		airkick 127.06409788075185 37.509458200913485
//		demokick 127.06276261366881 37.5099448372509
//		airkick 127.06413399986995 37.50722531200128
//		demokick 127.0572534553 37.5034232324
		/********************************************/
		/*this is only the demo code*/
		kickboard_file = "nearKickboard" + txt;
		
		return kickboard_file;
	}
}
