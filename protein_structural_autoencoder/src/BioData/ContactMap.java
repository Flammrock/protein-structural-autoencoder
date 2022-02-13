package BioData;
import java.util.ArrayList;
import java.util.List;
/*
 * TODO: 
 * 
 *   - le constructeur prend en entrÃ©e un Fragment (@see Fragment.java)
 *   - le constructeur itÃ¨re ensuite sur chaque Residue (@see Residue.java)
 *   - pour chaque rÃ©sidue, rÃ©cupÃ©rer le carbone alpha (Residue::getAlphaCarbon())
 *   - mettre cet Atom dans une List<Atom> temporaire
 *   - ensuite, crÃ©Ã© un Float[N][N] oÃ¹ N est le nombre d'atomes carbone alpha
 *   - pour terminer, Ã  l'aide d'une double boucle for imbriquÃ©e (sur la List<Atom> temporaire) calculer
 *   - la distance deux Ã  deux des Atom (Ã  l'aide de Atom::distance(Atom other)
 *   
 *   
 *   
 *   - [bonus] : crÃ©er une mÃ©thode :
 *       public Texture getTexture()    // pour l'affichage de la contact Map (@see Texture.java)
 *            // Hint : utiliser un OffScreen (@see OffScreen.java) ainsi qu'un Shader 2D (@see Shader.java)
 *            // on peut probablement aussi utiliser des mÃ©thodes natifs prÃ©faites de ImGui pour plot des matrices
 * 
 */

public class ContactMap {
		private Fragment F;
		private float[][] atomes;
		
		public ContactMap(Fragment F) {
			List<Atom> AlphaCarbons = new ArrayList<>();
			for(int i = 0; i < F.getResidues().size();i++) {
				AlphaCarbons.add(F.getResidues().get(i).getAlphaCarbon());
			}
			int N = AlphaCarbons.size();
		    this.atomes = new float[N][N];
			for(int j = 0; j<N;j++) {
				for(int k = j; k<N ;k++) {
					this.atomes[j][k] = AlphaCarbons.get(j).AtomDistance(AlphaCarbons.get(k));
					this.atomes[k][j] = this.atomes[j][k];
				}
			}
		}
		
}
