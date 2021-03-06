package display;

import imgui.ImGui;
import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;

public abstract class Component extends Identifier {
	
	protected display.event.Manager eventManager;
	protected String ID = getID();
	private DockerSpace.Node dockerNode = null;
	private String IDdock = getID();
	
	public Component() {
		eventManager = new display.event.Manager();
	}
	
	public String getInternalID() {
		return ID;
	}

	public void update() {
		eventManager.fire("draw");
	}
	
	public void setBindingOnDraw(display.event.Callback c) {
		eventManager.register("draw", c);
	}
	
	public void setDockerNode(DockerSpace.Node node) {
		dockerNode = node;
	}
	
	public DockerSpace.Node getDockerNode() {
		return dockerNode;
	}
	
	public void startDock() {
		if (dockerNode==null) return;
		ImGui.setNextWindowDockID(dockerNode.getID(), ImGuiCond.Once);
		if (!(this instanceof WindowPanel)) {
			ImGuiWindowClass window_class = new ImGuiWindowClass();
			window_class.setDockNodeFlagsOverrideSet(imgui.internal.flag.ImGuiDockNodeFlags.NoTabBar);
			ImGui.setNextWindowClass(window_class);
			ImGui.begin("##Container_"+IDdock);
			window_class.destroy();
		}
	}
	
	public void endDock() {
		if (dockerNode==null) return;
		if (!(this instanceof WindowPanel)) ImGui.end();
	}
	
	
	
	public ImVec2 getContentRegionMax() {
		ImVec2 max = new ImVec2();
		ImGui.getWindowContentRegionMax(max);
		return max;
	}
	
	public ImVec2 getContentRegionMin() {
		ImVec2 min = new ImVec2();
		ImGui.getWindowContentRegionMin(min);
		return min;
	}
	
	public ImVec2 getContentRegionSize() {
		ImVec2 max = getContentRegionMax();
		ImVec2 min = getContentRegionMin();
		return new ImVec2(max.x-min.x,max.y-min.y);
	}
	
}
