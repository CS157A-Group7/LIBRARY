
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
        view.admin.addRatingTwiceListener(new DoubleRatingUserEL());
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
            view.user.addSameAuthorListener(new SameAuthorSearchEL());
            view.user.addMultiSearchListener(new MultiSearchEL());
            
    }
    
//    Login Listeners
    class LoginUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String param = view.login.textBox.getText();
            
//            model.loginModel.Authenticate(param);
            model.login(param);
            view.user.SetAccount(Integer.toString(model.user.userID), model.user.userName, 
                    model.getBranch(model.user.preferredBranch),
                    model.libs.toArray(new String[model.libs.size()]));
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
    class ResetUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Reset Users");
            model.ResetUserTable();
        }
     }
    class AddUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Add");
            model.AddUser();
        }
    }
    class DelUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Delete");
            model.DelUser();
        }
    }
    class OverdueUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Overdue Users");
            model.OverdueUser();
        }
    }
    class DoubleRatingUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Users Who gave a higher rating the second time.");
            model.DoubleRatingUser();
        }
    }
    class LPLEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Loans Per Library");
            model.LPL();
        }
    }
    

//      User Listeners
    class UpdateInfoEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Updating preferred branch");
            model.UpdateInfo();
        }
    }
    class ResetSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Reset Search");
            model.Reset();
        }
    }
    class NameSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Name Search");
            model.NameSearch();
        }
    }
    class AuthorSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Author Search");
            model.AuthorSearch();
        }
    }    
    class CategorySearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Category Search");
            model.CategorySearch();
        }
    } 
    class SameAuthorSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Others books by this author");
            model.SameAuthorSearch();
        }
    }  
    class PopularSearchEL implements ActionListener{
         public void actionPerformed(ActionEvent e){
            System.out.println("Popular Search");
             model.PopularSearch();
         }
    }
    class RatingSearchEL implements ActionListener{
         public void actionPerformed(ActionEvent e){
            System.out.print("Rating Search");
            model.RatingSearch();
         }
    }
    class MultiSearchEL implements ActionListener{
         public void actionPerformed(ActionEvent e){
             System.out.println("Multi-Search");
             model.MultiSearch();
         }
    }
}
