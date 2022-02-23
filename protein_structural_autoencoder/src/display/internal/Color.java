package display.internal;

public final class Color {
	
	static public final Color Black   = new Color(0.0f, 0.0f, 0.0f);
	static public final Color Blue    = new Color(0.0f, 0.0f, 1.0f);
	static public final Color Cyan    = new Color(0.0f, 1.0f, 1.0f);
	static public final Color Gray    = new Color(0.5f, 0.5f, 0.5f);
	static public final Color Green   = new Color(0.0f, 1.0f, 0.0f);
	static public final Color Grey    = new Color(0.5f, 0.5f, 0.5f);
	static public final Color Magenta = new Color(1.0f, 0.0f, 1.0f);
	static public final Color Red     = new Color(1.0f, 0.0f, 0.0f);
	static public final Color Orange  = new Color(1.0f, 0.49f, 0.0f);
	static public final Color White   = new Color(1.0f, 1.0f, 1.0f);
	static public final Color Yellow  = new Color(1.0f, 0.92f, 0.016f);
	
	static public final Color DarkBlue    = new Color(0.0f, 0.0f, 1.0f).brighter(-0.4f);
	static public final Color DarkCyan    = new Color(0.0f, 1.0f, 1.0f).brighter(-0.4f);
	static public final Color DarkGray    = new Color(0.5f, 0.5f, 0.5f).brighter(-0.4f);
	static public final Color DarkGreen   = new Color(0.0f, 1.0f, 0.0f).brighter(-0.4f);
	static public final Color DarkGrey    = new Color(0.5f, 0.5f, 0.5f).brighter(-0.4f);
	static public final Color DarkMagenta = new Color(1.0f, 0.0f, 1.0f).brighter(-0.4f);
	static public final Color DarkRed     = new Color(1.0f, 0.0f, 0.0f).brighter(-0.4f);
	static public final Color DarkOrange  = new Color(1.0f, 0.49f, 0.0f).brighter(-0.4f);
	static public final Color DarkWhite   = new Color(1.0f, 1.0f, 1.0f).brighter(-0.4f);
	static public final Color DarkYellow  = new Color(1.0f, 0.92f, 0.016f).brighter(-0.4f);

	public final float r;
	public final float g;
	public final float b;
	public final float alpha;
	
	public Color() {
		this.r = 0.0f;
		this.g = 0.0f;
		this.b = 0.0f;
		this.alpha = 1.0f;
	}
	
	public Color(Number r, Number g, Number b) {
		this.r = r.floatValue();
		this.g = g.floatValue();
		this.b = b.floatValue();
		this.alpha = 1.0f;
	}
	
	public Color(Number r, Number g, Number b, Number alpha) {
		this.r = r.floatValue();
		this.g = g.floatValue();
		this.b = b.floatValue();
		this.alpha = alpha.floatValue();
	}
	
	public Color(Color c) {
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
		this.alpha = c.alpha;
	}
	
	public Color(Color c, Number alpha) {
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
		this.alpha = alpha.floatValue();
	}
	
	
	public static enum Mode {
		
		NORMAL(1/3.f,1/3.f,1/3.f),
		CCIR_601_1(0.299f,0.587f,0.114f),
		CCIR_709(0.2125f,0.7154f,0.0721f);
		
		private final float factorR;
		private final float factorG;
		private final float factorB;
		
		private final float factorCR;
		private final float factorCB;
		
		private final float factorSR;
		private final float factorSG;
		private final float factorSB;
		
		Mode(float fr,float fg, float fb) {
			factorR = fr;
			factorG = fg;
			factorB = fb;
			factorCR = 2.0f-2.0f*factorR;
			factorCB = 2.0f-2.0f*factorB;
			factorSR = 1.0f/factorCR;
			factorSG = 1.0f/factorG;
			factorSB = 1.0f/factorCB;
		}
		
	}
	
	
	private float clamp(float v) {
		return v < 0.0f ? 0.0f : v > 1.0f ? 1.0f : v;
	}
	
	public float[] data() {
		return new float[] {r,g,b,alpha};
	}
	
	public Color darker(float v) {
		return new Color(clamp(r-v),clamp(g-v),clamp(b-v),alpha);
	}
	
	public Color lighter(float v) {
		return new Color(clamp(r+v),clamp(g+v),clamp(b+v),alpha);
	}
	
	/**
	 * @param ratio between -1.0f and 1.0f
	 */
	public Color brighter(float ratio, Mode m) {
		float y = r*m.factorR + g*m.factorG + b*m.factorB;
		float cb = (b-y)*m.factorSB;
		float cr = (r-y)*m.factorSR;
		y+=ratio;
		float r = cr*m.factorCR+y;
		float b = cb*m.factorCB+y;
		float g = (y-b*m.factorB-r*m.factorR)*m.factorSG;
		return new Color(clamp(r),clamp(g),clamp(b),alpha);
	}
	
	/**
	 * @param ratio between -1.0f and 1.0f
	 */
	public Color brighter(float ratio) {
		return brighter(ratio,Mode.CCIR_709);
	}
	
	public float getLightness(Mode m) {
		return alpha * (m.factorR * r + m.factorG * g + m.factorB * b);
	}
	
	public float getLightness() {
		return getLightness(Mode.CCIR_709);
	}
	
	
	@Override
	public String toString() {
		return "Color {"+r+","+g+","+b+","+alpha+"}";
	}
	
}
