package system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resource {

	private String name;
	private InputStream is;
	
	
	Resource(String name, InputStream is) {
		this.name = name;
		this.is = is;
	}
	
	public BufferedReader getBuffer() throws IOException {
		return new BufferedReader(new InputStreamReader(is));
	}
	
	public String getName() {
		return name;
	}
	
	
}
