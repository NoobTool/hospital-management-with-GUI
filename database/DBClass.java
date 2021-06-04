package database;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Actions.*;

public class DBClass {
	
	static Connection connection;
	
	static void print(Object o) {
		System.out.println(o);
	}
	
	public DBClass() {}
	
	public void createTables() {
		try {
			
			ArrayList<String> posts = new ArrayList<String>();
			posts.add("MANAGER");
			posts.add("DOCTOR");
			posts.add("NURSE");
			
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/java2", "SA", "");
			Statement statement = connection.createStatement();
			
			String stmt=""; 
			
			for(String s: posts) {
				stmt = "CREATE TABLE IF NOT EXISTS "+s+"("
						+ "ID INT,"
						+ "NAME VARCHAR(50),"
						+ "AGE DOUBLE,"
						+ "GENDER CHAR,"
						+ "SHIFTS VARCHAR(12),"
						+ "PASSWORD VARCHAR(50));";
				statement.executeQuery(stmt);
			}
			
			
			stmt = "CREATE TABLE IF NOT EXISTS ACTION("
					+ "PERFORMER INT,"
					+ "RECEIVER INT,"
					+ "NAME VARCHAR(30),"
					+ "DATE VARCHAR(15),"
					+ "TIME VARCHAR(10));";
			statement.executeQuery(stmt);
			
			connection.commit();
			connection.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void addStaff(long id, String name, double age, char gender, String shifts, String password, String post) {
		try {
			
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/java2", "SA", "");
			Statement statement = connection.createStatement();
			
			String stmt = "INSERT INTO "+post.toUpperCase()+" VALUES("+id+",'"+name+"',"+age+",'"+gender+"',"
					+ "'"+shifts+"','"+password+"')";
			
			statement.executeQuery(stmt);
			
			connection.commit();
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}		
	}
	
	public void addAction(Action a) {
		try {
			
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/java2", "SA", "");
			Statement statement = connection.createStatement();
			
			String stmt = "INSERT INTO ACTION VALUES("+a.retPerformerId()+",'"+a.retReceiverId()+"','"+a.retActionName()+"','"
			+a.retDate()+"','"+a.retTime()+"')";
			statement.executeQuery(stmt);
			
			connection.commit();
			connection.close();
		}catch(Exception exception) {
			System.out.println(exception);
		}
		
	}
	
	public ArrayList<Action> retActions() {
		
		try {
			
			ArrayList<Action> actions = new ArrayList<Action>();
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/java2", "SA", "");
			Statement statement = connection.createStatement();
			
			String stmt = "SELECT * FROM ACTION";
			
			ResultSet result = statement.executeQuery(stmt);
			
			while(result.next())
				actions.add(new Action(result.getLong("PERFORMER"),result.getLong("RECEIVER"),
						result.getString("NAME"),LocalDate.parse(result.getString("DATE"))
						,LocalTime.parse(result.getString("TIME"))));
				
			
			connection.commit();
			connection.close();
			
			return actions;
		}catch(Exception exception) {
			System.out.println(exception);
			return new ArrayList<Action>();
		}
		
	}
	
	public void printRows(String tableName) {
		try {
	
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/java2", "SA", "");
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery("SELECT * FROM "+tableName.toUpperCase());
			
			while(result.next())
				print(result.getString("NAME"));
			
			connection.commit();
			connection.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void dropTables(String...args) {
		try {
			
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/java2", "SA", "");
			Statement statement = connection.createStatement();
			
			for(String s: args) {
				String stmt = "DROP TABLE "+s.toUpperCase();
				statement.executeUpdate(stmt);
			}
			
			connection.commit();
			connection.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	} 
	
//	public static void main(String args[]) {
//		DBClass db = new DBClass();
//		db.dropTables("Manager","Nurse","Doctor","Patient","medicine");
//		db.createTables();
		
//		db.addStaff(7730001,"Pappu", 23, 'M', "09:00-12:00", "1234","Nurse");
//		db.printRows("Manager");
//	}	
}
