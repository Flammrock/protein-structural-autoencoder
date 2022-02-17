package display.internal;

import org.joml.Vector3f;

public abstract class Geometry {

	protected float[] vertices;
	protected int[] indices;
	
	
	public float[] getVertices() {
		return vertices;
	}
	public int[] getIndices() {
		return indices;
	}
	
	public void setColor(Vector3f color) {
		for (int i = 0; i < vertices.length; i+=(Mesh.VERTEX_SIZE-3)) {
			vertices[i+3] = color.x;
			vertices[i+4] = color.y;
			vertices[i+5] = color.z;
		}
	}
	
}
