import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookDatabase {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookDatabase window = new BookDatabase();
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
	public BookDatabase() {
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
		
		JLabel lblBookDatabase = new JLabel("BOOK DATABASE");
		lblBookDatabase.setBounds(177, 23, 96, 14);
		frame.getContentPane().add(lblBookDatabase);
		
		JButton btnAddBooks = new JButton("ADD BOOK");
		btnAddBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBook.main(new String[]{});
				frame.dispose();
			}
		});
		btnAddBooks.setBounds(146, 68, 109, 23);
		frame.getContentPane().add(btnAddBooks);
		
		JButton btnDeleteBook = new JButton("DELETE BOOK");
		btnDeleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBook.main(new String[]{});
				frame.dispose();
			}
		});
		btnDeleteBook.setBounds(146, 127, 127, 23);
		frame.getContentPane().add(btnDeleteBook);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibrarianSuccess.main(new String[]{});
				frame.dispose();
			}
		});
		btnBack.setBounds(146, 179, 89, 23);
		frame.getContentPane().add(btnBack);
	}
}
