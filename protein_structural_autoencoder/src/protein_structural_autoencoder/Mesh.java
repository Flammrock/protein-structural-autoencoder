package protein_structural_autoencoder;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector3f;

public class Mesh {
	
	private static int VERTEX_SIZE = 9;

	private int vertexArrayObject;
	private int vertexBufferObject;
	private int vertexBufferObjectIndices;
	
	private int vertexCount;
	private int indexCount;
	
	
	public Mesh() {
	}
	
	// TODO: list of triangles, compute normal for each face then each vertex
	
	public boolean create(float raw_vertices[], int indices[]) {
		
		float[] vertices = computeNormal(raw_vertices, indices);
		
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
		
		vertexCount = vertices.length / Mesh.VERTEX_SIZE;
		indexCount = indices.length;
		
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
		glDeleteBuffers(vertexBufferObject);
		glDeleteBuffers(vertexBufferObjectIndices);
		glDeleteVertexArrays(vertexArrayObject);
	}
	
	
	public void draw() {
		glBindVertexArray(vertexArrayObject);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
	}
}
