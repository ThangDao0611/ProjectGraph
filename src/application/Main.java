package application;

import java.io.File;
import java.util.List;

import org.graphstream.algorithm.APSP;
import org.graphstream.algorithm.APSP.APSPInfo;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.geom.Point3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import testGraph.GraphCell;

public class Main extends Application {
	private GraphOut graphinit = new GraphOut();
	private String currentNode = "";
	@FXML
	private Button StartButton;
	@FXML
	private Button StopButton;
	@FXML
	private Button AutoButton;
	@FXML
	private MenuButton MenuStart;
	@FXML
	private MenuButton MenuEnd;
	@FXML
	private MenuButton MenuChoose;
	@FXML
	private TextArea LogTextArea;
	@FXML
	private AnchorPane anchor;
	@FXML
	private Button LogButton;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
			Scene scene = new Scene(root,1000,640);
			
			primaryStage.getIcons().add(new Image("file:graph.png"));
			primaryStage.setTitle("Graph MidTerm");
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			System.out.println("Start");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void FileButton(ActionEvent e) {
		
		Window window = ((javafx.scene.Node) (e.getSource())).getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(window);
		
		//debug(file);
		
		if(file != null) {
			graphinit = new GraphOut();
			
			graphinit.initGraph(file);
			
			FxViewer viewer = graphinit.getFxViewer();
			viewer.enableAutoLayout();
			
			
			FxViewPanel v =  (FxViewPanel) viewer.addDefaultView(false);
			
			//v.autosize();
				
			zoom(v);
			
			AnchorPane.setLeftAnchor(v, 201.0);
			AnchorPane.setBottomAnchor(v, 0.0);
			AnchorPane.setRightAnchor(v, 0.0);
			AnchorPane.setTopAnchor(v, 0.0);
			
			
			anchor.getChildren().addAll(v);
			
			log("Open file: "+file.getPath());
			
			GraphCell graphCell = graphinit.getGraphCell();
			List<Integer> list1 = graphCell.traverGraphGetBigSmall();
			List<Integer> list2 = graphCell.traverGraphGetList();
			// init default values for MenuStart & MenuEnd
			for(int i:list2) {
				MenuItem item1  = new MenuItem(String.valueOf(i));
				item1.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						MenuStart.setText(String.valueOf(i));
					}
				});
				MenuItem item2  = new MenuItem(String.valueOf(i));
				item2.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						MenuEnd.setText(String.valueOf(i));
					}
				});
				MenuStart.getItems().addAll(item1);
				MenuEnd.getItems().addAll(item2);
			}
			MenuStart.setText(String.valueOf(list1.get(0)));
			MenuEnd.setText(String.valueOf(list1.get(1)));
			currentNode = String.valueOf(list1.get(0));
		}
		
	}
	
	public void PNGButton(ActionEvent e) {
		Window window = ((javafx.scene.Node) (e.getSource())).getScene().getWindow();
		 FileChooser fileChooser = new FileChooser();
	     fileChooser.setTitle("Save");
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
		 fileChooser.getExtensionFilters().add(extFilter);
		try {
			File file = fileChooser.showSaveDialog(window);
			log("PNG Export");
			graphinit.PNGSinkImage(file.getPath());
		} catch (Exception e2) {
			// TODO: handle exception
		}	
	}
	
	public void StartButton(ActionEvent e) {
		debug("Start Button");
		log("Start Simulator");
		String startString = MenuStart.getText();
		String endString = MenuEnd.getText();
		
		if(graphinit.isInit) {
			if(checkPath()==true) {
				simulator(startString,endString);
			}
			else {
				debug("Path don't have exits");
				log("Path don't have exits");
			}
		}	
		else {
			log("Graph not exits");
		}
	}
	
	public void simulator(String startString,String endString) {
		List<Integer> list = graphinit.getGraphCell().getListVertical(Integer.parseInt(startString));
		
		if(list.isEmpty()) {
			log("No Path");
			MenuChoose.getItems().clear();
			return;
		}
		
		//list vertical
		MenuChoose.getItems().clear();
		log("Current Node: "+ startString);
		
		MenuItem itemStart  = new MenuItem(String.valueOf(startString));
		itemStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				log("Move Edge: "+Integer.parseInt(startString) +Integer.parseInt(currentNode));
				simulator(startString,endString);
			}
		});
		//add current to start
		MenuChoose.getItems().addAll(itemStart);
		for(int i:list) {
			MenuItem item  = new MenuItem(String.valueOf(i));
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
					currentNode = String.valueOf(i);
					graphinit.move(Integer.parseInt(startString), Integer.parseInt(currentNode));
					log("Move Edge: "+Integer.parseInt(startString) +Integer.parseInt(currentNode));
					simulator(currentNode,endString);
				}
			});
			
			MenuChoose.getItems().addAll(item);
		}
		if(startString.equals(endString)) {
			log("Finished!");
			MenuChoose.getItems().clear();
			System.out.println("Finished!");
		}
	}
	
	public void StopButton(ActionEvent e) {
		debug("Stop Button");
		log("Stop Simulator");
		MenuChoose.getItems().clear();
		MenuStart.setText(currentNode);
	}
	
	public void ResetButton(ActionEvent e) {
		debug("Reset Button");
		log("Reset Graph");
		graphinit.reset();
		List<Integer> list1 = graphinit.getGraphCell().traverGraphGetBigSmall();
		MenuStart.setText(String.valueOf(list1.get(0)));
		MenuEnd.setText(String.valueOf(list1.get(1)));
		currentNode = String.valueOf(list1.get(0));
	}
	
	private double xb =0,yb =0;
	
	public void zoom( FxViewPanel v) {
		
	    v.setOnScroll(
	        new EventHandler<ScrollEvent>() {
	        	double zoomFactor = 1;
	        	int paddingNumber = 15;
	        	int textSizeNumber = 20;
	            @Override
	            public void handle(ScrollEvent event) {
	                double deltaY = event.getDeltaY();
	                
	                if (deltaY < 0){
	                	paddingNumber -= 5;
	                	textSizeNumber -= 3;
	                    zoomFactor += 0.2;
	                }
	                else {
	                	paddingNumber += 5;
	                	textSizeNumber += 3;
	                	zoomFactor -= 0.2;
	                }
	                v.getCamera().setViewPercent(zoomFactor);
	                for(Node node : graphinit.getGraph()) {
	                	node.setAttribute("ui.style", "text-size:"+textSizeNumber+"; padding: "+paddingNumber+"px;");
	                }
	                
	                if(zoomFactor == 1)
	                	v.getCamera().resetView();
	                event.consume();
	            }
	        });
	    
	    
	    
	    v.setOnMousePressed(event->{
	    	event.consume();
	    	xb=event.getSceneX();
	    	yb=event.getSceneY();
	    	
	    });
	    
	    v.setOnMouseDragged(event->{
	    	if(xb == 0 && yb == 0) {
	    		xb = event.getSceneX();
	    		yb = event.getSceneY();
	    	}
	    	
        	double x = event.getSceneX()-xb;
    		double y = event.getSceneY()-yb;
    		
    		x = x/500;
    		y = y/500;
    		
    		
    		
    		Point3 point3 = v.getCamera().getViewCenter();
    		v.getCamera().setViewCenter(point3.x-x,point3.y+y, point3.z);
	    	event.consume();
	    	
	    	xb = event.getSceneX();
    		yb = event.getSceneY();
	    });
	    
	}
	
	
	public void AutoButton(ActionEvent e) throws InterruptedException {
		debug("Auto Button");
		
		if(graphinit.isInit) {
			APSP apsp = new APSP();
	 		apsp.init(graphinit.getGraph()); // registering apsp as a sink for the graph
	 		apsp.setDirected(true); // directed graph
	 		apsp.setWeightAttributeName("weight"); // ensure that the attribute name used is "weight"
	 
	 		apsp.compute(); // the method that actually computes shortest paths
	 		
	 		APSPInfo info = (APSPInfo) graphinit.getGraph().getNode(MenuStart.getText()).getAttribute(APSPInfo.ATTRIBUTE_NAME);		
	 		//System.out.println(info.getShortestPathTo(MenuEnd.getText()));
	 		Path path =  info.getShortestPathTo(MenuEnd.getText());
	 		
	 		if(path != null) {
	 			graphinit.printAllPaths(Integer.parseInt(currentNode), Integer.parseInt(MenuEnd.getText()));
	 			log("All Path to "+currentNode+" from "+MenuEnd.getText());
	 	 		for(List<Integer> i:graphinit.getPathList()) {
	 	 			log(i.toString());
	 	 		}
	 	 		log("Select shortest path");
	 	 		log("Auto Find Way");
	 			List<Node> list = path.getNodePath();
	 	 		for(int i=0;i<list.size()-1;i++) {
	 	 			int string1 = Integer.parseInt(list.get(i).getId());
	 	 			int string2 = Integer.parseInt(list.get(i+1).getId());
	 	 			graphinit.move(string1, string2);
	 	 			log("Current Node: "+ string1);
	 	 			log("Move Edge: "+string1 +string2);
	 	 		}
	 	 		log("Current Node: "+MenuEnd.getText());
	 	 		log("Finished Auto Find Way");
	 		}
	 		else {
	 			log("Path don't have exits");
	 		}
		}
		else {
			log("Graph not exits");
		}
	}
	
	public boolean checkPath() {
		APSP apsp = new APSP();
 		apsp.init(graphinit.getGraph()); // registering apsp as a sink for the graph
 		apsp.setDirected(true); // directed graph
 		apsp.setWeightAttributeName("weight"); // ensure that the attribute name used is "weight"
 
 		apsp.compute(); // the method that actually computes shortest paths
 		
 		APSPInfo info = (APSPInfo) graphinit.getGraph().getNode(MenuStart.getText()).getAttribute(APSPInfo.ATTRIBUTE_NAME);		
 		if(info.getShortestPathTo(MenuEnd.getText())!=null) {
 			return true;
 		}
		return false;
	}
	
	public void LogButton(ActionEvent e) {
		Window window = ((javafx.scene.Node) (e.getSource())).getScene().getWindow();
		 FileChooser fileChooser = new FileChooser();
	     fileChooser.setTitle("Save");
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		 fileChooser.getExtensionFilters().add(extFilter);
		try {
			File file = fileChooser.showSaveDialog(window);
			log("Export Log");
			graphinit.LogExport(LogTextArea.getText(), file.getPath());
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void MenuStart(ActionEvent e) {
		debug("Menu Start");
		
	}
	
	public void MenuEnd(ActionEvent e) {
		debug("Menu End");
	}
	
	public void MenuChoose(ActionEvent e) {
		debug("Menu Choose");
	}
	
	private int count = 1;
	public void log(String s) {
		LogTextArea.appendText(String.valueOf(count++)+". ");
		LogTextArea.appendText(s);
		LogTextArea.appendText("\n");
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void debug(Object s) {
		System.out.println(s);
	}

}
