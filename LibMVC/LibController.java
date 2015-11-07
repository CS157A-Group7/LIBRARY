
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
//      User AL
//          AcountAL
            view.user.addUpdateInfoListener(new UpdateInfoEL());
//          SearchAL
            view.user.addResetSearchListener(new ResetSearchEL());
            view.user.addNameSearchListener(new NameSearchEL());
            view.user.addCatSearchListener(new AuthorSearchEL());
    }
    
//    Login Listeners
    class LoginUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String param = view.login.textBox.getText();
            
//            model.loginModel.Authenticate(param);
            
            if(param.toLowerCase().compareTo("admin") == 0){
                
                adminState = true;
                view.admin.setVisible(true);
                view.login.setVisible(false);
            }
            else if(param.toLowerCase().compareTo("user") == 0){
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
            model.itemModel = model.addUser(uname,usertype,prefBranch,loans);
            model.itemModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.itemModel);
            view.admin.scrollPane.repaint();
            
        }
    }
    class DelUserEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Delete");
            String uID = view.admin.delF.getText();
            model.itemModel = model.delUser(uID);
            model.itemModel.fireTableDataChanged();
            view.admin.setAdminUserTable(model.itemModel);
            view.admin.scrollPane.repaint();
        }
    }
//      User Listeners
    class UpdateInfoEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Update Info");
        }
    }
    class ResetSearchEL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("Name Search");
            model.itemModel = model.GenTableModel("item");
            model.itemModel.fireTableDataChanged();
            view.user.setUserItemTable(model.itemModel);
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
}
