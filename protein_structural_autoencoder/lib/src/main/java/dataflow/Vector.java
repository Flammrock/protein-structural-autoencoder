package dataflow;

import org.joml.Vector2f;

public class Vector extends Vector2f {

	public Vector(float x, float y) {
		super(x,y);
	}
	
	public Vector(Vector v) {
		super(v.x,v.y);
	}
	
}
