package main;
import CommonSnippets.CommonCodes;

public class ResidentCare {
	
	public static void main(String args[]) {
		
		// Variable Initialization
		int choice=0;
		CommonCodes c = new CommonCodes();
		Manager man = new Manager();
		Login login = new Login();
		
		// Objects for displaying options
		
		do {
			System.out.println("1. Login as Manager");
			System.out.println("2. Login as Doctor");
			System.out.println("3. Login as Nurse");
			choice=c.inputInt("Enter your choice!");
			switch(choice) {
				case 1: 
						Manager m = login.managerLogin();
						if(m.retName()!=null)
							m.managerSequence();
						break;

				case 2:
						Doctor d = login.doctorLogin();
						if(d.retName()!=null)
							d.doctorFunctions();						
						break;
				case 3:
						Nurse n = login.nurseLogin();
						if(n.retName()!=null)
							System.out.println("Nurse logged in! ");
						break;
						
				case 4: System.out.println("Exiting...");
						break;
				default: System.out.println("Dobara enter krle bc :| ");
						
			}
		}while(choice!=4);
		
	}
}
