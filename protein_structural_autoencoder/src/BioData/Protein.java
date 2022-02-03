package BioData;

import java.util.List;

import org.joml.Vector3f;

import Display.Color3f;
import Display.Container;
import Display.CylinderGeometry;
import Display.Material;
import Display.Mesh;
import Display.QuaternionHelper;
import Display.SphereGeometry;

public class Protein {

	
	static public Container buildMesh(List<Atom> atoms) {
		Container container = new Container();
		SphereGeometry testsphere3 = new SphereGeometry(0.5f, 8, 16);
		Mesh spheremesh4 = null;
		for (Atom atom : atoms) {
			Vector3f color;
			if (atom.getType().substring(0, 1).equals("H")) {
				color = Color3f.WHITE;
			} else if (atom.getType().substring(0, 1).equals("C")) {
				color = Color3f.GRAY;
			} else if (atom.getType().substring(0, 1).equals("N")) {
				color = Color3f.BLUE;
			} else if (atom.getType().substring(0, 1).equals("O")) {
				color = Color3f.RED;
			} else if (atom.getType().substring(0, 1).equals("S")) {
				color = Color3f.YELLOW;
			} else {
				color = Color3f.MAGENTA;
			}
	        Mesh spheremesh3 = new Mesh(testsphere3, new Material(color));
	        spheremesh3.setPosition(atom.getPosition());
	        container.add(spheremesh3);
	        if (atom.getType().length() >= 2 && atom.getType().substring(0, 2).equals("CA")) {
	        	if (spheremesh4!=null) {
	        		
	        		Vector3f p1 = spheremesh4.getPosition();
	        		Vector3f p2 = spheremesh3.getPosition();
	        		
	        		float dist = p1.distance(p2);
					Vector3f dir = new Vector3f();
					p1.sub(p2,dir);
					dir.normalize();
					Vector3f pos = new Vector3f();
					p1.add(p2,pos);pos.div(2.0f);
					CylinderGeometry cylinderGeo2 = new CylinderGeometry(0.3f, dist);
					Mesh cylinder2 = new Mesh(cylinderGeo2, new Material(Color3f.MAGENTA));
					Vector3f v = new Vector3f(0,0,1);
					
					cylinder2.getTransform().setInternalRotation(QuaternionHelper.setFromUnitVectors(v, dir));
					
	        		
	        		
	        		cylinder2.setPosition(pos);
	        		container.add(cylinder2);
	        	}
	        	spheremesh4 = spheremesh3;
			}
		}
		return container;
	}
	
	static public Container buildMeshBackbone(List<Atom> atoms) {
		Container container = new Container();
		SphereGeometry testsphere3 = new SphereGeometry(0.5f, 8, 16);
		Mesh spheremesh4 = null;
		for (Atom atom : atoms) {
			Vector3f color;
			if (atom.getType().substring(0, 1).equals("H")) {
				color = Color3f.WHITE;
			} else if (atom.getType().substring(0, 1).equals("C")) {
				color = Color3f.GRAY;
			} else if (atom.getType().substring(0, 1).equals("N")) {
				color = Color3f.BLUE;
			} else if (atom.getType().substring(0, 1).equals("O")) {
				color = Color3f.RED;
			} else if (atom.getType().substring(0, 1).equals("S")) {
				color = Color3f.YELLOW;
			} else {
				color = Color3f.MAGENTA;
			}
			if (atom.getType().length() >= 2 && atom.getType().substring(0, 2).equals("CA")) {
	        	Mesh spheremesh3 = new Mesh(testsphere3, new Material(color));
	        	spheremesh3.setPosition(atom.getPosition());
	        	container.add(spheremesh3);
	        	if (spheremesh4!=null) {
	        		
	        		Vector3f p1 = spheremesh4.getPosition();
	        		Vector3f p2 = spheremesh3.getPosition();
	        		
	        		float dist = p1.distance(p2);
					Vector3f dir = new Vector3f();
					p1.sub(p2,dir);
					dir.normalize();
					Vector3f pos = new Vector3f();
					p1.add(p2,pos);pos.div(2.0f);
					CylinderGeometry cylinderGeo2 = new CylinderGeometry(0.5f, dist);
					Mesh cylinder2 = new Mesh(cylinderGeo2, new Material(Color3f.GRAY));
					Vector3f v = new Vector3f(0,0,1);
					
					cylinder2.getTransform().setInternalRotation(QuaternionHelper.setFromUnitVectors(v, dir));
					
	        		
	        		
	        		cylinder2.setPosition(pos);
	        		container.add(cylinder2);
	        	}
	        	spheremesh4 = spheremesh3;
			}
		}
		return container;
	}
	
}
