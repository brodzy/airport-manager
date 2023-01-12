package application;
	

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	//Instance Variables
	protected Label lblDist;
	protected Label lblAirports;
	protected TextArea dispMessage;
	protected TextField dispDistance;
	protected Button btnAirportI;
	protected Button btnDistanceB;
	protected Button btnAirportC;
	protected Button btnAirportW;
	protected Button btnAirportS;
	protected RadioButton rbCity;
	protected RadioButton rbState; 
	protected ListView<Airport> dispAirports;
	protected ToggleGroup tGrpStyleChoice = new ToggleGroup();
	
	//Getting airports from text file into list
	Map<String, Airport> map = AirportLoader.getAirportMap(new File ("src/application/airportsMedium.txt"));
	AirportManager am = new AirportManager(map);
	List<Airport> list = am.getAirports();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Create controls
			
			//Buttons Initialized with event handlers 
			btnAirportI = new Button("Airport Info");
			btnAirportI.setOnAction(new AirportInfo());
			btnDistanceB = new Button("Distance Between");
			btnDistanceB.setOnAction(new DistanceBetween());
			btnAirportC = new Button("Airport Closest To");
			btnAirportC.setOnAction(new ClosestTo());
			btnAirportW = new Button("Airport Within");
			btnAirportW.setOnAction(new WithinDistance());
			btnAirportS = new Button("Airports Sorted");
			btnAirportS.setOnAction(new Sort());
			
			//City or State Button
			rbCity = new RadioButton("City");
			rbCity.setToggleGroup(tGrpStyleChoice);
			rbCity.setSelected(true);
			rbState = new RadioButton("State");
			rbState.setToggleGroup(tGrpStyleChoice);
			
			//First HBox
			HBox hBoxSort = new HBox();
			hBoxSort.setSpacing(7);
			hBoxSort.getChildren().addAll(btnAirportS, rbCity, rbState);

			//Display Distance Button
			lblDist = new Label("Distance");
			dispDistance = new TextField();
			
			//Second HBox
			HBox hBoxDistance = new HBox();
			hBoxDistance.setSpacing(10);		
			hBoxDistance.getChildren().addAll(lblDist, dispDistance);
			
			//First VBox (Left Top Half GUI)
			VBox vBoxTop = new VBox();
			vBoxTop.setSpacing(30);
			vBoxTop.getChildren().addAll(btnAirportI, btnDistanceB, btnAirportC, btnAirportW, hBoxSort, hBoxDistance);
			
		
			//Text Field
			
			//Airport List, adds airports to ListView
			dispAirports = new ListView<Airport>();
			dispAirports.setEditable(false);
			dispAirports.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			dispAirports.setPrefHeight(300);
			dispAirports.setPrefWidth(260);
			dispAirports.getItems().addAll(list);
			
			//Second VBox Airports Field
			lblAirports = new Label("Airports");
			VBox vBoxAirports = new VBox();
			vBoxAirports.setSpacing(10);
			vBoxAirports.getChildren().addAll(lblAirports, dispAirports);
			
			HBox hBox1 = new HBox();
			hBox1.getChildren().addAll(vBoxTop, vBoxAirports);
			hBox1.getStyleClass().add("h_or_v_box");
			
			//Display Info
			dispMessage = new TextArea();
			dispMessage.setEditable(false);
			dispMessage.setPrefHeight(200);
			dispMessage.setPrefWidth(490);

			// Create container for controls
			GridPane root = new GridPane();

			// Add controls to container **************
			root.add(hBox1, 0, 0);
			root.add(dispMessage, 0, 1);

			// Set the size of window (pixels)
			Scene scene = new Scene(root,515,600);

			// Set the title for the window
			primaryStage.setTitle("Airport GUI");

			// Code to display the Gui
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//EventHandlers
	private class AirportInfo implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {//Loop through selected airports and display toString
			String message = "";
			List<Airport> airports = dispAirports.getSelectionModel().getSelectedItems();
			if(airports.size() < 1) {
				message = "Select an airport or airports!";
			}
			else {
				message += "-----This is the airport information below-----\n";
				for(Airport a : airports) {
					message += a.toString() + "\n";
				}
			}
			dispMessage.setText(message);
		}
	}
	
	private class DistanceBetween implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {//Gets distance between 2 airports
			String message = "";
			List<Airport> airports = dispAirports.getSelectionModel().getSelectedItems();
			if(airports.size() == 2) {
				message += "-----This is the distance between the airports-----\n";
				message += am.getDistanceBetween(airports.get(0), airports.get(1));
			}
			else if(airports.size() < 2) {
				message = "Select two airports!";
			}
			else {
				message = "Too many airports selected!";
			}
			dispMessage.setText(message);
			
		}
	}
	
	private class ClosestTo implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {//Gets airports closest to selected airport
			String message = "";
			Airport a = dispAirports.getSelectionModel().getSelectedItem();
			if (a == null) {
				message = "Select an airport!";
			}
			else {
			message += "-----This is the closest airport-----\n";
			message += am.getAirportClosestTo(a.getCode());
			}
			dispMessage.setText(message);
		}
	}
	
	private class WithinDistance implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {//Gets airport closest to selected airport
			String message = "";
			Airport a = dispAirports.getSelectionModel().getSelectedItem();
			if (a == null) {
				message = "Select an airport!";
			}
			else if (dispDistance.getText().equals("")) {
				message = "Enter a distance!";
			}
			else {
				try {
					double dist = Double.parseDouble(dispDistance.getText());
					List<Airport> airports = am.getAirportsWithin(a.getCode(), dist);
					message += "-----These are the closest airports within this distance-----\n";
					for (Airport aa : airports) {
						message += aa.toString() + "\n";
					}
			    } 
				catch (NumberFormatException nfe) {
			    	message = "Enter a valid number!";
			    }
			}
			dispMessage.setText(message);
		}
}
	
	private class Sort implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {//Sorts all airports based on city or state
			String message = "";
			List<Airport> sort = new ArrayList<>(map.values());
			if(rbCity.isSelected()) {
				am.getAirportsSortedByH("city", sort);
				message += "-----Airports sorted on city-----\n";
				for(Airport a : sort) {
					message += a.toString() + "\n";
				}
			}
			else if (rbState.isSelected()){
				am.getAirportsSortedByH("state", sort);
				message += "-----Airports sorted on state-----\n";
				for(Airport a : sort) {
					message += a.toString() + "\n";
				}
			}
			dispMessage.setText(message);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
