import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class AddBook {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBook window = new AddBook();
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
	public AddBook() {
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
		
		JLabel lblAddBook = new JLabel("ADD BOOK");
		lblAddBook.setBounds(176, 27, 64, 14);
		frame.getContentPane().add(lblAddBook);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int bookid=Integer.parseInt(textField.getText());
					String bookgroupid=textField_1.getText();
					String bookname=textField_2.getText();
										
					
					Trigger(bookid,bookgroupid,bookname);
					
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
							
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "Invalid data type is not accepted. Pleaset try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					
				}
			}
		});
		btnAdd.setBounds(158, 193, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookDatabase.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(158, 227, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JLabel lblBookId = new JLabel("BOOK ID");
		lblBookId.setBounds(93, 65, 59, 14);
		frame.getContentPane().add(lblBookId);
		
		JLabel lblBookName = new JLabel("BOOK NAME");
		lblBookName.setBounds(93, 117, 76, 14);
		frame.getContentPane().add(lblBookName);
		
		JLabel lblBookGroupId = new JLabel("BOOK GROUP ID");
		lblBookGroupId.setBounds(93, 90, 102, 14);
		frame.getContentPane().add(lblBookGroupId);
		
		textField = new JTextField();
		textField.setBounds(205, 62, 111, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(205, 87, 111, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(205, 114, 111, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
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
    public void insert(int BOOK_ID, String BOOK_GROUP_ID, String BOOK_NAME) {
        String sql = "INSERT INTO BookData(BOOK_ID,BOOK_GROUP_ID,BOOK_NAME) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, BOOK_ID);
            pstmt.setString(2, BOOK_GROUP_ID);
            pstmt.setString(3, BOOK_NAME );
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame,"Book added successfully!!");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        	System.out.println(e.getErrorCode());
        	if(e.getErrorCode()==19)
        		{
        		JOptionPane.showMessageDialog(frame, "This BOOK ID is alrady registered in the database. Try again with a different one!!", "Database Error!", JOptionPane.ERROR_MESSAGE);	
        		textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				}
        }
    }
 
     
   
    public void Trigger(int i,String s1, String s2) {
 
        insert(i,s1,s2);
                
    }
}
