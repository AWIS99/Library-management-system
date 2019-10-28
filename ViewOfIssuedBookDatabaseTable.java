import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class ViewOfIssuedBookDatabaseTable {

	private JFrame frame;
	private JTable table;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewOfIssuedBookDatabaseTable window = new ViewOfIssuedBookDatabaseTable();
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
	public ViewOfIssuedBookDatabaseTable() {
		initialize();
		extract();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 901, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 865, 204);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("REFRESH");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extract();
			}
		});
		btnNewButton.setBounds(215, 227, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnClear = new JButton("CLOSE");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnClear.setBounds(360, 227, 89, 23);
		frame.getContentPane().add(btnClear);
	}
	
	public void extract()
    {
        String sql = "SELECT * FROM TABLE1";
 
        try (Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();){
     
        ResultSet rs    = stmt.executeQuery(sql);
        
        table.setModel(DbUtils.resultSetToTableModel(rs));
                     
        } catch (SQLException e) {
          System.out.println(e.getMessage());
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

}
