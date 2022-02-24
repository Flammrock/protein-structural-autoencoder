package protein_structural_autoencoder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.joml.Vector3f;

import bio.Protein;
import bio.Residue;
import display.Button;
import display.Console;
import display.DockerSpace;
import display.Label;
import display.Navigation;
import display.Navigation.Item.Flag;
import display.Plot2D;
import display.UI;
import display.WindowPanel;
import display.eventtypes.ResizeEvent;
import display.internal.Container;
import display.internal.Scene;
import display.internal.SceneManager;
import imgui.ImGui;
import imgui.ImVec2;

public class Gui extends UI {
	
	public Navigation navigation;
	public WindowPanel panelNavigation;
	public WindowPanel view;
	public Console console;
	public WindowPanel welcome;
	
	SceneManager sceneManager;
	
	
	// contient toutes les instances de proteines
	List<WindowPanel> proteinPanel;
	
	
	DockerSpace dockerspace;
	
	public Gui() {
		create();
	}

	private void create() {
		
		proteinPanel = new ArrayList<>();
		
		dockerspace = DockerSpace.buildDefault();
		
		sceneManager = new SceneManager();
		
		
		Navigation navigation = new Navigation.Builder().addItem("Database").addItem("Example").build();
		

		panelNavigation = new WindowPanel();
		panelNavigation.setTitle("Navigation");
		panelNavigation.setDockerNode(dockerspace.getNode("left"));
		panelNavigation.addChildren(navigation);
		add(panelNavigation);
		
		
		/*sceneProtein = new WindowPanel();
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
		add(sceneBackbone);*/
		
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
		
		console = new Console(1000);
		console.setTitle("Console");
		console.setDockerNode(dockerspace.getNode("down"));
		console.log(">> v0.0.1 beta build 9845");
		add(console);
		
	}
	
	public void update(Float deltaTime) {
		//protein.rotate((float)Math.toRadians(20*deltaTime), new Vector3f(0,1,0));
		//proteinBackbone.rotate((float)Math.toRadians(20*deltaTime), new Vector3f(0,1,0));
	}
	
	@Override
	public void destroy() {
		sceneManager.destroy();
	}
	
	@Override
	protected void buildDockSpace(int dockID) {
		dockerspace.setup(dockID);
	}

	public void log(String data) {
		console.log(data);
	}
	
	public void log(Console.Level level, String data) {
		console.log(level,data);
	}
	
	private void setView(Protein p) {
		
		Container m = p.getMesh();
		Container bm = p.getBackboneMesh();
		
		Navigation.Item itemInfo = new Navigation.Item("Information");
		
		itemInfo.addChildren(new Label("Original File. . . . . . . . . . .: "+p.getOriginalFilename()));
		itemInfo.addChildren(new Label("Amount of Atoms. . . . . . . . . .: "+p.size()));
		itemInfo.addChildren(new Label("Amount of Residues. . . . . . . . : "+p.getResidues().size()));
		itemInfo.addChildren(new Label("Amount of Carbon Alpha. . . . . . : "+p.getResidues().size()));
		
		Navigation.Item itemResi = new Navigation.Item("Residues");
		Navigation n = new Navigation.Builder().addItem(itemInfo).addItem(itemResi).build();
		view.clearChildren();
		List<Residue> rs = p.getResidues();
		for (Residue r : rs) {
			Navigation.Item b = new Navigation.Item("Residue n°"+r.getID(),EnumSet.of(Navigation.Item.Flag.Closed));
			//b.setFullWidth(true);
			b.setBindingOnMouseIn((display.event.Sender sender, display.event.Data data) -> {
				Protein.setHighlightResidue(p,r,m,bm,true);
			});
			b.setBindingOnMouseOut((display.event.Sender sender, display.event.Data data) -> {
				Protein.setHighlightResidue(p,r,m,bm,false);
			});
			itemResi.addChildren(b);
		}
		view.addChildren(n);
	}

	public void loadProtein(String filename) {
		
		Protein p = Protein.buildFromFile(filename);
		
		Container proteinMesh = p.getMesh();
		Container proteinBackboneMesh = p.getBackboneMesh();
		
		WindowPanel panel = new WindowPanel();
		panel.setTitle("Protein `"+filename+"`");
		panel.setDockerNode(dockerspace.getNode("center"));
		panel.setDockerSpace(new DockerSpace.Builder().build());
		
		panel.setBindingOnFocus((display.event.Sender sender, display.event.Data data) -> {
			setView(p);
		});
		
		
		
		Scene sp = sceneManager.create("protein"+filename);
		Scene sb = sceneManager.create("backbone"+filename);
		
		
		
		proteinMesh.translate(proteinMesh.getCenter().mul(-1.0f));
		proteinBackboneMesh.translate(proteinBackboneMesh.getCenter().mul(-1.0f));
		
		sp.add(proteinMesh);
		sb.add(proteinBackboneMesh);
		

		sp.getCamera().fitView(proteinMesh);
		sb.getCamera().fitView(proteinBackboneMesh);
		
		
		WindowPanel sceneProtein = new WindowPanel();
		sceneProtein.setTitle("Protein View Scene##"+filename);
		sceneProtein.setDockerNode(panel.getDockerSpace().getBaseNode());
		sceneProtein.setBindingOnDraw((display.event.Sender sender, display.event.Data data) -> {
			ImGui.beginChild("GameRender##"+filename);
			proteinMesh.rotate((float)Math.toRadians(20*0.01), new Vector3f(0,1,0));
			proteinBackboneMesh.rotate((float)Math.toRadians(20*0.01), new Vector3f(0,1,0));
			ImVec2 childSize = ImGui.getWindowSize();
			sp.sendEvent(new ResizeEvent().setData(childSize));
			sp.draw();
			if (sp.hasTexture()) ImGui.image(sp.getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		});
		panel.addChildren(sceneProtein);
		
		
		WindowPanel sceneBackbone = new WindowPanel();
		sceneBackbone.setTitle("Backbone View Scene##"+filename);
		sceneBackbone.setDockerNode(panel.getDockerSpace().getBaseNode());
		sceneBackbone.setBindingOnDraw((display.event.Sender sender, display.event.Data data) -> {
			ImGui.beginChild("GameRender2##"+filename);
			proteinBackboneMesh.rotate((float)Math.toRadians(20*0.01), new Vector3f(0,1,0));
			proteinMesh.rotate((float)Math.toRadians(20*0.01), new Vector3f(0,1,0));
			ImVec2 childSize = ImGui.getWindowSize();
			sb.sendEvent(new ResizeEvent().setData(childSize));
			sb.draw();
			if (sb.hasTexture()) ImGui.image(sb.getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		});
		panel.addChildren(sceneBackbone);
		
		
		
		
		
		
		
		
		proteinPanel.add(panel);
		add(panel);
		
		/*Scene sp = gui.sceneManager.create("protein");
		Scene sb = gui.sceneManager.create("backbone");
		
		Protein p = Protein.buildFromFile("data.txt");
		
		gui.protein = p.getMesh();
		gui.proteinBackbone = p.getBackboneMesh();
		
		sp.add(gui.protein);
		sb.add(gui.proteinBackbone);
		

		sp.getCamera().setPosition(new Vector3f(5,8,50));
		sb.getCamera().setPosition(new Vector3f(5,8,50));*/
	}
	
}
