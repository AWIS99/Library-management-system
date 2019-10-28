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

public class ViewOfStudentDataBaseTable {

	private JFrame frame;
	private JTable table;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewOfStudentDataBaseTable window = new ViewOfStudentDataBaseTable();
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
	public ViewOfStudentDataBaseTable() {
		initialize();
		extract();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 209);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extract();
			}
		});
		btnRefresh.setBounds(113, 227, 89, 23);
		frame.getContentPane().add(btnRefresh);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnClose.setBounds(233, 227, 89, 23);
		frame.getContentPane().add(btnClose);
	}
	
	public void extract()
    {
        String sql = "SELECT * FROM StudentData";
 
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
