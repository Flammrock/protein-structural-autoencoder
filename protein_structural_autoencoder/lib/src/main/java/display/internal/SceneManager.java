package display.internal;


import java.util.HashMap;
import java.util.Map;

import display.event.Event;
import display.event.Eventable;

public class SceneManager implements Eventable {

	private Map<String, Scene> scenes;
	
	public SceneManager() {
		super();
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

	@Override
	public void sendEvent(Event e) {
		for (Map.Entry<String, Scene> entry : scenes.entrySet()) {
			entry.getValue().sendEvent(e);
		}
	}
	
}
