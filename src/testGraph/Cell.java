package testGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Cell {
	private int value;
	private List<Integer> vertical = new ArrayList<>();
	public Cell(int value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public void addEdge(int edge) {
		int check = 0;
		if(this.vertical != null) {
			for(Integer i:vertical) {
				if(i.equals(edge)){
					check = 1;
				}
			}
		}
		if(check == 0)
			this.vertical.add(edge);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public List<Integer> getListVertical(){
		return this.vertical;
	}
}
