package display.eventtypes;

import bio.pdb.records.Atom;
import display.event.Container;
import display.event.Event;

public class AtomLoadEvent extends Event {
	public static String Name = "atomLoad";
	
	public AtomLoadEvent setData(Atom data) {
		super.setData(new Container<Atom>(data));
		return this;
	}
	
	@Override
	protected String getName() {
		return AtomLoadEvent.Name;
	}
}
