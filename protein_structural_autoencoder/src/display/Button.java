package display;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;

public class Button extends Component {

	private String text = "Button";
	private ImVec2 size = new ImVec2(0.0f,0.0f);
	
	
	private Color backgroundColor = null;
	private Color foregroundColor = null;
	private Color hoverColor      = null;
	private Color activeColor     = null;
	
	public Button(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public float getWidth() {
		return size.x;
	}
	
	public float getHeight() {
		return size.y;
	}
	
	public Button setWidth(float width) {
		size.x = width;
		return this;
	}
	
	public Button setHeight(float height) {
		size.y = height;
		return this;
	}

	public Color getBackgroundColor() {
		return new Color(backgroundColor);
	}

	public Button setBackgroundColor(Color color) {
		this.backgroundColor = new Color(color);
		return this;
	}
	
	public Color getForegroundColor() {
		return new Color(foregroundColor);
	}

	public Button setForegroundColor(Color color) {
		this.foregroundColor = new Color(color);
		return this;
	}
	
	private void pushStyleColor(int style, Color color) {
		ImGui.pushStyleColor(style,color.r,color.g,color.b,color.alpha);
	}

	@Override
	public void update() {
		int pushstyle = 0;
		
		if (backgroundColor!=null) {
			pushStyleColor(ImGuiCol.Button,backgroundColor);
			pushstyle++;
		}
		
		if (foregroundColor!=null) {
			pushStyleColor(ImGuiCol.Text,foregroundColor);
			pushstyle++;
		}
		
		if (hoverColor!=null) {
			pushStyleColor(ImGuiCol.ButtonHovered,hoverColor);
			pushstyle++;
		}
		
		if (activeColor!=null) {
			pushStyleColor(ImGuiCol.ButtonActive,activeColor);
			pushstyle++;
		}
		
		ImGui.button(text,size.x,size.y);
		ImGui.popStyleColor(pushstyle);
		
		//ImGui.button(text);
	}
	
}
