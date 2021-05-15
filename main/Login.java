package main;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import CommonSnippets.CommonCodes;
import CustomExceptions.*;

public class Login {
	
	CommonCodes c = new CommonCodes();
	Manager manager = new Manager("Empty Object");
	
	public Login() {}
	
	// To verify if an employee is not logging in 
	// after taking 1 shift
	public boolean verifySecondLogin(Employee e) {
		Manager m = new Manager("Object to return max shifts");
		ArrayList<String> shiftTimings = e.retShifts();
		LocalTime shiftStart,shiftEnd;
		LocalTime currentTime = LocalTime.now();
		currentTime = LocalTime.parse(currentTime.format
				(DateTimeFormatter.ofPattern("HH:mm")));
		
		for(int i=0;i<e.retShifts().size();i++) {
			shiftStart = LocalTime.parse(shiftTimings.get(i).split("-")[0]);
			shiftEnd = LocalTime.parse(shiftTimings.get(i).split("-")[1]);
			
			// Checking if the login time is within shift timings or not
			if(currentTime==shiftStart || currentTime==shiftEnd || 
					(currentTime.isAfter(shiftStart) && 
							currentTime.isBefore(shiftEnd))) {
				
				
				/* There could be a scenario where a nurse could login at
				 * 14.01 hours and thus be lying in both the shifts and 
				 * as the timings are sorted, the shift recognized by the 
				 * if condition would be shift 1 which would finish in around
				 * 2 hours disobeying business rules of the assignment in which
				 * case the second shift should be considered.*/
				
				// Checking if the login and end shift time are not close
				if(currentTime.until(shiftEnd, ChronoUnit.HOURS)>7) {
					
					// Below conditions are to ensure only 1 shift is 
					// taken on a day
					
					if(e.retLastShiftDate()==null) {
						e.setLastShiftDate(LocalDate.now());
						e.setChosenShiftTime(shiftTimings.get(i));
						System.out.println();
						return true;
					}
					
					else if(e.retLastShiftDate()!=LocalDate.now()) {
						e.setLastShiftDate(LocalDate.now());
						e.setChosenShiftTime(shiftTimings.get(i));
						return true;
					}
					
					else if(e.retLastShiftDate()==LocalDate.now()) {
						if(e.retChosenShiftTime()==shiftTimings.get(i))
							return true;
						else
							return false;
					}
				}
				else
					continue;
			}
		}
		return false;
	}
	
	public Manager managerLogin() {
		LocalTime currentTime = LocalTime.now();
		long id = c.inputLong("Enter your id. ");
		String password;
		for(Manager m: manager.retManagerList()) {
			if(m.retId()==id) {
				password = c.inputString("Enter password. ");
					try {
						if(verifySecondLogin((Employee) m)) {
							if(password.matches(m.retPass()))
								return m;
							else {
								System.out.println("Id or password is wrong, try again! ");
								return new Manager("Empty Object");
							}
						}
						else
							throw new RestrictedTimingException();
					}catch(RestrictedTimingException exception) {
						System.out.println("Not rostered for this shift. ");
						return new Manager("Exceptionally Correct");
					}
			}
		}
		System.out.println("Manager not found! ");
		return new Manager("Empty Object");
	}
	
	public Doctor doctorLogin() {
		LocalTime currentTime = LocalTime.now();
		long id = c.inputLong("Enter your id. ");
		String password;
		for(Doctor d: manager.retDoctorList()) {
			if(d.retId()==id) {
				password = c.inputString("Enter password. ");
					try {
						if(verifySecondLogin((Employee) d)) {
							if(password.matches(d.retPass()))
								return d;
							else {
								System.out.println("Id or password is wrong, try again! ");
								return new Doctor();
							}
						}
						else
							throw new RestrictedTimingException();
					}catch(RestrictedTimingException exception) {
						System.out.println("Not rostered for this shift. ");
						return new Doctor();
					}
			}
		}
		System.out.println("Manager not found! ");
		return new Doctor();
	}
	
	public Nurse nurseLogin() {
		LocalTime currentTime = LocalTime.now();
		long id = c.inputLong("Enter your id. ");
		String password;
		for(Nurse n: manager.retNurseList()) {
			if(n.retId()==id) {
				password = c.inputString("Enter password. ");
					try {
						if(verifySecondLogin((Employee) n)) {
							if(password.matches(n.retPass()))
								return n;
							else {
								System.out.println("Id or password is wrong, try again! ");
								return new Nurse();
							}
						}
						else
							throw new RestrictedTimingException();
					}catch(RestrictedTimingException exception) {
						System.out.println("Not rostered for this shift. ");
						return new Nurse();
					}
			}
		}
		System.out.println("Manager not found! ");
		return new Nurse();
	}
	
}
	
