package protein_structural_autoencoder;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

	private int vertexArrayObject;
	private int vertexBufferObject;
	
	private int vertexCount;
	
	public Mesh() {
	}
	
	// TODO: list of triangles, compute normal for each face then each vertex
	
	public boolean create(float vertices[]) {
		vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(vertexArrayObject);
		
		vertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
		
		glBindVertexArray(0);
		
		vertexCount = vertices.length / 6;
		
		return true;
	}
	
	public void destroy() {
		glDeleteBuffers(vertexBufferObject);
		glDeleteVertexArrays(vertexArrayObject);
	}
	
	
	public void draw() {
		glBindVertexArray(vertexArrayObject);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);
		
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
	}
}
