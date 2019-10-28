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

public class DeleteBook {

	private JFrame frame;
	private JComboBox comboBox;
	Connection conn = this.connect();

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteBook window = new DeleteBook();
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
	public DeleteBook() {
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
		
		JLabel lblDeleteBook = new JLabel("DELETE BOOK");
		lblDeleteBook.setBounds(166, 24, 111, 14);
		frame.getContentPane().add(lblDeleteBook);
		
		JButton button = new JButton("BACK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookDatabase.main(new String[]{});
				frame.dispose();
			}
		});
		button.setBounds(162, 204, 89, 23);
		frame.getContentPane().add(button);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String n =comboBox.getSelectedItem().toString();
					int selectedItem =Integer.parseInt(n);
					delete(selectedItem);					
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "NO option selected. Please try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					comboBox.setSelectedIndex(0);
				}
			}
		});
		btnDelete.setBounds(162, 156, 89, 23);
		frame.getContentPane().add(btnDelete);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox.setBounds(228, 63, 139, 20);
		frame.getContentPane().add(comboBox);
		fillComboBox();
		
		JLabel lblUniqueBookId = new JLabel("UNIQUE BOOK ID");
		lblUniqueBookId.setBounds(46, 66, 169, 14);
		frame.getContentPane().add(lblUniqueBookId);
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
			String query="select * from BookData";
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
	
	public void delete(int bookid) {
        String sql = "DELETE FROM BookData WHERE BOOK_ID = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            
            pstmt.setInt(1, bookid);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame,"Book deleted successfully!!");
            
            comboBox.removeAllItems();
            comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
            comboBox.setSelectedIndex(0);
            fillComboBox();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
