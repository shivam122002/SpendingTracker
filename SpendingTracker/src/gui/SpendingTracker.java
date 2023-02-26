package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class SpendingTracker extends JFrame {

	private JFrame frmSpendingtracker;
	private JTextField amt;
    private JDateChooser d;
	private JTable table;
    private JComboBox category;
    private JLabel totalAmount;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpendingTracker window = new SpendingTracker();
					window.frmSpendingtracker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public SpendingTracker() {
		initialize();
		displayCategory();
		d.setSelectableDateRange(null, new java.util.Date());
		getEntries();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		d.setDate(new java.util.Date());
		
	}
	

	private void displayCategory() {
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
	private void getEntries() {
		try {
			javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel) table.getModel();
			int rc=dtm.getRowCount();
			while(rc--!=0) {
				dtm.removeRow(0);
			}
		    java.time.LocalDate cd=java.time.LocalDate.now();
		    java.time.LocalDate bd=java.time.LocalDate.now().minusDays(30);
		    
			ResultSet res=db.DbConnect.stmt.executeQuery("SELECT*FROM spendings WHERE date<='"+cd+"' and date>='"+bd+"'");
			int totalamount=00;
		while(res.next()) {
			  totalamount+=res.getInt("amount");
				Object obj []= {res.getInt("sID"),res.getDate("date"),res.getString("category"),res.getInt("amount")};
				dtm.addRow(obj);
		}
		totalAmount.setText(totalamount+"");
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
        private void initialize() {
		frmSpendingtracker = new JFrame();
		frmSpendingtracker.setResizable(false);
		frmSpendingtracker.setTitle("SpendingTracker");
		frmSpendingtracker.setType(Type.POPUP);
		frmSpendingtracker.setBounds(100, 100, 954, 603);
		frmSpendingtracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSpendingtracker.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 102, 255));
		panel.setBounds(0, 0, 938, 206);
		frmSpendingtracker.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 204));
		panel_1.setBounds(0, 0, 944, 62);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add New Expenses");
		lblNewLabel.setBackground(new Color(204, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(219, 11, 569, 40);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Date :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(32, 97, 41, 31);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Amount :");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(349, 97, 72, 31);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Category :");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(571, 97, 80, 31);
		panel.add(lblNewLabel_1_2);
		
		 d = new JDateChooser();
		d.setBounds(83, 97, 246, 31);
		panel.add(d);
		
		amt = new JTextField();
		amt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		amt.setBounds(431, 99, 130, 31);
		panel.add(amt);
		amt.setColumns(10);
		
		 category = new JComboBox();
		category.setBounds(661, 99, 194, 31);
		panel.add(category);
		
		
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.util.Date dt=d.getDate();
					String s1=amt.getText();
					String s2=(String)category.getSelectedItem();
					
					if(dt!=null && !s1.equals("") && !s2.equals("")) {
						int amount=Integer.parseInt(s1);
						java.sql.Date date=new java.sql.Date(dt.getTime());
						db.DbConnect.stmt.executeUpdate("INSERT INTO spendings (category,date,amount) values ('"+s2+"','"+date+"',"+amount+")");
						JOptionPane.showMessageDialog(btnNewButton, "Expens added SuccessFully");
						getEntries();
					}else {
						JOptionPane.showMessageDialog(btnNewButton, "Please fill all Details");
					}
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(btnNewButton, e1);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(865, 98, 63, 31);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add New Category");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Category().setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(661, 166, 194, 31);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Remove");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ri=table.getSelectedRow();	
			if(ri!=-1) {
					 
				int res=JOptionPane.showConfirmDialog(null,"Make sure you want to delete data", "Confirmation Window",JOptionPane.YES_NO_OPTION);
				if(res==JOptionPane.YES_OPTION) {
					 
      				 int id=(int)table.getValueAt(ri,0); 
                      try {  
						 db.DbConnect.stmt.executeUpdate("DELETE FROM spendings WHERE sID="+id);
						 JOptionPane.showMessageDialog(null, "SuccessFully Deleted");
						 getEntries();
				     	 
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
				}
		}
				
			
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBounds(32, 164, 129, 34);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Refresh");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayCategory();
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.setBounds(412, 167, 89, 31);
		panel.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 244, 928, 149);
		frmSpendingtracker.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "date", "Amount", "Category"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_2 = new JLabel("Last 30 Days Spendings:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 217, 172, 28);
		frmSpendingtracker.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Total Amount :");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(10, 395, 153, 28);
		frmSpendingtracker.getContentPane().add(lblNewLabel_2_1);
		
		totalAmount = new JLabel("00");
		totalAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
		totalAmount.setBounds(105, 395, 153, 28);
		frmSpendingtracker.getContentPane().add(totalAmount);
		
		JButton btnNewButton_4 = new JButton("View All Spendings");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ViewSpendings().setVisible(true);
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_4.setBounds(400, 398, 162, 23);
		frmSpendingtracker.getContentPane().add(btnNewButton_4);
	}
}
