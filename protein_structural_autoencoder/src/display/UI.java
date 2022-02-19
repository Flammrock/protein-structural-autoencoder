package display;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class UI {

	private Map<String,Component> root = new LinkedHashMap<>();
	
	protected void add(Component c) {
		root.put(c.getInternalID(), c);
	}
	
	public void draw() {
		for (Map.Entry<String,Component> entry : root.entrySet()) {
			entry.getValue().update();
		}
	}
	
	public abstract void destroy();
	
	protected abstract void buildDockSpace(int dockID);
	
}
