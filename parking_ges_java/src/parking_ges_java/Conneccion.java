package parking_ges_java;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conneccion {
	Connection cn;
	public Conneccion(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			cn=DriverManager.getConnection("jdbc:mysql://localhost/parking_db","root","");
	    System.out.println("Connection avec sucsses!");
		}
		catch(Exception e){
		    System.out.println("Connection failed: " + e.getMessage());
		}
		
	}
    public Connection laConnection(){
    	return cn;
    }
}

