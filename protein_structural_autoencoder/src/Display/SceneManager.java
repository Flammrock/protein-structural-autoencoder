package Display;


import java.util.HashMap;
import java.util.Map;

public class SceneManager {

	private Map<String, Scene> scenes;
	
	public SceneManager() {
		this.scenes = new HashMap<>();
	}
	
	public Scene create(String ID) {
		Scene scene = new Scene();
		scenes.put(ID, scene);
		return scene;
	}
	
	public Scene get(String ID) {
		if (!scenes.containsKey(ID)) throw new IllegalArgumentException("No scene exists under the ID : '"+ID+"'");
		return scenes.get(ID);
	}
	
	public void delete(String ID) {
		Scene scene = get(ID);
		scene.destroy();
		scenes.remove(ID);
	}
	
	public void draw() {
		for (Map.Entry<String, Scene> entry : scenes.entrySet()) {
			entry.getValue().draw();
		}
	}
	
	public void destroy() {
		for (Map.Entry<String, Scene> entry : scenes.entrySet()) {
			entry.getValue().destroy();
		}
	}
	
}
