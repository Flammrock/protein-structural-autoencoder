package protein_structural_autoencoder;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {

	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;
	
	public Transform() {
		position = new Vector3f();
		rotation = new Quaternionf();
		scale = new Vector3f(1);
	}
	
	public Matrix4f getTransformation() {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(position);
		matrix.rotate(rotation);
		matrix.scale(scale);
		return matrix;
	}
	
	

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Quaternionf getRotation() {
		return rotation;
	}

	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	
	
}
