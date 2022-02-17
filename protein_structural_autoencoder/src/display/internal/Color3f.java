package display.internal;

import org.joml.Vector3f;

public class Color3f extends Vector3f {
	
	static public Color3f BLACK   = new Color3f(0.0f, 0.0f, 0.0f);
	static public Color3f BLUE    = new Color3f(0.0f, 0.0f, 1.0f);
	static public Color3f CYAN    = new Color3f(0.0f, 1.0f, 1.0f);
	static public Color3f GRAY    = new Color3f(0.5f, 0.5f, 0.5f);
	static public Color3f GREEN   = new Color3f(0.0f, 1.0f, 0.0f);
	static public Color3f GREY    = new Color3f(0.5f, 0.5f, 0.5f);
	static public Color3f MAGENTA = new Color3f(1.0f, 0.0f, 1.0f);
	static public Color3f RED     = new Color3f(1.0f, 0.0f, 0.0f);
	static public Color3f WHITE   = new Color3f(1.0f, 1.0f, 1.0f);
	static public Color3f YELLOW  = new Color3f(1.0f, 0.92f, 0.016f);
	


	public Color3f(float r, float g, float b) {
		super(r,g,b);
	}
	
	public Color3f() {
		super(0.0f,0.0f,0.0f);
	}
	
	
}
