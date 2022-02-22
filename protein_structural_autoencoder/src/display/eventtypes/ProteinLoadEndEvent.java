package display.eventtypes;

import bio.Protein;
import display.event.Container;
import display.event.Event;

public class ProteinLoadEndEvent extends Event {
	public static String Name = "proteinLoadEnd";
	
	public ProteinLoadEndEvent setData(Protein data) {
		super.setData(new Container<Protein>(data));
		return this;
	}
	
	@Override
	protected String getName() {
		return ProteinLoadEndEvent.Name;
	}
}
