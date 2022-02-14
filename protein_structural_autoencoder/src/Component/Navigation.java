package Component;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import Component.WindowPanel.Flag;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTreeNodeFlags;

public class Navigation extends AbstractComponent {

	private List<AbstractComponent> items;
	
	private Navigation(List<AbstractComponent> items) {
		this.items = items;
	}
	
	public static class Item extends AbstractComponent {
		
		private String name;
		private String ID = getID();
		private EnumSet<Flag> flags = Flag.NONE;
		private int internalFlags = 0;
		
		private Item(String name) {
			this.name = name;
		}
		
		private Item(String name, EnumSet<Flag> flags) {
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
			ImGui.setNextItemOpen(true, ImGuiCond.Once);
			if (ImGui.treeNodeEx(name+"##"+ID, internalFlags)) {
				ImGui.button("Hello world");
				ImGui.treePop();
		    }
		}
		
	}
	
	public static class Builder {
		
		private List<AbstractComponent> items = new ArrayList<>();
		
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
		
	}
	
	@Override
	public void update() {
		for (AbstractComponent i : items) {
			i.update();
		}
	}
}
