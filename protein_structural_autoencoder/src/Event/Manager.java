package Event;

import java.util.HashMap;
import java.util.Map;

public class Manager {

	private Map<String, Callback> _events;
	
	public Manager() {
		_events = new HashMap<String, Callback>();
	}
	
	public void register(String identifier, Callback callback) {
		_events.put(identifier,callback);
	}
	
	public <D extends Data> void fire(String identifier, Sender sender, D data) {
		if (!_events.containsKey(identifier)) return;
		_events.get(identifier).execute(sender, data);
	}
	
	public <D extends Data> void fire(String identifier) {
		fire(identifier,null,null);
	}
	
	public <D extends Data> void fire(String identifier, D data) {
		fire(identifier,null,data);
	}
	
}
