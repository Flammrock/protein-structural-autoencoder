package display;

import display.internal.Color;
import imgui.ImGui;

public class Label extends Component {

	private String text = "Label";
	private Color color = null;
	
	public Label(String text) {
		this.text = text;
	}
	
	public Label(String text, Color color) {
		this(text);
		this.color = new Color(color);
	}

	public String getText() {
		return text;
	}

	public Label setText(String text) {
		this.text = text;
		return this;
	}
	
	public Color getColor() {
		return new Color(color);
	}

	public Label setColor(Color color) {
		this.color = new Color(color);
		return this;
	}

	@Override
	public void update() {
		if (color!=null) {
			ImGui.textColored(color.r,color.g,color.b,color.alpha,text);
		} else {
			ImGui.text(text);
		}
	}
	
}
