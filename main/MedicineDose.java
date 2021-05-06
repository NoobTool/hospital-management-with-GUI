package main;
import java.util.ArrayList;
import java.time.LocalTime;
import CommonSnippets.*;

public class MedicineDose {
	private String medicineName;
	private int dose;
	private ArrayList<LocalTime> times = new ArrayList<LocalTime>();
	
	public MedicineDose() {}
	
	public MedicineDose addMedicine() {
		CommonCodes c = new CommonCodes();
		String name = c.inputString("Enter the name of the medicine. ");
		this.medicineName = name;
		int doseNumber = c.inputInt("Enter the number of doses. ");
		this.dose = doseNumber;
		for(int i=0;i<doseNumber;i++) {
			times.add(c.inputTime("Enter the time for dose "+(i+1)));
		}
		return this;
	}
	
	public void printMedicineDose() {
		System.out.println("Medicine Name: "+this.retName()+"\n");
		
		for(int i=0;i<this.retDose();i++) {
			System.out.println("Dose "+(i+1)+": "+this.retTimes().get(i));
		}
			
	}
	
	
	public void printMedicineDose(int dose) {
		System.out.println("Medicine Name: "+this.retName()+"\n");
		System.out.println("Dose: "+this.retTimes().get(dose));			
	}
	
	
	
	public void changeTimes() {
		DisplayMenu dm = new DisplayMenu();
		CommonCodes c = new CommonCodes();
		int choice=0;
		do {
			dm.doctorDoseMenu();
			choice = c.inputInt("");
			
			switch(choice) {
				case 1: changeDoseTime();
						sortTimes();
						break;
						
				case 2: break;
						
			}
		}while(choice!=4);
	}
	
	public void sortTimes()
	{
	    int i, j,n=times.size();
	    LocalTime key;
	    for (i=1;i<n;i++)
	    {
	        key = times.get(i);
	        j = i-1;

	        while (j >= 0 && times.get(j).isAfter(key))
	        {
	            times.set(j+1, times.get(j));
	            j = j - 1;
	        }
	        times.set(j+1,key);
	    }
	}
	
	
	public void changeDoseTime() {
		int choice=0,last_iteration=0;
		CommonCodes c = new CommonCodes();
		do{
			for(int i=0;i<times.size();i++) {
				System.out.println((i+1)+". "+times.get(i));
				last_iteration=i;
			}
				
			choice = c.inputInt(last_iteration+". Exit. ");
			if(choice<last_iteration) {
				times.set(choice-1, c.inputTime("Enter the new time. "));
				sortTimes();
			}
			else if(choice==last_iteration){
				System.out.println("Exiting... ");
			}
			
			else {
				System.out.println("Wrong choice! ");
			}
		}while(choice!=last_iteration);
		
		
		
	}
	
	public void setName(String medicineName) {
		this.medicineName = medicineName;
	}
	
	public void setDose(int n) {
		dose = n;
	}
	
	public String retName() {
		return this.medicineName;
	}
	
	public int retDose() {
		return this.dose;
	}
	
	public ArrayList<LocalTime> retTimes(){
		return this.times;
	}
}
