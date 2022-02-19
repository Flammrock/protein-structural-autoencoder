package bio;

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
	private int residueID;
	
	public Atom(String type, Vector3f position, int residueID) {
		this.type = type.trim();
		this.position = position;
		this.residueID = residueID;
	}
	
	static Atom load(String data) {
		bio.pdb.records.Atom record = new bio.pdb.records.Atom(bio.pdb.Record.ATOM.parse(data));
		Vector3f pos = new Vector3f(record.x,record.y,record.z);
		//System.out.println("type :"+record.name+", pos: "+pos.toString()+", reqSeq: "+record.resSeq);
		return new Atom(record.name,pos,record.resSeq);
	}

	public Vector3f getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}
	
	public boolean isAlphaCarbon() {
		return type.length() >= 2 && type.substring(0,2).equals("CA");
	}
	
	public int getResidueID() {
		return residueID;
	}
	
	public float AtomDistance(Atom a) {
		float x = (float) Math.pow(this.position.x - a.position.x,2);
		float y = (float) Math.pow(this.position.y - a.position.y,2);
		float z = (float) Math.pow(this.position.z - a.position.z,2);
		return (float) Math.sqrt(x+y+z);
	}
	
	
}
