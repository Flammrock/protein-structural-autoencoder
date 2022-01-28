package Display;

import org.lwjgl.Version;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.Iterator;
import java.util.function.Consumer;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	
	private int width, height;
    private String title;
    private long window;
    private ImGuiLayer imguiLayer;
    private Shader shader;
    private Camera camera;
    private Scene scene;
    private Consumer<Double> callback;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.camera = new Camera();
		this.scene = new Scene();
		this.callback = null;
	}
	
	public void setUpdateCallback(Consumer<Double> callback) {
		this.callback = callback;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	

	public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();


        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        shader.destroy();
        glfwTerminate();
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
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - 640)/2, (videoMode.height() - 480)/2);
		
        // Enable v-sync
        glfwSwapInterval(1);
		
		glfwShowWindow(window);
		
		shader = new Shader();
		shader.create("basic");
		
		this.imguiLayer = new ImGuiLayer(window);
        this.imguiLayer.initImGui();
		
		/*Mesh testMesh = new Mesh();
		testMesh.create(new float[] {
				-1,-1,0,
				0,1,0,
				1,-1,0
		});
		
		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			shader.useShader();
			testMesh.draw();
			
			glfwSwapBuffers(window);
		}
		
		testMesh.destroy();
		shader.destroy();
		
		glfwTerminate();*/
		
		return true;
	}
	
	public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;
        
        glClearColor(0.0f,0.0f,0.0f,1.0f);
		
		
		camera.setPerspective((float)Math.toRadians(10), 640.0f / 480.0f, 0.01f, 1000.0f);
		camera.setPosition(new Vector3f(0, 0, 20));
		//camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(-1), new Vector3f(1,0,0))));
		
		
        while (!glfwWindowShouldClose(window)) {
        	
            glfwPollEvents();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            glViewport(0, 0, width, height);
            camera.setPerspective((float)Math.toRadians(60), (float)width / (float)height, 0.01f, 1000.0f);
            
            shader.useShader();
            shader.setCamera(camera);
            shader.setLight(new Vector3f(-4,0,4),new Vector3f(1,1,1));
            
            Iterator<Mesh> it = scene.getMeshs().iterator();
    		while (it.hasNext()) {
    		    Mesh mesh = it.next();
    		    mesh.create();
    		    shader.setTransform(mesh.getTransform());
    		    mesh.draw();
    			if (mesh.isDestroy()) {
    				it.remove();
    			}
    		}
            if (dt >= 0) {
            	if (this.callback!=null) this.callback.accept((double)dt);
            }

            this.imguiLayer.update(dt);
            glfwSwapBuffers(window);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
            
            try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
        this.imguiLayer.destroyImGui();
        scene.destroy();
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
		
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		Window.IS_GLFW_INIT = true;
	}
	
}
