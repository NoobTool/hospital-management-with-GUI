package main;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import CommonSnippets.DisplayMenu;
import CustomExceptions.InputValidation;
import CommonSnippets.CommonCodes;
import ward.Ward;
import ward.WardDetails;
import Actions.*
;
public class Manager extends Employee{
	final static int NO_OF_WARDS = 2;
	final static int MAX_SHIFTS = 2;
	
	CommonCodes c = new CommonCodes();
	static Staff<Manager> managerList = new Staff<Manager>();
	static Staff<Doctor> doctorList = new Staff<Doctor>();
	static Staff<Nurse> nurseList = new Staff<Nurse>();
	static Staff<Patient> patientList = new Staff<Patient>();
	static Ward wards[] = new Ward[NO_OF_WARDS];
	
	static ActionList a = new ActionList();
	
	static ArrayList<Long> idList = new ArrayList<Long>();
	static ArrayList<Long> availableIdList = new ArrayList<Long>();

	InputValidation i = new InputValidation();
	DisplayMenu dm = new DisplayMenu();
	
	public Manager(){
		for(int i=0;i<NO_OF_WARDS;i++)
			wards[i]= new Ward();
			
		
		idList.add((long)7730000);
		idList.add((long)6830000);
		idList.add((long)7830000);
		idList.add((long)8030000);
		managerList.addStaff(new Manager((long)7730000,"Ram",21,'M',"01:00-23:59","1234"));
		doctorList.addStaff(new Doctor((long)6830000,"Babu",16,'M',"16:00-17:00","1234"));
		Nurse nurse1= new Nurse((long)7830000,"Elisa",18,'F',"08:00-16:00","1234");
		nurse1.setShifts("14:00-22:00");
		nurseList.addStaff(nurse1);
		
		availableIdList.add((long)7830675);
		availableIdList.add((long)6830012);
	}
	
	public Manager(String msg) {}
	
	public Manager(long id, String name, double age, char gender,String shifts,String password){
		super(id,name,age,gender,shifts,password);
	}
	
	public Manager(InputValidation i) {}
	
	public long allotId(String post) {
		long id=0;
		int asciiCode = (int) post.charAt(0);
		long postId = asciiCode*100000;
		long postLastId = postId+100000-1;
		
		for(long x: availableIdList) {
			if(x>postId && x<postLastId)
				return x;
		}

		return id;
	}
	
	// Add patient to ward
	public void addWard(Patient p) {
		Ward w = new Ward();
		WardDetails wardDetails = new WardDetails();
		if(isWardFull()==false) {
			for(int i=0;i<NO_OF_WARDS;i++) {
				w=wards[i];
				wardDetails = w.addPatient(p);
				if(wardDetails.retRoomNumber()!=-1) {
					wardDetails.setWardNumber((i+1));
					p.setWard(wardDetails);
					System.out.println("Ward Set Successfully");
					System.out.println("Patient: "+p.retName()+" is resting at "
							+"ward "+(i+1)+" in room "+wardDetails.retRoomNumber()
							+" in bed "+wardDetails.retBedNumber());
					a.addAction(new Action(retId(),p.retId(),"centre admission",LocalDate.now(),LocalTime.now()));
					return;
				}
			}
		}
		
		char ans = c.inputChar(" Press 'y' to provide installation instead. ");
		if(ans=='y'){
			Nurse n = new Nurse();
			n.provideIsolation(p);
		    if(p.retWardDetails()!=null) {
			    System.out.println("Patient: "+p.retName()+" is isolated at "
				  		+"ward "+p.retWardNumber()+" in room "+wardDetails.retRoomNumber());
			    a.addAction(new Action(retId(),p.retId(),"centre isolation",LocalDate.now(),LocalTime.now()));
			    return;
		    }
		}
		
		System.out.println("Sorry, no space for you in the care centre! ");
		return;
	}
	
	public void addPeople(String post){
		
		long id;
		double age;
		String name,shifts="",password="";
		char gender;
		
		id = allotId(post);
		
		name = c.inputString("Enter the name of the "+post.toLowerCase()+"! ");
		name = name.strip();
		name = i.validateName(name);
		
		age = i.validateAge(c.inputDouble("Enter the age of "
		+name));
		
		gender = i.validateGender(c.inputChar("Enter the gender of the employee "+name));
		
		if(post!="Patient" && post!="Nurse")
		shifts = i.validateShifts(c.inputString("Enter the shift timings in the format "
				+ "XX:XX-YY:YY"), post.toLowerCase());
		
		if(post!="Patient" && post!="Nurse")
		password = c.inputString("Enter your password");
		
		if (post=="Manager") {
			
			if(id==0) {
				Manager m =  new Manager(idList.get(0)+1, name, age, gender, shifts, password);
				idList.set(0,idList.get(0)+1);
				managerList.addStaff(m, retId(), m.retId());				
			}
			
			else {
				Manager m =  new Manager(id, name, age, gender, shifts, password);
				availableIdList.remove(availableIdList.indexOf(id));
				managerList.addStaff(m, retId(), m.retId());
			}			
		}
		
		else if (post=="Doctor") {
			if(id==0) {
				Doctor d =  new Doctor(idList.get(1)+1, name, age, gender, shifts, password);
				idList.set(1,idList.get(1)+1);
				doctorList.addStaff(d, retId(), d.retId());
			}
			
			else {
				Doctor d =  new Doctor(id, name, age, gender, shifts, password);
				availableIdList.remove(availableIdList.indexOf(id));
				doctorList.addStaff(d, retId(), d.retId());
			}
		}
		
		else if(post == "Nurse") {
			if(id==0) {
				Nurse n =  new Nurse(idList.get(2)+1, name, age, gender, "08:00-16:00", password);
				n.setShifts("14:00-22:00");
				idList.set(2,idList.get(2)+1);
				nurseList.addStaff(n, retId(), n.retId());
			}
			
			else {
				Nurse n =  new Nurse(id, name, age, gender, "08:00-16:00", password);
				n.setShifts("14:00-22:00");
				availableIdList.remove(availableIdList.indexOf(id));
				nurseList.addStaff(n, retId(), n.retId());
			}
		}
		
		else {
			if(id==0) {
				Patient p =  new Patient(idList.get(3)+1, name, age, gender);
				idList.set(2,idList.get(2)+1);
				patientList.addStaff(p, retId(), p.retId());
				addWard(p);
			}
			
			else {
				Patient p =  new Patient(id, name, age, gender);
				availableIdList.remove(availableIdList.indexOf(id));
				patientList.addStaff(p, retId(), p.retId());
				addWard(p);
			}
		}
	}
	
	
	public void addStaff(Manager m){
			managerList.addStaff(m,retId(),m.retId());
		}
	
	
	// Changing details of the employee found
		public void changeDetails(Employee e, String post) {
			int choice=0;
			do {
				dm.modificationOptions();
				choice=c.inputInt("Enter your choice!");
				
				switch(choice) {
				
				case 1: String name = c.inputString("Enter the new name. ");
						name = name.strip();
						name = i.validateName(name);
						e.setName(name);
						break;
				
				case 2: double age = i.validateAge(c.inputDouble("Enter the new value for age. "));
						e.setAge(age);
						break;
				
				case 3: char gender = i.validateGender(c.inputChar("Enter new value for gender. "));
						e.setGender(gender);
						break;
						
				case 4: changeShifts(e, post);
						break;
				
				case 5: String password = c.inputString("Enter new password. ");
						e.setPassword(password);
						break;
				
				case 6: choice=6;
						System.out.println("Exiting. ");
						break;
				
				default : System.out.println("Wrong choice! ");
				}
				
			}while(choice!=6);
		}
	
	// Displaying the current employees present
	
	public void managerDisplays() {
		int choice;
		dm.managerMenuEmployeeSelection();
		choice = c.inputInt("");
		
		switch(choice) {
			case 1: displayManagers();break;
			case 2: displayDoctors();break;
			case 3: displayNurses();break;
			case 4: displayPatients();break;
			default: System.out.println("Wrong choice");break;
		}
	}

	
	// Displaying managers
	public long displayManagers() {
		for(Manager m: managerList.retStaff())
			System.out.print("\nId :"+m.retId()+"\n"+
					"Name: "+m.retName()+"\n"+
						"Age: "+(int)m.retAge()+"\n"+
							"Gender: "+m.retGender()+"\n"+
								"Shift: "+m.retShifts()+"\n\n");
		
		System.out.println("\n\n");
		
		return managerList.retStaff().get(managerList.retSize()-1).retId();
	}
	
	// Displaying doctors
	public long displayDoctors() {
		for (Doctor d: doctorList.retStaff())
			System.out.print("\nId :"+d.retId()+"\n"+
								"Name: "+d.retName()+"\n"+
									"Age: "+(int)d.retAge()+"\n"+
										"Gender: "+d.retGender()+"\n"+
											"Shift: "+d.retShifts()+"\n\n");
		
		System.out.println("\n\n");
		
		return doctorList.retStaff().get(doctorList.retSize()-1).retId();		
	}
	
	// Displaying Nurses
	public long displayNurses() {
		for(Nurse n: nurseList.retStaff())
			System.out.print("\nId :"+n.retId()+"\n"+
					"Name: "+n.retName()+"\n"+
						"Age: "+(int)n.retAge()+"\n"+
							"Gender: "+n.retGender()+"\n"+
								"Shift: "+n.retShifts()+"\n\n");
		
		System.out.println("\n\n");
		
		return nurseList.retStaff().get(nurseList.retSize()-1).retId();
	}
	
	// Displaying Patients
	public long displayPatients() {
		for(Patient p: patientList.retStaff()) {
			System.out.print("\nId :"+p.retId()+"\n"+
					"Name: "+p.retName()+"\n"+
						"Age: "+(int)p.retAge()+"\n"+
							"Gender: "+p.retGender()+"\n");
			p.printPrescription();
			p.printWardDetails();
		}
			
		
		System.out.println("\n\n");
		
		return patientList.retStaff().get(patientList.retSize()-1).retId();
	}
	
	// Print all actions
	
	public void printActionList() {
		a.printActionList();
	}
	
	// To check if ward is full or not
	public boolean isWardFull() {
		Ward w = new Ward();
		for(int i=0;i<NO_OF_WARDS;i++) {
			if(w.isFull()==false)
				return false;
		}
		return true;
	}
	
	
	// Setter Functions
	public void addAction(Action action) {
		a.addAction(action);
	}
	
	
	// Getter functions
	
	public long retId() {
		return super.retId();
	}
	
	public String retName() {
		return super.retName();
	}
	
	public double retAge() {
		return super.retAge();
	}
	
	public char retGender() {
		return super.retGender();
	}
	
	public ArrayList<String> retShifts() {
		return super.retShifts();
	}
	
	public String retPass() {
		return super.retPass();
	}
	
	public Ward[] retWardList() {
		return wards;
	}
	
	public int retWards() {
		return NO_OF_WARDS;
	}
	
	public int retMaxShifts() {
		return MAX_SHIFTS;
	}
	
	public ArrayList<Manager> retManagerList(){
		return managerList.retStaff();
	}
	public ArrayList<Doctor> retDoctorList(){
		return doctorList.retStaff();
	}
	public ArrayList<Nurse> retNurseList(){
		return nurseList.retStaff();
	}
	public ArrayList<Patient> retPatientList(){
		return patientList.retStaff();
	}

};
