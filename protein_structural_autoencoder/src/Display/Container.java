package Display;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Container {

	private List<Mesh> meshs;
	private Vector3f minWindow;
	private Vector3f maxWindow;
	
	private Vector3f position;
	
	public Container() {
		this.meshs = new ArrayList<>();
		this.minWindow = new Vector3f();
		this.maxWindow = new Vector3f();
		this.position = new Vector3f();
	}
	
	public void add(Mesh mesh) {
		Vector3f pos = mesh.getPosition();
		this.meshs.add(mesh);
		if (minWindow.x > pos.x) minWindow.x = pos.x;
		if (minWindow.y > pos.y) minWindow.y = pos.y;
		if (minWindow.z > pos.z) minWindow.z = pos.z;
		if (maxWindow.x < pos.x) maxWindow.x = pos.x;
		if (maxWindow.y < pos.y) maxWindow.y = pos.y;
		if (maxWindow.z < pos.z) maxWindow.z = pos.z;
	}
	
	public void remove(Mesh mesh) {
		this.meshs.remove(mesh);
	}
	
	public List<Mesh> getMeshs() {
		return meshs;
	}

	public Vector3f getMin() {
		return minWindow;
	}

	public Vector3f getMax() {
		return maxWindow;
	}
	
	public Vector3f getCenter() {
		return new Vector3f((maxWindow.x+minWindow.x)/2.0f,(maxWindow.y+minWindow.y)/2.0f,(maxWindow.z+minWindow.z)/2.0f);
		//return maxWindow.add(minWindow).div(2.0f);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void translate(Vector3f t) {
		position.add(t);
		for (Mesh mesh : meshs) {
			mesh.getPosition().add(t);
		}
	}
	
	public void rotate(float angle, Vector3f axis) {
		Vector3f center = getCenter().add(position);
		for (Mesh mesh : meshs) {
			mesh.getTransform().rotate(angle, axis, center);
		}
	}
	
	
}
