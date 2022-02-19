package display.event;

public class Event {

	private Sender sender = null;
	private Data data = null;
	
	public Event setSender(Sender sender) {
		this.sender = sender;
		return this;
	}
	
	public Event setData(Data data) {
		this.data = data;
		return this;
	}
	
	protected String getName() {
		return "";
	}
	
	Sender getSender() {
		return sender;
	}
	
	Data getData() {
		return data;
	}
	
}
