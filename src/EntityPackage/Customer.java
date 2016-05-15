/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

import Dbconnect.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;



/**
 *
 * @author Rish
 */
public class Customer {
   
   public int cus_id;
   public String name;
   public int order_id;
   public ResultSet itm_res ;
   public ResultSet pac_res ;
   public double total;
   public int countOrderPac;
   private int phone_no;
   private String address;
   private Statement vw_stat;
   private ResultSet vw_res ;
  
    
    public Customer()
    {
       vw_stat = null;
       vw_res = null; 
      
    }
    public Customer(String nam,String add,int phn_n)
    {
        this();
        this.name = nam;
        this.address = add;
        this.phone_no = phn_n;
        this.countOrderPac = 0;
        
    }
    
    //view item method for maincourses,softdrinks and beverages
    public static ResultSet viewItems(String item_type)
    {
       Customer cs = new Customer();
       
       String query ="SELECT item_no,name,description,price FROM items WHERE item_type= '"+item_type+"' AND status='fixed'";
          
       cs.vw_res = dbquery.selectQuery(query);
         
       return cs.vw_res;
    }
    
    
    //
    public static ResultSet viewPackages()
    {
        
        Customer cs = new Customer();
   
        String query = "SELECT package_no,name,description,price FROM packages WHERE status ='fixed'";
        cs.vw_res = dbquery.selectQuery(query);
         
        return cs.vw_res;
    }
    
    
    public static ResultSet Search(String keyword)
    {
        Customer cs = new Customer();
        
        String query = "SELECT package_no as 'Search item/pakacge No',name,description,price,'Package' FROM packages WHERE name LIKE '%"
                 + keyword+"%' AND status='fixed' UNION (SELECT item_no as 'Search item/pakacge No',name,description,price,'Item' FROM "
                 +"items WHERE name LIKE'%"+keyword+"%' AND status='fixed')" ;
        
        cs.vw_res = dbquery.selectQuery(query);
         
        return cs.vw_res;
    
    }
    
    public void enterDetails()
    {
       try{
           
          String squery = "INSERT INTO customer(name,address,phone_no) VALUES('"+name+"','"+address+"',"+phone_no+") "; 
         
          
          dbquery.UpdateQuery(squery);
          //get customer id
          
          
          //assign the customer id to object variable cus_id
          getcustID();
          //assign order no to a customer
          assignOrderId();
          
        
         JOptionPane.showMessageDialog(null,"Your details are successfully added!" );
        
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        } 
       
     
    
    }
    
    public void assignOrderId(){
         try{
           
          String squery = "INSERT INTO order_details(cid,status) VALUES("+cus_id+",'pending') "; 
         
          dbquery.UpdateQuery(squery);
          
          
          
          String squery2 = "SELECT order_no FROM order_details WHERE cid = "+cus_id;
          vw_res = dbquery.selectQuery(squery2);
          vw_res.next();
          
          //get the order ID;
          order_id = vw_res.getInt(1);
          
         
          
        //  JOptionPane.showMessageDialog(null,"correct" );
          
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        } 
    
    }
   //retrieve the customer id as soon as enters the details 
    public void getcustID()
    {
         try{
             String squery = "SELECT MAX(cid) FROM customer";
        
            vw_res = dbquery.selectQuery(squery);
            vw_res.next();
            cus_id = vw_res.getInt(1);
          
        
       //     JOptionPane.showMessageDialog(null,"correct" );
          
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        } 
    
    }
    
    
    public void addToOrder(int itmOrPac,int qty)
    {
        if(itmOrPac >= 200 && itmOrPac < 300)
        {
            try{
                 
                String squery = "INSERT INTO order_item(order_no,item_no,quantity)"+
                  " VALUES("+order_id+","+itmOrPac+","+qty+") "; 
         
                //update the tables 
                dbquery.UpdateQuery(squery);
          
                JOptionPane.showMessageDialog(null,"Successfully Added!" );
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"Please provide a valid Package/Item no:: If package/Item is a existing value  in the summary then delete and add again " );
                 } 
       
        }
        else if(itmOrPac >= 300 && itmOrPac <= 400){
             
          try{
             
            String squery = "INSERT INTO order_package(order_no,package_no,quantity)"+
                  " VALUES("+order_id+","+itmOrPac+","+qty+") "; 
         
            dbquery.UpdateQuery(squery);
            JOptionPane.showMessageDialog(null,"Successfully Added!" );
        
            
        }
         catch(Exception e)
         {
              JOptionPane.showMessageDialog(null,"Please provide a valid Package/Item no:: If package/Item is a existing value  in the summary then delete and add again ");
         } 
         
    
               
        }
        else 
        {
            JOptionPane.showMessageDialog(null,"Please provide a valid Package/Item no:: If package/Item is a existing value  in the summary then delete and add again ");
        
        }
    
    
    }
    
    
     public void viewSummary(){
                  //query item
         String squery = "SELECT i.item_no,i.name,i.price,o.quantity FROM  items i INNER JOIN order_item o  "
                         + " ON o.item_no = i.item_no WHERE o.order_no = "+order_id+"";
         String squeryCount = "SELECT COUNT(*) FROM  items i INNER JOIN order_item o  "
                         + " ON o.item_no = i.item_no WHERE o.order_no = "+order_id+"";
                          
        try{
                itm_res = dbquery.selectQuery(squery);
          
                vw_res = dbquery.selectQuery(squeryCount);
          
                vw_res.next();
          
             //get the count of the order items  to check whether cusomer is added or not
                countOrderPac = countOrderPac+vw_res.getInt(1);
      
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }                       

           //query package
         String squery1 = "SELECT p.package_no,p.name,p.price,o.quantity FROM  packages p INNER JOIN order_package o  "
                         + " ON o.package_no = p.package_no WHERE o.order_no = "+order_id+"";
           
         String squeryCount1 = "SELECT COUNT(*) FROM  packages p INNER JOIN order_package o  "
                         + " ON o.package_no = p.package_no WHERE o.order_no = "+order_id+"";
         try{
                pac_res = dbquery.selectQuery(squery1);
        
                vw_res = dbquery.selectQuery(squeryCount1);
          
                vw_res.next();
          
          countOrderPac = countOrderPac+vw_res.getInt(1);

         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }   

         
         //sum item
         
         double itotal=0;
         
          String squery2 = "SELECT SUM(i.price*o.quantity) FROM items i INNER JOIN order_item o  "
                         + " ON o.item_no = i.item_no WHERE o.order_no = "+order_id+"";
                          
         try{
                vw_res = dbquery.selectQuery(squery2);
                vw_res.next();
                itotal = vw_res.getDouble(1);
          
         }
        catch(Exception e)
        {
            //erre 24/03/15
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }                       

         
         //sum pack
         double ptotal=0;
         
          String squery3 = "SELECT SUM(p.price*o.quantity) FROM  packages p INNER JOIN order_package o  "
                         + " ON o.package_no = p.package_no WHERE o.order_no = "+order_id+"";
                          
          try{
          
          vw_res = dbquery.selectQuery(squery3);
          
          vw_res.next();
          
          ptotal = vw_res.getDouble(1);
         
          
         
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
         
         
         this.total = itotal+ptotal;
    }
     
     public void deleteOrderItemOrPack (int itmOrPac)
     {
        if(itmOrPac >= 200 && itmOrPac < 300)
        {
            try{
                  String squery = "DELETE FROM order_item WHERE item_no = "+itmOrPac+""; 
                  dbquery.UpdateQuery(squery);
                  JOptionPane.showMessageDialog(null,"Successfully Removed!" );
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"Please provide a valid Package/Item number!" );
                 } 
       
        }
        else if(itmOrPac >= 300 && itmOrPac <=400){
             
          try{
              
            String squery ="DELETE FROM order_package WHERE package_no = "+itmOrPac+"";
         
          dbquery.UpdateQuery(squery);
          
           
        
            JOptionPane.showMessageDialog(null,"Successfully Removed!" );
        }
         catch(Exception e)
         {
              JOptionPane.showMessageDialog(null,"Please provide a valid Package/Item number" + e);
         } 
     
     
     }
        
     }
     
     public void cancelOrder()
     {
        try{
               
                String squery = "DELETE FROM order_item WHERE order_no = "+order_id+"";
                String squery1 = " DELETE FROM order_package WHERE order_no = "+order_id+"";
                String squery2 = " DELETE FROM order_details WHERE order_no = "+order_id+"";
                String squery3 = "DELETE FROM customer WHERE cid ="+cus_id+"";
                
                  
                dbquery.UpdateQuery(squery);
                dbquery.UpdateQuery(squery1);
                dbquery.UpdateQuery(squery2);
                dbquery.UpdateQuery(squery3);
                
             
        
                JOptionPane.showMessageDialog(null,"Your order is successfully cancelled" );
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"Provide a valid Package/Item no"+e );
                 } 
        
                
     
     }
     
     public void updateOrder()
     {
         try{
                
                String squery = "UPDATE order_details SET total_price = "+total+"WHERE order_no="
                                +order_id+"";
         
             dbquery.UpdateQuery(squery);
          
                
        
            //    JOptionPane.showMessageDialog(null,"total added" );
          
               }
        
                catch(Exception e)
                {
                  JOptionPane.showMessageDialog(null,"Provide a valid Package/Item no:: If package/Item is a existing vale  in the summary then delete and add again " );
                 } 
     
     }
     
     
}
    
 