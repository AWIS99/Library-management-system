import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JComboBox;

public class IssueBook {

	private JFrame frame1;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	Connection conn = this.connect();

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IssueBook window = new IssueBook();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IssueBook() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame1 = new JFrame();
		frame1.setBounds(100, 100, 450, 300);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOK ID");
		lblNewLabel.setBounds(101, 31, 64, 14);
		frame1.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("BOOK NAME");
		lblNewLabel_1.setBounds(101, 72, 86, 14);
		frame1.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("BOOK GROUP ID");
		lblNewLabel_2.setBounds(101, 111, 99, 14);
		frame1.getContentPane().add(lblNewLabel_2);
		
		JLabel lblRegistrationNumber = new JLabel("REG. NUMBER");
		lblRegistrationNumber.setBounds(101, 152, 86, 14);
		frame1.getContentPane().add(lblRegistrationNumber);
		
		
		JButton btnIssue = new JButton("ISSUE");
		btnIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String n =comboBox.getSelectedItem().toString();
					int selectedItem =Integer.parseInt(n);
					
					String m =comboBox_1.getSelectedItem().toString();
					int selectedItem_1 =Integer.parseInt(m);
					
					String selectedItem_2 = comboBox_2.getSelectedItem().toString();
					String selectedItem_3 = comboBox_3.getSelectedItem().toString();
					
					Trigger(selectedItem_1,selectedItem_2,selectedItem_3,selectedItem);
					
					comboBox.setSelectedIndex(0);
					comboBox_1.setSelectedIndex(0);
					comboBox_2.setSelectedIndex(0);
					comboBox_3.setSelectedIndex(0);
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame1, "Invalid data type is not accepted. Pleaset try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					comboBox.setSelectedIndex(0);
					comboBox_1.setSelectedIndex(0);
					comboBox_2.setSelectedIndex(0);
					comboBox_3.setSelectedIndex(0);
				}
				
			}
		});
		btnIssue.setBounds(165, 200, 89, 23);
		frame1.getContentPane().add(btnIssue);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibrarianSuccess.main(new String[]{});
				frame1.dispose();
			}
		});
		btnBack.setBounds(165, 227, 89, 23);
		frame1.getContentPane().add(btnBack);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox.setBounds(210, 149, 148, 20);
		frame1.getContentPane().add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox_1.setBounds(210, 28, 148, 20);
		frame1.getContentPane().add(comboBox_1);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox_2.setBounds(210, 69, 148, 20);
		frame1.getContentPane().add(comboBox_2);
		
		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox_3.setBounds(210, 108, 148, 20);
		frame1.getContentPane().add(comboBox_3);
		
		fillComboBox();
		fillComboBox_1();
		fillComboBox_2();
		fillComboBox_3();
	}
	
	private Connection connect() {
        // SQLite connection string
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path=home.getAbsolutePath();
		
        String url = "jdbc:sqlite:"+path+"\\STU_DATA.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public void fillComboBox()
	{
		try {
			String query="select * from StudentData";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox.addItem(rs.getString("REG_NUMBER"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillComboBox_1()
	{
		try {
			String query="select * from BookData";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox_1.addItem(rs.getString("BOOK_ID"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillComboBox_2()
	{
		try {
			String query="select DISTINCT BOOK_NAME from BookData";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox_2.addItem(rs.getString("BOOK_NAME"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillComboBox_3()
	{
		try {
			String query="select DISTINCT BOOK_GROUP_ID from BookData";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox_3.addItem(rs.getString("BOOK_GROUP_ID"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
  
    /**
     * Insert a new row into the  table
     
     */
    public void insert(int BOOK_ID, String BOOK_NAME, String BOOK_GROUP_ID, int REG_NUMBER, String NAME, String DATE_AND_TIME) {
        String sql = "INSERT INTO TABLE1(BOOK_ID,BOOK_NAME,BOOK_GROUP_ID,REG_NUMBER,NAME,DATE_AND_TIME) VALUES(?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, BOOK_ID);
            pstmt.setString(2, BOOK_NAME);
            pstmt.setString(3, BOOK_GROUP_ID);
            pstmt.setInt(4, REG_NUMBER);
            pstmt.setString(5, NAME);
            pstmt.setString(6, DATE_AND_TIME);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame1,"Book issued successfully!!");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        	System.out.println(e.getErrorCode());
        	if(e.getErrorCode()==19)
        		{
        		JOptionPane.showMessageDialog(frame1, "This BOOK has already been issued. Try again with a different one!!", "Database Error!", JOptionPane.ERROR_MESSAGE);	
        		comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				comboBox_2.setSelectedIndex(0);
				comboBox_3.setSelectedIndex(0);
				}
        }
    }
 
    /**
     * @param args the command line arguments
     */
    
   
    public void Trigger(int i,String s1, String s2, int j) {
    	String g=fetchName(j); 
        DateTimeExample ob= new DateTimeExample();
        insert(i,s1,s2,j,g,ob.Fetch());
                
    }
    
    public String fetchName(int regnum)
    {
    	String name="";
        String sql = "SELECT NAME FROM StudentData WHERE REG_NUMBER = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            pstmt.setInt(1, regnum);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next())
            {
                name=rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return name;
    }
}
