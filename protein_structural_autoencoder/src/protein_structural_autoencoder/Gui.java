package protein_structural_autoencoder;

import java.util.EnumSet;

import org.joml.Vector3f;

import display.Button;
import display.Color;
import display.DockerSpace;
import display.Label;
import display.Navigation;
import display.Plot2D;
import display.UI;
import display.WindowPanel;
import display.eventtypes.ResizeEvent;
import display.internal.Container;
import display.internal.SceneManager;
import imgui.ImGui;
import imgui.ImVec2;

public class Gui extends UI {
	
	public Navigation navigation;
	public WindowPanel panelNavigation;
	public WindowPanel view;
	public WindowPanel console;
	public WindowPanel welcome;
	public WindowPanel sceneProtein;
	public WindowPanel sceneBackbone;
	
	Container protein; // will contain all atom of the protein
	Container proteinBackbone; // will contain all atom of the protein
	SceneManager sceneManager;
	
	
	DockerSpace dockerspace;
	
	public Gui() {
		create();
	}

	private void create() {
		
		dockerspace = DockerSpace.buildDefault();
		
		sceneManager = new SceneManager();
		
		protein = new Container();
		proteinBackbone = new Container();
		
		
		Navigation navigation = new Navigation.Builder().addItem("Database").addItem("Example").build();
		

		panelNavigation = new WindowPanel();
		panelNavigation.setTitle("Navigation");
		panelNavigation.setDockerNode(dockerspace.getNode("left"));
		panelNavigation.addChildren(navigation);
		add(panelNavigation);
		
		
		sceneProtein = new WindowPanel();
		sceneProtein.setTitle("Protein View Scene");
		sceneProtein.setDockerNode(dockerspace.getNode("center"));
		sceneProtein.setBindingOnDraw((display.event.Sender sender, display.event.Data data) -> {
			ImGui.beginChild("GameRender");
			ImVec2 childSize = ImGui.getWindowSize();
			sceneManager.get("protein").sendEvent(new ResizeEvent().setData(childSize));
			sceneManager.get("protein").draw();
			if (sceneManager.get("protein").hasTexture()) ImGui.image(sceneManager.get("protein").getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		});
		add(sceneProtein);
		
		
		sceneBackbone = new WindowPanel();
		sceneBackbone.setTitle("Backbone View Scene");
		sceneBackbone.setDockerNode(dockerspace.getNode("center"));
		sceneBackbone.setBindingOnDraw((display.event.Sender sender, display.event.Data data) -> {
			ImGui.beginChild("GameRender2");
			ImVec2 childSize = ImGui.getWindowSize();
			sceneManager.get("backbone").sendEvent(new ResizeEvent().setData(childSize));
			sceneManager.get("backbone").draw();
			if (sceneManager.get("backbone").hasTexture()) ImGui.image(sceneManager.get("backbone").getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		});
		add(sceneBackbone);
		
		view = new WindowPanel();
		view.setTitle("View");
		view.setDockerNode(dockerspace.getNode("right"));
		view.addChildren(new Label("nothing"));
		add(view);
		
		welcome = new WindowPanel();
		welcome.setTitle("Welcome");
		welcome.setDockerNode(dockerspace.getNode("up"));
		welcome.addChildren(new Label("Welcome to Protein Structural Autoencoder!"));
		welcome.addChildren(new Label("Coded by Lemmy Briot, Ahmed Chaudhry, Ameur Mahfoudi and Noa Weiss"));
		add(welcome);
		
		console = new WindowPanel();
		console.setTitle("Console");
		console.setDockerNode(dockerspace.getNode("down"));
		console.addChildren(new Label(">> v0.0.1 beta build 9845"));
		add(console);
	}
	
	public void update(Float deltaTime) {
		protein.rotate((float)Math.toRadians(20*deltaTime), new Vector3f(0,1,0));
		proteinBackbone.rotate((float)Math.toRadians(20*deltaTime), new Vector3f(0,1,0));
	}
	
	@Override
	public void destroy() {
		sceneManager.destroy();
	}
	
	@Override
	protected void buildDockSpace(int dockID) {
		dockerspace.setup(dockID);
	}
	
}
