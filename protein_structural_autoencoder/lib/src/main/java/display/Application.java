package display;

import display.internal.Shader;
import display.internal.Window;
import imgui.ImGui;
import imgui.extension.implot.ImPlot;
import imgui.extension.implot.ImPlotContext;

public abstract class Application implements AutoCloseable {

	public Window window;
	public ImPlotContext imPlotContext = ImPlot.createContext();
	private UI ui;
	
	public Application(int width, int height, String title) {
		window = new Window(width,height,title);
		window.registerStart(this::onInit);
		window.registerLoop(this::onUpdate);
		window.registerClose(this::onClose);
	}
	
	public void bindUI(UI ui) {
		this.ui = ui;
	}
	
	public void onInit() {
		ImPlot.setCurrentContext(imPlotContext);
		Shader.SHADER.create("basic");
	}
	
	public abstract void onUpdate(Float deltaTime);
	
	public void onClose() {
		close();
	}
	
	protected void startDockSpace() {
		if (ui!=null) window.setupDockSpace(ui::buildDockSpace);
	}
	
	protected void endDockSpace() {
		if (ui!=null) ImGui.end();
	}
	
	@Override
	public void close() {
		Shader.SHADER.destroy();
		if (ui!=null) ui.destroy();
	}
	
}
