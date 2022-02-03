package BioData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.Vector3f;

public class Atom {

	private Vector3f position;
	private String type;
	
	public Atom(String type, Vector3f position) {
		this.type = type;
		this.position = position;
	}
	
	static public Stream<Atom> loadFromFile(String filename) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Atom.class.getResourceAsStream(filename)));
		return reader.lines().map(Atom::load);
	}
	
	static Atom load(String data) {
		Deque<String> tokens = Arrays.stream(data.split(" ")).map(String::trim).filter(Predicate.not(String::isBlank)).collect(Collectors.toCollection(ArrayDeque::new));
		tokens.pop();tokens.pop();String type = tokens.pop();
		tokens.pop();tokens.pop();tokens.pop();
		Vector3f pos = new Vector3f((float)Double.parseDouble(tokens.pop()),(float)Double.parseDouble(tokens.pop()),(float)Double.parseDouble(tokens.pop()));
		//System.out.println("type :"+type+", pos: "+pos.toString());
		return new Atom(type,pos);
	}

	public Vector3f getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}
	
	
	
	
	
}
