package Display;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

	private Vector3f position;
	private Quaternionf rotation;
	private Matrix4f projection;
	
	private float fov;
	private float aspectRatio;
	private int width;
	private int height;
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	
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
		float h = (float) (2.0f * Math.tan(fov / 2.0f) * Math.abs(depth));
		float w = h * aspectRatio;
		return new Vector2f(w, h);
	}
	
	public void fitView(Container container) {
		Vector3f objectSizes = container.getMax().sub(container.getMin()).add(container.getPosition());
		float objectSize = Math.max(objectSizes.x, Math.max(objectSizes.y, objectSizes.z));
		float cameraView = (float) (2.0f * Math.tan(0.5f * fov));
		float distance = 0.5f * objectSize / cameraView;
		distance += 0.5f * objectSize;
		position = container.getCenter().add(container.getPosition()).sub(getForward().mul(distance));
	}

	public void setOrthographic(float left, float right, float top, float bottom) {
		projection.setOrtho2D(left, right, bottom, top);
	}
	
	public void setPerspective(float fov, int width, int height, float zNear, float zFar) {
		this.fov = fov;
		this.aspectRatio = (float)width / (float)height;
		this.width = width;
		this.height = height;
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
	
	public Vector3f getForward() {
		return new Vector3f(0,0,-1).rotate(rotation).normalize();
	}
	
	
}
