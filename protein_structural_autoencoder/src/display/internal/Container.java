package display.internal;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Container {

	private List<Mesh> meshs;
	private Vector3f minWindow;
	private Vector3f maxWindow;
	private Vector3f center;
	
	private Vector3f position;
	
	public Container() {
		this.meshs = new ArrayList<>();
		this.minWindow = null;
		this.maxWindow = null;
		this.position = new Vector3f(0.0f);
		this.center = new Vector3f(0.0f);
	}
	
	private void computeWindow(Vector3f pos) {
		if (minWindow==null) minWindow = new Vector3f(pos);
		if (maxWindow==null) maxWindow = new Vector3f(pos);
		if (minWindow.x > pos.x) minWindow.x = pos.x;
		if (minWindow.y > pos.y) minWindow.y = pos.y;
		if (minWindow.z > pos.z) minWindow.z = pos.z;
		if (maxWindow.x < pos.x) maxWindow.x = pos.x;
		if (maxWindow.y < pos.y) maxWindow.y = pos.y;
		if (maxWindow.z < pos.z) maxWindow.z = pos.z;
	}
	
	private void computeCenter(Vector3f pos) {
		if (meshs.isEmpty()) {
			center = new Vector3f(0.0f);
		} else if (meshs.size()==1) {
			center = new Vector3f(pos);
		} else {
			center = center.mul(meshs.size()-1).add(pos).div(meshs.size());
			
		}
	}
	
	public void add(Mesh mesh) {
		Vector3f pos = mesh.getPosition();
		this.meshs.add(mesh);
		computeWindow(pos);
		computeCenter(pos);
	}
	
	public void remove(Mesh mesh) {
		this.meshs.remove(mesh);
	}
	
	public List<Mesh> getMeshs() {
		return meshs;
	}

	public Vector3f getMin() {
		return new Vector3f(minWindow);
	}

	public Vector3f getMax() {
		return new Vector3f(maxWindow);
	}
	
	public Vector3f getCenter() {
		return new Vector3f(center);
	}
	
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	public void translate(Vector3f t) {
		position.add(t);
		maxWindow.add(t);
		minWindow.add(t);
		center = new Vector3f(0.0f);
		for (Mesh mesh : meshs) {
			mesh.translate(t);
			center = center.add(mesh.getPosition());
		}
		if (meshs.size()>0) center = center.div(meshs.size());
	}
	
	public void rotate(float angle, Vector3f axis) {
		Vector3f center = getCenter();
		for (Mesh mesh : meshs) {
			mesh.getTransform().rotate(angle, axis, center);
		}
	}

	public void colorize(int pos, int size, Color c) {
		int s = meshs.size();
		for (int i = pos; (i < pos+size) && (i < s); i++) {
			meshs.get(i).colorize(c);
		}
	}
	
	public void setHighlight(int pos, int size, Color c, boolean v) {
		int s = meshs.size();
		for (int i = pos; (i < pos+size) && (i < s); i++) {
			meshs.get(i).setHighlight(c, v);
		}
	}
	
	
}
