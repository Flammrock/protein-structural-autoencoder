package bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joml.Vector3f;

import display.event.Callback;
import display.event.Manager;
import display.eventtypes.AtomLoadEvent;
import display.eventtypes.ProteinLoadEndEvent;
import display.eventtypes.ProteinLoadStartEvent;
import display.internal.Color;
import display.internal.Container;
import display.internal.CylinderGeometry;
import display.internal.Material;
import display.internal.Mesh;
import display.internal.QuaternionHelper;
import display.internal.SphereGeometry;

import bio.pdb.Record;

public class Protein {
	
	private static Manager eventManager = new Manager();
	public static void setBindingOnLoadStart(Callback c) {
		eventManager.register(ProteinLoadStartEvent.Name, c);
	}
	public static void setBindingOnLoadEnd(Callback c) {
		eventManager.register(ProteinLoadEndEvent.Name, c);
	}
	
	private List<Residue> residues;
	private String filename;
	private Map<Integer,Integer> residuesPositionIndex;
	private int numAtoms;
	private final Container mesh;
	private final Container backboneMesh;
	
	private Protein(String filename, List<Residue> r) {
		this.filename = filename;
		residues = r;
		residuesPositionIndex = new HashMap<>();
		buildIndex();
		mesh = buildMesh();
		backboneMesh = buildBackboneMesh();
	}
	
	public Container getMesh() {
		return mesh;
	}
	
	public Container getBackboneMesh() {
		return backboneMesh;
	}
	
	public int size() {
		return numAtoms;
	}
	
	private void buildIndex() {
		int pos = 0;
		for (Residue r : residues) {
			residuesPositionIndex.put(r.getID(),pos);
			pos += r.size();
		}
		numAtoms = pos;
	}
	
	public static Protein buildFromFile(String filename) {
		Protein p = null;
		eventManager.fire(new ProteinLoadStartEvent().setData(p));
		BufferedReader reader = new BufferedReader(new InputStreamReader(Protein.class.getResourceAsStream(filename)));
		p = new Protein(
			filename,
			reader.lines()
			.filter(Record.is(Record.ATOM))
			.map(Atom::load)
			.collect(Collectors.groupingBy(
				Atom::getResidueID
			))
			.entrySet().stream()
			.map(entry -> Residue.build(entry.getKey(),entry.getValue()))
			.collect(Collectors.toList())
		)
		;
		eventManager.fire(new ProteinLoadEndEvent().setData(p));
		return p;
	}
	
	public List<Residue> getResidues() {
		return residues;
	}
	
	public String getOriginalFilename() {
		return filename;
	}
	
	
	private Container buildMesh() {
		Container container = new Container();
		Atom previousAtom = null;
		for (Residue r : residues) {
			for (Atom atom : r.getAtoms()) {
		        container.add(atom.buildMesh());
		        if (atom.isAlphaCarbon()) {
		        	if (previousAtom!=null) {
		        		container.add(Atom.buildMeshConnection(previousAtom,atom));
		        	}
		        	previousAtom = atom;
				}
			}
		}
		return container;
	}
	
	private Container buildBackboneMesh() {
		Container container = new Container();
		Atom previousAtom = null;
		for (Residue r : residues) {
			for (Atom atom : r.getAtoms()) {
		        if (atom.isAlphaCarbon()) {
		        	container.add(atom.buildMesh());
		        	if (previousAtom!=null) {
		        		container.add(Atom.buildMeshConnection(previousAtom,atom));
		        	}
		        	previousAtom = atom;
				}
			}
		}
		return container;
	}
	
	public static void setHighlightResidue(Protein p, Residue r, Container m, Container bm, boolean b) {
		int pos = p.residuesPositionIndex.get(r.getID());
		Color g = new Color(Color.Grey,0.3f);
		m.setHighlight(0,m.getMeshs().size(),g,b);
		m.setHighlight(pos+(r.getID()-p.residues.get(0).getID()),r.size(),Color.Red,b);
	}
	
}
