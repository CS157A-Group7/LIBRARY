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
    RatingFrame ratingFrame;
    UserView user;
    AdminView admin;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LibView(LibController cont) {
        login = new Login();
        ratingFrame = new RatingFrame();
        user = new UserView();
        admin = new AdminView();
    }
   
    class mainView extends JFrame {
        JTabbedPane tabbedPane;
        
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public mainView() {
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(900,400);
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
        JComboBox userLibCB;
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
        JButton avSearch;
        JButton popSearch;
        JButton ratingSearch;
        JButton ratedHigher;
        JButton sameAuthor;
        JButton multiSearchButton;
        JButton rent;
        JButton rate;
        
        JComboBox authorsCB;
        JComboBox catsCB;
        
        JPanel topPanel;
        JPanel bottomPanel;
//      TopPanel Panels
        JPanel searchButtonPanel;
        JPanel byRatingPanel;
        JPanel multiSearchPanel;
        JPanel searchPanel;
//            NEW
              JPanel titlePanel;
              JPanel authorPanel;
              JPanel categoryPanel;
//      Rating              
        JPanel rating;
        JPanel ratingPanel;
        JTable ratingTable;
        JScrollPane ratingScrollPane;
        JButton reRateButton;              
//      Returns              
        JPanel returns;
        JPanel loanPanel;
        JTable loanTable;
        JScrollPane loanScrollPane;
        JButton returnButton;
        
 
        public UserView(){
            super();
            this.setTitle("Library User");
            
            account = new JPanel();
            search = new JPanel();
            rating = new JPanel();
            returns = new JPanel();
            
            tabbedPane.addTab("Account", new ImageIcon(), account, "Account");
            tabbedPane.addTab("Search", new ImageIcon(), search, "Book Search");
            tabbedPane.addTab("Rating", new ImageIcon(), rating, "Rate Book");
            tabbedPane.addTab("Return", new ImageIcon(), returns, "Return Book");

//      Account Tab

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
            nameF.setEditable(false);
            userLibCB = new JComboBox();
            submit = new JButton("Update");
            account.add(idL);
            account.add(idF);
            account.add(nameL);
            account.add(nameF);
            account.add(libL);
            account.add(userLibCB);
//            account.add(new JPanel());
            account.add(submit);
            
//      Search Tab

//          PANELS
            topPanel = new JPanel();
            bottomPanel = new JPanel();
            searchButtonPanel = new JPanel();
            byRatingPanel = new JPanel();
            searchPanel = new JPanel();
            multiSearchPanel = new JPanel();
            
            titlePanel = new JPanel();
            authorPanel = new JPanel();
            categoryPanel = new JPanel();
            
//          LABELs AND BUTTONS
            SearchField = new JTextField("Search Title...",20);
            resetSearchTable = new JButton("Reset");
            nameSearch = new JButton("Name Search");
            authorSearch = new JButton("Author Search");
            categorySearch = new JButton("Category Search");
            avSearch = new JButton("Audio/Visual");
            popSearch = new JButton("Most Popular");
            ratingSearch = new JButton("Highest Rated");
            sameAuthor = new JButton("Other books by same author");
            multiSearchButton = new JButton("Multi-Search");
            rent = new JButton("Rent");
            rate = new JButton("Rate");
//          COMBOBOX
            authorsCB = new JComboBox();
            catsCB = new JComboBox();
//          FORMATING
            scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            search.setLayout(new BorderLayout());
            topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
            bottomPanel.setLayout(new GridLayout(2,1));
            searchButtonPanel.setLayout(new BoxLayout(searchButtonPanel,BoxLayout.X_AXIS));
            byRatingPanel.setLayout(new BoxLayout(byRatingPanel,BoxLayout.X_AXIS));
            multiSearchPanel.setLayout(new BoxLayout(multiSearchPanel,BoxLayout.X_AXIS));

            titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.X_AXIS));
            authorPanel.setLayout(new BoxLayout(authorPanel,BoxLayout.X_AXIS));
            categoryPanel.setLayout(new BoxLayout(categoryPanel,BoxLayout.X_AXIS));
            
//          SEARCH FIELD
            titlePanel.add(SearchField);
            titlePanel.add(nameSearch);
            authorPanel.add(authorsCB);
            authorPanel.add(authorSearch);
            categoryPanel.add(catsCB);
            categoryPanel.add(categorySearch);
//          SEARCH BUTTONS
            searchButtonPanel.add(multiSearchButton);
            searchButtonPanel.add(resetSearchTable);
//          RATING BUTTONS  
            byRatingPanel.add(avSearch);
            byRatingPanel.add(popSearch);
            byRatingPanel.add(ratingSearch);
            byRatingPanel.add(sameAuthor); 
//          Button Panel
            topPanel.add(titlePanel);
            topPanel.add(authorPanel);
            topPanel.add(categoryPanel);
            topPanel.add(searchButtonPanel);            
            topPanel.add(byRatingPanel);
            bottomPanel.add(rent);
            bottomPanel.add(rate);
//          TABLE  
            searchPanel.add(scrollPane);
//          TAB
            search.add(topPanel, BorderLayout.PAGE_START);
            search.add(searchPanel, BorderLayout.CENTER);
            search.add(bottomPanel,BorderLayout.PAGE_END);           
//      Rating Tab
            ratingPanel = new JPanel();
            rating.setLayout(new BorderLayout());
            ratingTable = new JTable();
            ratingScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            reRateButton = new JButton("Re-Rate");
            
            ratingPanel.add(ratingScrollPane);
            rating.add(ratingPanel, BorderLayout.CENTER);
            rating.add(reRateButton, BorderLayout.PAGE_END);
//      Return Tab
            loanPanel = new JPanel();
            returns.setLayout(new BorderLayout());
            loanTable = new JTable();
            loanScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            returnButton = new JButton("Return");
            
            loanPanel.add(loanScrollPane);
            returns.add(loanPanel, BorderLayout.CENTER);
            returns.add(returnButton, BorderLayout.PAGE_END);
        }
//      Set Account Tab
        public void SetAccount(String id, String name,
                String preferedLib, String[] libraries){
            this.idF.setText(id);
            this.nameF.setText(name);
            this.userLibCB.removeAllItems();
            this.userLibCB.setModel(new DefaultComboBoxModel(libraries));
            this.userLibCB.insertItemAt(preferedLib, 0);
            this.repaint();
        }
//      Set Search Tab
        public void SetSearchCB(String[] authors, String[] cats){
//            this.libsCB.setModel(new DefaultComboBoxModel(libs));
//            this.libsCB.insertItemAt("None", 0);
            this.authorsCB.setModel(new DefaultComboBoxModel(authors));
            this.authorsCB.insertItemAt("None", 0);
//            this.authorsCB.remove(1);
            this.authorsCB.setSelectedIndex(0);
            this.catsCB.setModel(new DefaultComboBoxModel(cats));
            this.catsCB.insertItemAt("None", 0);
//            this.catsCB.remove(1);
            this.catsCB.setSelectedIndex(0);
            this.repaint();
        }
        
//      Call this when you want to update User Table
        public void setUserItemTable(DefaultTableModel itemTableModel){
            searchPanel.removeAll();
            itemTable = new JTable(itemTableModel);
            itemTable.setSize(new Dimension(800, 200));
            itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane = new JScrollPane(itemTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            searchPanel.add(scrollPane);
            this.repaint();
            this.pack();
        }
        public void setRatingItemTable(DefaultTableModel itemTableModel){
            ratingPanel.removeAll();
            ratingTable = new JTable(itemTableModel);
            ratingTable.setSize(new Dimension(500, 200));
            ratingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ratingScrollPane = new JScrollPane(ratingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            ratingPanel.add(ratingScrollPane);
            this.repaint();
            this.pack();
        }
        //      Call this when you want to update Return Table
        public void setLoanItemTable(DefaultTableModel itemTableModel){
            loanPanel.removeAll();
            loanTable = new JTable(itemTableModel);
            loanTable.setSize(new Dimension(500, 200));
            loanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            loanScrollPane = new JScrollPane(loanTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            loanPanel.add(loanScrollPane);
            this.repaint();
            this.pack();
        }
        
        void addUpdateInfoListener(ActionListener al){submit.addActionListener(al);}  
        void addResetSearchListener(ActionListener al){resetSearchTable.addActionListener(al);}
        void addNameSearchListener(ActionListener al){nameSearch.addActionListener(al);}
        void addCatSearchListener(ActionListener al){authorSearch.addActionListener(al);}
        void addCategorySearchListener(ActionListener al){categorySearch.addActionListener(al);}
        void addAVSearchListener(ActionListener al){avSearch.addActionListener(al);}
        void addPopularSearchListener(ActionListener al){popSearch.addActionListener(al);}
        void addRatingSearchListener(ActionListener al){ratingSearch.addActionListener(al);}
        void addSameAuthorListener(ActionListener al){sameAuthor.addActionListener(al);}
        void addMultiSearchListener(ActionListener al){multiSearchButton.addActionListener(al);}
        void addRentListener(ActionListener al){rent.addActionListener(al);}
        void addRateListener(ActionListener al){rate.addActionListener(al);}
        void addReRateListener(ActionListener al){reRateButton.addActionListener(al);}
        void addReturnListener(ActionListener al){returnButton.addActionListener(al);}
        
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
        JPanel statP;
        JButton loaner;
        JButton noLoanUser;
        JButton overdueUser;
        JButton ratingUser;
        JComboBox libCB;
        JButton loansLib;
        JButton resetButton;
        
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
            statP = new JPanel();
            userTabelP = new JPanel();
            scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            bottomP.setLayout(new GridLayout(3,1));
            addP.setLayout(new FlowLayout());
            addP.setAlignmentX(FlowLayout.TRAILING);
            delP.setLayout(new FlowLayout());
            delP.setAlignmentX(FlowLayout.TRAILING);
            statP.setLayout(new FlowLayout());
            statP.setAlignmentX(FlowLayout.TRAILING);

//              ButtonPanel
                addUser = new JButton("Add User");
                delUser = new JButton("Delete User");
                noLoanUser = new JButton("No Loans");
                loaner = new JButton("Loaners");
                overdueUser = new JButton("Overdue Users");
                ratingUser  = new JButton("Higher Ratings");
                libCB = new JComboBox();
                loansLib = new JButton("Loans Per Library");
                resetButton = new JButton("Reset");
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
                statP.add(loaner);
                statP.add(noLoanUser);
                statP.add(overdueUser);
                statP.add(ratingUser);
                statP.add(libCB);
                statP.add(loansLib);
                statP.add(resetButton);
                bottomP.add(addP);
                bottomP.add(delP);
                bottomP.add(statP);
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
        
        public void SetLibCB(String[] lib){
            this.libCB.setModel(new DefaultComboBoxModel(lib));
            this.repaint();
        }
        
        void addAddUserListener(ActionListener al){addUser.addActionListener(al);}
        void addDelUserListener(ActionListener al){delUser.addActionListener(al);}
        void addLoanUserListener(ActionListener al){loaner.addActionListener(al);}
        void addNoLoanUserListener(ActionListener al){noLoanUser.addActionListener(al);}
        void addOverdueUserListener(ActionListener al){overdueUser.addActionListener(al);}
        void addRatingTwiceListener(ActionListener al){ratingUser.addActionListener(al);}
        void addLPLListener(ActionListener al){loansLib.addActionListener(al);}
        void addResetUserListener(ActionListener al){resetButton.addActionListener(al);}

        
        
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
    
    public void Error(String str){
        JFrame warning = new JFrame();
        JOptionPane.showMessageDialog(warning,
                str, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    class RatingFrame extends JFrame{
        
        JLabel label;
        JButton submit;
        JPanel mainP;
        JPanel radioP;
        ButtonGroup group;
        JRadioButton one;
        JRadioButton two;
        JRadioButton three;
        JRadioButton four;
        JRadioButton five;
        
        public RatingFrame(){
            this.setLocationRelativeTo(null);
            this.setTitle("Rate");
            this.setSize(200,150);
            
            label = new JLabel("");
            submit = new JButton("Rate");
            mainP = new JPanel();
            radioP = new JPanel();
            group = new ButtonGroup();
            one = new JRadioButton("1");
            one.setActionCommand("1");
            two = new JRadioButton("2");
            two.setActionCommand("2");
            three = new JRadioButton("3");
            three.setActionCommand("3");
            four = new JRadioButton("4");
            four.setActionCommand("4");
            five = new JRadioButton("5");
            five.setActionCommand("5");
            
            group.add(one);
            group.add(two);
            group.add(three);
            group.add(four);
            group.add(five);
            
            mainP.setLayout(new BorderLayout());
            radioP.setLayout(new GridLayout(1,0));
            radioP.add(one);
            radioP.add(two);
            radioP.add(three);
            radioP.add(four);
            radioP.add(five);
            mainP.add(label, BorderLayout.PAGE_START);
            mainP.add(radioP, BorderLayout.CENTER);
            mainP.add(submit, BorderLayout.PAGE_END);
            this.add(mainP);
            this.setVisible(false);
        }
        void addSubmitListener(ActionListener al){submit.addActionListener(al);}
        public void setLabel(String str){
            label.setText(str);
            this.repaint();
        }
    }
    
}
