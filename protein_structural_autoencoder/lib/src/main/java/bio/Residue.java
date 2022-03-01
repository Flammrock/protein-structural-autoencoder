package bio;

import java.util.ArrayList;
import java.util.List;

/*
 * TODO:
 * 
 *    - Un Residue correspond à un acide aminé (Gly,Ala,Val,Leu,Ile,Met,Phe,Try,Pro,Ser,Thr,Cys,Tyr,Asn,Gln,Asp,Glu,Lys,Arg,His)
 *    - un acide animé (Residue) est composé de plusieurs Atom (@see Atom.java)
 *    
 *    - le constructeur initialise une List<Atom>
 *    - ajouter une méthode void add(Atom atom) pour ajouter des Atom dans le Residue
 *    - ajouter une méthode Atom getAlphaCarbon() qui retourne le carbone alpha du Residue
 * 
 */

public class Residue {

	private List<Atom> atoms;
	private int ID;
	
	public List<Atom> getAtoms() {
		return atoms;
	}

	public int getID() {
		return ID;
	}
	
	public int size() {
		return atoms.size();
	}

	public Residue(int ID, List<Atom> l) {
		this.ID = ID;
		this.atoms = l;
	}
	
	public Atom getAlphaCarbon() {
		for (Atom a : atoms) {
			if (a.isAlphaCarbon()) {
				return a;
			}
		}
		throw new RuntimeException("No Alpha Carbon in this Residue.");
	}
	
	public static Residue build(int ID, List<Atom> atoms) {
		return new Residue(ID,atoms);
	}
	
}
