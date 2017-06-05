package com.example.OutputClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Naegling
 */
public class OutputFrame extends JFrame {

	// Variables declaration
    private LeftPanel leftPanel = new LeftPanel();
    private JScrollPane leftSideScrollPane = new JScrollPane();
    private JPanel centerPanel = new JPanel();
    private JScrollPane middleScrollPane = new JScrollPane();
    private JPanel rightPanel = new JPanel();
    private Search search = new Search();
    private Statistics statistics = new Statistics();
    
    // General colors
    public static final Color COLOR_DARK = new Color(188, 205, 240);
    public static final Color COLOR_MIDDLE = new Color(210, 220, 245);
    public static final Color COLOR_LIGHT = new Color(230, 240, 255);
    
    /** Constructor for the output frame */
    public OutputFrame() {
	    
        // Setting properties for frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/pics/ff_icon.png")).getImage());
        setLayout();
        setResizable(false);
        pack();
        
        // Load all the combo boxes and lists
        loadList();
        
        }
    
    /** Set the layout of the output frame and the panels */ 
    private void setLayout() {
    	leftPanel.setPreferredSize(new java.awt.Dimension(324, 1010));
    	rightPanel.setPreferredSize(new java.awt.Dimension(250, 1010));
    	
        // Adding left panel to a scroll pane
        leftSideScrollPane.setViewportView(leftPanel);

        
        // Middle panel displaying persons
        GridLayout middlePaneLayout = new GridLayout(0,1);
        centerPanel.setLayout(middlePaneLayout);
        
        

        // Adding middle panel to a scroll pane
        middleScrollPane.setViewportView(centerPanel);
        
        // Adding the right panel
        
        rightPanel.setLayout(null);
        search.setBounds(0, 0, 250, 100);
        rightPanel.add(search);
        statistics.setBounds(0, 100, 250, 600);
        rightPanel.add(statistics);
        
        // Setting the layout to the main content pane
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftSideScrollPane, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middleScrollPane, GroupLayout.PREFERRED_SIZE, 708, GroupLayout.PREFERRED_SIZE)
                .addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)                )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(middleScrollPane, GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
            .addComponent(leftSideScrollPane, GroupLayout.PREFERRED_SIZE, 700, Short.MAX_VALUE)
            .addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 681, Short.MAX_VALUE)
        );

    }
    
    /** Method to get five persons from the database at random */
    public ArrayList<Person> randomizePersons() {
    	ArrayList<Person> persons = new ArrayList<Person>();
    	ArrayList<Integer> list = Database.getPersonIDs();
    	Collections.shuffle(list);
    	for (int i = 0; i < 5; i++) {
    		persons.add(Database.getPerson(list.get(i)));
    	}
    	
    	return persons;
    }
    
    /** Method to create panels from an array list of person objects and draw them on the middle panel */
    public void loadPersonPanels(ArrayList<Person> persons) {
    	centerPanel.removeAll();
    	for (int i = 0; i < persons.size(); i++) {
    		PersonPanel panel = new PersonPanel(persons.get(i));
    		panel.setPreferredSize(new Dimension(680, 350));
    		centerPanel.add(panel);
    		
    	}
    	centerPanel.revalidate();
		centerPanel.repaint();
    }
    
    /** Method to get all selected music favorites as an ArrayList */
    public ArrayList<String> getMusicSelections(){
    	ArrayList<String> music = new ArrayList<String>();
    	
    	for (int i = 0; i < leftPanel.chkBoxMusic.length; i++) {
    		if(leftPanel.chkBoxMusic[i].isSelected())
    			music.add(leftPanel.chkBoxMusic[i].getText());
    	}
    	
    	return music;
    }
    
    
    /** Method to get all the selected movie favorites returned as an ArrayList */
    public ArrayList<String> getMovieSelections() {
    	ArrayList<String> movie = new ArrayList<String>();
    	
    	for (int i = 0; i < leftPanel.chkBoxMovies.length; i++) {
    		if(leftPanel.chkBoxMovies[i].isSelected())
    			movie.add(leftPanel.chkBoxMovies[i].getText());
    	}
    	
    	return movie;
    }
    
    
    /** Method to get all the selected book favorites returned as an ArrayList */
    public ArrayList<String> getBookSelections() {
    	ArrayList<String> book = new ArrayList<String>();
    	
    	for (int i = 0; i < leftPanel.chkBoxBooks.length; i++) {
    		if(leftPanel.chkBoxBooks[i].isSelected())
    			book.add(leftPanel.chkBoxBooks[i].getText());
    	}

    	return book;
    }
    
    
    /** Method to load all lists and combo boxes with data from the database */
    public void loadList(){
    	
    	String[] list = Database.getLists("regions", "region_name");
		Arrays.sort(list);
		leftPanel.cmbBoxRegion.removeAllItems();
		leftPanel.cmbBoxRegion.addItem("");
		for (String element : list)
			leftPanel.cmbBoxRegion.addItem(element);
		
		list = Database.getLists("religions", "religion_name");
		Arrays.sort(list);
		leftPanel.cmbBoxReligion.removeAllItems();
		leftPanel.cmbBoxReligion.addItem("");
		for (String element : list)
			leftPanel.cmbBoxReligion.addItem(element);

		list = Database.getLists("nationalities", "nationality_name");
		Arrays.sort(list);
		leftPanel.cmbBoxNationality.removeAllItems();
		leftPanel.cmbBoxNationality.addItem("");
		for (String element : list)
			leftPanel.cmbBoxNationality.addItem(element);
		
		list = Database.getLists("universities", "university_name");
		Arrays.sort(list);
		leftPanel.cmbBoxUniversity.removeAllItems();
		leftPanel.cmbBoxUniversity.addItem("");
		for (String element : list)
			leftPanel.cmbBoxUniversity.addItem(element);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		list = Database.getLists("interests", "interest_name");
		Arrays.sort(list);
		for (String element : list) {
			model.addElement(element);
		}
		leftPanel.listInterests.setModel(model);
		leftPanel.listInterests.setSelectedIndex(0);
		
		list = Database.getLists("languages", "language_name");
		Arrays.sort(list);
		leftPanel.cmbBoxLanguage.removeAllItems();
		leftPanel.cmbBoxLanguage.addItem("");
		for (String element : list)
			leftPanel.cmbBoxLanguage.addItem(element);
		
		
		
    }
	
    /** Method to remove an item from a list*/
	static void removeFromList(String item, JList<String> list) {
		DefaultListModel<String> model = (DefaultListModel<String>)list.getModel();
		model.removeElement(item);
	}
	
	/** Method to add an item to a list*/
	static void addToList(String item, JList<String> list) {
		DefaultListModel<String> model = (DefaultListModel<String>)list.getModel();
		model.addElement(item);
	}
    
	/** Action listener for the buttons to add and remove items to lists*/
    class AddRemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == leftPanel.btnAddInterests) {
				if (leftPanel.listInterests.getSelectedIndex() >= 0) {
					String element = (String) leftPanel.listInterests.getSelectedValue();
					addToList(element, leftPanel.listInterestsSel);
					removeFromList(element, leftPanel.listInterests);
				}
			}
			if (e.getSource() == leftPanel.btnRemoveInterests) {
				if (leftPanel.listInterestsSel.getSelectedIndex() >= 0) {
					String element = (String) leftPanel.listInterestsSel.getSelectedValue();
					addToList(element, leftPanel.listInterests);
					removeFromList(element, leftPanel.listInterestsSel);
				}
			}
			if (e.getSource() == leftPanel.btnAddCity) {
				if (leftPanel.listCity.getSelectedIndex() >= 0) {
					String element = (String) leftPanel.listCity.getSelectedValue();
					addToList(element, leftPanel.listCitySel);
					removeFromList(element, leftPanel.listCity);
				}
			}
			if (e.getSource() == leftPanel.btnRemoveCity) {
				if (leftPanel.listCitySel.getSelectedIndex() >= 0) {
					String element = (String) leftPanel.listCitySel.getSelectedValue();
					addToList(element, leftPanel.listCity);
					removeFromList(element, leftPanel.listCitySel);
				}
			}			
		}
		
	}
    
    
    /** Action listener for the filter search button*/
    class FilterButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Creates and array list of the different SQL statements to filter the search
			ArrayList<String> queries = new ArrayList<String>();
			
			// Add start statement
			queries.add("SELECT a.person_id FROM persons a");
			
			// Add statement for gender selection
			if (leftPanel.chkBoxFemale.isSelected() && !leftPanel.chkBoxMale.isSelected())
				queries.add(" INNER JOIN (SELECT person_id FROM persons WHERE gender = 'F') gen USING (person_id)");
			if (leftPanel.chkBoxMale.isSelected() && !leftPanel.chkBoxFemale.isSelected())
				queries.add(" INNER JOIN (SELECT person_id FROM persons WHERE gender = 'M') gen USING (person_id)");
			
			// Add statement for age
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (!leftPanel.txtMinAge.getText().equals("")) {
				Calendar now = new GregorianCalendar();
				now.set(Calendar.YEAR, now.get(Calendar.YEAR) - Integer.parseInt(leftPanel.txtMinAge.getText()));
				queries.add(" INNER JOIN (SELECT person_id FROM persons WHERE birth_date <= '" + sdf.format(now.getTime()) + "') minage USING (person_id)");
			}
			if (!leftPanel.txtMaxAge.getText().equals("")) {
				Calendar now = new GregorianCalendar();
				now.set(Calendar.YEAR, now.get(Calendar.YEAR) - Integer.parseInt(leftPanel.txtMaxAge.getText()) - 1);
				queries.add(" INNER JOIN (SELECT person_id FROM persons WHERE birth_date >= '" + sdf.format(now.getTime()) + "') maxage USING (person_id)");
			}
			
			// Add statement for nationality
			if(!leftPanel.cmbBoxNationality.getSelectedItem().equals("")) {
				queries.add(" INNER JOIN (SELECT person_id from persons inner join nationalities ON nationalities.nationality_id = persons.persons_nationality where nationality_name = '" + leftPanel.cmbBoxNationality.getSelectedItem() + "') nat USING (person_id)");
			}
			
			// Add statement for language
			if(!leftPanel.cmbBoxLanguage.getSelectedItem().equals("")) {
				queries.add(" INNER JOIN (SELECT persons.person_id FROM persons INNER JOIN persons_languages ON persons_languages.person_id = persons.person_id " +
						"INNER JOIN languages ON persons_languages.language_id = languages.language_id WHERE language_name = '" + leftPanel.cmbBoxLanguage.getSelectedItem() + "') lang USING (person_id)");
			}
			
			// Add statement for university
			if(!leftPanel.cmbBoxUniversity.getSelectedItem().equals("")) {
				queries.add(" INNER JOIN (SELECT person_id from persons INNER JOIN programs ON programs.program_id = persons.persons_program "
						+ "INNER JOIN universities ON universities.university_id = programs.programs_university WHERE university_name = '" + leftPanel.cmbBoxUniversity.getSelectedItem() +"') uni USING(person_id)");
			}
			
			// Add statement for program
			if(!leftPanel.cmbBoxProgram.getSelectedItem().equals("")) {
				queries.add(" INNER JOIN (SELECT person_id FROM persons INNER JOIN programs ON programs.program_id = persons.persons_program WHERE program_name = '" + leftPanel.cmbBoxProgram.getSelectedItem() + "') prog USING(person_id)");
			}
			
			// Add statement for religion
			if(!leftPanel.cmbBoxReligion.getSelectedItem().equals("")) {
				queries.add(" INNER JOIN (SELECT person_id FROM persons INNER JOIN religions ON religions.religion_id = persons.persons_religion "
						+ "WHERE religion_name = '" + leftPanel.cmbBoxReligion.getSelectedItem() + "') rel USING (person_id)");
			}
			
			// Add statements for selected cities
			int size = leftPanel.listCitySel.getModel().getSize();
			if (size > 0) {
				String temp = " INNER JOIN (SELECT person_id FROM persons INNER JOIN cities ON cities.city_id = persons.persons_city WHERE city_name IN (";
				for(int i = 0; i < size - 1; i++) {
					temp = temp + "'" + leftPanel.listCitySel.getModel().getElementAt(i) + "', ";
				}
				temp = temp + "'" + leftPanel.listCitySel.getModel().getElementAt(size -1) + "')) city USING (person_id)";
				
				queries.add(temp);
			}
			
			// Add statements for selected interests
			size = leftPanel.listInterestsSel.getModel().getSize();
			if (size > 0) {
				String temp = " INNER JOIN (SELECT person_id FROM persons_interests INNER JOIN interests ON interests.interest_id = persons_interests.interest_id WHERE interest_name IN (";
				for(int i = 0; i < size - 1; i++) {
					temp = temp + "'" + leftPanel.listInterestsSel.getModel().getElementAt(i) + "', ";
				}
				temp = temp + "'" + leftPanel.listInterestsSel.getModel().getElementAt(size -1) + "') ";
				temp = temp + "GROUP BY person_id HAVING count(*) = " + size + ") interest USING (person_id)";
				queries.add(temp);
			}
			
			// Add statements for selected music genres
			ArrayList<String> list = getMusicSelections();
			size = list.size();
			if (size > 0) {
				String temp = " INNER JOIN (SELECT person_id FROM persons_music_genres INNER JOIN music_genres ON music_genres.music_genre_id = persons_music_genres.music_genre_id WHERE music_genre IN (";
				for(int i = 0; i < size - 1; i++) {
					temp = temp + "'" + list.get(i) + "', ";
				}
				temp = temp + "'" + list.get(size -1) + "') ";
				temp = temp + "GROUP BY person_id HAVING count(*) = " + size + ") music USING (person_id)";
				queries.add(temp);
			}
			
			// Add statements for selected movie genres
			list = getMovieSelections();
			size = list.size();
			if (size > 0) {
				String temp = " INNER JOIN (SELECT person_id FROM persons_movie_genres INNER JOIN movie_genres ON movie_genres.movie_genre_id = persons_movie_genres.movie_genre_id WHERE movie_genre IN (";
				for(int i = 0; i < size - 1; i++) {
					temp = temp + "'" + list.get(i) + "', ";
				}
				temp = temp + "'" + list.get(size -1) + "') ";
				temp = temp + "GROUP BY person_id HAVING count(*) = " + size + ") movies USING (person_id)";
				queries.add(temp);
			}
			
			// Add statements for selected book genres
			list = getBookSelections();
			size = list.size();
			if (size > 0) {
				String temp = " INNER JOIN (SELECT person_id FROM persons_book_genres INNER JOIN book_genres ON book_genres.book_genre_id = persons_book_genres.book_genre_id WHERE book_genre IN (";
				for(int i = 0; i < size - 1; i++) {
					temp = temp + "'" + list.get(i) + "', ";
				}
				temp = temp + "'" + list.get(size -1) + "') ";
				temp = temp + "GROUP BY person_id HAVING count(*) = " + size + ") books USING (person_id)";
				queries.add(temp);
			}
			
			// Compiling all statements from the array list in to one string adding intersect in between
			String query = "";
			if (queries.size() > 1) {
				query = queries.get(0);
				for (int i = 1; i < queries.size(); i++) {
					query = query + queries.get(i);
				}
			} else {
				query = queries.get(0);
				
			}
			// Get the filtered persons data from the database and store it in an arraylist of Person objects
			ArrayList<Person> persons = Database.getPersonsFiltered(query);
			Collections.sort(persons, new Comparator<Person>() {

				@Override
				public int compare(Person o1, Person o2) {
					// TODO Auto-generated method stub
					return o1.getFirstName().compareTo(o2.getFirstName());
				}
			});
			
			// Load panels for the persons
			loadPersonPanels(persons);
			
		}
    }
	
	/** Search panel to the right to search for first and last name */
	class Search extends JPanel {
		
		ArrayList<Person> personsByName = new ArrayList<Person>();
		private JPanel searchPanel = new JPanel();
		private JTextField searchField = new JTextField(15);
		private JLabel searchLabel = new JLabel("Name Search");
		
		public Search() {
			
			searchField.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					personsByName.clear();
					String name = searchField.getText();
					String query = "SELECT person_id from persons where first_name like '%"
							+ name + "%' OR last_name like '%" + name + "%'";
					personsByName.addAll(Database.getPersonsFiltered(query));
					loadPersonPanels(personsByName);
				}

			});

			searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
			searchLabel.setAlignmentX(CENTER_ALIGNMENT);
			searchField.setAlignmentX(CENTER_ALIGNMENT);
			searchLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			searchLabel.setIcon(new ImageIcon(getClass().getResource("/pics/search.png"))); 
			searchPanel.add(searchLabel);
			searchPanel.add(searchField);

			add(searchPanel);

		}

	}
	
	/** Class for the left panel containing all filtering options */
	class LeftPanel extends JPanel {
		JTextField txtMinAge, txtMaxAge;
		JComboBox<String> cmbBoxRegion, cmbBoxUniversity, cmbBoxProgram, cmbBoxNationality, cmbBoxLanguage, cmbBoxReligion;
		JList<String> listCity, listCitySel, listInterests, listInterestsSel;
		JButton btnAddCity, btnRemoveCity, btnAddInterests, btnRemoveInterests;
		JCheckBox chkBoxMale, chkBoxFemale;
		JCheckBox[] chkBoxMusic, chkBoxMovies, chkBoxBooks;
		

		/**
		 * Create the panel.
		 */
		public LeftPanel() {
			setLayout(null);
			
			JPanel panelUpper = new JPanel();
			panelUpper.setBounds(0, 0, 323, 397);
			add(panelUpper);
			panelUpper.setLayout(null);
			
			JPanel panelGA = new JPanel();
			panelGA.setBounds(0, 1, 323, 70);
			panelUpper.add(panelGA);
			panelGA.setLayout(null);
			
			JLabel lblGender = new JLabel("Gender");
			lblGender.setHorizontalAlignment(SwingConstants.TRAILING);
			lblGender.setBounds(30, 41, 35, 14);
			panelGA.add(lblGender);
			
			chkBoxMale = new JCheckBox("");
			chkBoxMale.setBounds(71, 35, 24, 23);
			panelGA.add(chkBoxMale);
			
			chkBoxFemale = new JCheckBox("");
			chkBoxFemale.setBounds(129, 35, 24, 23);
			panelGA.add(chkBoxFemale);
			
			JLabel lblMale = new JLabel("");
			lblMale.setBounds(99, 35, 24, 23);
			lblMale.setIcon(new ImageIcon(getClass().getResource("/pics/gender_male.png")));
			panelGA.add(lblMale);
			
			JLabel lblFemale = new JLabel("");
			lblFemale.setIcon(new ImageIcon(getClass().getResource("/pics/gender_female.png")));
			lblFemale.setBounds(159, 35, 24, 23);
			panelGA.add(lblFemale);
			
			JLabel lblAge = new JLabel("Age");
			lblAge.setHorizontalAlignment(SwingConstants.TRAILING);
			lblAge.setBounds(178, 41, 46, 14);
			panelGA.add(lblAge);
			
			txtMinAge = new JTextField();
			txtMinAge.setBounds(234, 38, 24, 20);
			panelGA.add(txtMinAge);
			txtMinAge.setColumns(10);
			txtMinAge.setDocument(new AgeLimitDocument());
			
			JLabel lblDash = new JLabel("-");
			lblDash.setBounds(268, 41, 4, 14);
			panelGA.add(lblDash);
			
			txtMaxAge = new JTextField();
			txtMaxAge.setColumns(10);
			txtMaxAge.setBounds(282, 38, 24, 20);
			txtMaxAge.setDocument(new AgeLimitDocument());
			panelGA.add(txtMaxAge);
			
			JLabel lblFilterOptions = new JLabel("Filter options");
			lblFilterOptions.setBackground(OutputFrame.COLOR_MIDDLE);
			lblFilterOptions.setOpaque(true);
			lblFilterOptions.setHorizontalAlignment(SwingConstants.CENTER);
			lblFilterOptions.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblFilterOptions.setBounds(0, 0, 323, 30);
			panelGA.add(lblFilterOptions);
			
			JPanel panelLocation = new JPanel();
			panelLocation.setBounds(0, 70, 323, 183);
			panelUpper.add(panelLocation);
			panelLocation.setLayout(null);
			
			JLabel lblRegion = new JLabel("Region");
			lblRegion.setBounds(30, 11, 33, 14);
			panelLocation.add(lblRegion);
			
			cmbBoxRegion = new JComboBox<String>();
			cmbBoxRegion.setBounds(98, 8, 186, 20);
			panelLocation.add(cmbBoxRegion);
			
			cmbBoxRegion.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					DefaultListModel<String> model = new DefaultListModel<String>();
					String selected = (String)cmbBoxRegion.getSelectedItem();
					if (selected != null)
						if(!selected.equals("")) {
							ArrayList<String> list = Database.getAllCityNames(selected);
							Collections.sort(list);

							//Arrays.sort(list);
							for(String element : list)
								model.addElement(element);
							listCity.setModel(model);

						}
					
				}
			});
			
			JPanel panelCity = new JPanel();
			panelCity.setBorder(new TitledBorder(null, "City", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelCity.setBounds(10, 40, 303, 132);
			panelLocation.add(panelCity);
			panelCity.setLayout(null);
			
			JScrollPane scrollPaneCity = new JScrollPane();
			scrollPaneCity.setBounds(10, 23, 97, 98);
			panelCity.add(scrollPaneCity);
			
			listCity = new JList<String>();
			scrollPaneCity.setViewportView(listCity);
			
			JScrollPane scrollPaneCitySel = new JScrollPane();
			scrollPaneCitySel.setBounds(196, 23, 97, 98);
			panelCity.add(scrollPaneCitySel);
			
			listCitySel = new JList<String>();
			scrollPaneCitySel.setViewportView(listCitySel);
			
			btnAddCity = new JButton(">>");
			btnAddCity.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnAddCity.setBounds(117, 46, 69, 23);
			btnAddCity.addActionListener(new AddRemoveListener());
			panelCity.add(btnAddCity);
			
			btnRemoveCity = new JButton("<<");
			btnRemoveCity.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnRemoveCity.setBounds(117, 80, 69, 23);
			btnRemoveCity.addActionListener(new AddRemoveListener());
			panelCity.add(btnRemoveCity);
			
			JPanel panelOther = new JPanel();
			panelOther.setBounds(0, 251, 323, 144);
			panelUpper.add(panelOther);
			panelOther.setLayout(null);
			
			JLabel lblUniversity = new JLabel("University");
			lblUniversity.setBounds(30, 14, 58, 14);
			panelOther.add(lblUniversity);
			
			JLabel lblProgram = new JLabel("Program");
			lblProgram.setBounds(30, 39, 58, 14);
			panelOther.add(lblProgram);
			
			JLabel lblNationality = new JLabel("Nationality");
			lblNationality.setBounds(30, 64, 58, 14);
			panelOther.add(lblNationality);
			
			JLabel lblLanguage = new JLabel("Language");
			lblLanguage.setBounds(30, 89, 58, 14);
			panelOther.add(lblLanguage);
			
			JLabel lblReligion = new JLabel("Religion");
			lblReligion.setBounds(30, 114, 58, 14);
			panelOther.add(lblReligion);
			
			cmbBoxUniversity = new JComboBox<String>();
			cmbBoxUniversity.setBounds(98, 11, 186, 20);
			panelOther.add(cmbBoxUniversity);
			
			cmbBoxUniversity.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cmbBoxProgram.removeAllItems();
					cmbBoxProgram.addItem("");
					String selected = (String)cmbBoxUniversity.getSelectedItem();
					if (selected != null)
						if (!selected.equals("")) {
							ArrayList<String> list = new ArrayList<String>();
							list = Database.getAllPrograms(selected);
							for (String element : list)
								cmbBoxProgram.addItem(element);

						}
					
				}
			});
			
			cmbBoxProgram = new JComboBox<String>();
			cmbBoxProgram.setBounds(98, 36, 186, 20);
			panelOther.add(cmbBoxProgram);
			
			cmbBoxNationality = new JComboBox<String>();
			cmbBoxNationality.setBounds(98, 61, 186, 20);
			panelOther.add(cmbBoxNationality);
			
			cmbBoxLanguage = new JComboBox<String>();
			cmbBoxLanguage.setBounds(98, 86, 186, 20);
			panelOther.add(cmbBoxLanguage);
			
			cmbBoxReligion = new JComboBox<String>();
			cmbBoxReligion.setBounds(98, 111, 186, 20);
			panelOther.add(cmbBoxReligion);
			
			JPanel panelLower = new JPanel();
			panelLower.setBounds(0, 395, 323, 473);
			panelUpper.add(panelLower);
			
			
			JPanel panelInterests = new JPanel();
			panelInterests.setLayout(null);
			panelInterests.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Interests", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelInterests.setBounds(10, 397, 303, 132);
			add(panelInterests);
			
			JScrollPane scrollPaneInterests = new JScrollPane();
			scrollPaneInterests.setBounds(10, 23, 97, 98);
			panelInterests.add(scrollPaneInterests);
			
			listInterests = new JList<String>();
			scrollPaneInterests.setViewportView(listInterests);
			
			JScrollPane scrollPaneInterestsSel = new JScrollPane();
			scrollPaneInterestsSel.setBounds(196, 23, 97, 98);
			panelInterests.add(scrollPaneInterestsSel);
			
			listInterestsSel = new JList<String>();
			scrollPaneInterestsSel.setViewportView(listInterestsSel);
			
			btnAddInterests = new JButton(">>");
			btnAddInterests.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnAddInterests.setBounds(117, 46, 69, 23);
			btnAddInterests.addActionListener(new AddRemoveListener());
			panelInterests.add(btnAddInterests);
			
			btnRemoveInterests = new JButton("<<");
			btnRemoveInterests.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnRemoveInterests.setBounds(117, 80, 69, 23);
			btnRemoveInterests.addActionListener(new AddRemoveListener());
			panelInterests.add(btnRemoveInterests);
			
			
			// Movie genre panel
			JPanel panelMovies = new JPanel();
			panelMovies.setBorder(new TitledBorder(null, "Movie genres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelMovies.setBounds(10, 540, 303, 132);
			add(panelMovies);
			panelMovies.setLayout(new GridLayout(5, 3, 0, 0));
			
			chkBoxMovies = new JCheckBox[15];
			for (int i = 0; i < chkBoxMovies.length; i++){
				chkBoxMovies[i] = new JCheckBox();
				panelMovies.add(chkBoxMovies[i]);
			}
			chkBoxMovies[0].setText("Action");
			chkBoxMovies[1].setText("Adventure");
			chkBoxMovies[2].setText("Animation");
			chkBoxMovies[3].setText("Comedy");
			chkBoxMovies[4].setText("Crime");
			chkBoxMovies[5].setText("Documentary");
			chkBoxMovies[6].setText("Drama");
			chkBoxMovies[7].setText("Family");
			chkBoxMovies[8].setText("Fantasy");
			chkBoxMovies[9].setText("Horror");
			chkBoxMovies[10].setText("Musical");
			chkBoxMovies[11].setText("Romance");
			chkBoxMovies[12].setText("Sci-Fi");
			chkBoxMovies[13].setText("Thriller");
			chkBoxMovies[14].setText("War");
			
			// Book genre panel
			JPanel panelBooks = new JPanel();
			panelBooks.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Book genres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelBooks.setBounds(10, 683, 303, 109);
			add(panelBooks);
			panelBooks.setLayout(new GridLayout(4, 3, 0, 0));
			
			chkBoxBooks = new JCheckBox[12];
			for (int i=0; i < chkBoxBooks.length; i++) {
				chkBoxBooks[i] = new JCheckBox();
				panelBooks.add(chkBoxBooks[i]);
			}
			chkBoxBooks[0].setText("Action");
			chkBoxBooks[1].setText("Adventure");
			chkBoxBooks[2].setText("Children");
			chkBoxBooks[3].setText("Crime");
			chkBoxBooks[4].setText("Drame");
			chkBoxBooks[5].setText("Fantasy");
			chkBoxBooks[6].setText("Horror");
			chkBoxBooks[7].setText("Non-fiction");
			chkBoxBooks[8].setText("Romance");
			chkBoxBooks[9].setText("Sci-Fi");
			chkBoxBooks[10].setText("Thriller");
			chkBoxBooks[11].setText("Young Adult");
			
			// Music genre panel
			JPanel panelMusic = new JPanel();
			panelMusic.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Music genres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelMusic.setBounds(10, 803, 303, 153);
			add(panelMusic);
			panelMusic.setLayout(new GridLayout(6, 3, 0, 0));
			
			chkBoxMusic = new JCheckBox[18];
			for (int i = 0; i < chkBoxMusic.length; i++) {
				chkBoxMusic[i] = new JCheckBox();
				panelMusic.add(chkBoxMusic[i]);
			}
			chkBoxMusic[0].setText("Alternative");
			chkBoxMusic[1].setText("Blues");
			chkBoxMusic[2].setText("Classical");
			chkBoxMusic[3].setText("Country");
			chkBoxMusic[4].setText("Electronic");
			chkBoxMusic[5].setText("Folk");
			chkBoxMusic[6].setText("Funk");
			chkBoxMusic[7].setText("Grunge");
			chkBoxMusic[8].setText("Hard Rock");
			chkBoxMusic[9].setText("Indie");
			chkBoxMusic[10].setText("Jazz");
			chkBoxMusic[11].setText("Metal");
			chkBoxMusic[12].setText("New Age");
			chkBoxMusic[13].setText("Pop");
			chkBoxMusic[14].setText("R&B");
			chkBoxMusic[15].setText("Rap");
			chkBoxMusic[16].setText("Reggae");
			chkBoxMusic[17].setText("Rock");
			
			// Button panel
			JPanel panelButtons = new JPanel();
			panelButtons.setBounds(10, 967, 303, 42);
			add(panelButtons);
			
			JButton btnFilter = new JButton("Filter");
			btnFilter.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnFilter.addActionListener(new FilterButtonListener());
			panelButtons.add(btnFilter);
			
			JButton btnRandomize = new JButton("Randomize");
			btnRandomize.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnRandomize.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadPersonPanels(randomizePersons());
				}
			});
			panelButtons.add(btnRandomize);
			
			JButton btnReset = new JButton("Reset");
			btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnReset.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					reset();
				}
			});
			panelButtons.add(btnReset);
			
			listCity.setModel(new DefaultListModel<String>());
			listCitySel.setModel(new DefaultListModel<String>());
			listInterests.setModel(new DefaultListModel<String>());
			listInterestsSel.setModel(new DefaultListModel<String>());
			
		}
		
		
		/** Method to reset all fields in the left panel and remove all person panels */
		public void reset() {
			txtMaxAge.setText("");
			txtMinAge.setText("");
			listCity.setModel(new DefaultListModel<String>());
			listCitySel.setModel(new DefaultListModel<String>());
			listInterests.setModel(new DefaultListModel<String>());
			listInterestsSel.setModel(new DefaultListModel<String>());
			loadList();
			for (int i = 0; i < chkBoxMusic.length; i++)
				chkBoxMusic[i].setSelected(false);
			for(int i = 0; i < chkBoxMovies.length; i++)
				chkBoxMovies[i].setSelected(false);
			for (int i = 0; i < chkBoxBooks.length; i++)
				chkBoxBooks[i].setSelected(false);
			chkBoxFemale.setSelected(false);
			chkBoxMale.setSelected(false);
			
			centerPanel.removeAll();
			centerPanel.revalidate();
			centerPanel.repaint();
		}
	}
}