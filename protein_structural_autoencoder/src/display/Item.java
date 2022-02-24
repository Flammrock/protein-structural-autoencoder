package display;

import imgui.ImGui;

public class Item extends Component {
	
	private boolean isHovered = false;
	
	public void setBindingOnMouseIn(display.event.Callback c) {
		eventManager.register("mousein", c);
	}
	
	public void setBindingOnMouseOut(display.event.Callback c) {
		eventManager.register("mouseout", c);
	}
	
	private void propagateMouseInOut() {
		/* track internal imgui event */
		boolean s_isHovered = isHovered;
		isHovered = ImGui.isItemHovered();
		
		/* propagate only if internal state changed */
		if (s_isHovered != isHovered) {
			if (isHovered) {
				eventManager.fire("mousein");
			} else {
				eventManager.fire("mouseout");
			}
		}
	}

	public void propagateEvents() {
		propagateMouseInOut();
	}
	
}
