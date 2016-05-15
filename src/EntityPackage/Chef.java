/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;


import Dbconnect.dbquery;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author BhANu
 */
public class Chef extends Employee {
    
    
    
    public Chef()
    {
        employeeid = -999;
    }
    public Chef(int id)
    {
        employeeid  = id;
    }
    
    public ResultSet viewItems(){
            
            
        try{
          String query = "SELECT item_no,name,item_type,cost,quantity,status,description FROM items ";
          vw_res = dbquery.selectQuery(query);
         
          
         
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
       //return the resultset
        return vw_res;
        
    }
    
    

    
    
    public ResultSet viewPackages(){
         try{
       
         String query = "SELECT package_no,name,cost,status,description FROM packages ";
            vw_res = dbquery.selectQuery(query);
         
          
         
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
        
       //return the resultset
        return vw_res;
       
        
    }
    
        
    public ResultSet viewUpdate(){
         try{
      
         String query = "SELECT item_no,name,item_type,cost,quantity,status,description FROM items ";
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
       
                 String query = "SELECT item_no,name,description,cost FROM items WHERE name LIKE '%"
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
              
                   String query = "SELECT package_no,name,description,cost FROM packages WHERE name LIKE '%"
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
    
    public void addToItems(String name,String type,double cost,int qty,String description)
    {
        
        {
            try{
                
                String addquery = "INSERT INTO items(name,item_type,cost,quantity,status,description)"+
                  " VALUES('"+name+"','"+type+"',"+cost+","+qty+",'Not fixed','"+description+"') "; 
                 dbquery.UpdateQuery(addquery);
              
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                 } 
       
        }
    }
       //Need to add a chef ID attribute
    //Write a query to list the items in the item table
    
    
        public void addToPackages(String name,double cost,String description)
         {   
          try{
             
            String squery = "INSERT INTO packages(name,cost,status,description)"+
                  " VALUES('"+name+"',"+cost+",'Not fixed','"+description+"') "; 
             dbquery.UpdateQuery(squery);
        
          //  JOptionPane.showMessageDialog(null,"correct" );
        }
         catch(Exception e)
         {
              JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
         } 
         
    
               
        }
        
        
         public void UpdateQty(int item_no,int quantity)
         {   
          try{
             
            String squery = "UPDATE items SET quantity="+quantity+" WHERE item_no="+item_no+"";
                dbquery.UpdateQuery(squery);
         
         
        }
         catch(Exception e)
         {
              JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
         } 
         
    
               
        }
      
      
    
    

    
    
 
    
}
