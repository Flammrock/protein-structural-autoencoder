package Display;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joml.Vector3f;

public class Scene {

	List<Mesh> meshs;
	Shader shader;
	
	public Scene() {
		this.meshs = new ArrayList<>();
		this.shader = null;
	}
	
	public void add(Mesh mesh) {
		this.meshs.add(mesh);
	}

	public List<Mesh> getMeshs() {
		return meshs;
	}
	
	public void destroy() {
		for (Mesh mesh : meshs) {
			mesh.destroy();
		}
		this.meshs = new ArrayList<>();
	}
	
	public void useShader(Shader shader) {
		this.shader = shader;
	}
	
	public void draw(Camera camera) {
		
		if (this.shader==null) {
			throw new IllegalStateException("Scene::useShder() is never called.");
		}
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        glViewport(0, 0, camera.getWidth(), camera.getHeight());
        
        shader.useShader();
        shader.setCamera(camera);
        shader.setLight(new Vector3f(-50,0,50),new Vector3f(1,1,1));
        
        Iterator<Mesh> it = getMeshs().iterator();
		while (it.hasNext()) {
		    Mesh mesh = it.next();
		    mesh.create();
		    shader.setTransform(mesh.getTransform());
		    mesh.draw();
			if (mesh.isDestroy()) {
				it.remove();
			}
		}
	}
	
}
