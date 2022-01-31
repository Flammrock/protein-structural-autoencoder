package Display;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

public class OffScreen {
	
	private FrameBuffer fbo;
	private Texture tex;
	private RenderBuffer rbo;
	
	private int width;
	private int height;
	private boolean isCreate;

	public OffScreen() {
		width = 0;
		height = 0;
		isCreate = false;
	}
	
	public void resize(int width, int height) {
		if (isCreate && this.width == width && this.height == height) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	        glViewport(0, 0, width, height);
	        return;
		}
		if (isCreate) destroy();
		isCreate = true;
		fbo = new FrameBuffer();
        fbo.bind();
        tex = new Texture(width,height);
        tex.attach();
        rbo = new RenderBuffer(width,height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, width, height);
        fbo.unbind();
	}
	
	public void destroy() {
		if (!isCreate) return;
		isCreate = false;
		fbo.destroy();
        tex.destroy();
        rbo.destroy();
	}
	
	public void bind() {
		if (!isCreate) return;
		fbo.bind();
	}
	
	public void unbind() {
		if (!isCreate) return;
		fbo.unbind();
	}
	
	public Texture getTexture() {
		if (!isCreate) return null;
		return tex;
	}
	
	public boolean hasTexture() {
		return isCreate;
	}
	
}
