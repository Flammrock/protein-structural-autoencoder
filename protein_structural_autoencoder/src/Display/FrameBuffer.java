package Display;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

	private int fbo;
	
	public FrameBuffer() {
		fbo = glGenFramebuffers();
	}
	
	public void destroy() {
		glDeleteFramebuffers(fbo);
	}
	
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	}
	
	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	
}
