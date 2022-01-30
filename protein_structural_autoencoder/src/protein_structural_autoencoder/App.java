package protein_structural_autoencoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.Vector3f;

import BioData.Atom;
import Display.Container;
import Display.Material;
import Display.Mesh;
import Display.SphereGeometry;
import Display.Window;

public class App {

	public static void main(String[] args) {
		App app = new App();
		app.start();
	}
	
	float x;
	
	App() {
		this.x = 0;
	}
	
	void start() {
		SphereGeometry testsphere = new SphereGeometry(1.0f, 64, 128);
        SphereGeometry testsphere2 = new SphereGeometry(2.0f, 64, 128);
        Mesh spheremesh = new Mesh(testsphere, new Material(new Vector3f(1,0,0)));
        Mesh spheremesh2 = new Mesh(testsphere2, new Material(new Vector3f(0,0,1)));
        
        spheremesh.setPosition(new Vector3f(0,0,-15));
        spheremesh2.setPosition(new Vector3f(10,0,-15));
        
        
        
        Container container = new Container();
		
		Window myWindow = new Window(640,480,"Protein Structure Autoencoder");
		
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
        	myWindow.getScene().add(spheremesh3);
        	container.add(spheremesh3);
		}
		
		System.out.println("min = "+container.getMin());
		System.out.println("max = "+container.getMax());
		System.out.println("cen = "+container.getCenter());

		//myWindow.getCamera().fitView(container);
		//myWindow.getScene().add(spheremesh);
		//myWindow.getScene().add(spheremesh2);
		
		//myWindow.getCamera().fitView(container);
		myWindow.getCamera().setPosition(new Vector3f(0,0,20));
		
		//myWindow.getCamera().fitView(container);
		
		System.out.println("pos = "+myWindow.getCamera().getPosition());
		//container.translate(new Vector3f(0,0,-50));

		myWindow.setUpdateCallback((dt) -> {
			x+=0.1f;
			container.rotate((float)Math.toRadians(10*dt), new Vector3f(0,1,0));
			//myWindow.getCamera().getPosition().add(myWindow.getCamera().getForward().mul(0.1f));
			//spheremesh.setPosition(new Vector3f(0,(float)(4*Math.sin(Math.toRadians(x))),-15));
			//spheremesh2.setPosition(new Vector3f(10+(float)(6*Math.sin(Math.toRadians(x))),0,-15+(float)(15*Math.cos(Math.toRadians(x)))));
		});
		
		myWindow.run();
	}

}