package display.internal;

import org.lwjgl.Version;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.function.Consumer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	
	private int width, height;
    private String title;
    private long window;
    private ImGuiLayer imguiLayer;
    
    private Consumer<Float> onloop;
    private Runnable onstart;
    private Runnable onclose;
    
    private long variableYieldTime, lastTime;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.onstart = null;
		this.onloop = null;
		this.onclose = null;
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, true);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	


	public String getTitle() {
		return title;
	}
	
	public void run() {
        init();
        loop();
        destroy();
    }
	
	private void destroy() {
		glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
	}
	
	public void imgGuiPrepare(float dt) {
		this.imguiLayer.prepare(dt);
	}
	
	public void imgGuiRender() {
		this.imguiLayer.render();
	}
	
	public boolean init() {
		this.setup();
		
		window = glfwCreateWindow(width, height, title, 0, 0);
		if (window==0) {
			throw new IllegalStateException("Failed to create window!");
		}
		
		Window that = this;
		glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(window, (w, newWidth, newHeight) -> {
            that.width = newWidth;
            that.height = newHeight;
        });
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		
		//GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		//glfwSetWindowPos(window, (videoMode.width() - 640)/2, (videoMode.height() - 480)/2);
		
        // Enable v-sync
        glfwSwapInterval(1);
		
		glfwShowWindow(window);
		
		this.imguiLayer = new ImGuiLayer(window);
        this.imguiLayer.initImGui();
        
        if (this.onstart!=null) this.onstart.run();
		
		return true;
	}
	
	public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;
        
        glClearColor(0.0f,0.0f,0.0f,1.0f);
		
        while (!glfwWindowShouldClose(window)) {
        	
            glfwPollEvents();
            
            if (this.onloop!=null) this.onloop.accept(dt);

            glfwSwapBuffers(window);
            
            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
            
            sync(30);
            
        }
        
        this.imguiLayer.destroyImGui();
        if (this.onclose!=null) this.onclose.run();
    }
	
	private static boolean IS_GLFW_INIT = false;

	private void setup() {
		if (Window.IS_GLFW_INIT) return;
		
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
		
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		Window.IS_GLFW_INIT = true;
	}
	
	private void sync(int fps) {
	    if (fps <= 0) return;
	     
	    long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
	    // yieldTime + remainder micro & nano seconds if smaller than sleepTime
	    long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
	    long overSleep = 0; // time the sync goes over by
	     
	    try {
	        while (true) {
	            long t = System.nanoTime() - lastTime;
	             
	            if (t < sleepTime - yieldTime) {
	                Thread.sleep(1);
	            }else if (t < sleepTime) {
	                // burn the last few CPU cycles to ensure accuracy
	                Thread.yield();
	            }else {
	                overSleep = t - sleepTime;
	                break; // exit while loop
	            }
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }finally{
	        lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
	       
	        // auto tune the time sync should yield
	        if (overSleep > variableYieldTime) {
	            // increase by 200 microseconds (1/5 a ms)
	            variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
	        }
	        else if (overSleep < variableYieldTime - 200*1000) {
	            // decrease by 2 microseconds
	            variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
	        }
	    }
	}
	
	public void setupDockSpace(Consumer<Integer> dockspaceBuilder) {
		this.imguiLayer.setupDockSpace(dockspaceBuilder);
	}
	
	public void registerStart(Runnable onstart) {
		this.onstart = onstart;
	}
	
	public void registerLoop(Consumer<Float> onloop) {
		this.onloop = onloop;
	}
	
	
	public void registerClose(Runnable onclose) {
		this.onclose = onclose;
	}

	
}
