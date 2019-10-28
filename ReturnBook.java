import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ReturnBook {

	private JFrame frame;
	Connection conn = this.connect();
	private JComboBox comboBox;
	private JComboBox comboBox_1;

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnBook window = new ReturnBook();
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
	public ReturnBook() {
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
		
		JLabel lblReturnBook = new JLabel("RETURN BOOK");
		lblReturnBook.setBounds(146, 25, 92, 14);
		frame.getContentPane().add(lblReturnBook);
		
		JLabel lblBookId = new JLabel("BOOK ID");
		lblBookId.setBounds(53, 76, 58, 14);
		frame.getContentPane().add(lblBookId);
		
		JLabel lblRegistrationNumber = new JLabel("REGISTRATION NUMBER");
		lblRegistrationNumber.setBounds(53, 117, 140, 14);
		frame.getContentPane().add(lblRegistrationNumber);
		
		JButton btnReturn = new JButton("RETURN");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String n =comboBox.getSelectedItem().toString();
					int selectedItem =Integer.parseInt(n);
					
					String m =comboBox_1.getSelectedItem().toString();
					int selectedItem_1 =Integer.parseInt(m);
					
					if(check(selectedItem,selectedItem_1)==1)
					    deleteRow(selectedItem,selectedItem_1);
					else {
						JOptionPane.showMessageDialog(frame, "This book has not been issued against this registration number. Please try again!", "Database Error!", JOptionPane.ERROR_MESSAGE);
						comboBox.setSelectedIndex(0);
						comboBox_1.setSelectedIndex(0);
					}
						
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "No option selected. Please try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					comboBox.setSelectedIndex(0);
				}
			}
		});
		btnReturn.setBounds(138, 177, 89, 23);
		frame.getContentPane().add(btnReturn);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianSuccess.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(138, 211, 89, 23);
		frame.getContentPane().add(btnBack);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox.setBounds(250, 73, 121, 20);
		frame.getContentPane().add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox_1.setBounds(250, 114, 121, 20);
		frame.getContentPane().add(comboBox_1);
		
		fillComboBox();
		fillComboBox_1();
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
			String query="select * from TABLE1";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox.addItem(rs.getString("BOOK_ID"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillComboBox_1()
	{
		try {
			String query="select DISTINCT REG_NUMBER from TABLE1";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox_1.addItem(rs.getString("REG_NUMBER"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int check(int n,int m)
	{
		int k=5;
		String sql = "SELECT (COUNT(*) > 0) as found FROM TABLE1 WHERE TABLE1.BOOK_ID = ? AND TABLE1.REG_NUMBER= ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			 
            pstmt.setInt(1, n);
            pstmt.setInt(2, m);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Only expecting a single result
                if (rs.next()) {
                    boolean found = rs.getBoolean(1); // "found" column
                    if (found) {
                        k=1;// You have rows
                    } else {
                    	k=0;// You have no rows
                    }
                }
            }
            
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return k;
	}
	
	public void deleteRow(int n,int m)
	{
        String sql = "DELETE FROM TABLE1 WHERE BOOK_ID = ? AND REG_NUMBER = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            pstmt.setInt(1, n);
            pstmt.setInt(2, m);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame,"Book returned successfully!!");
            
            comboBox.removeAllItems();
            comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
            comboBox.setSelectedIndex(0);
            fillComboBox();
            
            comboBox_1.removeAllItems();
            comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
            comboBox_1.setSelectedIndex(0);
            fillComboBox_1();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}
