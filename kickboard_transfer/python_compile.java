package kickboard_transfer;
//import java.io.*;
import org.python.util.PythonInterpreter;
public class python_compile {
	static String python_exe;
	static String python_source_file;
	
	public static void python_operate(String python_source_file) {
		PythonInterpreter pi = new PythonInterpreter();
		pi.execfile(python_source_file);
		
	}
}