import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewPortal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPortal window = new ViewPortal();
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
	public ViewPortal() {
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
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianSuccess.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(163, 227, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JLabel lblViewIssuedBooks = new JLabel("VIEW PORTAL");
		lblViewIssuedBooks.setBounds(171, 24, 81, 14);
		frame.getContentPane().add(lblViewIssuedBooks);
		
		JButton btnNewButton_1 = new JButton("VIEW BOOKS ISSUED AGAINST A REGISTRATION NUMBER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BooksIssuedAgainstRegistrationNumber.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(57, 58, 323, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("VIEW ALL COPIES OF A BOOK ISSUED");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewAllCopiesOfABookIssued.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(57, 92, 323, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnViewOfBook = new JButton("VIEW OF BOOK DATABASE TABLE");
		btnViewOfBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOfBookDataBaseTable.main(new String[]{});
			}
		});
		btnViewOfBook.setBounds(57, 126, 323, 23);
		frame.getContentPane().add(btnViewOfBook);
		
		JButton btnViewOfStudent = new JButton("VIEW OF STUDENT DATABASE TABLE");
		btnViewOfStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOfStudentDataBaseTable.main(new String[]{});
			}
		});
		btnViewOfStudent.setBounds(57, 160, 323, 23);
		frame.getContentPane().add(btnViewOfStudent);
		
		JButton btnViewOfIssued = new JButton("VIEW OF ISSUED BOOK DATABASE TABLE");
		btnViewOfIssued.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOfIssuedBookDatabaseTable.main(new String[]{});
			}
		});
		btnViewOfIssued.setBounds(57, 194, 323, 23);
		frame.getContentPane().add(btnViewOfIssued);
	}
}
