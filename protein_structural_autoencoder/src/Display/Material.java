package Display;

import org.joml.Vector3f;

public class Material {

	protected Vector3f color;
	
	public Material(Vector3f color) {
		this.color = color;
	}

	public Vector3f getColor() {
		return color;
	}
	
	
	
}
