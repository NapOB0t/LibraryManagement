/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mphel
 */
public class DataHandler {
    
    String URl ="jdbc:derby:libraryDB;create=true";
    String Driver = "org.apache.derby.jdbc.EmbeddedDriver";
    
    Connection con;
    
    public DataHandler()
    {
    }
    
    public void getConnection()throws ClassNotFoundException
    {
        try
        {
            Class.forName(Driver);

            this.con = DriverManager.getConnection(URl);

            if(this.con != null)
            {

                System.out.println("Connection established");

            }
        
        }
        catch(SQLException ex)
        {
        
            System.out.println(ex.getMessage());
        
        }
          
    }
    
    public void create_booktbl()
    {
     
        try
        {
            
            
            
            
            
            String query = "CREATE TABLE Books("
                + "BookID VARCHAR(20) NOT NULL PRIMARY KEY,"
                + "Name  VARCHAR(50),"
                + "Genre VARCHAR(50),"
                + "Author VARCHAR(50),"
                + "Borrowed VARCHAR(5),"
                + "BorrowedID VARCHAR(20))";
            
            this.con.createStatement().execute(query);
            
            System.out.println("Table created");
        
        
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        
        }
        
    
    
    }
    
    
    public void create_borrowertbl()
    {
     
        try
        {
            String query = "CREATE TABLE Borrower("
                + "BorrowedID VARCHAR(20) NOT NULL PRIMARY KEY,"
                + "Name  VARCHAR(50),"
                + "surname VARCHAR(50),"
                + "DateBorrowed DATE) ";
            
            this.con.createStatement().execute(query);
            
            System.out.println("Borrower Table created");
        
        
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        
        }
        
    
    
    }
    
    
    public void addBook(String id,String name, String genre , String author, 
            String brw, String brwID) throws ClassNotFoundException
    {
        if(con == null)
        {
            getConnection();
        
        }
        String query= "INSERT INTO Books (BookID,Name,Genre,Author,Borrowed,"
                + "BorrowedID) VALUES (?,?,?,?,?,?)";
        try
        {
            PreparedStatement myst = con.prepareStatement(query);
            myst.setString(1, id);
            myst.setString(2, name);
            myst.setString(3, genre);
            myst.setString(4, author);
            myst.setString(5,brw);
            myst.setString(6, brwID);
            
            myst.executeUpdate();
            
             JOptionPane.showMessageDialog(null, 
                "Successfully added a book", 
                "Adding Error", 
                JOptionPane.ERROR_MESSAGE);
       
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public ArrayList<String[]> view() 
    {
        if(con == null)
        {
            try {
                getConnection();
            } 
            catch (ClassNotFoundException ex)
            {
                System.out.println(ex.getMessage());
            }
        
        }
    
        ArrayList<String[]> dl = new ArrayList<>();
        
        try
        {
            String query = "SELECT * FROM Books";
            
            ResultSet table = this.con.createStatement().executeQuery(query);
            
            while(table.next())
            {
                String id = table.getString("BookID");
                String n = table.getString("Name");
                String g = table.getString("Genre");
                String a = table.getString("Author");
                String brw = table.getString("Borrowed");
                String brw_id = table.getString("BorrowedID");
                
                String[] row = {id,n,g,a,brw,brw_id};
                dl.add(row);
           
            }
       
        }
        catch(SQLException ex)
        {
        
            System.out.println();
        
        }
        
        return dl;
    
    
    
    }
    
    public void updateBook(String id,String name, String genre , String author, 
            String brw, String brwID) throws ClassNotFoundException
    {
        String query = "UPDATE Books SET Name= ?,Genre = ?,Author = ?,Borrowed = ?,"
                + "BorrowedID = ? WHERE BookID = ?";
        
        if(con == null)
        {
            getConnection();
        
        }
        try
        {
            PreparedStatement myst = con.prepareStatement(query);
            
            myst.setString(1, name);
            myst.setString(2, genre);
            myst.setString(3, author);
            myst.setString(4,brw);
            myst.setString(5,brwID);
            myst.setString(6, id);
           
            myst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, 
                "Information Updated", 
                "Update Error", 
                JOptionPane.ERROR_MESSAGE);
                       
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    
    
    
    
  }
    
    
    public void delete(String id) 
    {
        
        if(con == null)
        {
            try 
            {
                getConnection();
            } 
            catch (ClassNotFoundException ex) 
            {
                System.out.println(ex.getMessage());
            }
        
        }
        
        String query = "DELETE FROM Books WHERE BookID = ? ";
        try
        {
            PreparedStatement myst = con.prepareStatement(query);
            myst.setString(1, id);
            
            myst.executeUpdate();
            
            System.out.println("Successful Deletion was done ");
        
        
        }
        catch(SQLException ex)
        {
            
            System.out.println(ex);
        
        
        
        
        
        }
    
    
    
    
    
    
    }
    
    
    //-------------------------Borrower Functions---------------------------------------//
    
    public void addBorrower(String id,String name, String surname, String date) throws ClassNotFoundException
    {
        String query= "INSERT INTO Borrower (BorrowedID,Name,surname,"
                + "DateBorrowed) VALUES (?,?,?,?)";
        
        if(con == null)
        {
            getConnection();
        
        }
        try
        {
            PreparedStatement myst = con.prepareStatement(query);
            myst.setString(1, id);
            myst.setString(2, name);
            myst.setString(3, surname);
            myst.setString(4, date);
   
            
            myst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, 
                "Successfully added a borrower information", 
                "Adding Error", 
                JOptionPane.ERROR_MESSAGE);
       
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public ArrayList<String[]> viewB()
    {
    
        ArrayList<String[]> dl = new ArrayList<>();
        
        if(con == null)
        {
            try 
            {
                getConnection();
            } 
            catch (ClassNotFoundException ex)
            {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        try
        {
            String query = "SELECT * FROM Borrower";
            
            ResultSet table = this.con.createStatement().executeQuery(query);
            
            while(table.next())
            {
                String d = table.getString("DateBorrowed");
                String name = table.getString("Name");
                String sname = table.getString("surname");
             
                String brw_id = table.getString("BorrowedID");
                
                String[] row = {brw_id,name,sname,d};
                dl.add(row);
           
            }
       
        }
        catch(SQLException ex)
        {
        
            System.out.println();
        
        }
        
        return dl;
    
    
    
    }
    
    public void updateBorrower(String id  , String n, String sn, String d) throws ClassNotFoundException
    {
        String query = "UPDATE Borrower SET Name= ?,surname = ?,DateBorrowed = ? WHERE BorrowedID = ?";
        
        if(con == null)
        {
            getConnection();
        
        }
        try
        {
            PreparedStatement myst = con.prepareStatement(query);
            
            myst.setString(1,n);
            myst.setString(2,sn);
            myst.setString(3, d);
            myst.setString(4,id);
            System.out.println("iT got here");
            myst.executeUpdate();
            
          JOptionPane.showMessageDialog(null, 
                "Information Updated", 
                "Update Error", 
                JOptionPane.ERROR_MESSAGE);;
                       
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    
    
    
    
  }
    
    public void deleteB(String id) 
    {
        String query = "DELETE FROM Borrower WHERE BorrowedID = ? ";
        if(con == null)
        {
            try 
            {
                getConnection();
            } 
            catch (ClassNotFoundException ex) 
            {
                System.out.println(ex.getMessage());
            }
        
        }
        try
        {
            PreparedStatement myst = con.prepareStatement(query);
            myst.setString(1, id);
            
            myst.executeUpdate();
            
            System.out.println("Successful Deletion was done ");
        
        
        }
        catch(SQLException ex)
        {
            
            System.out.println(ex);
        }
    }
    
    
}
