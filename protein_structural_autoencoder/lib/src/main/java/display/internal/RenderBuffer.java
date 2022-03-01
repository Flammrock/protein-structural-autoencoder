package display.internal;

import static org.lwjgl.opengl.GL30.*;

public class RenderBuffer {

	private int rbo;
	
	public RenderBuffer(int width, int height) {
		rbo = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbo); 
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);  
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);
	}
	
	public void destroy() {
		glDeleteRenderbuffers(rbo);
	}
	
}
