package testGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.graphstream.graph.*;

//import Graph.Node;

// Graph class
class GraphInput {
	private int Vertices;
	private LinkedList<Integer> ver_list[];
    // node of adjacency list 
    static class Node {
        int value, weight;
        Node(int value){ //, int weight)  {
            this.value = value;
           // this.weight = weight;
        }
    };
 
    // define adjacency list
 
    List<List<Node>> adj_list = new ArrayList<>();
 
    //Graph Constructor
    public GraphInput(List<Edge> edges)
    {
        // adjacency list memory allocation
        for (int i = 0; i < edges.size(); i++)
            adj_list.add(i, new ArrayList<>());
 
        // add edges to the graph
        for (Edge e : edges)
        {
            // allocate new node in adjacency List from src to dest
            adj_list.get(e.src).add(new Node(e.dest)); //, e.weight));
        }
    }
    public GraphInput(int v) {
    	Vertices = v; 
    	ver_list = new LinkedList[v]; 
    	for (int i=0; i<v; ++i)         //create adjacency lists
    		ver_list[i] = new LinkedList(); 
    }
    public void addEdge(int v,int w) { 
    	ver_list[v].add(w); 
    } 
    // print adjacency list for the graph
    public static void printGraph(GraphInput graph)  {
        int src_vertex = 0;
        int list_size = graph.adj_list.size();
 
        System.out.println("The contents of the graph:");
        while (src_vertex < list_size) {
            //traverse through the adjacency list and print the edges
            for (Node edge : graph.adj_list.get(src_vertex)) {
                System.out.print("Vertex:" + src_vertex + " ==> " + edge.value + "\t"); 
                                //" (" + edge.weight + ")\t");
            }
 
            System.out.println();
            src_vertex++;
        }
    }
    public void BFS(int root_node)   { 
        // initially all vertices are not visited 
        boolean visited[] = new boolean[Vertices]; 
   
        // BFS queue 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
   
        // current node = visited, insert into queue 
        visited[root_node]=true; 
        queue.add(root_node); 
   
        while (queue.size() != 0)  { 
            // deque an entry from queue and process it  
            root_node = queue.poll(); 
            System.out.print(root_node + " "); 
   
            // get all adjacent nodes of current node and process
            Iterator<Integer> i = ver_list[root_node].listIterator();
            while (i.hasNext()){ 
                int n = i.next(); 
                if (!visited[n]) { 
                    visited[n] = true; 
                    queue.add(n); 
                } 
            } 
        }
        System.out.println();
    } 
    public void traverseEdge(GraphInput graph,Graph graph2) {
    	int src_vertex = 0;
        int list_size = graph.adj_list.size();
 
        System.out.println("\n"+ "The contents of the graph:");
        while (src_vertex < list_size) {
            for (Node edge : graph.adj_list.get(src_vertex)) {
                System.out.print("Canh : " + src_vertex + "-->" + edge.value + "\t");
                graph2.addEdge(String.valueOf(src_vertex) + String.valueOf(edge.value), String.valueOf(src_vertex),
                		String.valueOf(edge.value),true);
            }
            System.out.println();
            src_vertex++;
        }
    }
    public void traverseVertice(GraphInput graph,Graph graph2) {
    	for(int i = 1; i < graph.ver_list.length; i++ ) {
    		System.out.println("Dinh " + i + " : " + graph.ver_list[i]);
    		try {
				graph2.addNode(String.valueOf(i));
			} catch (Exception e) {
				// TODO: handle exception
			}
    	}
    }
}