package display.eventtypes;

import display.event.Container;
import display.event.Event;
import imgui.ImVec2;

public class ResizeEvent extends Event {
	public static String Name = "resize";
	
	public ResizeEvent setData(ImVec2 data) {
		super.setData(new Container<ImVec2>(data));
		return this;
	}
	
	@Override
	protected String getName() {
		return ResizeEvent.Name;
	}
}
