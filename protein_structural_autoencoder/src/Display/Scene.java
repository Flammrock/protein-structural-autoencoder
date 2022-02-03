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

	private List<Mesh> meshs;
	private Shader shader;
	private CameraManager cameraManager;
	private boolean useOffscreen;
	private OffScreen offscreen;
	
	public Scene() {
		this.meshs = new ArrayList<>();
		this.shader = null;
		this.cameraManager = new CameraManager();
		this.cameraManager.createCamera("default");
		this.cameraManager.setMainCamera("default");
		this.useOffscreen = true;
		this.offscreen = new OffScreen();
	}
	
	public boolean hasTexture() {
		return offscreen.hasTexture();
	}
	
	public Texture getTexture() {
		return offscreen.getTexture();
	}
	
	public void resize(Number width, Number height) {
		getCamera().resize(width.intValue(), height.intValue());
		offscreen.resize(width.intValue(), height.intValue());
	}
	
	public CameraManager getCameraManager() {
		return cameraManager;
	}
	
	public Camera getCamera() {
		return cameraManager.getMainCamera();
	}
	
	public void add(Mesh mesh) {
		this.meshs.add(mesh);
	}
	
	public void add(Container container) {
		this.meshs.addAll(container.getMeshs());
	}

	public List<Mesh> getMeshs() {
		return meshs;
	}
	
	public void destroy() {
		for (Mesh mesh : meshs) {
			mesh.destroy();
		}
		this.meshs = new ArrayList<>();
		this.offscreen.destroy();
	}
	
	public void useShader(Shader shader) {
		this.shader = shader;
	}
	
	public void draw() {
		
		Shader usedShader = shader==null ? Shader.SHADER : shader;
		
		Camera camera = cameraManager.getMainCamera();
		
		if (useOffscreen) this.offscreen.bind();
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        glViewport(0, 0, camera.getWidth(), camera.getHeight());
        
        usedShader.useShader();
        usedShader.setCamera(camera);
        usedShader.setLight(new Vector3f(-50,0,50),new Vector3f(1,1,1));
        
        Iterator<Mesh> it = getMeshs().iterator();
		while (it.hasNext()) {
		    Mesh mesh = it.next();
		    mesh.create();
		    usedShader.setTransform(mesh.getTransform());
		    mesh.draw();
			if (mesh.isDestroy()) {
				it.remove();
			}
		}
		
		if (useOffscreen) this.offscreen.unbind();
	}
	
}