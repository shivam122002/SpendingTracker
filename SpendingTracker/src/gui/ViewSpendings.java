package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class ViewSpendings extends JFrame {

	private JPanel contentPane;
	private JTable table_1;
	private JTable table_2;
	private JLabel TotalAmount1;
	private JLabel TotalAmount2;
	private JComboBox category;
	private JDateChooser d1;
    private JDateChooser d2;
    private JDateChooser d3;
    private JDateChooser d4;

	/**
	 * Launch the application.
	 */
	void displayCategory() {
		try {
			category.removeAllItems();
			ResultSet res=db.DbConnect.stmt.executeQuery("Select* from CATEGORY");
			while(res.next()) {
				category.addItem(res.getString("category"));
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * Create the frame.
	 */
	public ViewSpendings() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 952, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 255));
		panel.setBounds(0, 0, 460, 561);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 102, 102));
		panel_2.setBounds(10, 11, 440, 101);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("View All Spendings Date Wise");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setForeground(new Color(0, 255, 255));
		lblNewLabel.setBackground(new Color(0, 204, 204));
		lblNewLabel.setBounds(10, 31, 420, 43);
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("From :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 136, 53, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("To :");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(10, 181, 53, 25);
		panel.add(lblNewLabel_1_1);
		
		d1 = new JDateChooser();
		d1.setBounds(62, 141, 377, 20);
		panel.add(d1);
		
		d2 = new JDateChooser();
		d2.setBounds(62, 186, 377, 20);
		panel.add(d2);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)table_1.getModel();
					java.sql.Date dt1=new java.sql.Date(d1.getDate().getTime());
					java.sql.Date dt2=new java.sql.Date(d2.getDate().getTime());
					ResultSet res=db.DbConnect.stmt.executeQuery("Select*from spendings where date>='"+dt1+"' and date<='"+dt2+"' order by date asc");
					
					int rc=dtm.getRowCount();
					while(rc--!=0) {
						dtm.removeRow(0);
					}
					int totalamount=0;
					while(res.next()) {
						 totalamount+=res.getInt("amount");
						Object [] obj= {res.getDate("date"),res.getString("category"),res.getInt("amount")};
						dtm.addRow(obj);
					}
					TotalAmount1.setText(totalamount+"");
					}
					catch(Exception e1) {
						JOptionPane.showMessageDialog(btnNewButton, "Search Button on table 1 Error !");
					}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(10, 234, 89, 32);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1_2 = new JLabel("Total Amount :");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(10, 287, 105, 25);
		panel.add(lblNewLabel_1_2);
		
		TotalAmount1 = new JLabel("00");
		TotalAmount1.setFont(new Font("Tahoma", Font.BOLD, 13));
		TotalAmount1.setBounds(113, 287, 53, 25);
		panel.add(TotalAmount1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 314, 440, 236);
		panel.add(scrollPane);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Category", "Amount"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setResizable(false);
		scrollPane.setViewportView(table_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 182, 193));
		panel_1.setLayout(null);
		panel_1.setBounds(476, 0, 460, 561);
		contentPane.add(panel_1);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(new Color(0, 102, 102));
		panel_2_1.setBounds(10, 11, 440, 101);
		panel_1.add(panel_2_1);
		
		JLabel lblViewAllSpendings = new JLabel("View All Spendings Category Wise");
		lblViewAllSpendings.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAllSpendings.setForeground(Color.CYAN);
		lblViewAllSpendings.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewAllSpendings.setBackground(new Color(0, 204, 204));
		lblViewAllSpendings.setBounds(10, 31, 420, 43);
		panel_2_1.add(lblViewAllSpendings);
		
		JLabel lblNewLabel_1_3 = new JLabel("Category:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3.setBounds(10, 138, 72, 25);
		panel_1.add(lblNewLabel_1_3);
		
		category = new JComboBox();
		category.setBounds(92, 140, 347, 22);
		panel_1.add(category);
		
		JLabel lblNewLabel_1_4 = new JLabel("From :");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_4.setBounds(10, 174, 53, 25);
		panel_1.add(lblNewLabel_1_4);
		displayCategory();
		
		JLabel lblNewLabel_1_1_2 = new JLabel("To :");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_2.setBounds(10, 204, 53, 25);
		panel_1.add(lblNewLabel_1_1_2);
		
		d3 = new JDateChooser();
		d3.setBounds(92, 173, 347, 20);
		panel_1.add(d3);
		
		d4 = new JDateChooser();
		d4.setBounds(92, 204, 347, 20);
		panel_1.add(d4);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)table_2.getModel();
					int rc=dtm.getRowCount();
					while(rc--!=0) {
						dtm.removeRow(0);
					}
					String c=(String) category.getSelectedItem();
					java.sql.Date dt1=new java.sql.Date(d1.getDate().getTime());
					java.sql.Date dt2=new java.sql.Date(d2.getDate().getTime());
					ResultSet res=db.DbConnect.stmt.executeQuery("Select*from spendings where date>='"+dt1+"' and date<='"+dt2+"' and category='"+c+"' ");
					int  totalamount=0;
					while(res.next()) {
						 totalamount+=res.getInt("amount");
						Object [] obj= {res.getDate("date"),res.getString("category"),res.getInt("amount")};
						dtm.addRow(obj);
					}
					TotalAmount2.setText(totalamount+"");
					}
					catch(Exception e1) {
						JOptionPane.showMessageDialog(btnNewButton, "Search Button on table_2 Error ! ");
					}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(10, 240, 89, 32);
		panel_1.add(btnNewButton_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 313, 440, 237);
		panel_1.add(scrollPane_1);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Category", "Amount"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_2.getColumnModel().getColumn(1).setResizable(false);
		table_2.getColumnModel().getColumn(2).setResizable(false);
		scrollPane_1.setViewportView(table_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Total Amount :");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_2_1.setBounds(10, 287, 105, 25);
		panel_1.add(lblNewLabel_1_2_1);
		
		TotalAmount2 = new JLabel("00");
		TotalAmount2.setFont(new Font("Tahoma", Font.BOLD, 13));
		TotalAmount2.setBounds(109, 287, 53, 25);
		panel_1.add(TotalAmount2);
		d1.setDate(new java.util.Date());
		d2.setDate(new java.util.Date());
		d3.setDate(new java.util.Date());
		d4.setDate(new java.util.Date());
		
	}
}
