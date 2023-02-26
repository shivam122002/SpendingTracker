package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DbConnect {
   public static Connection con;
   public static Statement stmt;
   
   static {
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
		   con=DriverManager.getConnection("jdbc:mysql://localhost:3306/spendingdb"+"?useSSL=false","root","root");
			stmt=con.createStatement();
		   
	   }catch(Exception e){
		   JOptionPane.showMessageDialog(null,e);
	   }
   }
}
