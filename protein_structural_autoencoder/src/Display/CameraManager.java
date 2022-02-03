package Display;

import java.util.HashMap;
import java.util.Map;

public class CameraManager {

	private Map<String, Camera> cameras;
	private Camera mainCamera;
	
	public CameraManager() {
		this.cameras = new HashMap<>();
		this.mainCamera = null;
	}
	
	public void createCamera(String ID) {
		cameras.put(ID, new PerspectiveCamera(60.0f, 640, 480));
	}
	
	public void setMainCamera(String ID) {
		mainCamera = getCamera(ID);
	}
	
	public Camera getMainCamera() {
		return mainCamera;
	}
	
	public Camera getCamera(String ID) {
		if (!cameras.containsKey(ID)) throw new IllegalArgumentException("No camera exists under the ID : '"+ID+"'");
		return cameras.get(ID);
	}
	
	public void deleteCamera(String ID) {
		cameras.remove(ID);
	}
	
	public void resize(int width, int height) {
		if (mainCamera!=null) {
			mainCamera.resize(width, height);
		}
	}
	
}
