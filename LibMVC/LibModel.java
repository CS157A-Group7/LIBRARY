
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


public class LibModel {
    LibView view;
    
    DefaultTableModel userModel;
    DefaultTableModel itemModel;
    
     // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/Library";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "1433239_Jp";
    
    public LibModel(LibView view){
        this.view = view;
        userModel = GenTableModel("person");
        this.view.admin.setAdminUserTable(userModel);
        itemModel = GenTableModel("item");
        this.view.user.setUserItemTable(itemModel);
    }
//    USER
    public DefaultTableModel Search(String searchParam, String table, String col,
            String where, DefaultTableModel model){
        Connection conn = null;
        Statement stmt = null;
        
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(  "SELECT " + col +
                                    " FROM " + table +
                                    " WHERE " + where + " = '" + searchParam +"'");
            ResultSet rs = 
                stmt.executeQuery(  "SELECT " + col +
                                    " FROM " + table +
                                    " WHERE " + where + " = '" + searchParam +"'");
            
            int rowcount = 0;
                if (rs.last()) {
                  rowcount = rs.getRow();
                  rs.beforeFirst(); 
                }
                System.out.println("" + rowcount);

            return buildTableModel(rs);
           
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(stmt!=null)
                 stmt.close();
           }catch(SQLException se2){
            }
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }
        return null;
    }
//  ADMIN
//    insert into person(uname,usertype,preferredbranch,totalLoansMade) values('Jake','A',10,0);
    public DefaultTableModel addUser(String uname, String usertype, String prefBranch, String loans){
        Connection conn = null;
        Statement stmt = null;
        String sqlInsert = "insert into person(uname,usertype,preferredbranch,totalLoansMade) values('" + 
                       uname + "','" + usertype + "','" + prefBranch + "','" + loans + "')";
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(sqlInsert);
            stmt.executeUpdate(sqlInsert);
            ResultSet rs = stmt.executeQuery("SELECT * from person" );
            return buildTableModel(rs);
           
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(stmt!=null)
                 stmt.close();
           }catch(SQLException se2){
            }
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }
        return null;
    }
    
     public DefaultTableModel delUser(String uID){
        Connection conn = null;
        Statement stmt = null;
        String sqlDelete = "DELETE FROM person where PersonId = '" + uID + "'";
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(sqlDelete);
            stmt.executeUpdate(sqlDelete);
            ResultSet rs = stmt.executeQuery("SELECT * from person" );
            return buildTableModel(rs);
           
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(stmt!=null)
                 stmt.close();
           }catch(SQLException se2){
            }
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }
        return null;
    }
    
// TABLE MODEL GENERATION
    public DefaultTableModel GenTableModel(String table){
        Connection conn = null;
        Statement stmt = null;
        DefaultTableModel tableModel = null;
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + table );
            tableModel = buildTableModel(rs);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(stmt!=null)
                 stmt.close();
           }catch(SQLException se2){
            }
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }
        
        return tableModel;
    }  
    
    public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}