import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibrarianSuccess {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibrarianSuccess window = new LibrarianSuccess();
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
	public LibrarianSuccess() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 419);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LIBRARIAN SECTION");
		lblNewLabel.setForeground(new Color(153, 0, 255));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
		lblNewLabel.setBounds(118, 23, 197, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("ISSUE BOOKS");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IssueBook.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnNewButton_2.setBounds(127, 68, 179, 34);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("VIEW PORTAL");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewPortal.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnNewButton_3.setBounds(127, 127, 179, 34);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("RETURN BOOK");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReturnBook.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnNewButton_4.setBounds(127, 186, 179, 34);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("LOGOUT");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibrarianLogin.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton_5.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnNewButton_5.setBounds(127, 245, 179, 31);
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnStudentRegistration = new JButton("STUDENT DATABASE");
		btnStudentRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentDatabase.main(new String[]{});
				frame.dispose();
			}
		});
		btnStudentRegistration.setBounds(127, 287, 179, 31);
		frame.getContentPane().add(btnStudentRegistration);
		
		JButton btnNewButton = new JButton("BOOK DATABASE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookDatabase.main(new String[]{});
				frame.dispose();
			}
		});
		btnNewButton.setBounds(127, 329, 179, 31);
		frame.getContentPane().add(btnNewButton);
	}
}
