package display.eventtypes;

import bio.Protein;
import display.event.Container;
import display.event.Event;

public class ProteinLoadStartEvent extends Event {
public static String Name = "proteinLoadStart";
	
	public ProteinLoadStartEvent setData(Protein data) {
		super.setData(new Container<Protein>(data));
		return this;
	}
	
	@Override
	protected String getName() {
		return ProteinLoadStartEvent.Name;
	}
}
