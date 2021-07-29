package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.ui.fx_viewer.FxViewer;

import testGraph.Cell;
import testGraph.GraphCell;

public class GraphOut {
	private Graph graphOut;
	private FxViewer viewer;
	private FileSinkImages pic;
	private GraphCell graphCell;
	public Boolean isInit = false;
	private List<String> listEdgeMoved = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> pathListfind = new ArrayList<ArrayList<Integer>>();
	
	public GraphOut() {
		graphOut = new SingleGraph("Main");
		viewer = new FxViewer(graphOut, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		graphCell = new GraphCell();
	}
	
	public void initGraph(File fi) {
		String a,b,c,d;
    	int ver1,ver2;
		 
    	try {
    		Scanner sc = new Scanner(fi);
    		while(sc.hasNextLine()) {
    			String str = sc.nextLine();
    			String[] split = str.split(" ");
    			//str = str.replaceAll("\\s+", "");
    			c = split[0]; // cell
    			ver1 = Integer.parseInt(c);
    			graphCell.addCellInGraph(new Cell(ver1));
    			for(int i = 0; i < split.length - 1; i++) {
    				d = split[i+1]; // vertical
    				ver2 = Integer.parseInt(d);
    				if(graphCell.isVertical(ver2)==false) {
    					graphCell.addCellInGraph(new Cell(ver2));
    				}
    				graphCell.addEdge( ver1, ver2 );
    			}
    		}
    		sc.close();
    	} catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(null, "An error occurred.");
    		e.printStackTrace();
    	}
    	graphCell.traverGraphToAddGraph(graphOut);
    	
		// Display graphstream
    	System.setProperty("org.graphstream.ui", "javafx");
		String styleSheet ="node { stroke-mode: plain; fill-color: white;shape: rounded-box;size-mode:fit; "
				+ "text-size:20;text-color:white ; padding: 15px;} ";
		graphOut.setAttribute("ui.stylesheet",styleSheet);
		for(Node node: graphOut) {
			node.setAttribute("ui.label", node.getId());
			node.setAttribute("ui.style", "fill-color: rgb(0,0,0);");
		}
		
		isInit = true;
	}
	
	public Graph getGraph() {
		return this.graphOut;
	}
	
	public GraphCell getGraphCell() {
		return this.graphCell;
	}
	
	public FxViewer getFxViewer() {
		return this.viewer;
	}
	
	public void cleanGraph() {
		graphOut.clear();
	}
	
	public void move(int src,int dest) {
		Edge edge = graphOut.getEdge(String.valueOf(src)+String.valueOf(dest));
		edge.setAttribute("ui.style", "fill-color: rgb(255,69,0);");
		listEdgeMoved.add(String.valueOf(src)+String.valueOf(dest));
	}
	
	public void reset() {
		for(String string : listEdgeMoved) {
			Edge edge = graphOut.getEdge(string);
			edge.setAttribute("ui.style", " fill-color: rgb(0,0,0); ");
		}
		listEdgeMoved.clear();	
	}
	
	
	public void PNGSinkImage(String path) {
		System.setProperty("org.graphstream.ui", "swing");
		pic = FileSinkImages.createDefault();
		pic.setOutputPolicy( OutputPolicy.BY_STEP );
		pic.setLayoutPolicy( LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE );
		//System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graphOut.addSink(pic);
		
		String styleSheet ="node { stroke-mode: plain; fill-color: white;shape: rounded-box;size-mode:fit; "
				+ "text-size:20;text-color:white ; padding: 15px;} ";
		graphOut.setAttribute("ui.stylesheet",styleSheet);
		for(Node node: graphOut) {
			node.setAttribute("ui.label", node.getId());
			node.setAttribute("ui.style", "fill-color: rgb(0,0,0);");
		}
		
		try {
			pic.begin(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Image export
		try {
			pic.writeAll(graphOut, path);
			pic.end();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LogExport(String text,String path) {
		try {
		      FileWriter myWriter = new FileWriter(path);
		      myWriter.write(text);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	private int Vertices=0;
	
	/*******************************************************/
	   public void printAllPaths(int s, int d)
	   {
		   Vertices = graphCell.getGraphCells().size()+1;
	       boolean[] isVisited = new boolean[Vertices];
	       ArrayList<Integer> pathList = new ArrayList<>();
	       pathList.add(s);
	       printAllPathsUtil(s, d, isVisited, pathList);
	       //System.out.println(pathListfind);
	   }
	   private void printAllPathsUtil(Integer u, Integer d,
	                                  boolean[] isVisited,
	                                  ArrayList<Integer> localPathList)
	   {
	 
	       if (u.equals(d)) {
	          
	           ArrayList<Integer> listTemp = new ArrayList<>(localPathList);
	          
	           this.pathListfind.add(listTemp);
	       }
	       else {
		       isVisited[u] = true;
			       for (Integer i : graphCell.getListVertical(u) ) {
			           if (!isVisited[i]) {
			               localPathList.add(i);
			               printAllPathsUtil(i, d, isVisited, localPathList);
			               localPathList.remove(i);
			           }
			       }
		       isVisited[u] = false;
	       }
	   }
	   
	   public ArrayList<ArrayList<Integer>> getPathList(){
		   return this.pathListfind;
	   }
	/*******************************************************/

}
