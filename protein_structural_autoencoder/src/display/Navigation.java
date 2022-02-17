package display;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import display.WindowPanel.Flag;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTreeNodeFlags;

public class Navigation extends Component {

	private List<Component> items;
	
	private Navigation(List<Component> items) {
		this.items = items;
	}
	
	public static class Item extends Component {
		
		private String name;
		private String ID = getID();
		private EnumSet<Flag> flags = Flag.NONE;
		private int internalFlags = 0;
		
		public Item(String name) {
			this.name = name;
		}
		
		public Item(String name, EnumSet<Flag> flags) {
			this(name);
			this.flags = EnumSet.copyOf(flags);
			this.internalFlags = Flag.getImGuiTreeNodeFlags(flags);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public enum Flag {
			
			Framed;
			
			public static final EnumSet<Flag> ALL = EnumSet.allOf(Flag.class);
			public static final EnumSet<Flag> NONE = EnumSet.noneOf(Flag.class);
			
			public static int getImGuiTreeNodeFlags(EnumSet<Flag> flags) {
				int bitflags = 0;
				if (flags.contains(Framed)) bitflags |= ImGuiTreeNodeFlags.Framed;
				return bitflags;
			}
			
		}
		
		@Override
		public void update() {
			super.update();
			ImGui.setNextItemOpen(true, ImGuiCond.Once);
			if (ImGui.treeNodeEx(name+"##"+ID, internalFlags)) {
				eventManager.fire("drawcontent");
				ImGui.treePop();
		    }
		}
		
		public void setBindingOnContentDraw(display.event.Callback c) {
			eventManager.register("drawcontent", c);
		}
		
	}
	
	public static class Builder {
		
		private List<Component> items = new ArrayList<>();
		
		public Navigation build() {
			return new Navigation(items);
		}
		
		public Builder addItem(String name) {
			items.add(new Item(name));
			return this;
		}
		
		public Builder addItem(String name, EnumSet<Item.Flag> flags) {
			items.add(new Item(name,flags));
			return this;
		}
		
		public Builder addItem(Item item) {
			items.add(item);
			return this;
		}
		
	}
	
	@Override
	public void update() {
		startDock();
		for (Component i : items) {
			i.update();
		}
		endDock();
	}
}
