import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/** Class for the main frame of the client */
public class StartFrame extends JFrame {
	
	// Variable declaration
	private javax.swing.JButton addButton;
	protected javax.swing.JButton deleteButton;
	protected javax.swing.JButton editButton;
	protected javax.swing.JComboBox<String> columnBox;
	private javax.swing.JLabel searchLabel;
	private javax.swing.JScrollPane jScrollPane1;
	protected javax.swing.JTable personTable;
	protected javax.swing.JTextField searchTextField;

	public StartFrame() {
		jScrollPane1 = new javax.swing.JScrollPane();
		personTable = new javax.swing.JTable();
		personTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Adding list selection listener to enable buttons when a row is selected
		personTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						editButton.setEnabled(true);
						deleteButton.setEnabled(true);

					}
				});

		deleteButton = new javax.swing.JButton();
		deleteButton.setEnabled(false);
		deleteButton.setText("Delete person");

		// Adding listener to delete a person from the database
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt((String) personTable.getValueAt(
						personTable.getSelectedRow(), 0));
				int option = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete person?");
				if (option == JOptionPane.YES_OPTION)
					Database.deletePerson(id);
				searchTable("person_id", "");
			}
		});
		editButton = new javax.swing.JButton();
		editButton.setText("Edit person");
		editButton.setEnabled(false);
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int personId = Integer.parseInt((String) personTable.getModel().getValueAt(personTable.getSelectedRow(), 0));
				FormFrame frame = new FormFrame(Database.getPerson(personId));
				frame.setVisible(true);
				frame.setTitle("Edit person");
				frame.setLocationRelativeTo(null);
			}
		});

		addButton = new javax.swing.JButton();
		addButton.setText("Add new person");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FormFrame frame = new FormFrame();
				frame.setVisible(true);
				frame.setTitle("Add new person");
				frame.setLocationRelativeTo(null);

			}
		});

		// Document listener for search textField to update search directly
		DocumentListener docListener = new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				search();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				search();
			}

			public void search() {
				int index = columnBox.getSelectedIndex();
				String column = "first_name";
				switch (index) {
				case 0:
					column = "person_id";
					break;
				case 1:
					column = "first_name";
					break;
				case 2:
					column = "last_name";
					break;
				case 3:
					column = "email";
					break;
				}

				searchTable(column, searchTextField.getText());

				if (personTable.getSelectedRow() == -1) {
					editButton.setEnabled(false);
					deleteButton.setEnabled(false);
				}
			}

		};
		searchTextField = new javax.swing.JTextField();
		searchTextField.getDocument().addDocumentListener(docListener); // Adding
																		// document
																		// listener

		columnBox = new javax.swing.JComboBox<String>();
		columnBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] {
				"Id", "First name", "Last name", "e-mail" }));

		searchLabel = new javax.swing.JLabel();
		searchLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		searchLabel.setText("Search for");

		// Setting layout of frame
		setLayout();

		// Last settings for start frame
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		searchTable("person_id", ""); // Refresh table from database
		pack();
		setLocationRelativeTo(null);
	}
	
	// Setting layout of form
	public void setLayout() {
				javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
						getContentPane());
				getContentPane().setLayout(layout);
				layout.setHorizontalGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addContainerGap(61, Short.MAX_VALUE)
										.addGroup(
												layout.createParallelGroup(
														javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(addButton)
														.addGroup(
																layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				jScrollPane1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				560,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGroup(
																				layout.createSequentialGroup()
																						.addComponent(
																								editButton)
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								deleteButton))
																		.addGroup(
																				layout.createSequentialGroup()
																						.addComponent(
																								searchLabel)
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								columnBox,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								83,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								searchTextField,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								144,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGap(60, 60, 60)));
				layout.setVerticalGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup()
										.addContainerGap(73, Short.MAX_VALUE)
										.addGroup(
												layout.createParallelGroup(
														javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																searchTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																columnBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(searchLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												382,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout.createParallelGroup(
														javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(editButton)
														.addComponent(deleteButton))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(addButton).addGap(43, 43, 43)));
	}

	// Method to search the database for attribute starting with a certain
	// string and loading it to the table also used to refresh table
	public void searchTable(String column, String str) {
		ArrayList<String> list = Database.getTuplesStartsWith(column, str);
		String[][] tuples = new String[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			String[] temp = list.get(i).split(";");
			for (int j = 0; j < 4; j++)
				tuples[i][j] = temp[j];
		}

		personTable.setModel(new DefaultTableModel(tuples, new String[] { "Id",
				"First name", "Last name", "e-mail" }) {
			Class[] types = new Class[] { Integer.class, String.class,
					String.class, String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		personTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(personTable);
		if (personTable.getColumnModel().getColumnCount() > 0) {
			personTable.getColumnModel().getColumn(0).setPreferredWidth(10);
			personTable.getColumnModel().getColumn(1).setPreferredWidth(40);
			personTable.getColumnModel().getColumn(2).setPreferredWidth(60);
			personTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		}

	}
}
