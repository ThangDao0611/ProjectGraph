package testGraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
public class maintest{
    public static void main (String[] args) {
        // define edges of the graph 
    	char a,b,c,d;
    	int ver1,ver2;
    	
    	Graph graphOut = new SingleGraph("Main");
    	
    	GraphCell graphCell = new GraphCell();
    	
    	
    	String pathname = "C:\\Users\\Dao Thang\\eclipse-workspace\\testGraph\\src\\testGraph\\file.txt";
    	try {
    		File fi = new File(pathname);
    		Scanner sc = new Scanner(fi);
    		while(sc.hasNextLine()) {
    			String str = sc.nextLine();
    			str = str.replaceAll("\\s+", "");
    			c = str.charAt(0); // cell
    			ver1 = Character.getNumericValue(c);
    			graphCell.addCellInGraph(new Cell(ver1));
    			for(int i = 0; i < str.length() - 1; i++) {
    				d = str.charAt(i+1); // vertical
    				ver2 = Character.getNumericValue(d);
    				if(graphCell.isVertical(ver2)) {
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
        // call graph class Constructor to construct a graph
    	//graphCell.traverGraph();
    	graphCell.traverGraphToAddGraph(graphOut);
    	
		// Display graphsteam
		System.setProperty("org.graphstream.ui","swing");
		String styleSheet ="node { stroke-mode: plain; fill-color: white;shape: rounded-box;size-mode: "
				+ "fit;text-size:13; padding: 4px, 4px;} ";
		graphOut.setAttribute("ui.stylesheet",styleSheet);
		for(Node node: graphOut) {
			node.setAttribute("ui.label", node.getId());
			node.setAttribute("ui.style", "fill-color: rgb(0,100,255);");
		}
		// Export Image
		
		FileSinkImages pic = FileSinkImages.createDefault();
		
		pic.setOutputPolicy( OutputPolicy.BY_STEP );
		pic.setLayoutPolicy( LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE );
		
		graphOut.addSink(pic);
		
		try {
			pic.begin("main.png");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//-----------------
		graphOut.display();
		
		try {
			pic.writeAll(graphOut, "main.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}