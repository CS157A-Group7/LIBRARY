
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class LibModel
{

    LibView view;

    DefaultTableModel userModel;
    DefaultTableModel itemModel;

    User user;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Library";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public LibModel(LibView view)
    {
        this.view = view;
        user = new User();
        userModel = GenTableModel("person");
        this.view.admin.setAdminUserTable(userModel);
        itemModel = GenTableModel("item");
        this.view.user.setUserItemTable(itemModel);
    }

    public class User
    {

        int userID;
        String userName;
        String userType;
        int preferredBranch;

        public User()
        {
            userID = 0;
            userName = null;
            userType = null;
            preferredBranch = 0;
        }

        public void reset()
        {
            userID = 0;
            userName = null;
            userType = null;
            preferredBranch = 0;
        }

        public void set(int id, String name, String type, int pref)
        {
            userID = id;
            userName = name;
            userType = type;
            preferredBranch = pref;
        }

        public void printUser()
        {
            System.out.println(userID);
            System.out.println(userName);
            System.out.println(userType);
            System.out.println(preferredBranch);
        }

    }

    public void login(String id)
    {
        Connection conn = null;
        Statement stmt = null;

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(
                    "Select PersonID, uName, UserType, PreferredBranch"
                    + "From person Where PersonID = " + id);
            ResultSet rs = stmt.executeQuery(
                    "Select PersonID, uName, UserType, PreferredBranch"
                    + " From person Where PersonID = '" + id + "'");

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }

            if (rowcount != 0)
            {
                rs.next();
                user.userID = ((Number) rs.getObject(1)).intValue();
                user.userName = rs.getString(2);
                user.userType = rs.getString(3);
                user.preferredBranch = ((Number) rs.getObject(4)).intValue();
            } else
            {
                view.login.LoginError();
            }

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
    }

    public String getBranch(int branchID)
    {
        Connection conn = null;
        Statement stmt = null;

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT BranchName FROM librarybranch Where '"
                    + branchID + "'");

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            rs.next();
            return rs.getString(1);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

    public String[] getLibraries()
    {
        ArrayList<String> libs = null;

        Connection conn = null;
        Statement stmt = null;

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT BranchName FROM librarybranch");

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            libs = new ArrayList<String>();
            for (int i = 0; i < rowcount; i++)
            {
                rs.next();
                libs.add(rs.getString(1));
            }
            String[] ary = libs.toArray(new String[libs.size()]);
            return ary;

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

//    USER
    public DefaultTableModel Search(String searchParam, String table, String col,
            String where, DefaultTableModel model)
    {
        Connection conn = null;
        Statement stmt = null;

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println("SELECT " + col + " FROM " + table + " WHERE "
                    + where + " = '%" + searchParam + "%'");
            ResultSet rs = stmt.executeQuery("SELECT " + col + " FROM " + table
                    + " WHERE " + where + " LIKE '%" + searchParam + "%'");

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            System.out.println("" + rowcount);

            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

    public DefaultTableModel DefaultItemSearch(String sql)
    {

        Connection conn = null;
        Statement stmt = null;

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }

        return null;
    }

//  ADMIN
//    insert into person(uname,usertype,preferredbranch,totalLoansMade) values('Jake','A',10,0);
    public DefaultTableModel addUser(String uname, String usertype,
            String prefBranch, String loans)
    {
        Connection conn = null;
        Statement stmt = null;
        String sqlInsert
                = "insert into person(uname,usertype,preferredbranch,totalLoansMade) values('"
                + uname + "','" + usertype + "','" + prefBranch + "','" + loans
                + "')";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(sqlInsert);
            stmt.executeUpdate(sqlInsert);
            ResultSet rs = stmt.executeQuery("SELECT * from person");
            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

    public DefaultTableModel delUser(String uID)
    {
        Connection conn = null;
        Statement stmt = null;
        String sqlDelete = "DELETE FROM person where PersonId = '" + uID + "'";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(sqlDelete);
            stmt.executeUpdate(sqlDelete);
            ResultSet rs = stmt.executeQuery("SELECT * from person");
            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

    public DefaultTableModel DefualtUserSearch(String sql)
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            System.out.println("" + rowcount);

            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

    //Updates a user's information.
    public DefaultTableModel updateUserBranch(String uID, String newLib)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        String sqlUpdate
                = "update person set PreferredBranch= (select libraryBranchID from librarybranch where BranchName=?) where person.PersonId = ?";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sqlUpdate);

            stmt.setString(1, newLib);
            stmt.setInt(2, Integer.parseInt(uID));
            System.out.println(stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("SELECT * from person");
            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

// TABLE MODEL GENERATION
    public DefaultTableModel GenTableModel(String table)
    {
        Connection conn = null;
        Statement stmt = null;
        DefaultTableModel tableModel = null;
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + table);
            tableModel = buildTableModel(rs);
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }

        return tableModel;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException
    {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++)
        {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next())
        {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
            {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public DefaultTableModel SearchBooksBySameAuthor(String title, DefaultTableModel model)
    {
        Connection conn = null;
        Statement stmt = null;

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println("select distinct title from item old where author = (select distinct author  from item  where title=\"" + title +"\");");
            ResultSet rs = stmt.executeQuery("select distinct title from item old where author = (select distinct author  from item  where title=\"" + title +"\");");

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            System.out.println("" + rowcount);

            return buildTableModel(rs);

        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            } catch (SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        return null;
    }

}
