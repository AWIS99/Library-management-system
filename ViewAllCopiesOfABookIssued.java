import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ViewAllCopiesOfABookIssued {

	private JFrame frame;
	private JTable table;
	private JComboBox comboBox;
	
	Connection conn=this.connect();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAllCopiesOfABookIssued window = new ViewAllCopiesOfABookIssued();
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
	public ViewAllCopiesOfABookIssued() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
    
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 659, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBookName = new JLabel("BOOK NAME");
		lblBookName.setBounds(135, 43, 77, 14);
		frame.getContentPane().add(lblBookName);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
		comboBox.setBounds(233, 40, 174, 20);
		frame.getContentPane().add(comboBox);
		fillComboBox();
		
		JButton button = new JButton("VIEW");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					String n =comboBox.getSelectedItem().toString();
					
					if(n != "- SELECT -")
						extract(n);
					else
					{
						JOptionPane.showMessageDialog(frame, "No option selected. Please try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
						comboBox.setSelectedIndex(0);
						DefaultTableModel model=(DefaultTableModel) table.getModel() ;
						model.setRowCount(0);
					}
						
			}
		});
		button.setBounds(43, 215, 89, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("CLEAR");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel) table.getModel() ;
		        model.setRowCount(0);
		        comboBox.setSelectedIndex(0);
			}
		});
		button_1.setBounds(167, 215, 89, 23);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("BACK");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewPortal.main(new String[]{});
				frame.dispose();
			}
		});
		button_2.setBounds(295, 215, 89, 23);
		frame.getContentPane().add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 623, 119);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNoOfBooks = new JButton("VIEW DETAILS");
		btnNoOfBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String n =comboBox.getSelectedItem().toString();
				if(n != "- SELECT -")
				    {
					    int booksissued=find(n);
					    int booksleft=total(n)-booksissued;
					    JOptionPane.showMessageDialog(frame,"Total number of books issued is : "+ booksissued +" and number of books remaining is : "+ booksleft); 
				    }
				else
					{
					    JOptionPane.showMessageDialog(frame, "No option selected. Please try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					    comboBox.setSelectedIndex(0);
						DefaultTableModel model=(DefaultTableModel) table.getModel() ;
						model.setRowCount(0);
					}
								
			}
		});
		btnNoOfBooks.setBounds(418, 215, 181, 23);
		frame.getContentPane().add(btnNoOfBooks);
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
			String query="select DISTINCT BOOK_NAME from BookData";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next())
			{
				comboBox.addItem(rs.getString("BOOK_NAME"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void extract(String bookname)
    {
        String sql = "SELECT * FROM TABLE1 WHERE BOOK_NAME = ?";
 
        try (Connection conn = this.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){
     
        // set the value
        pstmt.setString(1,bookname);
        //
        ResultSet rs  = pstmt.executeQuery();
        
        if(!rs.isBeforeFirst())
        	JOptionPane.showMessageDialog(frame,"No data is available against this selection.");
                
        table.setModel(DbUtils.resultSetToTableModel(rs));
                      	
                     
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
    }
	
	public int find(String bookname)
	{
		int l=0;
		String sql = "SELECT COUNT(*) FROM TABLE1 WHERE BOOK_NAME = ?";
		 
        try (Connection conn = this.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){
     
        // set the value
        pstmt.setString(1,bookname);
        //
        ResultSet rs  = pstmt.executeQuery();
        
        l=rs.getInt("count(*)");
                     
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
		return l;
	}
	
	public int total(String bookname)
	{
		int m=0;
		String sql = "SELECT COUNT(*) FROM BookData WHERE BOOK_NAME = ?";
		 
        try (Connection conn = this.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){
     
        // set the value
        pstmt.setString(1,bookname);
        //
        ResultSet rs  = pstmt.executeQuery();
        
        m=rs.getInt("count(*)");
                     
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
		return m;
	}
}
