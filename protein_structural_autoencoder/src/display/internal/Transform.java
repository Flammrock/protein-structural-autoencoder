package display.internal;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {

	private Vector3f position;
	private Quaternionf rotation;
	private Quaternionf rotation_internal;
	private Vector3f scale;
	
	public Transform() {
		position = new Vector3f();
		rotation = new Quaternionf();
		rotation_internal = new Quaternionf();
		scale = new Vector3f(1);
	}
	
	public Matrix4f getTransformation() {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(position);
		matrix.rotate(rotation);
		matrix.rotate(rotation_internal);
		matrix.scale(scale);
		return matrix;
	}
	
	

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = new Vector3f(position);
	}

	public Quaternionf getRotation() {
		return rotation;
	}

	public void setRotation(Quaternionf rotation) {
		this.rotation = new Quaternionf(rotation);
	}
	
	public void setInternalRotation(Quaternionf rotation) {
		this.rotation_internal = new Quaternionf(rotation);
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	
	public void rotate(float angle, Vector3f axis, Vector3f origin) {
		position.sub(origin);
		rotation.rotateAxis(angle, axis.x, axis.y, axis.z);
		position.rotateAxis(angle, axis.x, axis.y, axis.z);
		position.add(origin);
	}
	
	
	
}
