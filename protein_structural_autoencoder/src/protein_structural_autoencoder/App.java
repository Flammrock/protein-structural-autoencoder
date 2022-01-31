package protein_structural_autoencoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.Vector3f;

import BioData.Atom;
import Display.*;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDir;
import imgui.type.ImInt;

public class App extends Application {

	public static void main(String[] args) {
		new App();
	}
	

	Container container; // will contain all atom of the protein
	OffScreen offscreen; // offscreen canvas to draw the protein
	Camera camera;       // a camera to render the protein
	Scene scene;         // a scene where the proteil will be drawn
	
	/* Docking Area */
	ImInt dockLeft;
	ImInt dockCenter;
	ImInt dockRight;
	ImInt dockUp;
	ImInt dockDown;
	
	ImVec2 childSize; // scene width,height (imgui child window)
	
	public App() {
		super(640,480,"Protein Structural Autoencoder");

		offscreen = new OffScreen();
		camera = new PerspectiveCamera(60.0f, 640, 480);
		scene = new Scene();
		container = new Container();
		
		window.run();
	}
	
	@Override
	public void onClose() {
		offscreen.destroy();
		scene.destroy();
	}
	
	@Override
	public void onInit() {
		
		scene.useShader(getShader());
 
        
		

		Stream<Atom> atoms = Atom.loadFromFile("data.txt");
		List<Atom> atomss = atoms.collect(Collectors.toList());
		
		SphereGeometry testsphere3 = new SphereGeometry(0.5f, 8, 16);
		for (Atom atom : atomss) {
			Vector3f color;
			if (atom.getType().substring(0, 1).equals("H")) {
				color = new Vector3f(1,1,1);
			} else if (atom.getType().substring(0, 1).equals("C")) {
				color = new Vector3f(0.3f,0.3f,0.3f);
			} else if (atom.getType().substring(0, 1).equals("N")) {
				color = new Vector3f(0,0,1);
			} else if (atom.getType().substring(0, 1).equals("O")) {
				color = new Vector3f(1,0,0);
			} else if (atom.getType().substring(0, 1).equals("S")) {
				color = new Vector3f(1,1,0);
			} else {
				color = new Vector3f(1,0,1);
			}
        	Mesh spheremesh3 = new Mesh(testsphere3, new Material(color));
        	spheremesh3.setPosition(atom.getPosition());
        	scene.add(spheremesh3);
        	container.add(spheremesh3);
		}
		

		camera.setPosition(new Vector3f(0,0,20));


	}

	@Override
	public void onUpdate(Float deltaTime) {

		if (deltaTime >= 0) this.draw(deltaTime);
		
		offscreen.unbind();
		
		window.imgGuiPrepare(deltaTime);
		
		startDockSpace();
		
		ImGui.setNextWindowDockID(dockLeft.get(), ImGuiCond.Once);
		ImGui.begin("Panel Left");
		{
			ImGui.text("Welcome to Protein Structural Autoencoder!");
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockCenter.get(), ImGuiCond.Once);
		ImGui.begin("Scene");
		{
			ImGui.beginChild("GameRender");
			childSize = ImGui.getWindowSize();
			if (offscreen.hasTexture()) ImGui.image(offscreen.getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockRight.get(), ImGuiCond.Once);
		ImGui.begin("Panel Right");
		{
			ImGui.text("Welcome to Protein Structural Autoencoder!");
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockUp.get(), ImGuiCond.Once);
		ImGui.begin("Panel Up");
		{
			ImGui.text("Welcome to Protein Structural Autoencoder!");
		}
		ImGui.end();
		
		ImGui.setNextWindowDockID(dockDown.get(), ImGuiCond.Once);
		ImGui.begin("Panel Down");
		{
			ImGui.text("Welcome to Protein Structural Autoencoder!");
		}
		ImGui.end();
		
		endDockSpace();
		
		window.imgGuiRender();
		
	}
	
	private void draw(Float deltaTime) {
		container.rotate((float)Math.toRadians(10*deltaTime), new Vector3f(0,1,0));
		camera.resize((int)childSize.x, (int)childSize.y);
		offscreen.resize((int)childSize.x, (int)childSize.y);
		offscreen.bind();
		scene.draw(camera);
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