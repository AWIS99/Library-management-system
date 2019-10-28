import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

public class RemoveStudent {

	private JFrame frame;
	private JComboBox comboBox;	
	Connection conn = this.connect();

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoveStudent window = new RemoveStudent();
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
	public RemoveStudent() {
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
		
		JLabel lblRemoveStudent = new JLabel("REMOVE STUDENT");
		lblRemoveStudent.setBounds(164, 22, 144, 14);
		frame.getContentPane().add(lblRemoveStudent);
		
		JLabel lblRegistrationNumber = new JLabel("REGISTRATION NUMBER");
		lblRegistrationNumber.setBounds(40, 71, 169, 14);
		frame.getContentPane().add(lblRegistrationNumber);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox.setBounds(222, 68, 139, 20);
		frame.getContentPane().add(comboBox);
		fillComboBox();
		
		JButton btnRemove = new JButton("REMOVE");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String n =comboBox.getSelectedItem().toString();
					int selectedItem =Integer.parseInt(n);
					delete(selectedItem);					
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "No option selected. Please try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					comboBox.setSelectedIndex(0);
				}
			}
		});
		btnRemove.setBounds(156, 161, 89, 23);
		frame.getContentPane().add(btnRemove);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentDatabase.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(156, 209, 89, 23);
		frame.getContentPane().add(btnBack);
		

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
	
	public void delete(int regnum) {
        String sql = "DELETE FROM StudentData WHERE REG_NUMBER = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            
            pstmt.setInt(1, regnum);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame,"Student record removed successfully!!");
            
            comboBox.removeAllItems();
            comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
            comboBox.setSelectedIndex(0);
            fillComboBox();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}







