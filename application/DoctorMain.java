package application;
import java.time.LocalTime;
import java.time.format.*;
import prescription.MedicineDose;
import CustomExceptions.InputValidation;
import main.Doctor;
import main.Manager;
import main.Patient;
import java.util.*;
import javafx.util.Pair;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;

public class DoctorMain {
	
	CommonOperations co = new CommonOperations();
	Manager m = new Manager("Object to return patient list");
	InputValidation i = new InputValidation();
	ArrayList<MedicineDose> meds = new ArrayList<MedicineDose>();
	
	public BorderPane addPrescription(Doctor d, BorderPane bp) {
		
		// Big Wrapper
		BorderPane wrapperPane = new BorderPane();
		wrapperPane.setPadding(new Insets(20,0,0,20));
		
		// Layouts
		GridPane searchGrid = new GridPane();
		Button idButton = new Button("Search by ID");
		Button nameButton = new Button("Search by Name");
		TextField idField = new TextField();
		TextField nameField = new TextField();
		HBox buttonHolder = co.addButtonHolder(bp);
		
		//Adding nodes in grid pane
		searchGrid.add(idButton, 1, 0);
		searchGrid.add(idField, 2, 0);
		searchGrid.add(nameButton, 1, 1);
		searchGrid.add(nameField, 2, 1);
		searchGrid.add(buttonHolder, 2, 2);
		
		// GridPane formatting
		searchGrid.setHgap(20);
		searchGrid.setVgap(20);
		idField.setVisible(false);
		nameField.setVisible(false);
		searchGrid.setPadding(new Insets(20,0,0,0));
		Label errorMsg = new Label();
		
		// errorMsg editing
		errorMsg.setFont(new Font("cambria",16));
		errorMsg.setTextFill(Color.RED);
		
		// Defining actions for the buttons
		
		idButton.setOnAction(e->{
			idField.setVisible(true);
			nameField.setVisible(false);
			nameField.setText("");
		});
		
		nameButton.setOnAction(e->{
			idField.setVisible(false);
			idField.setText("");
			nameField.setVisible(true);
		});
		
		wrapperPane.setTop(searchGrid);
		
		((Button)buttonHolder.getChildren().get(0)).setOnAction(e->{
			
			if (!idField.isVisible() && !nameField.isVisible())
				errorMsg.setText("Please select a search option.");
			
			// Searching by ID
			else if(idField.isVisible()) {
				String id = idField.getText();
				try {
					if(!co.checkBlankFields(idField.getText()))
						errorMsg.setText("Please fill the ID field.");
					else {
						Patient p = d.doctorSearch(m.retPatientList(),Long.parseLong(id)
								,"");
						
						if(p.retName()==null)
							errorMsg.setText("Patient not found ");
						else {
							
							// Layout Elements
							Button addMedicine = new Button("Add Medicine");
							ScrollPane sp = new ScrollPane();
							VBox prescriptionBox = new VBox(5);
							HBox prescriptionButtonHolder = co.addButtonHolder(bp);
							
							// Adding elements in prescription box and formatting
							errorMsg.setText("");
							prescriptionBox.getChildren().addAll(prescriptionButtonHolder,errorMsg);
							prescriptionBox.setPadding(new Insets(10,0,0,0));
							
							// Assigning elements to corresponding elements
							wrapperPane.setTop(null);
							wrapperPane.setLeft(addMedicine);
							wrapperPane.setCenter(sp);
							BorderPane.setMargin(sp, new Insets(0,0,0,20));
							wrapperPane.setBottom(prescriptionBox);
							BorderPane.setAlignment(prescriptionBox, Pos.CENTER);
							
							
							//Action Events
							addMedicine.setOnAction(e2->{
								addMedicine(d,p,sp);
							});
							
							((Button)prescriptionButtonHolder.getChildren().get(0)).setOnAction(e3->{
								d.addPrescription(p, meds);
							});
						}
					}
				}catch(NumberFormatException exception) {
					errorMsg.setText("The ID must be in numberic characters!");
				}
			}
		});
		
		wrapperPane.setBottom(errorMsg);		
		return wrapperPane;
	}
	
	public void addMedicine(Doctor d, Patient p,ScrollPane sp) {
		Stage medicineStage = new Stage();
		FlowPane flowPane = new FlowPane(Orientation.VERTICAL,5,5);
		HBox buttonsBox = new HBox(10);
		
		// Labels
		Label errorMsg = new Label("\n\n\n");
		errorMsg.setTextFill(Color.RED);
		
		// Text fields
		TextField medicineNameField = new TextField();
		TextField medicineDoseField = new TextField();
		TextField medicineTimeField = new TextField();
		
		// Buttons
		Button submitButton = new Button("Submit");
		Button cancelButton = new Button("Cancel");
		
		// Adding buttons in buttonsBox
		buttonsBox.getChildren().addAll(submitButton,cancelButton);
		
		
		// FlowPane Config
		flowPane.getChildren().addAll(errorMsg,new Label("Medicine Name:"), 
				medicineNameField, new Label("Medicine Doses:"),medicineDoseField, 
				new Label("Dose Timings:"),medicineTimeField,new Label("\n\n"), 
				buttonsBox);
		flowPane.setPadding(new Insets(10,0,0,50));
		
		submitButton.setOnAction(e3->{
			errorMsg.setTextFill(Color.RED);
			
			Pair<Boolean,String> medNamePair = i.validateName(
					medicineNameField.getText(),true);
			String medTimesText = medicineTimeField.getText();
			if(!co.checkBlankFields(medTimesText))
				errorMsg.setText("All fields are manadatory!\n\n\n");
			else if (medNamePair.getKey()==false)
				errorMsg.setText(medNamePair.getValue());
			else {
				
				try {
					int medDose = Integer.parseInt(
							medicineDoseField.getText());
					
					String[] medTimesString = medTimesText.split(",");
					
					if(medTimesString.length==medDose) {
						ArrayList<LocalTime> medTimes = new ArrayList<LocalTime>();
						for(int i=0;i<medTimesString.length;i++) {
							medTimes.add(LocalTime.parse(medTimesString[i].strip()));
						}

						meds.add(new MedicineDose(medNamePair.getValue(), medDose, medTimes));
						displayMedicineBlock(sp);
						medicineStage.close();
					}
					
					else
						errorMsg.setText("Dose timings are less than"
								+ " number of doses.\n\n\n");
				}catch(NumberFormatException exception) {
					errorMsg.setText("Number of doses is incorrect.\n\n\n");
				}catch(DateTimeParseException exception) {
					errorMsg.setText("Please enter time in format HH:MM!\n\n\n");
				}
			}
		});
		
		cancelButton.setOnAction(e->{
			medicineStage.close();
		});
		
		medicineStage.setScene(new Scene(flowPane,300,500));
		medicineStage.show();
	}
		
	
	public void displayMedicineBlock(ScrollPane sp) {
		VBox medicineBox = new VBox(10);
		
		for(MedicineDose med: meds) {
			String label = "Medicine Name:"+med.retName()+"\nNumber of Doses: "
					+med.retDose();
			String medTimings = "";
			for(LocalTime time: med.retTimes())
				medTimings=medTimings+" "+time.toString();
			medicineBox.getChildren().add(new Label(label+"\nDose Timings: "+medTimings));
		}
		
		sp.setContent(medicineBox);
	}
	
	
}
