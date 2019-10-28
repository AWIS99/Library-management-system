import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Library {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Library window = new Library();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	
	File home = FileSystemView.getFileSystemView().getHomeDirectory();
	String path=home.getAbsolutePath();
	
	public Library() {
		initialize();
		
		
		File tmpDir = new File(path+"\\STU_DATA.db");
		boolean exists = tmpDir.exists();
		
		if(exists==false)
			{
			createNewDatabase();
			createNewTable();
			createNewTable1();
			createNewTable2();
			}
		else
			System.out.println("The database is already present");
		
		
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 309);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GCE LIBRARY MANAGEMENT SYSTEM");
		lblNewLabel.setForeground(new Color(51, 0, 153));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(23, 83, 388, 27);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("WELCOME TO");
		lblNewLabel_1.setForeground(new Color(51, 0, 153));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setBounds(139, 55, 155, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("LIBRARIAN  LOGIN");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianLogin.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnNewButton_1.setBounds(139, 171, 155, 37);
		frame.getContentPane().add(btnNewButton_1);
	}
	
	public void createNewDatabase() {
		 
        String url = "jdbc:sqlite:"+path+"\\STU_DATA.db";
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+path+"\\STU_DATA.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS TABLE1 (\n"
                + "	BOOK_ID integer PRIMARY KEY,\n"
                + "	BOOK_NAME text NOT NULL,\n"
                + "	BOOK_GROUP_ID text NOT NULL,\n"
                + "	REG_NUMBER integer NOT NULL,\n"
                + "	NAME text NOT NULL,\n"
                + "	DATE_AND_TIME text NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void createNewTable1() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+path+"\\STU_DATA.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS BookData (\n"
                + "	BOOK_ID integer PRIMARY KEY,\n"
                + "	BOOK_GROUP_ID string(10) NOT NULL,\n"
                + "	BOOK_NAME string(80) NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void createNewTable2() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+path+"\\STU_DATA.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS StudentData (\n"
                + "	NAME varchar(50) NOT NULL,\n"
                + "	REG_NUMBER integer PRIMARY KEY,\n"
                + "	BRANCH varchar(50) NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
}
