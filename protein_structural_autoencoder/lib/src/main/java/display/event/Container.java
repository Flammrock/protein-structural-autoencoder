package display.event;

public class Container<T> extends Data {

	private T data;
	
	public Container(T data) {
		this.data = data;
	}
	
	@Override
	public T get() {
		return data;
	}
	
}
