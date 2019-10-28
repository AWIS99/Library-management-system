import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddStudent {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddStudent window = new AddStudent();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddStudent() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAddStudentData = new JLabel("ADD STUDENT DATA");
		lblAddStudentData.setBounds(170, 11, 123, 14);
		frame.getContentPane().add(lblAddStudentData);
		
		JLabel lblName = new JLabel("NAME");
		lblName.setBounds(88, 70, 46, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblRegNumber = new JLabel("REG. NUMBER");
		lblRegNumber.setBounds(88, 112, 85, 14);
		frame.getContentPane().add(lblRegNumber);
		
		JLabel lblBranch = new JLabel("BRANCH");
		lblBranch.setBounds(88, 175, 61, 14);
		frame.getContentPane().add(lblBranch);
		
		textField = new JTextField();
		textField.setBounds(176, 67, 220, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(176, 109, 193, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"-SELECT-", "COMPUTER SCIENCE", "ELECTRICAL", "MECHANICAL", "MINERAL", "MINING", "METALLURGY", "CIVIL"}));
		comboBox.setBounds(176, 172, 159, 20);
		frame.getContentPane().add(comboBox);
		
			
		
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String name=textField.getText();
					int regnum=Integer.parseInt(textField_1.getText());
					String selectedItem = comboBox.getSelectedItem().toString();
					
					if (selectedItem=="-SELECT-")
						JOptionPane.showMessageDialog(frame, "Default selected option is not accepted. Pleaset try again !!", "User Error!", JOptionPane.ERROR_MESSAGE);
					
					else Trigger2(name,regnum,selectedItem);
					
					textField.setText("");
					textField_1.setText("");
					comboBox.setSelectedIndex(0);
							
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "Invalid data type is not accepted. Pleaset try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					textField_1.setText("");
					comboBox.setSelectedIndex(0);
				}
				
			}
		});
		btnAdd.setBounds(173, 207, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentDatabase.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(173, 227, 89, 23);
		frame.getContentPane().add(btnBack);
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
 
    /**
     * Insert a new row into the  table
     
     */
    public void insert(String NAME,int REG_NUMBER, String BRANCH) {
        String sql = "INSERT INTO StudentData(NAME,REG_NUMBER,BRANCH) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, NAME);
            pstmt.setInt(2, REG_NUMBER);
            pstmt.setString(3, BRANCH);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame,"Data added successfully!!");
        } 
        catch (SQLException e) {
        	System.out.println(e.getMessage());
        	System.out.println(e.getErrorCode());
        	if(e.getErrorCode()==19)
        		{
        		JOptionPane.showMessageDialog(frame, "This registration number is alrady registered in the database. Try again with a different one!!", "Database Error!", JOptionPane.ERROR_MESSAGE);	
        		textField.setText("");
				textField_1.setText("");
				comboBox.setSelectedIndex(0);
        		}
       	   
        }
    }
	public void Trigger2(String i, int j ,String k) {
		           
       insert(i,j,k);
    }
	
}
