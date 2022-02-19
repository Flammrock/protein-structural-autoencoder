package bio.pdb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class File {

	private String filename;
	
	public File(String filename) {
		this.filename = filename;
		BufferedReader reader = new BufferedReader(new InputStreamReader(File.class.getResourceAsStream(filename)));
		reader.lines()
		.filter(s -> s.length() >= 6)
		.map(Record::parseLine)
		;
	}
	
	public String getFilename() {
		return filename;
	}
}
