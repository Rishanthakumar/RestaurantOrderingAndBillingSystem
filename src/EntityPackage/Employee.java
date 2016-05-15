/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

import java.sql.ResultSet;
import java.sql.Statement;
import Dbconnect.*;
import javax.swing.JOptionPane;

/**
 *
 * @author BhANu
 */
public abstract class Employee {
    
    public int employeeid;
    Statement vw_stat;
    ResultSet vw_res ;
    
     public abstract  ResultSet viewItems();
    
    
    
    Employee()
    {
        vw_stat = null;
        vw_res = null;
    }
    
    public int Login(String username,String password,String type)
    {
       int empid = -999;
        try{
                String query = "SELECT COUNT(*) FROM Employee WHERE username = '"+username+"'";
        
                vw_res = dbquery.selectQuery(query);
        
                vw_res.next();
        
                int count = vw_res.getInt(1);
        
             if(count == 0)
            {
                JOptionPane.showMessageDialog(null,"Invalid Username or Password");
                return -999;
                
            }
            else
            {
                String query1 = "SELECT password FROM Employee WHERE username = '"+username+"'";
            
                vw_res = dbquery.selectQuery(query1);
                vw_res.next();
             
                String querypassword = vw_res.getString(1);
             
             if(querypassword.equals(password))
             {
                 String query2 = "SELECT eid FROM Employee WHERE username = '"+username+"' AND password = '"+password+"'";
                 vw_res = dbquery.selectQuery(query2);
                 vw_res.next();
                 
                 empid = vw_res.getInt(1);
                 
  //from here               
                 String mquery ="SELECT COUNT(*) FROM "+type+" WHERE eid ="+empid+"";
                 vw_res = dbquery.selectQuery(mquery);
                 vw_res.next();
                    
                 int lcount = vw_res.getInt(1); 
                 
                 if(lcount == 1)
                 {
                     return empid;
                 }
            
                 
                 
                 
                 
             }
        
        return -999;
        }
        
       }
       catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error");
        }
        
       return -999;
    
    }
    
    
    
    
}
