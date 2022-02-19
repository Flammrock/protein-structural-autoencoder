package display;

import java.util.ArrayDeque;
import java.util.Deque;

public class Console extends Component {

	private Deque<Component> items = new ArrayDeque<>();
	private int limits = 100;
	
	public Console(int limits) {
		this.limits = limits;
	}
	
	public void log(String text) {
		items.add(new Label(text));
	}
	
	@Override
	public void update() {
		while (items.size() > limits) items.pop();
		for (Component c : items) {
			c.update();
		}
	}

}
