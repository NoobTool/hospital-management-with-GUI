package CommonSnippets;
import java.util.ArrayList;

public class DisplayMenu {
	
	public ArrayList<String> managerMenu() {
		ArrayList<String> managerMenu = new ArrayList<String>();
		managerMenu.add("Admit a patient.");
		managerMenu.add("Hire new employees.");
		managerMenu.add("Modify staff details.");
		managerMenu.add("Display staff members.");
		managerMenu.add("Display the actions performed. ");
		return managerMenu;
	}
	
	public void managerMenuEmployeeSelection() {
		System.out.println("1. Manager.");
		System.out.println("2. Doctor.");
		System.out.println("3. Nurse.");
	}
	
	public void searchOptions() {
		System.out.println("1. Search by id");
		System.out.println("2. Search by name");
		System.out.println("3. Exit");
		System.out.println("Enter your choice! ");
	}
	
	public void modificationOptions() {
		System.out.println("1. Name ");
		System.out.println("2. Age ");
		System.out.println("3. Gender");
		System.out.println("4. Shifts");
		System.out.println("5. Password");
	}
	
	public ArrayList<String > doctorMenu() {
		ArrayList<String> doctorMenu = new ArrayList<String>();
		doctorMenu.add("1. Add a prescription.");
		doctorMenu.add("2. Suggest Changes");
		doctorMenu.add("3. Display a patient's details. ");
		doctorMenu.add("4. Update prescription. ");
		return doctorMenu;
	}
	
	public void doctorAddMedicines() {
		System.out.println("1. Add medicine.");
		System.out.println("2. Exit");
		System.out.println("Enter your choice! ");
	}
	
	public void doctorUpdatePrescription() {
		System.out.println("1. Add new medicines.");
		System.out.println("2. Exit");
		System.out.println("Enter your choice! ");
	}
	
	public void doctorDoseMenu() {
		System.out.println("1. Change name ");
		System.out.println("2. Change dose. ");
		System.out.println("3. Change timings. ");
		System.out.println("4. Exit. ");
		System.out.println("Enter your choice. ");
	}
	
	// Nurse 
	
	public ArrayList<String> nurseMenu() {
		ArrayList<String> nurseMenu = new ArrayList<String>();
		nurseMenu.add("1. Administer a medicine. ");
		nurseMenu.add("2. Change bed automatically. ");
		nurseMenu.add("3. Change bed manually. ");
		nurseMenu.add("4. Exit. ");
		nurseMenu.add("Enter your choice. ");
		return nurseMenu;
	}
	
	
	// Medicine Dose
	
	public void timesMenu() {
		System.out.println("1. Change time of dose. ");
		System.out.println("2. Create new dose schedule. ");
		System.out.println("3. Exit. ");
		System.out.println("Enter your choice. ");
	}
	
	
	
}
