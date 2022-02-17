package display.internal;

import imgui.ImGui;

public abstract class Application {

	public Window window;
	
	public Application(int width, int height, String title) {
		window = new Window(width,height,title);
		window.registerStart(this::onInit);
		window.registerLoop(this::onUpdate);
		window.registerClose(this::onClose);
	}
	
	public abstract void onInit();
	
	public abstract void onUpdate(Float deltaTime);
	
	public abstract void onClose();
	
	protected abstract void buildDockSpace(int dockID);
	
	protected void startDockSpace() {
		window.setupDockSpace(this::buildDockSpace);
	}
	
	protected void endDockSpace() {
		ImGui.end();
	}
}
