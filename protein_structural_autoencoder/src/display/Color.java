package display;


public class Color {
	
	static public Color Black   = new Color(0.0f, 0.0f, 0.0f);
	static public Color Blue    = new Color(0.0f, 0.0f, 1.0f);
	static public Color Cyan    = new Color(0.0f, 1.0f, 1.0f);
	static public Color Gray    = new Color(0.5f, 0.5f, 0.5f);
	static public Color Green   = new Color(0.0f, 1.0f, 0.0f);
	static public Color Grey    = new Color(0.5f, 0.5f, 0.5f);
	static public Color Magenta = new Color(1.0f, 0.0f, 1.0f);
	static public Color Red     = new Color(1.0f, 0.0f, 0.0f);
	static public Color Orange  = new Color(1.0f, 0.49f, 0.0f);
	static public Color White   = new Color(1.0f, 1.0f, 1.0f);
	static public Color Yellow  = new Color(1.0f, 0.92f, 0.016f);
	
	static public Color DarkRed = new Color(0.6f, 0.0f, 0.0f);

	public float r = 0.0f;
	public float g = 0.0f;
	public float b = 0.0f;
	public float alpha = 1.0f;
	
	public Color(Number r, Number g, Number b) {
		this.r = r.floatValue();
		this.g = g.floatValue();
		this.b = b.floatValue();
	}
	
	public Color(Number r, Number g, Number b, Number alpha) {
		this(r,g,b);
		this.alpha = alpha.floatValue();
	}
	
	public Color(Color c) {
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
		this.alpha = c.alpha;
	}
	
	@Override
	public String toString() {
		return "Color {"+r+","+g+","+b+","+alpha+"}";
	}
	
}
