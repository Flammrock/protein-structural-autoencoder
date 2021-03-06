package display;


import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class WindowPanel extends Box {
	
	
	public enum Flag {
		NoCloseable,
		NoResizeable,
		NoTitle,
		NoMoveable,
		NoDockable,
		MenuBar,
		NoNav,
		UnsavedDocument,
		NoCollapse,
		NoMouseInputs,
		NoInputs,
		Centered,
		VerticalCentered,
		HorizontalCentered,
		PositionStateSaved,
		SizeStateSaved
		;
		
		public static final EnumSet<Flag> ALL = EnumSet.allOf(Flag.class);
		public static final EnumSet<Flag> NONE = EnumSet.noneOf(Flag.class);
		
		public static int getImGuiWindowFlags(EnumSet<Flag> flags) {
			int bitflags = 0;
			if (flags.contains(NoResizeable)) bitflags |= ImGuiWindowFlags.NoResize;
			if (flags.contains(NoMoveable)) bitflags |= ImGuiWindowFlags.NoMove;
			if (flags.contains(NoTitle)) bitflags |= ImGuiWindowFlags.NoTitleBar;
			if (flags.contains(NoDockable)) bitflags |= ImGuiWindowFlags.NoDocking;
			if (flags.contains(MenuBar)) bitflags |= ImGuiWindowFlags.MenuBar;
			if (flags.contains(NoNav)) bitflags |= ImGuiWindowFlags.NoNav;
			if (flags.contains(UnsavedDocument)) bitflags |= ImGuiWindowFlags.UnsavedDocument;
			if (flags.contains(NoCollapse)) bitflags |= ImGuiWindowFlags.NoCollapse;
			if (flags.contains(NoMouseInputs)) bitflags |= ImGuiWindowFlags.NoMouseInputs;
			if (flags.contains(NoInputs)) bitflags |= ImGuiWindowFlags.NoInputs;
			return bitflags;
		}
	}
	
	
	
	private String title;
	
	
	
	private ImBoolean isOpenContainer = new ImBoolean(true);
	private ImVec2 position = new ImVec2();
	private ImVec2 originPosition = new ImVec2();
	private ImVec2 regionMin = new ImVec2();
	private ImVec2 regionMax = new ImVec2();
	private ImVec2 size = new ImVec2(500.0f,500.0f);
	private ImVec2 originSize = new ImVec2(size.x,size.y);
	private EnumSet<Flag> flags = Flag.NONE;
	private DockerSpace dockerspace = null;
	private int internalFlags = 0;
	private boolean initialized = false;
	private Float scrollY = null;
	
	
	public boolean isOpen() {
		return isOpenContainer.get();
	}
	

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setFlags(EnumSet<Flag> flags) {
		this.flags = EnumSet.copyOf(flags);
		this.internalFlags = Flag.getImGuiWindowFlags(this.flags);
	}
	
	
	
	
	public void setBindingOnClose(display.event.Callback c) {
		eventManager.register("close", c);
	}
	
	public void setBindingOnOpen(display.event.Callback c) {
		eventManager.register("open", c);
	}
	
	public void setBindingOnFocus(display.event.Callback c) {
		eventManager.register("focus", c);
	}
	
	private void computePosition() {
		ImVec2 regionMinCurrent = ImGui.getWindowContentRegionMin();
		ImVec2 regionMaxCurrent = ImGui.getWindowContentRegionMax();
		boolean autoX = false;
		boolean autoY = false;
		if (flags.contains(Flag.Centered) || flags.contains(Flag.VerticalCentered)) {
			autoY = true;
		}
		if (flags.contains(Flag.Centered) || flags.contains(Flag.HorizontalCentered)) {
			autoX = true;
		}
		float x = autoX ? (originPosition.x + regionMinCurrent.x + ((regionMaxCurrent.x-regionMinCurrent.x)-size.x)/2.0f) : position.x;
		float y = autoY ? (originPosition.y + regionMinCurrent.y + ((regionMaxCurrent.y-regionMinCurrent.y)-size.y)/2.0f) : position.y;
		if (!initialized) ImGui.setNextWindowPos(x, y, flags.contains(Flag.PositionStateSaved)?ImGuiCond.Once:ImGuiCond.Always);
		if (initialized && (!regionMinCurrent.equals(regionMin) || !regionMaxCurrent.equals(regionMax)) && (autoX || autoY)) {
			float px = (position.x+size.x/2.0f)/(regionMax.x-regionMin.x);
			float py = (position.y+size.y/2.0f)/(regionMax.y-regionMin.y);
			regionMin = new ImVec2(regionMinCurrent.x,regionMinCurrent.y);
			regionMax = new ImVec2(regionMaxCurrent.x,regionMaxCurrent.y);
			ImGui.setNextWindowPos(autoX?px*(regionMax.x-regionMin.x)-size.x/2.0f:x,autoY?py*(regionMax.y-regionMin.y)-size.y/2.0f:y);
		}
		if (!initialized) {
			regionMin = new ImVec2(regionMinCurrent.x,regionMinCurrent.y);
			regionMax = new ImVec2(regionMaxCurrent.x,regionMaxCurrent.y);
		}
	}
	
	@Override
	public void update() {
		if (!isOpen()) return;
		if (getDockerNode()==null) {
			computePosition();
			if (!initialized) ImGui.setNextWindowSize(originSize.x, originSize.y, flags.contains(Flag.SizeStateSaved)?ImGuiCond.Once:ImGuiCond.Always);
		}
		boolean opened = false;
		startDock();
		if (flags.contains(Flag.NoCloseable)) {
			opened = ImGui.begin(title+"##"+ID, internalFlags);
		} else {
			opened = ImGui.begin(title+"##"+ID, isOpenContainer, internalFlags);
		}
		if (opened) {
			position = ImGui.getWindowPos();
			size = ImGui.getWindowSize();
			if (ImGui.isWindowFocused()) {
				eventManager.fire("focus");
			}
			if (dockerspace!=null) dockerspace.init();
			drawChildren();
			eventManager.fire("draw");
			if (scrollY!=null) {
				ImGui.setScrollHereY(scrollY);
				scrollY = null;
			}
		}
		ImGui.end();
		endDock();
		initialized = true;
	}
	
	public void open() {
		if (!flags.contains(Flag.PositionStateSaved)) position = new ImVec2(originPosition.x,originPosition.y);
		if (!flags.contains(Flag.SizeStateSaved)) size = new ImVec2(originSize.x,originSize.y);
		eventManager.fire("open");
		isOpenContainer.set(true);
	}
	
	public void close() {
		eventManager.fire("close");
		initialized = false;
		isOpenContainer.set(false);
	}
	
	
	public void setDockerSpace(DockerSpace dockerspace) {
		this.dockerspace = dockerspace;
	}
	
	public DockerSpace getDockerSpace() {
		return dockerspace;
	}
	
	
	@Deprecated
	public static void draw(String title, ImVec2 size, Runnable ondraw) {
		ImGui.setNextWindowSize(size.x, size.y, ImGuiCond.Once);
		ImGui.begin(title);
		ondraw.run();
		ImGui.end();
	}


	public void scrollYDown() {
		scrollY = 1.0f;
	}
	
	public void scrollYTop() {
		scrollY = 0.0f;
	}
	
}
