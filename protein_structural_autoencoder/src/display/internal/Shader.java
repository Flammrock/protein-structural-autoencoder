package display.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
	
	public static Shader SHADER = new Shader();

	private int vertexShader, fragmentShader, program;
	
	private int uniMatProjection, uniMatTransformWorld, uniMatTransformObject;
	private int lightPos, lightColor;
	
	private boolean isCreate;
	
	public Shader() {
		isCreate = false;
	}
	
	public boolean create(String shader) {
		
		if (isCreate) destroy();
		
		int success;
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readSource(shader + ".vs"));
		glCompileShader(vertexShader);
		
		
		success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(vertexShader));
		}
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, readSource(shader + ".fs"));
		glCompileShader(fragmentShader);
		
		
		success = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(fragmentShader));
		}
		
		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		glLinkProgram(program);
		success = glGetProgrami(program, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(program));
		}
		glValidateProgram(program);
		success = glGetProgrami(program, GL_VALIDATE_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(program));
		}
		
		uniMatProjection = glGetUniformLocation(program, "cameraProjection");
		uniMatTransformWorld = glGetUniformLocation(program, "transformWorld");
		uniMatTransformObject = glGetUniformLocation(program, "transformObject");
		lightPos = glGetUniformLocation(program, "lightPos");
		lightColor = glGetUniformLocation(program, "lightColor");
		
		isCreate = true;
		
		return true;
	}
	
	public void destroy() {
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(program);
		isCreate = false;
	}
	
	public void useShader() {
		if (!isCreate) throw new IllegalStateException("Shader::create() never called! A shader must be created before using it.");
		glUseProgram(program);
	}
	
	public void setCamera(Camera camera) {
		if (uniMatProjection != -1) {
			float matrix[] = new float[16];
			camera.getProjection().get(matrix);
			glUniformMatrix4fv(uniMatProjection, false, matrix);
		}
		if (uniMatTransformWorld != -1) {
			float matrix[] = new float[16];
			camera.getTransformation().get(matrix);
			glUniformMatrix4fv(uniMatTransformWorld, false, matrix);
		}
	}
	
	public void setTransform(Transform transform) {
		if (uniMatTransformObject != -1) {
			float matrix[] = new float[16];
			transform.getTransformation().get(matrix);
			glUniformMatrix4fv(uniMatTransformObject, false, matrix);
		}
	}
	
	public void setLight(Vector3f pos, Vector3f col) {
		float vpos[] = new float[]{pos.x,pos.y,pos.z};
		float vcol[] = new float[]{col.x,col.y,col.z};
		glUniform3fv(lightPos, vpos);
		glUniform3fv(lightColor, vcol);
	}
	
	private String readSource(String file) {
		BufferedReader reader = null;
		StringBuilder sourceBuilder = new StringBuilder();
		
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("shaders/"+file)));
			String line;
			while ((line = reader.readLine()) != null) {
				sourceBuilder.append(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sourceBuilder.toString();
	}
	
}
