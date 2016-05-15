/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Rish
 */
public class Connect {
    
    public static Connection connectToDb(){
      
        Connection con= null;
      

      try{
    
         con = DriverManager.getConnection
         ("jdbc:mysql://localhost:3306/robs_final","root",
         "Rish@123"); 
         
         
      }
      catch(SQLException e){
           JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
      }
       
     return con;
    } 
     
    
}
