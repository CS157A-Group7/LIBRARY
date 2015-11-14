
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author GS60
 */
public class LibController {
    LibView view;
    LibModel model;
//    True is admin
    Boolean adminState;
    public LibController(){
        adminState = false;
        view = new LibView(this);
        model = new LibModel(view);
        view.login.setVisible(true);
//      Add Action Listeners
//      Login AL
        view.login.addLoginListener(new LoginUserEL());
//      Admin AL
        view.admin.addAddUserListener(new AddUserEL());
        view.admin.addDelUserListener(new DelUserEL());
        view.admin.addOverdueUserListener(new OverdueUserEL());
        view.admin.addRatingUserListener(new DoubleRaingUserEL());
        view.admin.addLPLListener(new LPLEL());
        view.admin.addResetUserListener(new ResetUserEL());
        
//      User AL
//          AcountAL
            view.user.addUpdateInfoListener(new UpdateInfoEL());
//          SearchAL
            view.user.addResetSearchListener(new ResetSearchEL());
            view.user.addNameSearchListener(new NameSearchEL());
            view.user.addCatSearchListener(new AuthorSearchEL());
            view.user.addCategorySearchListener(new CategorySearchEL());
            view.user.addPopularSearchListener(new PopularSearchEL());
            view.user.addRatingSearchListener(new RatingSearchEL());
    }
    
//    Login Listeners
    class LoginUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String param = view.login.textBox.getText();
            
//            model.loginModel.Authenticate(param);
            model.login(param);
            view.user.SetAccount(Integer.toString(model.user.userID), model.user.userName, 
                    model.getBranch(model.user.preferredBranch), model.getLibraries());
            if(model.user.userType.compareTo("A") == 0){
                System.out.println("Welcome " + model.user.userName);
                adminState = true;
                view.admin.setVisible(true);
                view.login.setVisible(false);
            }
            else if(model.user.userType.compareTo("U") == 0){
                System.out.println("Welcome " + model.user.userName);
                adminState = false;
                view.user.setVisible(true);
                view.login.setVisible(false);
                
            }
            else {
                view.login.LoginError();
            }
        }
    }
//    Admin Listeners
    class AddUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Add");
            String uname = view.admin.addUnameF.getText();
            String usertype = view.admin.addTypeCB.getSelectedItem().toString();
            String prefBranch = view.admin.addPrefBranchCB.getSelectedItem().toString();
            String loans = "0";
            model.userModel = model.addUser(uname,usertype,prefBranch,loans);
            model.userModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.itemModel);
            view.admin.scrollPane.repaint();
            
        }
    }
    class DelUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Delete");
            String uID = view.admin.delF.getText();
            model.userModel = model.delUser(uID);
            model.userModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.itemModel);
            view.admin.scrollPane.repaint();
        }
    }
    class OverdueUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Overdue Users");
            String sql =    "select PersonId,uname,count(*) " +
                            "from Person join Loan on Person.PersonId=Loan.Pid " +
                            "where overdue = true " +
                            "group by personid " +
                            "having count(*)>=2";
            model.userModel = model.DefualtUserSearch(sql);
            model.userModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.userModel);
            view.admin.scrollPane.repaint();
        }
    }
    class DoubleRaingUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Users Who Double Rated");
            String sql =    "select * " +
                            "from rating r1 join rating r2 on r1.personid=R2.PERSONID " + 
                            "join Item i1 on i1.itemid=r1.itemid " + 
                            "join item i2 on i2.itemid=r2.itemid " +
                            "where r1.ratingDate<r2.ratingDate and r1.stars<=r2.stars and i1.title=i2.title";
            model.userModel = model.DefualtUserSearch(sql);
            model.userModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.userModel);
            view.admin.scrollPane.repaint();
        }
    }
    class LPLEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Loans Per Library");
            String sql =    "select count(*) " +
                            "from loan join item on item.itemid=loan.itemid " +
                            "where libraryBranchID=(select librarybranchid from libraryBranch where branchname=?)";
            model.userModel = model.DefualtUserSearch(sql);
            model.userModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.userModel);
            view.admin.scrollPane.repaint();
        }
    }
    class ResetUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Reset Users");
            String sql = "Select * from person";
            model.userModel = model.DefualtUserSearch(sql);
            model.userModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.userModel);
            view.admin.scrollPane.repaint();
        }
     }

//      User Listeners
    class UpdateInfoEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Updating preferred branch");
            String newLib = (String)view.user.libCB.getSelectedItem();
            System.out.println(newLib);
            model.itemModel = model.updateUserBranch(view.user.idF.getText(),newLib);
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.scrollPane.repaint();
        }
    }
    class ResetSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Reset Search");
            model.itemModel = model.GenTableModel("item");
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.SearchField.setText("Enter a Book Title/Author ...");
            view.user.scrollPane.repaint();
        }
    }
    class NameSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Name Search");
            String searchParam = view.user.SearchField.getText();
            System.out.println(searchParam);
            model.itemModel = model.Search(searchParam, "item", "*", "title", model.itemModel);
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.scrollPane.repaint();
        }
    }
    class AuthorSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Author Search");
            String searchParam = view.user.SearchField.getText();
            System.out.println(searchParam);
            model.itemModel = model.Search(searchParam, "item", "*", "author", model.itemModel);
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.scrollPane.repaint();
        }
    }
    
    class CategorySearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Category Search");
            String searchParam = view.user.SearchField.getText();
            System.out.println(searchParam);
            model.itemModel = model.Search(searchParam, "item", "*", "itemtype", model.itemModel);
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.scrollPane.repaint();
        }
    }
    
    class PopularSearchEL implements ActionListener{
         public void actionPerformed(ActionEvent e){
            System.out.println("Popular Search");
            String sql =    "select title,count(*) total " +
                            "from loan join item on Loan.itemid=Item.itemid " +
                            "where ItemType='BOOK' " +
                            "group by title,standardNumber " +
                            "order by total desc " +
                            "Limit 5";
            model.itemModel = model.DefaultItemSearch(sql);
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.scrollPane.repaint();
             
         }
    }
    class RatingSearchEL implements ActionListener{
         public void actionPerformed(ActionEvent e){
            String sql =    "select title,avg(stars) " +
                            "from rating join item on rating.itemid=Item.itemid " +
                            "where ItemType='Book' " +
                            "group by title,standardnumber " +
                            "order by avg(stars) desc " +
                            "limit 5";
            model.itemModel = model.DefaultItemSearch(sql);
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
            view.user.scrollPane.repaint();
         }
    }
}
