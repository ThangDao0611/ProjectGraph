package testGraph;

import java.util.LinkedList;

import org.graphstream.graph.Graph;

public class GraphCell {
	LinkedList<Cell> graphCells = new LinkedList<>();
	public GraphCell() {
		// TODO Auto-generated constructor stub
	}
	
	private void addCell(Cell cell) {
		graphCells.add(cell);
	}
	
	public void addCellInGraph(Cell cell) {
		int check = 0;
		for(Cell cellCheck:this.graphCells) {
			if(cellCheck.getValue() == cell.getValue()) {
				check = 1;
			}
		}
		if(check == 0) {
			addCell(cell);
		}
	}
	
	public void addEdge(int src, int dest) {
		for(Cell cellCheck:this.graphCells) {
			if(cellCheck.getValue() == src) {
				cellCheck.addEdge(dest);
			}
		}
	}
	
	public boolean isVertical(int ver) {
		for(Cell cellCheck:this.graphCells) {
			if(cellCheck.getValue() == ver) {
				return true;
			}
		}
		return false;
	}
	
	public void traverGraphToAddGraph(Graph graph) {
		for(Cell cellCheck:this.graphCells) {
			String cellNode = String.valueOf(cellCheck.getValue());
			
			try {
				graph.addNode(cellNode);
			} catch (Exception e) {
				// TODO: handle exception
			}
			for(Integer i:cellCheck.getListVertical()) {
				String verNode = String.valueOf(i);
				String edgeName = cellNode + verNode;
				//System.out.println(edgeName + '\t' + cellNode + '\t' + verNode);
				if(graph.getNode(verNode)!=null) {
					graph.addEdge(edgeName,cellNode,verNode,true);
				}
				else {
					graph.addNode(verNode);
					graph.addEdge(edgeName,cellNode,verNode,true);
				}
			}
		}
	}
	
	public void traverGraph() {
		for(Cell cellCheck:this.graphCells) {
			String cellNode = String.valueOf(cellCheck.getValue());
			System.out.println(cellNode);
			for(Integer i:cellCheck.getListVertical()) {
				String verNode = String.valueOf(i);
				String edgeName = cellNode + verNode;
				System.out.println(verNode);
			}
		}
	}

}
