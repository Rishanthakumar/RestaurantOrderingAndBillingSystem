/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

/**
 *
 * @author HP
 */


import Dbconnect.Connect;
import Dbconnect.dbquery;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;




public class Manager  extends Employee {
    ResultSet resul;

    
    public Manager()
    {
        employeeid = -999;
    }
    public Manager(int id)
    {
        employeeid  = id;
    }


 public ResultSet viewItems(){
    
 
         
          try{
         
         
         String query ="SELECT item_no,name,item_type,cost,price,quantity,status,description FROM items";
          
           vw_res = dbquery.selectQuery(query);
         
          
   
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
        
      
        return vw_res;
    }
 
 
 
 
 
 public ResultSet viewPackages()
 {
     
   try{
         
         String query = "SELECT package_no,name,cost,price,status,description FROM packages";
          vw_res = dbquery.selectQuery(query);
             
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
        
       //return the resultset
        return vw_res;
    
    
    
    
    
}

 
 
 
public ResultSet viewEmployee()
 {
     
 
        try{
         
         String query = "SELECT eid,name,username,password,phone_no,designation FROM employee";
          vw_res = dbquery.selectQuery(query);
             
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
        
       //return the resultset
        return vw_res;
        
    
    
        
    
    
    
    
}

public  ResultSet Search(String type,String keyword)
    {
        
       
        if(type == "items")
        {
            try{
             
                   String query = "SELECT item_no,name,description,cost,price FROM items WHERE name LIKE '%"
                            + keyword+"%'";
         
                vw_res = dbquery.selectQuery(query);
          
         
           
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                }
        }
        else if(type == "packages")
        {
             try{
                
                    String query ="SELECT package_no,name,description,cost,price FROM packages WHERE name LIKE '%"
                            + keyword+"%'";
                    vw_res = dbquery.selectQuery(query);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                }
        
        
        }
            
       
        return vw_res;
    
    }


public  ResultSet Searchemployee(String keyword)
    {
        
        
            try{
               
                String query="SELECT eid,name,username,password,phone_no,designation FROM employee WHERE name LIKE '%"
                            + keyword+"%'";
            vw_res = dbquery.selectQuery(query);
         
          
       
           
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                }
        
        
        
        
            
       
        return vw_res;
    
    
    }

    
      public void addEmployee(String name,String username,String password ,int phone,String designation)
    {
        
        try{
               
                
                String addquerymn = "INSERT INTO employee (name,username,password,phone_no,designation)"+
                  " VALUES('"+name+"','"+username+"','"+password+"',"+phone+",'"+designation+"') "; 
         
                dbquery.UpdateQuery(addquerymn);
                
                String squery = "SELECT MAX(eid) FROM employee";
        
                vw_res = dbquery.selectQuery(squery);
                vw_res.next();
                int emp_id = vw_res.getInt(1);
                
                
                String insquery = "INSERT INTO "+designation+" VALUES("+emp_id+")";
                dbquery.UpdateQuery(insquery);
               
               
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                } 
       
        
       
        
    }   
        
        
         public void fixPrices(int packageno,double packageprice)
    {
        
        
             if(packageno>=200 &&packageno<300){
            try{
                
                 
                String addquerymnf = "UPDATE items SET price = "+packageprice+",status = 'fixed' WHERE item_no ="+packageno+"  ";
                        
                 dbquery.UpdateQuery(addquerymnf);
        
                
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                 } 
       
        }
            if(packageno>=300 &&packageno<400){
            try{
               
                String addquerymnf = "UPDATE packages SET price = "+packageprice+",status='fixed' WHERE package_no ="+packageno+"  ";
                        
                dbquery.UpdateQuery(addquerymnf);
        
               
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                 } 
        
        
            }  
        
    }   
    
         
         
         
         
    public void deleteitemOrPackage(int itemno) 
     {
         
               if(itemno>=200 && itemno<300)
            {
            try{
                
                 
                 
                 String addquery = "DELETE FROM items WHERE item_no = "+itemno+" ";
                        
                dbquery.UpdateQuery(addquery);
        
               
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                 } 
            }
            
           else if(itemno>=300 && itemno<400)
           {
            try{
               
                 
                 
                String addquery = "DELETE  FROM packages WHERE package_no = "+itemno+"   ";
                        
                dbquery.UpdateQuery(addquery);
        
               
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                 } 
       
        }
         
     }
    
    public void deleteEmployee(int empno)
    {
        try{
                
                
                 
                 String addquery = "DELETE FROM employee WHERE eid = "+empno+" ";
                        
                dbquery.UpdateQuery(addquery);
        
              
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                 } 
            }
    
    public void ViewReport(String fileName,HashMap parameter) {
     
        
        
        
        Connection conn = Connect.connectToDb(); 
        try{
           
        JasperReport jasperreport = JasperCompileManager.compileReport(fileName);
        JasperPrint print = JasperFillManager.fillReport(jasperreport,parameter,conn);
        JasperViewer.viewReport(print,false);
         

       
        conn.close();
        
        }
        catch(JRException jre)
        {
            jre.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
   
      
    
    
    
    
    
    }

        
     
    
    
    
    

    

