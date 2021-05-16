package testGraph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.*;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.images.*;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;

public class testgraph {
	
		public static void main(String args[]) {
			System.setProperty("org.graphstream.ui","swing");
			Graph graph = new SingleGraph("Test");
			
			FileSinkImages pic = FileSinkImages.createDefault();
			
			pic.setOutputPolicy( OutputPolicy.BY_STEP );
			pic.setLayoutPolicy( LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE );
			
			graph.addSink(pic);
			
			try {
				pic.begin("ex.png");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String styleSheet ="node { stroke-mode: plain; fill-color: white;shape: rounded-box;size-mode: "
					+ "fit;text-size:13; padding: 4px, 4px;} ";
			
			graph.addNode("1");
			graph.addNode("2");
			graph.addNode("3");
			graph.addEdge("12", "1", "2",true);
			graph.addEdge("23", "2", "3",true);
			graph.addEdge("31", "3", "1",true);
			graph.addNode("D");
			
			for(Node node: graph) {
				node.setAttribute("ui.label", node.getId());
				node.setAttribute("ui.style", "fill-color: rgb(0,100,255);");
			}
			
			graph.setAttribute("ui.stylesheet",styleSheet);
			
			
			Viewer viewer = graph.display();
			
			Node node = graph.getNode("1");
			Edge edge = graph.getEdge("12");
			
			for(Node n:graph) {
				System.out.println(n.getId());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//graph.addNode("10");
			node.setAttribute("ui.style", "fill-color: rgb(0,0,0);");
			edge.setAttribute("ui.style", "fill-color: rgb(200,100,100);");
			graph.addEdge("1D", "1", "D");
			try {
				graph.addNode("1");
			} catch (Exception e) {
				
			}
			
			graph.addEdge("D2", "D", "2");
			// dijkstra
			Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
			dijkstra.init(graph);
			dijkstra.setSource(graph.getNode("1"));
			dijkstra.compute();
			
			System.out.println(dijkstra.getPath(graph.getNode("D")));
			// image
			
			
			try {
				pic.writeAll(graph, "ex.png");
				pic.end();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
