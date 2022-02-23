package display;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;

import display.WindowPanel.Flag;
import display.event.Data;
import display.event.Sender;
import display.internal.Color;

public class Console extends Component {
	
	public static enum Level {
		Default(Color.White),
		Success(Color.Green),
		Warning(Color.Orange),
		Error(Color.Red),
		Fatal(Color.DarkRed),
		Debug(Color.Grey),
		Trace(Color.Magenta)
		;
		
		private Color color;
		
		private Level(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return new Color(color);
		}
	}

	private Deque<Component> items = new ArrayDeque<>();
	private int limits = 100;
	private WindowPanel container = new WindowPanel();
	
	public Console(int limits) {
		this.limits = limits;
		container.setBindingOnDraw((Sender sender, Data data) -> {
			updateInternal();
		});
	}
	
	public void setTitle(String title) {
		container.setTitle(title);
	}
	
	public void setFlags(EnumSet<Flag> flags) {
		container.setFlags(flags);
	}
	
	public void log(String text) {
		items.add(new Label(text,Level.Default.getColor()));
		container.scrollYDown();
	}
	
	public void log(Level level, String text) {
		items.add(new Label(text,level.getColor()));
		container.scrollYDown();
	}
	
	private void updateInternal() {
		while (items.size() > limits) items.pop();
		for (Component c : items) {
			c.update();
		}
	}
	
	@Override
	public void setDockerNode(DockerSpace.Node node) {
		container.setDockerNode(node);
	}
	
	@Override
	public void update() {
		container.update();
	}

}
