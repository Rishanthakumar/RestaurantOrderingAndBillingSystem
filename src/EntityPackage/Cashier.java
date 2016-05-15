/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

import Dbconnect.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


/**
 *
 * @author user1
 */
public class Cashier extends Employee{
    
     
     public int tableno;
     public int receiptno;
     public String payamenttype;
     public String date;
     public  Customer cus1;
     public int employeeID;
     private ResultSet cusdetail;
     
     
     
     
     public Cashier (){
        cus1= new Customer();
        cusdetail = null;
        tableno =1;
     }
     
     public void viewSummary(int orderno){
         
         //query item
         String squery = "SELECT i.item_no,i.name,i.price,o.quantity FROM  items i INNER JOIN order_item o  "
                         + " ON o.item_no = i.item_no WHERE o.order_no = "+orderno+"";
                          
         try{
       
                 cus1.itm_res = dbquery.selectQuery(squery);
        
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }                       

           //query package
         String squery1 = "SELECT p.package_no,p.name,p.price,o.quantity FROM  packages p INNER JOIN order_package o  "
                         + " ON o.package_no = p.package_no WHERE o.order_no = "+orderno+"";
                          
         try{
      
                cus1.pac_res = dbquery.selectQuery(squery1);
          
         
         
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }   

         
         //sum item
         
         double itotal=0;
         
          String squery2 = "SELECT SUM(i.price*o.quantity) FROM items i INNER JOIN order_item o  "
                         + " ON o.item_no = i.item_no WHERE o.order_no = "+orderno+"";
                          
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
                         + " ON o.package_no = p.package_no WHERE o.order_no = "+orderno+"";
                          
          try{
         
            vw_res = dbquery.selectQuery(squery3);
          
            vw_res.next();
          
            ptotal = vw_res.getDouble(1);
         
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
         
         
         String query = "SELECT cid FROM order_details WHERE order_no= "+orderno+"";
         
         try{
         cusdetail = dbquery.selectQuery(query);
         cusdetail.next();
         
         cus1.cus_id = cusdetail.getInt(1);
         
         String query2 = "SELECT name FROM customer WHERE cid= "+cus1.cus_id+"";
         cusdetail = dbquery.selectQuery(query2);
         cusdetail.next();
         
        cus1.name = cusdetail.getString(1);
         }
         catch(Exception e){
         
            JOptionPane.showMessageDialog(null,"Invalid Order number!");
            
         }
    

         cus1.total = itotal+ptotal;
    }
    
    public void assignTableId(int table){
        try{
            
                tableno=table;
    
                String squery = "UPDATE order_details SET table_no = "+table+" WHERE order_no="+cus1.order_id+"";
          
         
                dbquery.UpdateQuery(squery);
   
                JOptionPane.showMessageDialog(null,"Table number assigned successfully!" );
          
        }
        catch(Exception e)
        {
                JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        } 
    
    }
    
    public  ResultSet Search(String soption,int keyword){
  
            if(soption.equals("Ohistory"))
            {
             try{
               
                
                String query = "SELECT order_date,order_no,cid,table_no,total_price FROM order_details WHERE order_no= "+keyword;
                vw_res = dbquery.selectQuery(query);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                }
            }
            else if(soption.equals("customer"))
            {
                try{
                     
                
                    String query = "SELECT * FROM customer WHERE cid= "+keyword;
                      vw_res = dbquery.selectQuery(query);
                    }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                }
                
            }
            else if(soption.equals("penorders"))
            {
                 try{
              
                
                String query = "SELECT order_date,order_no,cid,total_price FROM order_details WHERE order_no= "+keyword+" AND status ='pending'";
                 vw_res = dbquery.selectQuery(query);
                
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
                }
            
            }
        
        
        
       return vw_res;
    }
    
    public ResultSet ViewPendingOrders(){
       
       try{
      
       String orderDetails = "SELECT order_date,order_no,cid,total_price FROM Order_details WHERE status = 'pending' ";
        vw_res = dbquery.selectQuery(orderDetails);
       
       }
       
       catch(Exception e){
       JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
       }
       
       return vw_res;
       
      
        
        
    
    }


 public void make_payment(String ptype)
{
    String query = "INSERT INTO receipt(order_no,payment_type) VALUES("+cus1.order_id+",'"+ptype+"')";
    String query2 ="UPDATE order_details SET status='paid' WHERE order_no="+cus1.order_id+"";
    try{
                   dbquery.UpdateQuery(query);
                   dbquery.UpdateQuery(query2);
               
                   
                   JOptionPane.showMessageDialog(null,"Payment successfully made!");
            }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
}


public void getReceiptDetails()
{
     String rquery = "SELECT i.item_no,i.name,i.price,o.quantity FROM  items i INNER JOIN order_item o  "
                         + " ON o.item_no = i.item_no WHERE o.order_no = "+cus1.order_id+"";
                          
         try{
       
          cus1.itm_res = dbquery.selectQuery(rquery);
          
         
          
        
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }                       

           //query package
         String rquery1 = "SELECT p.package_no,p.name,p.price,o.quantity FROM  packages p INNER JOIN order_package o  "
                         + " ON o.package_no = p.package_no WHERE o.order_no = "+cus1.order_id+"";
                          
         try{
     
            cus1.pac_res = dbquery.selectQuery(rquery1);
          
         
          
       
         }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }   
         
         
         String recdetail = "SELECT * FROM receipt WHERE order_no="+cus1.order_id+"";
         
         
         try{
            vw_res = dbquery.selectQuery(recdetail);
         
            vw_res.next();
         
            receiptno = vw_res.getInt(1);
             date = vw_res.getString(2);
             payamenttype = vw_res.getString(4);
         
         
             
         }
         catch(Exception e)
         {
              JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
         }
         
         
         
         



}
    public ResultSet viewItems()
    {
    
    
        return vw_res;
    }
    
    
     public ResultSet ViewCustomerDetails(){
       
       try{
       
       String cusDetails = "SELECT cid,name,address,phone_no FROM customer";
        vw_res = dbquery.selectQuery(cusDetails);
       
       }
       
       catch(Exception e){
       JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
       }
       
       return vw_res;
  
    }
    
    public ResultSet ViewOrderHistory(){
       
       try{
      
       String historyDetails = "SELECT cid,order_no,order_date,table_no,total_price FROM Order_details";
      
        vw_res = dbquery.selectQuery(historyDetails);
       }
       
       catch(Exception e){
       JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
       }
       
       return vw_res;
       
      
        
        
    
    }
    


}