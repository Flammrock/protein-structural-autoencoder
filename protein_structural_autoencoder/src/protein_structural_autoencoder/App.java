package protein_structural_autoencoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import BioData.Atom;
import BioData.Protein;
import Display.*;
import imgui.extension.implot.ImPlot;
import imgui.extension.implot.ImPlotContext;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

public class App extends Application {

	ImBoolean t = new ImBoolean(true);
	
	public static void main(String[] args) {
		new App();
	}
	

	Container protein; // will contain all atom of the protein
	Container proteinBackbone; // will contain all atom of the protein
	SceneManager sceneManager;
	ImPlotContext imPlotContext;
	
	/* Docking Area */
	ImInt dockLeft;
	ImInt dockCenter;
	ImInt dockRight;
	ImInt dockUp;
	ImInt dockDown;
	
	ImVec2 childSize; // scene width,height (imgui child window)
	ImVec2 childSizeBackbone;
	
	
	//Mesh cylinder;
	float x = 0.0f;
	
	public App() {
		super(640,480,"Protein Structural Autoencoder");

		
		sceneManager = new SceneManager();
		imPlotContext = ImPlot.createContext();
		
		protein = new Container();
		proteinBackbone = new Container();
		
		window.run();
	}
	
	@Override
	public void onClose() {
		sceneManager.destroy();
		Shader.SHADER.destroy();
	}
	

	@Override
	public void onInit() {
		
		ImPlot.setCurrentContext(imPlotContext);
		
		Shader.SHADER.create("basic");
		
		Scene sp = sceneManager.create("protein");
		Scene sb = sceneManager.create("backbone");

		Stream<Atom> atoms = Atom.loadFromFile("data.txt");
		List<Atom> atomss = atoms.collect(Collectors.toList());
		
		
		protein = Protein.buildMesh(atomss);
		proteinBackbone = Protein.buildMeshBackbone(atomss);
		
		sp.add(protein);
		sb.add(proteinBackbone);
		

		sp.getCamera().setPosition(new Vector3f(5,8,50));
		sb.getCamera().setPosition(new Vector3f(5,8,50));

	}

	@Override
	public void onUpdate(Float deltaTime) {

		if (deltaTime >= 0) this.draw(deltaTime);

		window.imgGuiPrepare(deltaTime);
		
		ImBoolean test = new ImBoolean(false),
				test1 = new ImBoolean(false),
				test2 = new ImBoolean(false),
				test3 = new ImBoolean(false),
				test4 = new ImBoolean(false),
				test5 = new ImBoolean(false);

		
		// Menu
	    if (ImGui.beginMainMenuBar()) {
	        if (ImGui.beginMenu("File")) {
	        	ImGui.menuItem("Open PDB File...", "CTRL+O");
	        	ImGui.separator();
	        	if (ImGui.menuItem("Quitter", null)) {
	        		window.close();
	        	}
	            ImGui.endMenu();
	        }
	        if (ImGui.beginMenu("Examples")) {
	            ImGui.menuItem("Main menu bar", null, test);
	            ImGui.menuItem("Console", null, test1);
	            ImGui.menuItem("Log", null, test2);
	            ImGui.endMenu();
	        }
	        if (ImGui.beginMenu("Help")) {
	            ImGui.menuItem("Metrics", null, test3);
	            ImGui.menuItem("Style Editor", null, test4);
	            ImGui.menuItem("About ImGui", null, test5);
	            ImGui.endMenu();
	        }
	        ImGui.endMainMenuBar();
	    }
		
		startDockSpace();
		
		ImGui.setNextWindowDockID(dockLeft.get(), ImGuiCond.Once);
		ImGui.begin("Navigation");
		{
			ImGui.setNextItemOpen(true, ImGuiCond.Once);
			if (ImGui.collapsingHeader("Database"))
		    {
				ImGui.setNextItemOpen(true, ImGuiCond.Once);
				if (ImGui.treeNode("HOMSTRAD"))
			    {
					ImGui.button("Hello world");
					ImGui.treePop();
					
			    }
		    }
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockCenter.get(), ImGuiCond.Once);
		ImGui.begin("Protein View Scene");
		{
			ImGui.beginChild("GameRender");
			childSize = ImGui.getWindowSize();
			if (sceneManager.get("protein").hasTexture()) ImGui.image(sceneManager.get("protein").getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockCenter.get(), ImGuiCond.Once);
		ImGui.begin("Backbone View Scene");
		{
			ImGui.beginChild("GameRender2");
			childSizeBackbone = ImGui.getWindowSize();
			if (sceneManager.get("backbone").hasTexture()) ImGui.image(sceneManager.get("backbone").getTexture().getID(), childSizeBackbone.x, childSizeBackbone.y, 0, 1, 1, 0);
			ImGui.endChild();
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockRight.get(), ImGuiCond.Once);
		ImGui.begin("View");
		{
			ImGui.text("nothing");
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockUp.get(), ImGuiCond.Once);
		ImGui.begin("Welcome");
		{
			ImGui.text("Welcome to Protein Structural Autoencoder!");
			ImGui.text("Coded by Lemmy Briot, Ahmed Chaudhry, Ameur Mahfoudi and Noa Weiss");
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockDown.get(), ImGuiCond.Once);
		ImGui.begin("Console");
		{
			ImGui.text(">> v0.0.1 beta build 9845");
		}
		ImGui.end();
		
		
		
		
		//ImPlot.showColormapSelector("test color");
		
		ImPlot.showDemoWindow(test5);
		
		/*ImGui.begin("Test Plot");
		{
			if (ImPlot.beginPlot("test")) {
				//ImPlot.
				ImPlot.endPlot();
			}
		}
		ImGui.end();*/
		
		
		
		
		endDockSpace();
		
		window.imgGuiRender();
		
	}
	
	
	
	private void draw(Float deltaTime) {
		protein.rotate((float)Math.toRadians(20*deltaTime), new Vector3f(0,1,0));
		proteinBackbone.rotate((float)Math.toRadians(20*deltaTime), new Vector3f(0,1,0));
		//x+=(Math.PI/50.0f);
		
		sceneManager.get("protein").resize(childSize.x, childSize.y);
		sceneManager.get("backbone").resize(childSizeBackbone.x, childSizeBackbone.y);
		
		sceneManager.draw();
	}
	
	@Override
	protected void buildDockSpace(int dockID) {
		imgui.internal.ImGui.dockBuilderAddNode(dockID);
		
		dockLeft = new ImInt();
		dockRight = new ImInt();
		dockCenter = new ImInt();
		dockDown = new ImInt();
		dockUp = new ImInt();
		
		ImInt tmp = new ImInt();
		ImInt tmp2 = new ImInt();
		ImInt tmp3 = new ImInt();
		
		imgui.internal.ImGui.dockBuilderSplitNode(dockID, ImGuiDir.Left, 0.2f, dockLeft, tmp);
		imgui.internal.ImGui.dockBuilderSplitNode(tmp.get(), ImGuiDir.Right, 0.2f, dockRight, tmp2);
		imgui.internal.ImGui.dockBuilderSplitNode(tmp2.get(), ImGuiDir.Up, 0.08f, dockUp, tmp3);
		imgui.internal.ImGui.dockBuilderSplitNode(tmp3.get(), ImGuiDir.Down, 0.08f, dockDown, dockCenter);
		
		imgui.internal.ImGui.dockBuilderFinish(dockID);
	}


}