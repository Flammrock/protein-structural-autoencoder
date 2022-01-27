package protein_structural_autoencoder;

import org.lwjgl.Version;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	
	private static boolean IS_GLFW_INIT = false;
	
	private int width, height;
    private String title;
    private long window;
    private ImGuiLayer imguiLayer;
    private Shader shader;
    private Camera camera;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.camera = new Camera();
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
        
        Sphere testsphere = new Sphere(new Vector3f(1,0,0));
        Sphere testsphere2 = new Sphere(new Vector3f(0,0,1));
        
        Mesh testMesh = new Mesh();
		testMesh.create(new float[] {
				
				-0.5f, 0.5f, 0.0f, 1,0,0,
				-0.5f, -0.5f, 0.0f,  0,1,0,
				0.5f, -0.5f, 0.0f,   0,0,1,
				0.5f, 0.5f, 0.0f,    1,1,0
		},new int[]{
				0, 1, 3,
                3, 1, 2
	    });
		
		
		Transform transform = new Transform();
		Transform transform2 = new Transform();

		camera.setPerspective((float)Math.toRadians(10), 640.0f / 480.0f, 0.01f, 1000.0f);
		camera.setPosition(new Vector3f(0, 5, 20));
		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(-30), new Vector3f(1,0,0))));
		
		float x = 0.0f;
		
		
		
        while (!glfwWindowShouldClose(window)) {
        	
            glfwPollEvents();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            glViewport(0, 0, width, height);
            camera.setPerspective((float)Math.toRadians(60), (float)width / (float)height, 0.01f, 1000.0f);
            
            x++;
            transform.setPosition(new Vector3f((float)Math.sin(Math.toRadians(x)),0,0));
            transform.getRotation().rotateAxis((float)Math.toRadians(1), 0, 1, 0);
            transform2.setPosition(new Vector3f(5,(float)Math.sin(Math.toRadians(x)),5));
            transform2.getRotation().rotateAxis((float)Math.toRadians(1), 0, 1, 0);
            
            shader.useShader();
            shader.setCamera(camera);
            shader.setTransform(transform);
            shader.setLight(new Vector3f(4,0,0),new Vector3f(1,1,1));
            testsphere.getMesh().draw();
            
            shader.setTransform(transform2);
            testsphere2.getMesh().draw();

            if (dt >= 0) {
                //currentScene.update(dt);
            }

            this.imguiLayer.update(dt);
            glfwSwapBuffers(window);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        
        testMesh.destroy();
    }

	public void setup() {
		
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
