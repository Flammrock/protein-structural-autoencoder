package display;

import java.util.LinkedHashMap;
import java.util.Map;

public class Box extends Item {
	
	private Map<String,Component> children = new LinkedHashMap<>();

	public void addChildren(Component component) {
		children.put(component.getInternalID(), component);
	}
	
	public boolean hasChildren(Component component) {
		return children.containsKey(component.getInternalID());
	}
	
	public void removeChildren(Component component) {
		if (!hasChildren(component)) return;
		children.remove(component.getInternalID());
	}
	
	public void clearChildren() {
		children.clear();
	}
	
	protected void drawChildren() {
		for (Map.Entry<String,Component> entry : children.entrySet()) {
			entry.getValue().update();
		}
	}
	
}
