package Display;


public abstract class Application {

	public Window window;
	
	public Application(int width, int height, String title) {
		window = new Window(width,height,title);
		window.registerStart(this::onInit);
		window.registerLoop(this::onUpdate);
		window.registerClose(this::onClose);
	}
	
	public Shader getShader() {
		return window.getShader();
	}
	
	public abstract void onInit();
	
	public abstract void onUpdate(Float deltaTime);
	
	public abstract void onClose();
}
