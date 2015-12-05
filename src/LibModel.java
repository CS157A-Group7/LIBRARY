
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class LibModel
{

    LibView view;

    DefaultTableModel userModel;
    DefaultTableModel itemModel;
    DefaultTableModel ratingModel;
    DefaultTableModel loanModel;

    User user;

    ArrayList<String> libs;
    ArrayList<String> authors;
    ArrayList<String> cats;

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

        libs = new ArrayList<String>();
        getCBData(libs, "BranchName", "librarybranch");
        authors = new ArrayList<String>();
        getCBData(authors, "author", "item");
        authors.remove(0);
        cats = new ArrayList<String>();
        getCBData(cats, "ItemType", "item");
        cats.remove(0);
        view.user.SetSearchCB(authors.toArray(new String[authors.size()]),
                cats.toArray(new String[cats.size()]));
        view.admin.SetLibCB(libs.toArray(new String[libs.size()]));

        userModel = GenTableModel("person");
        this.view.admin.setAdminUserTable(userModel);
        itemModel = GenTableModel("item");
        this.view.user.setUserItemTable(itemModel);
        ratingModel = RatingTableGen();
        this.view.user.setRatingItemTable(ratingModel);
        loanModel = LoanTableGen();
        this.view.user.setLoanItemTable(loanModel);
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
        PreparedStatement stmt = null;

        String sql
                = "Select PersonID, uName, UserType, PreferredBranch From person Where PersonID = ?";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

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
        ratingModel = RatingTableGen();
        this.view.user.setRatingItemTable(ratingModel);
        loanModel = LoanTableGen();
        this.view.user.setLoanItemTable(loanModel);
    }

    public String getBranch(int branchID)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        String sql = "SELECT BranchName FROM librarybranch Where ?";

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();

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

    public void getCBData(ArrayList<String> ary, String col, String tab)
    {
//        ArrayList<String> libs = null;

        Connection conn = null;
        Statement stmt = null;
        String sql = "SELECT " + col + " FROM " + tab + " GROUP BY " + col;
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int rowcount = 0;
            if (rs.last())
            {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
//            libs = new ArrayList<String>();
            for (int i = 0; i < rowcount; i++)
            {
                rs.next();
                ary.add(rs.getString(1));
            }
//            String[] ary = libs.toArray(new String[libs.size()]);
//            return ary;

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
//        return null;
    }

//  USER Methods
    public void UpdateInfo()
    {
        String newLib = view.user.userLibCB.getSelectedItem().toString();
        System.out.println(newLib);
        itemModel = updateUserBranch(Integer.toString(user.userID), newLib);
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
    }

    public void Reset()
    {
        itemModel = GenTableModel("item");
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.SearchField.setText("Search Title...");
        view.user.authorsCB.setSelectedIndex(0);
        view.user.catsCB.setSelectedIndex(0);
        view.user.scrollPane.repaint();
    }

    public void NameSearch()
    {
        String searchParam = view.user.SearchField.getText();
        if (searchParam.compareTo("Search Title...") != 0
                && searchParam.compareTo("") != 0)
        {
            System.out.println(searchParam);
            itemModel = Search(searchParam, "item", "*", "title", itemModel);
            itemModel.fireTableDataChanged();
            view.user.setUserItemTable(itemModel);
            view.user.scrollPane.repaint();
        } else
        {
            view.Error("No Title Entered");
        }
    }

    public void AuthorSearch()
    {
        String searchParam = view.user.authorsCB.getSelectedItem().toString();
        if (searchParam.compareTo("None") != 0)
        {
            System.out.println(searchParam);
            itemModel = Search(searchParam, "item", "*", "author", itemModel);
            itemModel.fireTableDataChanged();
            view.user.setUserItemTable(itemModel);
            view.user.scrollPane.repaint();
        } else
        {
            view.Error("No Author Selected");
        }
    }

    public void CategorySearch()
    {
        String searchParam = view.user.catsCB.getSelectedItem().toString();
        if (searchParam.compareTo("None") != 0)
        {
            System.out.println(searchParam);
            itemModel = Search(searchParam, "item", "*", "itemtype", itemModel);
            itemModel.fireTableDataChanged();
            view.user.setUserItemTable(itemModel);
            view.user.scrollPane.repaint();
        } else
        {
            view.Error("No Category Selected");
        }
    }

    public void SameAuthorSearch()
    {
        String searchParam = view.user.SearchField.getText();
        System.out.println(searchParam);
        itemModel = SearchBooksBySameAuthor(searchParam, itemModel);
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
    }

    public void AVSearch()
    {
        String sql
                = "select distinct title,author,edition,itemtype from item where itemtype like \"%ebook%\" \n"
                + " union   \n"
                + " select distinct title,author,edition,itemtype from item where itemtype like \"%audio%\" \n"
                + " union\n"
                + " select  distinct title,author,edition,itemtype from item where itemtype like\"%cd%\"\n"
                + " union  \n"
                + " distinct select title,author,edition,itemtype from item where itemtype like \"%dvd%\" ;";
        itemModel = DefaultItemSearch(sql);
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
    }

    public void PopularSearch()
    {
        String sql = "select title,count(*) total "
                + "from loan join item on Loan.itemid=Item.itemid "
                + "where ItemType='BOOK' " + "group by title,standardNumber "
                + "order by total desc " + "Limit 5";
        itemModel = DefaultItemSearch(sql);
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
    }

    public void RatingSearch()
    {

        String sql = "select title,avg(stars) "
                + "from rating join item on rating.itemid=Item.itemid "
                + "where ItemType='Book' " + "group by title,edition,author "
                + "order by avg(stars) desc " + "limit 5";

        itemModel = DefaultItemSearch(sql);
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
    }

    public void MultiSearch()
    {
        Boolean i = false;
        String sql = "SELECT * FROM item WHERE";
        if (view.user.SearchField.getText().compareTo("Search Title...") != 0)
        {
            sql = sql + " title Like '%" + view.user.SearchField.getText()
                    + "%'";
            i = true;
        }
        if (view.user.authorsCB.getSelectedItem().toString().compareTo("None")
                != 0)
        {
            if (i)
            {
                sql = sql + " and";
            }
            sql = sql + " author = '" + view.user.authorsCB.getSelectedItem().
                    toString() + "'";
            i = true;
        }
        if (view.user.catsCB.getSelectedItem().toString().compareTo("None") != 0)
        {
            if (i)
            {
                sql = sql + " and";
            }
            sql = sql + " ItemType = '" + view.user.catsCB.getSelectedItem().
                    toString() + "'";
        }
        itemModel = DefaultItemSearch(sql);
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
    }

    public void RentItem()
    {
        Connection conn = null;
        int userID = user.userID;
        String itemID = view.user.itemTable.getModel().getValueAt(
                view.user.itemTable.getSelectedRow(), 2).toString();
        int copies = Integer.parseInt(view.user.itemTable.getModel().getValueAt(
                view.user.itemTable.getSelectedRow(), 7).toString());
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());

        PreparedStatement itemStmt = null;
        String itemSql
                = "update item set copies = copies -1 where item.ItemId = ?";
//        PreparedStatement personStmt = null;
//        String personSql = "update person set TotalLoansMade = TotalLoansMade + 1 where personId = ?";
        PreparedStatement loanStmt = null;
        String loanSql
                = "insert into loan SET pid = ?, ItemId = ?, loandate = ?,overdue = false ON DUPLICATE KEY UPDATE ItemId = ?, loandate = ?,overdue = false;";

        if (copies > 0)
        {
            try
            {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                itemStmt = conn.prepareStatement(itemSql);
                itemStmt.setInt(1, Integer.parseInt(itemID));

                System.out.println(itemStmt);
                itemStmt.executeUpdate();

//                personStmt = conn.prepareStatement(personSql);
//                personStmt.setInt(1, userID);
//                System.out.println(personStmt);
//                personStmt.executeUpdate();
                loanStmt = conn.prepareStatement(loanSql);
                loanStmt.setInt(1, userID);
                loanStmt.setInt(2, Integer.parseInt(itemID));
                loanStmt.setDate(3, date);
                loanStmt.setInt(4, Integer.parseInt(itemID));
                loanStmt.setDate(5, date);
                System.out.println(loanStmt);
                loanStmt.executeUpdate();


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
                    if (itemStmt != null)
                    {
                        itemStmt.close();
                    }
//                    if (personStmt != null){
//                        personStmt.close();
//                    }
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
        } else
        {
            view.Error("No Copies Available");
        }

        itemModel = DefaultItemSearch("SELECT * from item");
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
        loanModel = LoanTableGen();
        loanModel.fireTableDataChanged();
        view.user.setLoanItemTable(loanModel);
        view.user.loanScrollPane.repaint();
    }

    public void ReturnItem()
    {
        Connection conn = null;
        int userID = user.userID;
        String loanID = view.user.loanTable.getModel().getValueAt(
                view.user.loanTable.getSelectedRow(), 0).toString();
        String itemID = view.user.loanTable.getModel().getValueAt(
                view.user.loanTable.getSelectedRow(), 2).toString();

        PreparedStatement itemStmt = null;
        String itemSql
                = "update item set copies = copies +1 where item.ItemId = ?";
 //       PreparedStatement personStmt = null;
        //       String personSql = "update person set TotalLoansMade = TotalLoansMade - 1 where personId = ?";
        PreparedStatement delStmt = null;
        String loanSql = "delete from loan where loan.LoanId = ?";

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            itemStmt = conn.prepareStatement(itemSql);
            itemStmt.setInt(1, Integer.parseInt(itemID));
            System.out.println(itemStmt);
 
            itemStmt.executeUpdate();

            delStmt = conn.prepareStatement(loanSql);
            delStmt.setInt(1, Integer.parseInt(loanID));
            System.out.println(delStmt);
            delStmt.executeUpdate();

   

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
                if (itemStmt != null)
                {
                    itemStmt.close();
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
        itemModel = DefaultItemSearch("SELECT * from item");
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
        loanModel = LoanTableGen();
        loanModel.fireTableDataChanged();
        view.user.setLoanItemTable(loanModel);
        view.user.loanScrollPane.repaint();
    }

    public void RateItem()
    {
        String title = view.user.itemTable.getModel().getValueAt(
                view.user.itemTable.getSelectedRow(), 0).toString();
        view.ratingFrame.setLabel(title);
        view.ratingFrame.setVisible(true);
    }

    public void ReRateItem()
    {
        String title = view.user.ratingTable.getModel().getValueAt(
                view.user.ratingTable.getSelectedRow(), 1).toString();
        view.ratingFrame.setLabel(title);
        view.ratingFrame.setVisible(true);
    }

    public void SubRateItem()
    {
        String itemID = view.user.itemTable.getModel().getValueAt(
                view.user.itemTable.getSelectedRow(), 2).toString();
        String ratingScore = view.ratingFrame.group.getSelection().
                getActionCommand();
        int userID = user.userID;
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());

        System.out.println(itemID);
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql
                = "insert into rating set ratingdate = ?,itemid = ?,personid = ?,stars =?;";

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setInt(2, Integer.parseInt(itemID));
            stmt.setInt(3, userID);
            stmt.setInt(4, Integer.parseInt(ratingScore));
            System.out.println(stmt);
            stmt.executeUpdate();

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

        itemModel = DefaultItemSearch("SELECT * from item");
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
        ratingModel = RatingTableGen();
        ratingModel.fireTableDataChanged();
        view.user.setRatingItemTable(ratingModel);
        view.user.ratingScrollPane.repaint();
        view.ratingFrame.setVisible(false);

    }

    public void SubReRateItem()
    {
        String itemID = view.user.ratingTable.getModel().getValueAt(
                view.user.ratingTable.getSelectedRow(), 2).toString();
        String ratingID = view.user.ratingTable.getModel().getValueAt(
                view.user.ratingTable.getSelectedRow(), 0).toString();
        String ratingScore = view.ratingFrame.group.getSelection().
                getActionCommand();
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());

        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "update rating set Stars = ? where RatingId = ?;";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(ratingScore));
            stmt.setInt(2, Integer.parseInt(ratingID));
            System.out.println(stmt);
            stmt.executeUpdate();

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

        itemModel = DefaultItemSearch("SELECT * from item");
        itemModel.fireTableDataChanged();
        view.user.setUserItemTable(itemModel);
        view.user.scrollPane.repaint();
        ratingModel = RatingTableGen();
        ratingModel.fireTableDataChanged();
        view.user.setRatingItemTable(ratingModel);
        view.user.ratingScrollPane.repaint();
        view.ratingFrame.setVisible(false);
    }

//  USER MODELS
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

    public DefaultTableModel SearchBooksBySameAuthor(String title,
            DefaultTableModel model)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        String sql
                = "select distinct title,edition from item old where author = (select distinct author  from item  where title= ? )";

        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            System.out.println(sql);
            stmt.setString(1, title);
            System.out.println(stmt.toString());
            ResultSet rs = stmt.executeQuery();
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

    public DefaultTableModel RatingTableGen()
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql
                = "SELECT rating.ratingid, item.title, item.ItemId, rating.ratingdate, rating.stars FROM item, rating WHERE rating.PersonId = ? and item.ItemId = rating.Itemid;";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.userID);
            ResultSet rs = stmt.executeQuery();

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

    public DefaultTableModel LoanTableGen()
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql
                = "SELECT loan.LoanID, item.title, item.ItemId, loan.loanDate, loan.overdue FROM item, loan WHERE loan.Pid = ? and item.ItemId = loan.Itemid;";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.userID);
            ResultSet rs = stmt.executeQuery();

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

//  ADMIN METHODS
    public void ResetUserTable()
    {
        String sql = "Select * from person";
        userModel = DefualtUserSearch(sql);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void AddUser()
    {
        String uname = view.admin.addUnameF.getText();
        String usertype = view.admin.addTypeCB.getSelectedItem().toString();
        String prefBranch = view.admin.addPrefBranchCB.getSelectedItem().
                toString();
        String loans = "0";
        userModel = addUser(uname, usertype, prefBranch, loans);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void DelUser()
    {
        String uID = view.admin.delF.getText();
        userModel = delUser(uID);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void NoLoanUser()
    {
        String sql
                = "SELECT * FROM PERSON left outer JOIN LOAN ON person.personid = LOAN.pid where loanid is null;";
        userModel = DefualtUserSearch(sql);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void LoanUser()
    {
        String sql = "select distinct title , uNAME "
                + "from person join loan on person.PersonId=loan.pid join item on Loan.itemid=Item.itemid "
                + "where UserType ='U'" + "order by title;  ";
        userModel = DefualtUserSearch(sql);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void OverdueUser()
    {
        String sql = "select PersonId,uname,count(*) "
                + "from Person join Loan on Person.PersonId=Loan.Pid "
                + "where overdue = true " + "group by personid "
                + "having count(*)>=2";
        userModel = DefualtUserSearch(sql);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void DoubleRatingUser()
    {
        String sql
                = "select distinct r1.personid,i1.title,i1.author,i1.edition,r1.stars oldRating,r2.stars newRating "
                + "      from rating r1 join rating r2 on r1.personid=R2.PERSONID"
                + "           join Item i1 on "
                + "                     (select distinct title,edition,author from item where itemid=i1.itemid)"
                + "                     =(select distinct title,edition,author from item where itemid=r1.itemid) "
                + "    join item i2 on  "
                + "                     (select distinct title,edition,author from item where itemid=i2.itemid)"
                + "                     =(select distinct title,edition,author from item where itemid=r2.itemid)"
                + " where r1.ratingDate<r2.ratingDate and r1.stars<=r2.stars and i1.title=i2.title";
        userModel = DefualtUserSearch(sql);
        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }

    public void LPL()
    {
        String lib = view.admin.libCB.getSelectedItem().toString();
        String sql = "select count(*) "
                + "from loan join item on item.itemid=loan.itemid "
                + "where libraryBranchID=(select librarybranchid from libraryBranch where branchname=?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, lib);
            ResultSet rs = stmt.executeQuery();

            userModel = buildTableModel(rs);
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

        userModel.fireTableDataChanged();
        view.admin.setAdminUserTable(userModel);
        view.admin.scrollPane.repaint();
    }
//  ADMIN MODELS

    public DefaultTableModel addUser(String uname, String usertype,
            String prefBranch, String loans)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        String sql
                = "insert into person(uname,usertype,preferredbranch,totalLoansMade) values(?,?,?,?)";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uname);
            stmt.setString(2, usertype);
            stmt.setInt(3, Integer.parseInt(prefBranch));
            stmt.setInt(4, Integer.parseInt(loans));
            System.out.println(stmt.toString());
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

    public DefaultTableModel delUser(String uID)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "DELETE FROM person where PersonId = ?";
        try
        {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(uID));
            System.out.println(stmt.toString());
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

}
