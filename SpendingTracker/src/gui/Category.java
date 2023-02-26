package gui;

// all necessary import statement

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;



public class Category extends JFrame {

	/**
	 * 
	 */
	// declaring all necessary components of JFrame/swing
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField t;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	
	// Function to show the table 
	void getEntries() {
		
		try {
			javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel) table.getModel();
			int rc=dtm.getRowCount();
			while(rc--!=0) {
				dtm.removeRow(0);
			}
			
			ResultSet res=db.DbConnect.stmt.executeQuery("SELECT*FROM CATEGORY");
			int sNo=0;
		while(res.next()) {
				String category=res.getString("category");
				Object obj []= {++sNo,category};
				dtm.addRow(obj);
		}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * Create the frame.
	 */
	public Category() {
		setResizable(false);
		setTitle("Category");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 952, 602);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 936, 178);
		panel.setBackground(new Color(153, 153, 102));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 255, 153));
		panel_1.setBounds(20, 25, 906, 59);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add New Category");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(225, 0, 450, 49);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Category :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(20, 109, 77, 37);
		panel.add(lblNewLabel_1);
		
		t = new JTextField();
		t.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 try {
						String category=t.getText();
						if(category.equals("")) {
							JOptionPane.showMessageDialog(null,"Please Entered Some Value");
						}else {
							db.DbConnect.stmt.executeUpdate("INSERT INTO CATEGORY Values('"+category+"')");
						JOptionPane.showMessageDialog(null,"Category added SuccessFully");
						getEntries();
					    }
					}
					catch(SQLIntegrityConstraintViolationException e1) {
						JOptionPane.showMessageDialog(null,"Category already exist");
					}
					catch(Exception e1) {
						JOptionPane.showMessageDialog(null,e1);
					}
			 }
		});
		t.setBounds(107, 109, 714, 37);
		panel.add(t);
		t.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String category=t.getText();
					if(category.equals("")) {
						JOptionPane.showMessageDialog(null,"Please Entered Some Value");
					}else {
						db.DbConnect.stmt.executeUpdate("INSERT INTO CATEGORY Values('"+category+"')");
					JOptionPane.showMessageDialog(null,"Category added SuccessFully");
					getEntries();
				    }
				}
				catch(SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null,"Category already exist");
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null,e1);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(849, 109, 77, 37);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res=JOptionPane.showConfirmDialog(null,"Make sure you want to delete data", "Confirmation Window",JOptionPane.YES_NO_OPTION);
				
			if(res==JOptionPane.YES_OPTION) {
				int ri=table.getSelectedRow();
				String cat=(String)table.getValueAt(ri, 1);
				
				try {			
					db.DbConnect.stmt.executeUpdate("DELETE FROM CATEGORY WHERE category='"+cat+"'");
					JOptionPane.showMessageDialog(null," Category Successfully Deleted");
					getEntries();
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null,e1);
				}
			}
			}
		});
		btnNewButton_1.setBounds(449, 481, 89, 38);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 178, 936, 292);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "category"
			}
		));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setViewportView(table);
		getEntries();
		
		
	}
}
