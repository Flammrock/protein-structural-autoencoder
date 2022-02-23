package display.internal;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector3f;
import org.joml.Vector4f;

import display.Color;

public class Mesh {
	
	public static int VERTEX_SIZE = 9;

	private int vertexArrayObject;
	private int vertexBufferObject;
	private int vertexBufferObjectIndices;
	
	private int indexCount;
	
	private boolean isDestroyState;
	private boolean isCreatedState;
	
	private Transform transform;
	
	private float[] vertices;
	private Vector4f color;
	private Vector4f highlightColor;
	private int[] indices;
	
	
	public Mesh(Geometry geo, Material mat) {
		this.isCreatedState = false;
		this.isDestroyState = false;
		this.transform = new Transform();
		Vector3f c = mat.getColor();
		this.color = new Vector4f(c.x,c.y,c.z,1.0f);
		this.highlightColor = null;
		geo.setColor(mat.getColor());
		this.vertices = computeNormal(geo.getVertices(), geo.getIndices());
		this.indices = geo.getIndices();
	}
	
	public boolean create() {
		
		if (isCreatedState) return false;
		//if (this.isDestroy()) return false;
		
		vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(vertexArrayObject);
		
		vertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		
		vertexBufferObjectIndices = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vertexBufferObjectIndices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Mesh.VERTEX_SIZE * Float.BYTES, 0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, Mesh.VERTEX_SIZE * Float.BYTES, 3 * Float.BYTES);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Mesh.VERTEX_SIZE * Float.BYTES, 6 * Float.BYTES);
		
		glBindVertexArray(0);
		
		indexCount = indices.length;
		
		isCreatedState = true;
		isDestroyState = false;
		
		return true;
	}
	
	private float[] computeNormal(float[] vertices, int[] indices) {
		Vector3f[] normals = new Vector3f[vertices.length / (Mesh.VERTEX_SIZE-3)];
		for (int i = 0; i < normals.length; i++) {
			normals[i] = new Vector3f(0,0,0);
		}
		for (int i = 0; i < indices.length; i += 3) {
			int i1 = indices[i]*(Mesh.VERTEX_SIZE-3);
			int i2 = indices[i+1]*(Mesh.VERTEX_SIZE-3);
			int i3 = indices[i+2]*(Mesh.VERTEX_SIZE-3);
		   Vector3f v0 = new Vector3f(vertices[i1],vertices[i1+1],vertices[i1+2]);
		   Vector3f v1 = new Vector3f(vertices[i2],vertices[i2+1],vertices[i2+2]);
		   Vector3f v2 = new Vector3f(vertices[i3],vertices[i3+1],vertices[i3+2]);
		   Vector3f normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
		   normals[indices[i]].add(normal);
		   normals[indices[i+1]].add(normal);
		   normals[indices[i+2]].add(normal);
		}
		for (int i = 0; i < normals.length; i++) {
			normals[indices[i]].normalize();
		}
		int j = 0;
		int k = 0;
		float[] vertices_computed = new float[vertices.length+normals.length*3];
		for (int i = 0; i < vertices_computed.length; i+=Mesh.VERTEX_SIZE) {
			vertices_computed[i] = vertices[j++];
			vertices_computed[i+1] = vertices[j++];
			vertices_computed[i+2] = vertices[j++];
			vertices_computed[i+3] = vertices[j++];
			vertices_computed[i+4] = vertices[j++];
			vertices_computed[i+5] = vertices[j++];
			vertices_computed[i+6] = normals[k].x;
			vertices_computed[i+7] = normals[k].y;
			vertices_computed[i+8] = normals[k++].z;
		}
		return vertices_computed;
	}
	
	public void destroy() {
		if (this.isDestroyState) return;
		if (!this.isCreatedState) return;
		glDeleteBuffers(vertexBufferObject);
		glDeleteBuffers(vertexBufferObjectIndices);
		glDeleteVertexArrays(vertexArrayObject);
		this.isDestroyState = true;
		this.isCreatedState = false;
	}
	
	
	public void draw() {
		
		if (this.isDestroy()) return;
		
		glBindVertexArray(vertexArrayObject);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		if (highlightColor==null) {
			Shader.SHADER.setColor(color.x,color.y,color.z,color.w);
		} else {
			Shader.SHADER.setColor(highlightColor.x,highlightColor.y,highlightColor.z,highlightColor.w);
		}

		
		glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
		
		//glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
	}
	
	public Transform getTransform() {
		return transform;
	}

	public void setPosition(Vector3f position) {
		transform.setPosition(position);
	}
	
	public Vector3f getPosition() {
		return transform.getPosition();
	}
	
	public boolean isDestroy() {
		return isDestroyState;
	}

	public void translate(Vector3f t) {
		transform.translate(t);
	}
	
	private void applyColor(Vector3f c) {
		for (int i = 0; i < vertices.length; i+=(Mesh.VERTEX_SIZE)) {
			vertices[i+3] = c.x;
			vertices[i+4] = c.y;
			vertices[i+5] = c.z;
		}
		destroy();
		create();
	}

	public void colorize(Color c) {
		color = new Vector4f(c.r,c.g,c.b,c.alpha);
		//applyColor(color);
	}
	
	public void setHighlight(Color c, boolean v) {
		if (v) {
			//applyColor(new Vector3f(c.r,c.g,c.b));
			highlightColor = new Vector4f(c.r,c.g,c.b,c.alpha);
		} else {
			//applyColor(color);
			highlightColor = null;
		}
	}
}
