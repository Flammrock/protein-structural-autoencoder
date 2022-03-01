package display.internal;

public class PerspectiveCamera extends Camera {

	private float fovDeg;

	
	public PerspectiveCamera(float fovDeg, int width, int height) {
		super();
		this.fovDeg = fovDeg;
		setPerspective((float)Math.toRadians(this.fovDeg), width, height, 0.01f, 100000.0f);
	}
	
	public void resize(int width, int height) {
		setPerspective((float)Math.toRadians(this.fovDeg), width, height, 0.01f, 100000.0f);
	}
	
}
