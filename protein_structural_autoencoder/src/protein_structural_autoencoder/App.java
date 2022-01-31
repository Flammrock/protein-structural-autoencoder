package protein_structural_autoencoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.Vector3f;

import BioData.Atom;
import Display.*;
import imgui.ImGui;
import imgui.ImVec2;

public class App extends Application {

	public static void main(String[] args) {
		new App();
	}
	

	Container container;
	OffScreen offscreen;
	Camera camera;
	Scene scene;
	
	ImVec2 childSize;
	
	public App() {
		super(640,480,"Protein Structural Autoencoder");

		offscreen = new OffScreen();
		camera = new PerspectiveCamera(60.0f, 640, 480);
		scene = new Scene();
		container = new Container();
		
		window.run();
	}
	
	public void onClose() {
		
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
		
		ImGui.begin("GameWindow");
		{
			ImGui.beginChild("GameRender");
			childSize = ImGui.getWindowSize();
			if (offscreen.hasTexture()) ImGui.image(offscreen.getTexture().getID(), childSize.x, childSize.y, 0, 1, 1, 0);
			ImGui.endChild();
		}
		ImGui.end();
		
		window.imgGuiRender();
		
	}
	
	public void draw(Float deltaTime) {
		container.rotate((float)Math.toRadians(10*deltaTime), new Vector3f(0,1,0));
		camera.resize((int)childSize.x, (int)childSize.y);
		offscreen.resize((int)childSize.x, (int)childSize.y);
		offscreen.bind();
		scene.draw(camera);
	}



}