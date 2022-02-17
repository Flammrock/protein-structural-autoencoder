package display;

import java.util.LinkedHashMap;
import java.util.Map;

import imgui.ImGui;
import imgui.flag.ImGuiDir;
import imgui.type.ImInt;

public class DockerSpace extends Component {
	
	private Map<String,Node> dockIds = new LinkedHashMap<>();
	private Node rootNode;
	private int dockSpaceID;
	private boolean isSetup = false;
	
	private DockerSpace(Node rootNode, Map<String,Node> dockIds, Map<String,Node> dockIdsOpposite) {
		this.dockIds = dockIds;
		this.rootNode = rootNode;
	}
	
	public boolean hasNode(String name) {
		return dockIds.containsKey(name);
	}
	
	public Node getNode(String name) {
		if (!hasNode(name)) throw new IllegalStateException("Error: the node \""+name+"\" doesn't exist.");
		return dockIds.get(name);
	}
	
	public enum Direction {
		Left(ImGuiDir.Left),
		Right(ImGuiDir.Right),
		Up(ImGuiDir.Up),
		Down(ImGuiDir.Down);
		
		int dir;
		
		private Direction(int dir) {
			this.dir = dir;
		}
		
		public Direction opposite() {
			switch (dir) {
				case ImGuiDir.Left:
					return Direction.Right;
				case ImGuiDir.Right:
					return Direction.Left;
				case ImGuiDir.Up:
					return Direction.Down;
				case ImGuiDir.Down:
					return Direction.Up;
			}
			throw new IllegalArgumentException("Error: internal error : bad docker space direction.");
		}
	}
	
	public static class Node {
		
		private String name;
		private Node opposite;
		private float ratio;
		private Direction dir;
		private ImInt id = new ImInt();
		private Node parent = null;
		private int index;
		
		private Node(String name, Node opposite, float ratio, Direction dir) {
			this.name = name;
			this.opposite = opposite;
			this.ratio = ratio;
			this.dir = dir;
		}
		
		public Node getOpposite() {
			return opposite;
		}
		
		public int getID() {
			return id.get();
		}
		
		public float getRatio() {
			return ratio;
		}
		
		public Direction getDirection() {
			return dir;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	public static class Builder {
		
		private Map<String,Node> dockIds = new LinkedHashMap<>();
		private Map<String,Node> dockIdsOpposite = new LinkedHashMap<>();
		private Node rootNode;
		private int index = 0;
		
		public Builder addRootNode(String name, Direction dir, float ratio) {
			
			if (!dockIds.isEmpty()) {
				throw new IllegalStateException("Error: a root node has already been added. You must use .addNode(String nodeName, String name, Direction dir, float ratio, boolean opposite) to add other node.");
			}
			if (dockIds.containsKey(name)) {
				throw new IllegalStateException("Error: a node with same name already exist.");
			}
			
			Node dataid = new Node(name,null,ratio,dir);
			Node dataidopposite = new Node(name,dataid,1.0f-ratio,dir.opposite());
			dataid.opposite = dataidopposite;
			dataid.index = index;
			dataidopposite.index = index;
			index++;
			
			rootNode = dataid;
			
			//imgui.internal.ImGui.dockBuilderSplitNode(ID, dir.dir, ratio, dataid.id, dataidopposite.id);
			
			dockIds.put(name, dataid);
			dockIdsOpposite.put(name, dataidopposite);
			
			return this;
		}
		
		public Builder addNode(String nodeName, String name, Direction dir, float ratio, boolean opposite) {
			if (dockIds.isEmpty()) {
				throw new IllegalStateException("Error: You must add a root node first by doing : .addRootNode(String name, DockerSpace.Direction, float ratio).");
			}
			if (dockIds.containsKey(name)) {
				throw new IllegalStateException("Error: a node with same name already exist.");
			}
			if (!dockIds.containsKey(nodeName)) {
				throw new IllegalStateException("Error: cannot found the node \""+nodeName+"\".");
			}
			
			Node dataid = new Node(name,null,ratio,dir);
			Node dataidopposite = new Node(name,dataid,1.0f-ratio,dir.opposite());
			dataid.opposite = dataidopposite;
			
			Node parent = opposite ? dockIdsOpposite.get(nodeName) : dockIds.get(nodeName);
			dataid.parent = parent;
			dataidopposite.parent = parent;
			
			dataid.opposite = dataidopposite;
			dataid.index = index;
			dataidopposite.index = index;
			index++;
			
			//ImInt root = parent.id;
			//imgui.internal.ImGui.dockBuilderSplitNode(root.get(), dir.dir, ratio, dataid.id, dataidopposite.id);
			dockIds.put(name, dataid);
			dockIdsOpposite.put(name, dataidopposite);
			return this;
		}
		
		public DockerSpace build() {
			return new DockerSpace(rootNode,dockIds,dockIdsOpposite);
		}
		
	}
	
	public void setup() {
		if (isSetup) return;
		isSetup = true;
		dockSpaceID = ImGui.getID("Dockspace_"+getID());
		imgui.internal.ImGui.dockBuilderAddNode(dockSpaceID);
		imgui.internal.ImGui.dockBuilderSplitNode(dockSpaceID, rootNode.dir.dir, rootNode.ratio, rootNode.id, rootNode.opposite.id);
		for (Map.Entry<String,Node> entry : dockIds.entrySet()) {
			if (entry.getValue().index==rootNode.index) continue;
			Node current = entry.getValue();
			ImInt root = current.parent.id;
			imgui.internal.ImGui.dockBuilderSplitNode(root.get(), current.dir.dir, current.ratio, current.id, current.opposite.id);
		}
		imgui.internal.ImGui.dockBuilderFinish(dockSpaceID);
	}
	
	@Override
	public void update() {
		setup();
		ImGui.dockSpace(dockSpaceID);
	}
	
}
