package protein_structural_autoencoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import Autoencoder.*;
import BioData.*;
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
	
	//jliefjilfjlifdfsfsdfsdf

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
		
		
		Protein p = Protein.buildFromFile("data.txt");
		
		List<Residue> r = p.getResidues();
		
		double dist[][] = {{0.0, 0.9419477112754197, 1.3208840996383444, 1.4437059562281347, 0.8567744888056508, 0.9768478086265459, 1.007129528182404, 0.8186632336495451, 1.0700700031212242, 0.910355294848767, 0.5338186027408536, 0.63175125257699, 0.5289270182147966, 0.6440638920896464, 0.5404274038031474}, {0.9419477112754197, 0.0, 1.3660960637729993, 0.9583561921485992, 0.726281349206081, 1.0747966040474803, 0.7881665613723051, 1.1662100197717722, 0.7586877498174806, 0.335546705462875, 1.1348220353219516, 0.786438584451838, 1.3234022687629026, 0.9054286098259238, 0.8942924236608201}, {1.3208840996383444, 1.3660960637729993, 0.0, 1.0255684095848572, 1.0691656989324867, 1.1443055438393015, 1.1087333594355702, 1.1711174761397671, 0.962474409217193, 1.1890414448772586, 0.9961738077100973, 1.2989237496787256, 1.3934958262265293, 1.4401750130145217, 1.1003642940205525}, {1.4437059562281347, 0.9583561921485992, 1.0255684095848572, 0.0, 0.9944420636264405, 1.0401620243449947, 1.0097236872922484, 1.1024887206199692, 0.8444734885781213, 0.7610713730846326, 1.2640865668094143, 1.1431755437713331, 1.565485341293469, 1.2838419623773758, 1.337362865917155}, {0.8567744888056508, 0.726281349206081, 1.0691656989324867, 0.9944420636264405, 0.0, 0.6134891755385394, 1.1051056167383762, 0.9951042905915224, 0.9835972612569166, 0.4975508337442241, 0.827327892279536, 0.8388025029074311, 1.124885958498231, 1.1375172480018794, 0.8707720560098932}, {0.9768478086265459, 1.0747966040474803, 1.1443055438393015, 1.0401620243449947, 0.6134891755385394, 0.0, 1.386570412453899, 0.9579280101770642, 1.2666584528132474, 0.8338615638690163, 0.7529053811971317, 0.7151112671378759, 0.9456831525476068, 1.1478024488943561, 1.00300236555527}, {1.007129528182404, 0.7881665613723051, 1.1087333594355702, 1.0097236872922484, 1.1051056167383762, 1.386570412453899, 0.0, 1.020174870938322, 0.5491222206194849, 0.8489753107561336, 1.1149295072229226, 1.0759717725193056, 1.3298605430362458, 0.9201890018418729, 0.9802616398983007}, {0.8186632336495451, 1.1662100197717722, 1.1711174761397671, 1.1024887206199692, 0.9951042905915224, 0.9579280101770642, 1.020174870938322, 0.0, 0.9948535566976167, 0.961354753768025, 0.6057537860274099, 0.9186087827275934, 0.7641156376715252, 0.8005237352637484, 1.040964285732599}, {1.0700700031212242, 0.7586877498174806, 0.962474409217193, 0.8444734885781213, 0.9835972612569166, 1.2666584528132474, 0.5491222206194849, 0.9948535566976167, 0.0, 0.7050010333647068, 1.0185371567315575, 1.0106323947613187, 1.3333085905057727, 0.8959355896422097, 0.8581285993461328}, {0.910355294848767, 0.335546705462875, 1.1890414448772586, 0.7610713730846326, 0.4975508337442241, 0.8338615638690163, 0.8489753107561336, 0.961354753768025, 0.7050010333647068, 0.0, 0.9615739555589088, 0.7333890844032942, 1.2131306328218674, 0.9063519242774759, 0.8780274781747224}, {0.5338186027408536, 1.1348220353219516, 0.9961738077100973, 1.2640865668094143, 0.827327892279536, 0.7529053811971317, 1.1149295072229226, 0.6057537860274099, 1.0185371567315575, 0.9615739555589088, 0.0, 0.6769892991138725, 0.4452791658340371, 0.7729301185572673, 0.6034362136172443}, {0.63175125257699, 0.786438584451838, 1.2989237496787256, 1.1431755437713331, 0.8388025029074311, 0.7151112671378759, 1.0759717725193056, 0.9186087827275934, 1.0106323947613187, 0.7333890844032942, 0.6769892991138725, 0.0, 0.7102815105888086, 0.5510067115464706, 0.5960105828228769}, {0.5289270182147966, 1.3234022687629026, 1.3934958262265293, 1.565485341293469, 1.124885958498231, 0.9456831525476068, 1.3298605430362458, 0.7641156376715252, 1.3333085905057727, 1.2131306328218674, 0.4452791658340371, 0.7102815105888086, 0.0, 0.7468876478709511, 0.7896816340934889}, {0.6440638920896464, 0.9054286098259238, 1.4401750130145217, 1.2838419623773758, 1.1375172480018794, 1.1478024488943561, 0.9201890018418729, 0.8005237352637484, 0.8959355896422097, 0.9063519242774759, 0.7729301185572673, 0.5510067115464706, 0.7468876478709511, 0.0, 0.702614719542201}, {0.5404274038031474, 0.8942924236608201, 1.1003642940205525, 1.337362865917155, 0.8707720560098932, 1.00300236555527, 0.9802616398983007, 1.040964285732599, 0.8581285993461328, 0.8780274781747224, 0.6034362136172443, 0.5960105828228769, 0.7896816340934889, 0.702614719542201, 0.0}};
		MultidimensionalScaling m = new  MultidimensionalScaling(new Matrix(dist));
		
		System.out.println(m.scale2D());
		
		/*for (Residue ri : r) {
			System.out.println(ri.getID()+", "+ri.getAtoms().size()+", "+ri.getAlphaCarbon().getType());
			
		}*/

		/*Stream<Atom> atoms = Atom.loadFromFile("data.txt");
		List<Atom> atomss = atoms.collect(Collectors.toList());*/
		
		
		
		/*sceneManager = new SceneManager();
		imPlotContext = ImPlot.createContext();
		
		protein = new Container();
		proteinBackbone = new Container();
		
		window.run();*/
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
		
		Protein p = Protein.buildFromFile("data.txt");

		/*Stream<Atom> atoms = Atom.loadFromFile("data.txt");
		List<Atom> atomss = atoms.collect(Collectors.toList());
		
		
		
		protein = Protein.buildMesh(atomss);
		proteinBackbone = Protein.buildMeshBackbone(atomss);
		
		sp.add(protein);
		sb.add(proteinBackbone);*/
		
		protein = p.getMesh();
		proteinBackbone = p.getBackboneMesh();
		
		sp.add(protein);
		sb.add(proteinBackbone);
		

		sp.getCamera().setPosition(new Vector3f(5,8,50));
		sb.getCamera().setPosition(new Vector3f(5,8,50));

	}
	//dab
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
		
		
		////
		//ImPlot.showColormapSelector("test color");
		
		//ImPlot.showDemoWindow(test5);
		
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