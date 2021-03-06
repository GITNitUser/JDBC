package sa.nt.se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AgeCaluculatorOracle {
	private static final String  AGE_CALC_QUERY="SELECT (SYSDATE-DOB)/365 AS AGE FROM PERSON_DATES WHERE PID=?";
	public static void main(String[] args) {
		Scanner sc=null; 
		int pid=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Person Id::");
				pid=sc.nextInt();
			}
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","scott","tiger");
			//create PrepardSTatement object
			if(con!=null)
				ps=con.prepareStatement(AGE_CALC_QUERY);
			//set values to query parameters
			if(ps!=null)
				ps.setInt(1,pid);
			//execute the query
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ResultSet
			if(rs!=null){
				if(rs.next())
				   //System.out.println("age :::"+rs.getFloat(1));
					//System.out.println("age :::"+rs.getFloat("(SYSDATE-DOB)/365"));
					System.out.println("age :::"+rs.getFloat("AGE"));
				else
					System.out.println("No Person found");
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//close jdbc objs
			try{
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			try{
				if(con!=null)
					con.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}//finally
	}//main method
}//class
