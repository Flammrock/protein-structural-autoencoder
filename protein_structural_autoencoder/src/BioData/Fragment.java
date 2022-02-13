package BioData;

import java.util.ArrayList;
import java.util.List;

/*
 * TODO :
 * 
 *   - un Fragment correspond à une List<Residue> (@see Residue.java),
 *   - on construira des ContactMap (@see ContactMap.java) pour chaque Fragment
 *   - que l'on récupérera le long des chaînes polypeptidiques de la Protein (@see Protein.java)
 * 
 * 
 *   - créé une méthode factory "List<Fragment> createFromSlidingWindow(List<Residue>, int size)"
 *   - cette méthode créé une List<Fragment> où chaque Fragment contient {size} Residue
 *   
 *   @example :
 *    si {size=3}, alors le 1er fragment contiendra le 1er Residue, le 2ème Résidue, le 3ème Résidue
 *                      le 2ème fragment contiendra le 2ème Residue, le 3ème Residue et le 4ème Residue
 *                      ....
 * 
 */

public class Fragment {
	private List<Residue> residues;
	
	public Fragment() {
		this.residues = new ArrayList<>();
	}
	
	public Fragment(List<Residue> R) {
		this.residues = R;
	}
	
	public void set(List<Residue> R) {
		this.residues = R;
	}
	
	public List<Residue> getResidues(){
		return this.residues;
	}
	public List<Fragment> createFromSlidinWindow(List<Residue> Residues, int size){
		List<Fragment> F = new ArrayList<>();
		for(int i = 0; i<Residues.size()-size+1;i++) {
			List<Residue> R = new ArrayList<>();
			for(int j = 0; j<size;j++) {
				R.add(Residues.get(i+j));
			}
			Fragment Fr = new Fragment();
			Fr.set(R);
			F.add(Fr);
		}
		return F;
	}
}
