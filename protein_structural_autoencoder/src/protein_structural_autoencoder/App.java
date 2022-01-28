package protein_structural_autoencoder;

import org.joml.Vector3f;

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
		
		Window myWindow = new Window(640,480,"Protein Structure Autoencoder");
		
		myWindow.getScene().add(spheremesh);
		myWindow.getScene().add(spheremesh2);
		

		myWindow.setUpdateCallback((dt) -> {
			x++;
			spheremesh.setPosition(new Vector3f(0,(float)(4*Math.sin(Math.toRadians(x))),-15));
			spheremesh2.setPosition(new Vector3f(10+(float)(6*Math.sin(Math.toRadians(x))),0,-15+(float)(15*Math.cos(Math.toRadians(x)))));
		});
		
		myWindow.run();
	}

}