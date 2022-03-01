package display;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.implot.ImPlot;

public class Plot2D extends Component {
	
	private static int flag = 0;//ImPlotAxisFlags.AutoFit | ImPlotAxisFlags.NoDecorations;
	

	public static <T extends Number> void imshow(String ID, T[][] data) {
		ImPlot.pushColormap("grey");
		ImVec2 testj = ImGui.getWindowSize();
		testj.x -= 20;
		testj.y -= 40;
		if (ImPlot.beginPlot(ID, "", "", testj, flag, flag, flag, flag, flag, "", "")) {
			/*ImVec2 posl = ImPlot.getPlotPos();
			ImVec2 testj2 = ImPlot.getPlotSize();
			ImDrawList plotdata = ImPlot.getPlotDrawList();
			ImVec2 start = plotdata.getClipRectMax();
			ImVec2 end = plotdata.getClipRectMin();
			ImPlot.plotHeatmap("heat", data, 0);
			//plotdata.addCircleFilled(0.0f, 0.0f, 3.0f, 0xFF0000FF);
			float margin = 0.0f;
			plotdata.addLine(posl.x+0.0f, posl.y+0.0f, posl.x+200.0f, posl.y+200.0f, 0xFF0000FF);*/
			ImPlot.plotHeatmap(ID+"_heatmap", data, 0);
			ImPlot.endPlot();
		}
		ImPlot.popColormap();
	}
	
	public static <T extends Number> void plot(String ID, T[] x, T[] y) {
		//ImPlot.pushColormap("grey");
		ImVec2 testj = ImGui.getWindowSize();
		testj.x -= 20;
		testj.y -= 40;
		if (ImPlot.beginPlot(ID, "", "", testj, flag, flag, flag, flag, flag, "", "")) {
			/*ImVec2 posl = ImPlot.getPlotPos();
			ImVec2 testj2 = ImPlot.getPlotSize();
			ImDrawList plotdata = ImPlot.getPlotDrawList();
			ImVec2 start = plotdata.getClipRectMax();
			ImVec2 end = plotdata.getClipRectMin();
			ImPlot.plotHeatmap("heat", data, 0);
			//plotdata.addCircleFilled(0.0f, 0.0f, 3.0f, 0xFF0000FF);
			float margin = 0.0f;
			plotdata.addLine(posl.x+0.0f, posl.y+0.0f, posl.x+200.0f, posl.y+200.0f, 0xFF0000FF);*/
			ImPlot.plotLine(ID+"_plotline", x, y);
			ImPlot.endPlot();
		}
		//ImPlot.popColormap();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
