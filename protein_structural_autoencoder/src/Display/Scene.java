package Display;

import java.util.ArrayList;
import java.util.List;

public class Scene {

	List<Mesh> meshs;
	
	public Scene() {
		this.meshs = new ArrayList<>();
	}
	
	public void add(Mesh mesh) {
		this.meshs.add(mesh);
	}

	public List<Mesh> getMeshs() {
		return meshs;
	}
	
	public void destroy() {
		for (Mesh mesh : meshs) {
			mesh.destroy();
		}
		this.meshs = new ArrayList<>();
	}
	
}
