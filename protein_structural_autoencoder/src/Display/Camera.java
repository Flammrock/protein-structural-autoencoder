package Display;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

	private Vector3f position;
	private Quaternionf rotation;
	private Matrix4f projection;
	
	float fov;
	float aspectRatio;
	
	public Camera() {
		position = new Vector3f();
		rotation = new Quaternionf();
		projection = new Matrix4f();
	}
	
	public Matrix4f getTransformation() {
		Matrix4f matrix = new Matrix4f();
		matrix.rotate(rotation.conjugate(new Quaternionf()));
		matrix.translate(position.mul(-1, new Vector3f()));
		return matrix;
	}
	
	public Vector2f visibleRect(float depth) {
		float cameraOffset = position.z;
		if (depth < cameraOffset)
			depth -= cameraOffset;
		else
			depth += cameraOffset;
		float vFOV = fov;
		float h = (float) (2 * Math.tan(vFOV / 2) * Math.abs(depth));
		float w = h * aspectRatio;
		return new Vector2f(w, h);
	}

	public void setOrthographic(float left, float right, float top, float bottom) {
		projection.setOrtho2D(left, right, bottom, top);
	}
	
	public void setPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		this.fov = fov;
		this.aspectRatio = aspectRatio;
		projection.setPerspective(fov, aspectRatio, zNear, zFar);
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

	public Matrix4f getProjection() {
		return projection;
	}
	
	
}
