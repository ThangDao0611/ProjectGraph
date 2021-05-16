package testGraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.JOptionPane;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
public class main{
    public static void main (String[] args) {
        // define edges of the graph 
    	char a,b,c,d;
    	int n = 0;
    	Graph graphOut = new SingleGraph("Main");
//        List<Edge> edges = Arrays.asList(new Edge(0, 1, 2),new Edge(0, 2, 4),
//                   new Edge(1, 2, 4),new Edge(2, 0, 5), new Edge(2, 1, 4),
//                   new Edge(3, 2, 3), new Edge(4, 5, 1),new Edge(5, 4, 3));
    	List<Edge> edges = new ArrayList<Edge>();  //copy
//    	edges.add(new Edge(1,2));
//    	edges.add(new Edge(2,3));
//    	edges.add(new Edge(2,4));
//    	edges.add(new Edge(3,4));
    	String pathname = "C:\\Users\\Dao Thang\\eclipse-workspace\\testGraph\\src\\testGraph\\file.txt";
    	try {
    		File fi = new File(pathname);
    		Scanner sc = new Scanner(fi);
    		while(sc.hasNextLine()) {
    			String str = sc.nextLine();
    			str = str.replaceAll("\\s+", "");
    			for(int i = 0; i < str.length() - 1; i++) {
    				c = str.charAt(i);
    				d = str.charAt(i+1);
    				edges.add(new Edge(Character.getNumericValue(c),Character.getNumericValue(d)));
    			}
    		}
    		sc.close();
    	} catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(null, "An error occurred.");
    		e.printStackTrace();
    	}
        // call graph class Constructor to construct a graph
    	GraphInput graph = new GraphInput(edges);
 
        // print the graph as an adjacency list
        //Graph.printGraph(graph);    
    	try {
    		File fi = new File(pathname);
    		Scanner sc = new Scanner(fi);
    		while(sc.hasNextLine()) {
    			String str = sc.nextLine();
    			str = str.replaceAll("\\s+", "");
    			for(int i = 0; i < str.length(); i++) {
    				a = str.charAt(i);
    				if(Character.getNumericValue(a) > n) {
    					n = Character.getNumericValue(a);
    				}
    			}
    		}	
    		sc.close();
    	} catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(null, "An error occurred.");
    		e.printStackTrace();
    	}
    	
		try {
    		File fi = new File(pathname);
    		Scanner sc = new Scanner(fi);
    		GraphInput g = new GraphInput(n+1);
    		while(sc.hasNextLine()) {
    			String str = sc.nextLine();
    			str = str.replaceAll("\\s+", "");
    			for(int i = 0; i < str.length() - 1; i++) {
    				a = str.charAt(i);
    				b = str.charAt(i+1);
    				g.addEdge(Character.getNumericValue(a), Character.getNumericValue(b));
    			}
    		}
    		System.out.println("Following is Breadth First Traversal "+
                    "(starting from vertex 2)");
    		g.BFS(1);
    		g.traverseVertice(g,graphOut);
    		sc.close();
    	} catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(null, "An error occurred.");
    		e.printStackTrace();
    	}
		graph.traverseEdge(graph,graphOut);
		// Display graphsteam
		System.setProperty("org.graphstream.ui","swing");
		String styleSheet ="node { stroke-mode: plain; fill-color: white;shape: rounded-box;size-mode: "
				+ "fit;text-size:13; padding: 4px, 4px;} ";
		graphOut.setAttribute("ui.stylesheet",styleSheet);
		for(Node node: graphOut) {
			node.setAttribute("ui.label", node.getId());
			node.setAttribute("ui.style", "fill-color: rgb(0,100,255);");
		}
		graphOut.display();
    }
}