package testGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Graph;


public class GraphCell {
	private LinkedList<Cell> graphCells = new LinkedList<>();
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
	
	public int getIdWithValue(int value) {
		int i=0;
		for(Cell tmp: graphCells) {
			if(tmp.getValue() == value) {
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	public List<Integer> getListVertical(int vertical){
		List<Integer> list = new ArrayList<>();
		for(Cell cellCheck:this.graphCells) {
			if(cellCheck.getValue() == vertical) {
				list = cellCheck.getListVertical();
			}
		}
		return list;
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
					//System.out.println(verNode);
					graph.addNode(verNode);
					graph.addEdge(edgeName,cellNode,verNode,true);
				}
			}
		}
	}
	
	public List<Integer> traverGraphGetBigSmall() {
		List<Integer> list = new ArrayList<>();
		int min = this.graphCells.get(0).getValue();
		int max = min;
		for(Cell cellCheck:this.graphCells) {
			int cellNode = cellCheck.getValue();
			if(cellNode > max) {
				max = cellNode;
			}
			if(cellNode < min) {
				min = cellNode;
			}
		}
		list.add(min);
		list.add(max);
		return list;
	}
	
	public List<Integer> traverGraphGetList() {
		List<Integer> list = new ArrayList<>();
		for(Cell cellCheck:this.graphCells) {
			//String cellNode = String.valueOf(cellCheck.getValue());
			list.add(cellCheck.getValue());
		}
		Collections.sort(list);
		return list;
	}

	public LinkedList<Cell> getGraphCells() {
		return graphCells;
	}
}
