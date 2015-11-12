/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author GS60
 */
public class LibView{
//    Test Data
    Object[][] data = {};
    String[] columnNames = {};
    
    Login login;
    UserView user;
    AdminView admin;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LibView(LibController cont) {
        login = new Login();
        user = new UserView();
        admin = new AdminView();
    }
   
    class mainView extends JFrame {
        JTabbedPane tabbedPane;
        
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public mainView() {
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(750,400);
            this.setLayout(new BorderLayout());
            tabbedPane = new JTabbedPane();
            this.add(tabbedPane);
            
        }
    }
    class UserView extends mainView {
//      Account Tab Var
        JPanel account;
        JLabel idL;
        JLabel nameL;
        JLabel libL;
        JTextField idF;
        JTextField nameF;
        JComboBox libCB;
        JButton submit;
        
//      Search Tab Var
        JPanel search;
        JTable itemTable;
        JScrollPane scrollPane;
        JTextArea textArea;
        JLabel searchLabel;
        JTextField SearchField;
        JButton resetSearchTable;
        JButton nameSearch;
        JButton authorSearch;
        JButton categorySearch;
        JPanel topPanel;
        JPanel searchButtonPanel;
        JPanel searchPanel;
 
        public UserView(){
            super();
            this.setTitle("Library User");
            
            account = new JPanel();
            search = new JPanel();
            
            tabbedPane.addTab("Account", new ImageIcon(), account, "Account");
            tabbedPane.addTab("Search", new ImageIcon(), search, "Book Search");

//          Account Tab

            account.setLayout(new GridLayout(7,1,2,4));
            idL = new JLabel("User ID:");
            idL.setHorizontalAlignment(SwingConstants.LEFT);
            nameL = new JLabel("Name:");
            nameL.setHorizontalAlignment(SwingConstants.LEFT);
            libL = new JLabel("Library");
            libL.setHorizontalAlignment(SwingConstants.LEFT);
            idF = new JTextField("ID",20);
            idF.setEditable(false);
            nameF = new JTextField("name",20);
            libCB = new JComboBox();
            submit = new JButton("Update");
            account.add(idL);
            account.add(idF);
            account.add(nameL);
            account.add(nameF);
            account.add(libL);
            account.add(libCB);
//            account.add(new JPanel());
            account.add(submit);
            

//          Search Tab
            topPanel = new JPanel();
            searchButtonPanel = new JPanel();
            searchPanel = new JPanel();
            SearchField = new JTextField("Enter a Book Title/Author ...",20);
            resetSearchTable = new JButton("Reset");
            nameSearch = new JButton("Name Search");
            authorSearch = new JButton("Author Search");
            categorySearch = new JButton("Category Search");
            scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            search.setLayout(new BorderLayout());
            topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
            searchButtonPanel.setLayout(new BoxLayout(searchButtonPanel,BoxLayout.X_AXIS));
            topPanel.add(SearchField);
            searchButtonPanel.add(nameSearch);
            searchButtonPanel.add(authorSearch);
            searchButtonPanel.add(categorySearch);
            searchButtonPanel.add(resetSearchTable);
            topPanel.add(searchButtonPanel);
            searchPanel.add(scrollPane);
            search.add(topPanel, BorderLayout.NORTH);
            search.add(searchPanel, BorderLayout.CENTER);
        }
//        Set Account Tab
        public void SetAccount(String id, String name,
                String preferedLib, String[] libraries){
            this.idF.setText(id);
            this.nameF.setText(name);
            this.libCB.removeAllItems();
            this.libCB.setModel(new DefaultComboBoxModel(libraries));
            this.libCB.insertItemAt(preferedLib, 0);
            this.repaint();
        }
        
//      Call this when you want to update User Table
        public void setUserItemTable(DefaultTableModel itemTableModel){
            searchPanel.removeAll();
            itemTable = new JTable(itemTableModel);
            itemTable.setSize(new Dimension(500, 200));
            itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane = new JScrollPane(itemTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            searchPanel.add(scrollPane);
            this.repaint();
            this.pack();
            
            
        }
        
        void addUpdateInfoListener(ActionListener al){submit.addActionListener(al);}
        
        void addResetSearchListener(ActionListener al){resetSearchTable.addActionListener(al);}
        void addNameSearchListener(ActionListener al){nameSearch.addActionListener(al);}
        void addCatSearchListener(ActionListener al){authorSearch.addActionListener(al);}
        void addCategorySearchListener(ActionListener al){categorySearch.addActionListener(al);}
    }
    
    class AdminView extends mainView {
        String[] UserStates = {"U", "A"};
        String[] LibStates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//      user Admin Tab Var
        JPanel userP;
        JPanel userTabelP;
        JPanel bottomP;
        
        JPanel addP;
        JLabel addL;
        JTextField addUnameF;
        JLabel addTypeL;
        JComboBox addTypeCB;
        JLabel addPBL;
        JComboBox addPrefBranchCB;
        
        JButton addUser;
        JPanel delP;
        JLabel delL;
        JButton delUser;
        JTextField delF;
        JTable  userTable;
        JScrollPane scrollPane;
        
        public AdminView(){
            super();
            this.setTitle("Library Admin");
            
            userP = new JPanel();

            userP.setLayout(new BorderLayout());
            tabbedPane.addTab("Users", new ImageIcon(), userP, "User Admin");
             
//          User Admin Tab
            
            bottomP = new JPanel();
            addP = new JPanel();
            addL = new JLabel("Add User:");
            addUnameF = new JTextField ("User Name...", 10);
            addTypeL = new JLabel("Status:");
            addTypeCB = new JComboBox(UserStates);
            addPBL = new JLabel("Library:");
            addPrefBranchCB = new  JComboBox(LibStates);
            delP = new JPanel();
            delL = new JLabel("Detete User");
            delF = new JTextField("User ID" , 10);
            userTabelP = new JPanel();
            scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            bottomP.setLayout(new GridLayout(2,1));
            addP.setLayout(new FlowLayout());
            addP.setAlignmentX(FlowLayout.TRAILING);
            delP.setLayout(new FlowLayout());
            delP.setAlignmentX(FlowLayout.TRAILING);

//              ButtonPanel
                addUser = new JButton("Add User");
                delUser = new JButton("Delete User");
                addP.add(addL);
                addP.add(addUnameF);
                addP.add(addTypeL);
                addP.add(addTypeCB);
                addP.add(addPBL);
                addP.add(addPrefBranchCB);
                addP.add(addUser);
                delP.add(delL);
                delP.add(delF);
                delP.add(delUser);
                bottomP.add(addP);
                bottomP.add(delP);
                userTabelP.add(scrollPane);
                
            userP.add(userTabelP, BorderLayout.CENTER);
            userP.add(bottomP, BorderLayout.PAGE_END);
                
        }

//        Call This when you want to repaint Admin User table
        public void setAdminUserTable(DefaultTableModel userTableModel){
            userTabelP.removeAll();
            userTabelP.setLayout(new BorderLayout());
            this.userTable = new JTable(userTableModel);
            userTable.setSize(new Dimension(500, 200));
            userTable.setFillsViewportHeight(true);
            userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane = new JScrollPane(userTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setSize(650, 200); 
            userTabelP.add(scrollPane, BorderLayout.CENTER);
            this.repaint();
            
        }
        
        void addAddUserListener(ActionListener al){addUser.addActionListener(al);}
        void addDelUserListener(ActionListener al){delUser.addActionListener(al);}
        
    }
    
    class Login extends JFrame {
        
        JLabel label;
        JTextField textBox;
        JButton login;
        JPanel loginPanel;
        
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public Login () {
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Login");
            this.setSize(200,150);
            this.setLayout(new GridLayout(3,1));
            
            textBox = new JTextField(20);
            label = new JLabel("Enter User ID:");
            login = new JButton("Login");
            
            this.add(label);
            this.add(textBox);
            this.add(login);
        }
        
        public void LoginError(){
            JFrame warning = new JFrame();
            JOptionPane.showMessageDialog(warning,
                    "Sorry ID does not exist",
                        "Login Error",
                            JOptionPane.ERROR_MESSAGE);
        }
        
        void addLoginListener(ActionListener al){login.addActionListener(al);}
        
    }
    
}
