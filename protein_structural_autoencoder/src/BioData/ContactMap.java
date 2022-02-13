package BioData;

/*
 * TODO: 
 * 
 *   - le constructeur prend en entrée un Fragment (@see Fragment.java)
 *   - le constructeur itère ensuite sur chaque Residue (@see Residue.java)
 *   - pour chaque résidue, récupérer le carbone alpha (Residue::getAlphaCarbon())
 *   - mettre cet Atom dans une List<Atom> temporaire
 *   - ensuite, créé un Float[N][N] où N est le nombre d'atomes carbone alpha
 *   - pour terminer, à l'aide d'une double boucle for imbriquée (sur la List<Atom> temporaire) calculer
 *   - la distance deux à deux des Atom (à l'aide de Atom::distance(Atom other)
 *   
 *   
 *   
 *   - [bonus] : créer une méthode :
 *       public Texture getTexture()    // pour l'affichage de la contact Map (@see Texture.java)
 *            // Hint : utiliser un OffScreen (@see OffScreen.java) ainsi qu'un Shader 2D (@see Shader.java)
 *            // on peut probablement aussi utiliser des méthodes natifs préfaites de ImGui pour plot des matrices
 * 
 */

public class ContactMap {

}
