import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;

public class BooksIssuedAgainstRegistrationNumber {

	private JFrame frame;
	private JComboBox comboBox;
	Connection conn=this.connect();
	private JTable table;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BooksIssuedAgainstRegistrationNumber window = new BooksIssuedAgainstRegistrationNumber();
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
	public BooksIssuedAgainstRegistrationNumber() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 679, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistrationNumber = new JLabel("REGISTRATION NUMBER");
		lblRegistrationNumber.setBounds(48, 43, 138, 14);
		frame.getContentPane().add(lblRegistrationNumber);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"- SELECT -"}));
        //comboBox.setSelectedIndex(0);
		comboBox.setBounds(222, 40, 131, 20);
		frame.getContentPane().add(comboBox);
		fillComboBox();
		
		JButton btnView = new JButton("VIEW");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String n =comboBox.getSelectedItem().toString();
					int selectedItem =Integer.parseInt(n);
					
					extract(selectedItem);
																
				}
				catch(NumberFormatException ne){
					JOptionPane.showMessageDialog(frame, "No option selected. Please try again", "Database Error!", JOptionPane.ERROR_MESSAGE);
					comboBox.setSelectedIndex(0);
				}
			}
		});
		btnView.setBounds(167, 184, 89, 23);
		frame.getContentPane().add(btnView);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewPortal.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(419, 184, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 643, 106);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model=(DefaultTableModel) table.getModel() ;
		        model.setRowCount(0);
		        comboBox.setSelectedIndex(0);
			}
		});
		btnClear.setBounds(291, 184, 89, 23);
		frame.getContentPane().add(btnClear);
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
			String query="select DISTINCT REG_NUMBER from TABLE1";
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
	
    public void extract(int regnum)
    {
        String sql = "SELECT * FROM TABLE1 WHERE REG_NUMBER = ?";
 
        try (Connection conn = this.connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){
     
        // set the value
        pstmt.setInt(1,regnum);
        //
        ResultSet rs  = pstmt.executeQuery();
        
        table.setModel(DbUtils.resultSetToTableModel(rs));
                     
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
    }
}
