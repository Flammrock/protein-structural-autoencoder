package dataflow.cluster;

import dataflow.Vector;

public class ClusterPoint<T> {

	private T data;
	private Vector position;
	
	public ClusterPoint(Vector position, T data) {
		this.position = new Vector(position);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public Vector getPosition() {
		return position;
	}
	
}
