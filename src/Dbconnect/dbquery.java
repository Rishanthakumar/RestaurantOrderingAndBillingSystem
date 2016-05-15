/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dbconnect;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Rish
 */
public class dbquery {
    //Interface statement
    Statement statem;//The object used for executing a static SQL statement and returning the results it produces.
    ResultSet resul;
    
    public static ResultSet selectQuery(String query)
    {
            dbquery quer = new dbquery();
         try{
                   quer.statem = Connect.connectToDb().createStatement();
                   quer.resul = quer.statem.executeQuery(query);
           
            }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
        
       
        return quer.resul;
    
    }
    
    public static void UpdateQuery(String query)
    {
            dbquery quer = new dbquery();
         try{
                   quer.statem = Connect.connectToDb().createStatement();
                   quer.statem.executeUpdate(query);
           
            }
        catch(Exception e)
        {
           JOptionPane.showMessageDialog(null,"SQL exception occured" + e);
        }
        
       
        
    
    }
    

    
    
}
