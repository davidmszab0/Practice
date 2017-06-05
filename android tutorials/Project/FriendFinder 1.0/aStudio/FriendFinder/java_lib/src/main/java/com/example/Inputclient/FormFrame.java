package com.example.Inputclient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/** Class for the person form */
public class FormFrame extends JFrame {

	// Variables declaration
	DefaultListModel<String> model;
	private JList<String> interestChoiceList = new JList<String>();
	private JLabel interestLabel = new JLabel();
	private JList<String> interestList = new JList<String>();
	private JComboBox<String> programBox = new JComboBox<String>();
	private JLabel programLabel = new JLabel();
	private JComboBox<String> schoolBox = new JComboBox<String>();
	private JLabel schoolLabel = new JLabel();
	private JButton addBookButton = new JButton();
	private JButton addInterestButton = new JButton();
	private JButton addLanguageButton = new JButton();
	private JButton addMovieButton = new JButton();
	private JButton addMusicButton = new JButton();
	private JButton addNewCityButton = new JButton();
	private JButton addNewInterestButton = new JButton();
	private JButton addNewLanguageButton = new JButton();
	private JButton addNewNationalityButton = new JButton();
	private JButton addNewProgramButton = new JButton();
	private JButton addNewReligionButton = new JButton();
	private JButton addNewSchoolButton = new JButton();
	private JLabel ageLabel = new JLabel();
	private JLabel ageStar = new JLabel();
	private JTextField ageText = new JTextField();
	private JList<String> bookChoiceList = new JList<String>();
	private JLabel bookLabel = new JLabel();
	private JList<String> bookList = new JList<String>();
	private JButton cancelButton = new JButton();
	private JLabel emailLabel = new JLabel();
	private JTextField emailText = new JTextField();
	private JLabel firstNStar = new JLabel();
	private JLabel firstName = new JLabel();
	private JTextField firstNameText = new JTextField();
	private JLabel gStar = new JLabel();
	private JComboBox<String> genderBox = new JComboBox<String>();
	private JLabel genderLabel = new JLabel();
	private JList<String> languageChoiceList = new JList<String>();
	private JLabel languageLabel = new JLabel();
	private JList<String> languageList = new JList<String>();
	private JLabel lastNStar = new JLabel();
	private JLabel lastName = new JLabel();
	private JTextField lastNameText = new JTextField();
	private JScrollPane leftBookScroll = new JScrollPane();
	private JScrollPane leftIntScroll = new JScrollPane();
	private JScrollPane leftLangScroll = new JScrollPane();
	private JScrollPane leftMovieScroll = new JScrollPane();
	private JScrollPane leftMusicScroll = new JScrollPane();
	private JComboBox<String> locationCityBox = new JComboBox<String>();
	private JLabel locationCityLabel = new JLabel();
	private JComboBox<String> locationStateBox = new JComboBox<String>();
	private JLabel locationStateLabel = new JLabel();
	private JLabel mailStar = new JLabel();
	private JPanel mainPanel = new JPanel();
	private JList<String> movieChoiceList = new JList<String>();
	private JLabel movieLabel = new JLabel();
	private JList<String> movieList = new JList<String>();
	private JList<String> musicChoiceList = new JList<String>();
	private JLabel musicLabel = new JLabel();
	private JList<String> musicList = new JList<String>();
	private JComboBox<String> nationalityBox = new JComboBox<String>();
	private JLabel nationalityLabel = new JLabel();
	private JLabel phoneNumberLabel = new JLabel();
	private JTextField phoneNumberText = new JTextField();
	private JLabel phoneStar = new JLabel();
	private JComboBox<String> religionBox = new JComboBox<String>();
	private JLabel religionLabel = new JLabel();
	private JButton removeBookButton = new JButton();
	private JButton removeInterestButton = new JButton();
	private JButton removeLanguageButton = new JButton();
	private JButton removeMovieButton = new JButton();
	private JButton removeMusicButton = new JButton();
	private JScrollPane rightBookScroll = new JScrollPane();
	private JScrollPane rightIntScroll = new JScrollPane();
	private JScrollPane rightLangScroll = new JScrollPane();
	private JScrollPane rightMovieScroll = new JScrollPane();
	private JScrollPane rightMusicScroll = new JScrollPane();
	private JButton savePersonButton = new JButton();
	private JScrollPane mainScroll = new JScrollPane(mainPanel);
	private boolean edit;
	private Person person = new Person();

	/**
	 * Constructor for the edit frame that takes a Person object as parameter to
	 * set the fields
	 */
	public FormFrame(Person person) {
		this(); // Calling the default constructor to set up frame
		edit = true;
		this.person = person;
		firstNameText.setText(person.getFirstName());
		lastNameText.setText(person.getLastName());
		ageText.setText("" + person.getDob());
		emailText.setText(person.getEmail());
		phoneNumberText.setText(person.getPhoneNumber());
		String gender = person.getGender();
		if (gender.equals("M"))
			genderBox.setSelectedItem("Male");
		else
			genderBox.setSelectedItem("Female");
		locationStateBox.setSelectedItem(person.getRegion());
		locationCityBox.setSelectedItem(person.getCity());
		religionBox.setSelectedItem(person.getReligion());
		nationalityBox.setSelectedItem(person.getNationality());
		schoolBox.setSelectedItem(person.getUniversity());
		programBox.setSelectedItem(person.getProgram());
		setChoicesOfPerson(person.getInterests(), interestChoiceList, interestList);
		setChoicesOfPerson(person.getLanguages(), languageChoiceList, languageList);
		setChoicesOfPerson(person.getMovieGenres(), movieChoiceList, movieList);
		setChoicesOfPerson(person.getBookGenres(), bookChoiceList, bookList);
		setChoicesOfPerson(person.getMusicGenres(), musicChoiceList, musicList);
		
		
	}

	/** Default constructor to set up the frame */
	public FormFrame() {
		edit = false;
		// Casting of lists do default list models (HE)
		languageList.setModel(new DefaultListModel<String>());
		languageChoiceList.setModel(new DefaultListModel<String>());
		interestList.setModel(new DefaultListModel<String>());
		interestChoiceList.setModel(new DefaultListModel<String>());
		musicList.setModel(new DefaultListModel<String>());
		musicChoiceList.setModel(new DefaultListModel<String>());
		movieList.setModel(new DefaultListModel<String>());
		movieChoiceList.setModel(new DefaultListModel<String>());
		bookList.setModel(new DefaultListModel<String>());
		bookChoiceList.setModel(new DefaultListModel<String>());

		mainScroll.setViewportView(mainPanel);
		mainScroll.getVerticalScrollBar();
		mainScroll.setBorder(BorderFactory.createTitledBorder("Add New Person"));

		firstName.setFont(new Font("Tahoma", 0, 14));
		firstName.setText("First Name");
		lastName.setFont(new Font("Tahoma", 0, 14));
		lastName.setText("Last Name");

		ageLabel.setFont(new Font("Tahoma", 0, 14));
		ageLabel.setText("DOB (YYYY-MM-DD)");

		genderBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Male", "Female" }));

		genderLabel.setFont(new Font("Tahoma", 0, 14));
		genderLabel.setText("Gender");

		locationStateLabel.setFont(new Font("Tahoma", 0, 14));
		locationStateLabel.setText("Location State");
		locationCityLabel.setFont(new Font("Tahoma", 0, 14));
		locationCityLabel.setText("Location City");
		addNewCityButton.setText("Add New");
		addNewCityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Type in the new city: ");
				locationCityBox.addItem(input);
				Database.addCity(input, (String)locationStateBox.getSelectedItem());
			}
		});

		phoneNumberLabel.setFont(new Font("Tahoma", 0, 14));
		phoneNumberLabel.setText("Phone Number");

		emailLabel.setFont(new Font("Tahoma", 0, 14));
		emailLabel.setText("Email address");

		religionLabel.setFont(new Font("Tahoma", 0, 14));
		religionLabel.setText("Religion");
		addNewReligionButton.setText("Add New");
		addNewReligionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Type in the new religion: ");
				religionBox.addItem(input);
				Database.addNewElement("religions", input, "religion_name");
				
			}
		});

		nationalityLabel.setFont(new Font("Tahoma", 0, 14));
		nationalityLabel.setText("Nationality");
		addNewNationalityButton.setText("Add New");
		addNewNationalityButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Type in the new nationality: ");
				nationalityBox.addItem(input);
				Database.addNewElement("nationalities", input, "nationality_name");				
			}
		});

		languageLabel.setFont(new Font("Tahoma", 0, 14));
		languageLabel.setText("Language");
		leftLangScroll.setViewportView(languageList);
		rightLangScroll.setViewportView(languageChoiceList);
		addLanguageButton.setText("->");
		removeLanguageButton.setText("<-");
		addNewLanguageButton.setText("Add New");
		addNewLanguageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Type in the new language: ");
				DefaultListModel<String> model = (DefaultListModel<String>) languageList.getModel();
				model.addElement(input);
				Database.addNewElement("languages", input, "language_name");
				
			}
		});

		interestLabel.setFont(new Font("Tahoma", 0, 14));
		interestLabel.setText("Interest");
		leftIntScroll.setViewportView(interestList);
		rightIntScroll.setViewportView(interestChoiceList);
		addInterestButton.setText("->");
		removeInterestButton.setText("<-");
		addNewInterestButton.setText("Add New");
		addNewInterestButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Type in the new interest: ");
				DefaultListModel<String> model = (DefaultListModel<String>) interestList.getModel();
				model.addElement(input);
				Database.addNewElement("interests", input, "interest_name");				
			}
		});

		musicLabel.setFont(new Font("Tahoma", 0, 14));
		musicLabel.setText("Music");
		leftMusicScroll.setViewportView(musicList);
		addMusicButton.setText("->");
		removeMusicButton.setText("<-");
		rightMusicScroll.setViewportView(musicChoiceList);

		leftMovieScroll.setViewportView(movieList);
		movieLabel.setFont(new Font("Tahoma", 0, 14));
		movieLabel.setText("Movies");
		addMovieButton.setText("->");
		removeMovieButton.setText("<-");
		rightMovieScroll.setViewportView(movieChoiceList);

		leftBookScroll.setViewportView(bookList);
		bookLabel.setFont(new Font("Tahoma", 0, 14));
		bookLabel.setText("Books");
		addBookButton.setText("->");
		removeBookButton.setText("<-");
		rightBookScroll.setViewportView(bookChoiceList);

		savePersonButton.setText("Save");
		savePersonButton.addActionListener(new SaveButtonListener());
		cancelButton.setText("Cancel");

		schoolLabel.setFont(new Font("Tahoma", 0, 14));
		schoolLabel.setText("School / University");
		addNewSchoolButton.setText("Add New");
		addNewSchoolButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Type in the new school: ");
				schoolBox.addItem(input);
				Database.addNewElement("universities", input, "university_name");				
			}
		});

		programLabel.setFont(new Font("Tahoma", 0, 14));
		programLabel.setText("Program");
		addNewProgramButton.setText("Add New");
		addNewProgramButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog("Type in the new program: ");
				programBox.addItem(input);
				Database.addProgram(input, (String)schoolBox.getSelectedItem());
				
			}
		});

		// Stars for showing a field is mandatory
		firstNStar.setForeground(new Color(255, 0, 0));
		firstNStar.setText("*");
		lastNStar.setForeground(new Color(255, 0, 0));
		lastNStar.setText("*");
		ageStar.setForeground(new Color(255, 0, 0));
		ageStar.setText("*");
		gStar.setForeground(new Color(255, 0, 0));
		gStar.setText("*");
		phoneStar.setForeground(new Color(255, 0, 0));
		phoneStar.setText("*");
		mailStar.setForeground(new Color(255, 0, 0));
		mailStar.setText("*");

		// Restrictions for the phone number text field (HE)
		phoneNumberText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
						&& (c != '+')) {
					e.consume(); // ignore event
				}
			}
		});

		// Close Form-Frame when pressing the Cancel button (HE)
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormFrame.this.dispose();
			}
		});

		// Add & remove button-listeners (HE)
		addLanguageButton.addActionListener(new AddRemoveListener());
		removeLanguageButton.addActionListener(new AddRemoveListener());
		addInterestButton.addActionListener(new AddRemoveListener());
		removeInterestButton.addActionListener(new AddRemoveListener());
		addMovieButton.addActionListener(new AddRemoveListener());
		removeMovieButton.addActionListener(new AddRemoveListener());
		addBookButton.addActionListener(new AddRemoveListener());
		removeBookButton.addActionListener(new AddRemoveListener());
		addMusicButton.addActionListener(new AddRemoveListener());
		removeMusicButton.addActionListener(new AddRemoveListener());
		
		// Method so programs only show related schools (HE)
				schoolBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> list = new ArrayList<String>();
						programBox.removeAllItems();
						list = Database.getAllPrograms((String)schoolBox.getSelectedItem());
						for (String element : list)
							programBox.addItem(element);
						list.clear();
					}
				});
				
				// Method so states only show related cities (HE)
				locationStateBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> list = new ArrayList<String>();
						locationCityBox.removeAllItems();
						list = Database.getAllCityNames((String)locationStateBox.getSelectedItem());
						for (String element : list)
							locationCityBox.addItem(element);
						list.clear();
					}
				});

		setLayout(); // sets layout in frame
		loadBoxLists(); // loads all the lists
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}
	
	/** Method to set the different selected options */
	private void setChoicesOfPerson(String[] array, JList<String> addList, JList<String> removeList) {
		if(array.length > 0)
			for (int i = 0; i < array.length; i++) {
				addToList(array[i], addList);
				removeFromList(array[i], removeList);
			}
	}
	
	/** Method to remove an item from a list */
	private void removeFromList(String item, JList<String> list) {
		DefaultListModel<String> model = (DefaultListModel<String>)list.getModel();
		model.removeElement(item);
	}
	
	
	/** Method to add an item to a list */
	private void addToList(String item, JList<String> list) {
		DefaultListModel<String> model = (DefaultListModel<String>)list.getModel();
		model.addElement(item);
		
	}

	/** Method to set layout of form */
	private void setLayout() {
		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								mainPanelLayout
										.createSequentialGroup()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				savePersonButton)
																		.addGap(2,
																				2,
																				2))
														.addGroup(
																GroupLayout.Alignment.LEADING,
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												ageText,
																												GroupLayout.PREFERRED_SIZE,
																												80,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(71,
																												71,
																												71))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												firstNameText)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)))
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGap(12,
																												12,
																												12)
																										.addComponent(
																												genderLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												gStar,
																												GroupLayout.PREFERRED_SIZE,
																												18,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								genderBox,
																								GroupLayout.PREFERRED_SIZE,
																								78,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lastNameText,
																								GroupLayout.PREFERRED_SIZE,
																								108,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												lastName)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												lastNStar,
																												GroupLayout.PREFERRED_SIZE,
																												18,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE))
														.addGroup(
																GroupLayout.Alignment.LEADING,
																mainPanelLayout
																		.createSequentialGroup()
																		.addGap(2,
																				2,
																				2)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																locationCityBox,
																																GroupLayout.Alignment.LEADING,
																																0,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																locationStateBox,
																																GroupLayout.Alignment.LEADING,
																																0,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addGroup(
																																GroupLayout.Alignment.LEADING,
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				phoneNumberText,
																																				GroupLayout.PREFERRED_SIZE,
																																				130,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addGap(0,
																																				0,
																																				Short.MAX_VALUE)))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												addNewCityButton))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																religionBox,
																																GroupLayout.Alignment.LEADING,
																																0,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																nationalityBox,
																																0,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING,
																																false)
																														.addComponent(
																																addNewReligionButton,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																addNewNationalityButton,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												schoolBox,
																												GroupLayout.PREFERRED_SIZE,
																												166,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												addNewSchoolButton,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												programBox,
																												GroupLayout.PREFERRED_SIZE,
																												166,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												addNewProgramButton,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																emailText,
																																GroupLayout.PREFERRED_SIZE,
																																130,
																																GroupLayout.PREFERRED_SIZE)
																														.addGroup(
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED,
																																				8,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addGroup(
																																				mainPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.LEADING)
																																						.addGroup(
																																								mainPanelLayout
																																										.createParallelGroup(
																																												GroupLayout.Alignment.TRAILING)
																																										.addGroup(
																																												mainPanelLayout
																																														.createSequentialGroup()
																																														.addComponent(
																																																addNewLanguageButton)
																																														.addGap(147,
																																																147,
																																																147))
																																										.addGroup(
																																												GroupLayout.Alignment.LEADING,
																																												mainPanelLayout
																																														.createSequentialGroup()
																																														.addComponent(
																																																schoolLabel)
																																														.addGap(123,
																																																123,
																																																123)))
																																						.addComponent(
																																								programLabel))))
																										.addGap(0,
																												0,
																												Short.MAX_VALUE))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												leftLangScroll,
																												GroupLayout.PREFERRED_SIZE,
																												81,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(18,
																												18,
																												18)
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																addLanguageButton)
																														.addComponent(
																																removeLanguageButton,
																																GroupLayout.Alignment.TRAILING))
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												rightLangScroll,
																												GroupLayout.PREFERRED_SIZE,
																												0,
																												Short.MAX_VALUE)))))
										.addGap(29, 29, 29)
										.addComponent(cancelButton)
										.addGap(222, 222, 222))
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				firstName)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				firstNStar)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				interestLabel)
																		.addGap(112,
																				112,
																				112))
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																mainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				locationCityLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				addNewInterestButton)
																		.addGap(2,
																				2,
																				2))
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												ageLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												ageStar,
																												GroupLayout.PREFERRED_SIZE,
																												18,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								locationStateLabel))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				leftIntScroll,
																				GroupLayout.PREFERRED_SIZE,
																				85,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								addInterestButton,
																								GroupLayout.PREFERRED_SIZE,
																								52,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								removeInterestButton,
																								GroupLayout.PREFERRED_SIZE,
																								52,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				rightIntScroll,
																				GroupLayout.PREFERRED_SIZE,
																				85,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING,
																								false)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																nationalityLabel)
																														.addComponent(
																																languageLabel))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addGroup(
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addGap(79,
																																				79,
																																				79)
																																		.addComponent(
																																				movieLabel))
																														.addGroup(
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				leftMovieScroll,
																																				GroupLayout.PREFERRED_SIZE,
																																				85,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addGroup(
																																				mainPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.TRAILING)
																																						.addComponent(
																																								addMovieButton,
																																								GroupLayout.PREFERRED_SIZE,
																																								52,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								removeMovieButton,
																																								GroupLayout.PREFERRED_SIZE,
																																								52,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				rightMovieScroll,
																																				GroupLayout.PREFERRED_SIZE,
																																				85,
																																				GroupLayout.PREFERRED_SIZE))))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.TRAILING)
																														.addGroup(
																																GroupLayout.Alignment.LEADING,
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				phoneNumberLabel)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				phoneStar,
																																				GroupLayout.PREFERRED_SIZE,
																																				18,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																GroupLayout.Alignment.LEADING,
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				emailLabel)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				mailStar,
																																				GroupLayout.PREFERRED_SIZE,
																																				18,
																																				GroupLayout.PREFERRED_SIZE))
																														.addComponent(
																																religionLabel,
																																GroupLayout.Alignment.LEADING))
																										.addGap(198,
																												198,
																												198)
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addGroup(
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				leftMusicScroll,
																																				GroupLayout.PREFERRED_SIZE,
																																				85,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addGroup(
																																				mainPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.TRAILING)
																																						.addComponent(
																																								addMusicButton,
																																								GroupLayout.PREFERRED_SIZE,
																																								52,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								removeMusicButton,
																																								GroupLayout.PREFERRED_SIZE,
																																								52,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				rightMusicScroll,
																																				GroupLayout.PREFERRED_SIZE,
																																				85,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				leftBookScroll,
																																				GroupLayout.PREFERRED_SIZE,
																																				85,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addGroup(
																																				mainPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.TRAILING)
																																						.addComponent(
																																								addBookButton,
																																								GroupLayout.PREFERRED_SIZE,
																																								52,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								removeBookButton,
																																								GroupLayout.PREFERRED_SIZE,
																																								52,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				rightBookScroll,
																																				GroupLayout.PREFERRED_SIZE,
																																				85,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																mainPanelLayout
																																		.createSequentialGroup()
																																		.addGap(79,
																																				79,
																																				79)
																																		.addGroup(
																																				mainPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.LEADING)
																																						.addComponent(
																																								musicLabel)
																																						.addComponent(
																																								bookLabel))))))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))));
		mainPanelLayout
				.setVerticalGroup(mainPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(firstName)
														.addComponent(lastName)
														.addComponent(
																interestLabel)
														.addComponent(
																firstNStar)
														.addComponent(lastNStar))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																firstNameText,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lastNameText,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																ageLabel)
																														.addComponent(
																																genderLabel)
																														.addComponent(
																																ageStar)
																														.addComponent(
																																gStar)))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGap(9,
																												9,
																												9)
																										.addComponent(
																												addInterestButton)))
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												mainPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																ageText,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																genderBox,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												locationStateLabel))
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGap(3,
																												3,
																												3)
																										.addComponent(
																												removeInterestButton)))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				locationStateBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																leftIntScroll,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																rightIntScroll,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																locationCityLabel)
														.addComponent(
																addNewInterestButton))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								locationCityBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								addNewCityButton))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								phoneNumberLabel,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								phoneStar,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addGap(4,
																				4,
																				4)
																		.addComponent(
																				phoneNumberText,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								emailLabel)
																						.addComponent(
																								mailStar))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				emailText,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				religionLabel)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								religionBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								addNewReligionButton))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				nationalityLabel)
																		.addGap(1,
																				1,
																				1)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								nationalityBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								addNewNationalityButton))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				languageLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								mainPanelLayout
																										.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																										.addComponent(
																												leftLangScroll,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addGroup(
																												mainPanelLayout
																														.createSequentialGroup()
																														.addComponent(
																																addLanguageButton)
																														.addGap(18,
																																18,
																																18)
																														.addComponent(
																																removeLanguageButton)))
																						.addComponent(
																								rightLangScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				addNewLanguageButton)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				schoolLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								schoolBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								addNewSchoolButton))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				programLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								programBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								addNewProgramButton)))
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				musicLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGap(9,
																												9,
																												9)
																										.addComponent(
																												addMusicButton)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												removeMusicButton))
																						.addComponent(
																								leftMusicScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								rightMusicScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				movieLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGap(9,
																												9,
																												9)
																										.addComponent(
																												addMovieButton)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												removeMovieButton))
																						.addComponent(
																								leftMovieScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								rightMovieScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				bookLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								mainPanelLayout
																										.createSequentialGroup()
																										.addGap(9,
																												9,
																												9)
																										.addComponent(
																												addBookButton)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												removeBookButton))
																						.addComponent(
																								leftBookScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								rightBookScroll,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED,
												40, Short.MAX_VALUE)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																savePersonButton)
														.addComponent(
																cancelButton))));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(mainScroll, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(mainScroll, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
	}

	/** Method to populate all lists with data from database */
	private void loadBoxLists() {
		String[] list = Database.getLists("regions", "region_name");
		Arrays.sort(list);
		for (String element : list)
			locationStateBox.addItem(element);
		
		list = Database.getLists("religions", "religion_name");
		
		for (String element : list)
			religionBox.addItem(element);
		Arrays.sort(list);
		list = Database.getLists("nationalities", "nationality_name");
		Arrays.sort(list);
		for (String element : list)
			nationalityBox.addItem(element);
		
		list = Database.getLists("universities", "university_name");
		Arrays.sort(list);
		for (String element : list)
			schoolBox.addItem(element);
		
		model = new DefaultListModel<String>();
		list = Database.getLists("languages", "language_name");
		Arrays.sort(list);
		for (String element : list) {
			model.addElement(element);
		}
		languageList.setModel(model);
		languageList.setSelectedIndex(0);
		
		model = new DefaultListModel<String>();
		list = Database.getLists("interests", "interest_name");
		Arrays.sort(list);
		for (String element : list) {
			model.addElement(element);
		}
		interestList.setModel(model);
		interestList.setSelectedIndex(0);
		
		model = new DefaultListModel<String>();
		list = Database.getLists("music_genres", "music_genre");
		Arrays.sort(list);
		for (String element : list) {
			model.addElement(element);
		}
		musicList.setModel(model);
		musicList.setSelectedIndex(0);
		
		model = new DefaultListModel<String>();
		list = Database.getLists("movie_genres", "movie_genre");
		Arrays.sort(list);
		for (String element : list) {
			model.addElement(element);
		}
		movieList.setModel(model);
		movieList.setSelectedIndex(0);
		
		model = new DefaultListModel<String>();
		list = Database.getLists("book_genres", "book_genre");
		Arrays.sort(list);
		for (String element : list) {
			model.addElement(element);
		}
		bookList.setModel(model);
		bookList.setSelectedIndex(0);
	}

	/** Actionlistener to save persons to the database */
	class SaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			person.setFirstName(firstNameText.getText());
			person.setLastName(lastNameText.getText());
			person.setDob(ageText.getText());
			if (genderBox.getSelectedItem().equals("Male"))
				person.setGender("M");
			else
				person.setGender("F");
			person.setPhoneNumber(phoneNumberText.getText());
			person.setEmail(emailText.getText());
			if (person.getFirstName().equals("")) {
				JOptionPane.showMessageDialog(savePersonButton,
						"You forgot the first name!");
			} else if (person.getLastName().equals("")) {
				JOptionPane.showMessageDialog(savePersonButton,
						"You forgot the last name!");
			} else if (person.getDob() == null) {
				JOptionPane.showMessageDialog(savePersonButton,
						"You forgot to input date of birth!");
			} else if (person.getPhoneNumber().equals("")) {
				JOptionPane.showMessageDialog(savePersonButton,
						"You forgot the phone-number!");
			} else if (person.getEmail().equals("")) {
				JOptionPane.showMessageDialog(savePersonButton,
						"You forgot the Email!");
			} else {

				person.setLanguages(new String[languageChoiceList.getModel()
						.getSize()]);
				for (int i = 0; i < languageChoiceList.getModel().getSize(); i++)
					person.getLanguages()[i] = languageChoiceList.getModel()
							.getElementAt(i);

				person.setInterests(new String[interestChoiceList.getModel()
						.getSize()]);
				for (int i = 0; i < interestChoiceList.getModel().getSize(); i++)
					person.getInterests()[i] = interestChoiceList.getModel()
							.getElementAt(i);

				person.setMovieGenres(new String[movieChoiceList.getModel()
						.getSize()]);
				for (int i = 0; i < movieChoiceList.getModel().getSize(); i++)
					person.getMovieGenres()[i] = movieChoiceList.getModel()
							.getElementAt(i);

				person.setBookGenres(new String[bookChoiceList.getModel()
						.getSize()]);
				for (int i = 0; i < bookChoiceList.getModel().getSize(); i++)
					person.getBookGenres()[i] = bookChoiceList.getModel()
							.getElementAt(i);

				person.setMusicGenres(new String[musicChoiceList.getModel()
						.getSize()]);
				for (int i = 0; i < musicChoiceList.getModel().getSize(); i++)
					person.getMusicGenres()[i] = musicChoiceList.getModel()
							.getElementAt(i);
				
				person.setNationality((String)nationalityBox.getSelectedItem());
				person.setReligion((String)religionBox.getSelectedItem());
				person.setProgram((String)programBox.getSelectedItem());
				person.setCity((String)locationCityBox.getSelectedItem());

				if (edit == true)
					Database.editPerson(person);
				else
					Database.addPerson(person);
				FormFrame.this.dispose();
			}
		}
	}

	/** Actionlistener to add and remove attributes from the database */
	class AddRemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addInterestButton) {
				if (interestList.getSelectedIndex() >= 0) {
					String element = interestList.getSelectedValue();
					addToList(element, interestChoiceList);
					removeFromList(element, interestList);
				}
			}
			if (e.getSource() == removeInterestButton) {
				if (interestChoiceList.getSelectedIndex() >= 0) {
					String element = interestChoiceList.getSelectedValue();
					addToList(element, interestList);
					removeFromList(element, interestChoiceList);
				}
			}
			if (e.getSource() == addLanguageButton) {
				if (languageList.getSelectedIndex() >= 0) {
					String element = languageList.getSelectedValue();
					addToList(element, languageChoiceList);
					removeFromList(element, languageList);
				}
			}
			if (e.getSource() == removeLanguageButton) {
				if (languageChoiceList.getSelectedIndex() >= 0) {
					String element = languageChoiceList.getSelectedValue();
					addToList(element, languageList);
					removeFromList(element, languageChoiceList);
				}
			}
			if (e.getSource() == addMovieButton) {
				if (movieList.getSelectedIndex() >= 0) {
					String element = movieList.getSelectedValue();
					addToList(element, movieChoiceList);
					removeFromList(element, movieList);
				}
			}
			if (e.getSource() == removeMovieButton) {
				if (movieChoiceList.getSelectedIndex() >= 0) {
					String element = movieChoiceList.getSelectedValue();
					addToList(element, movieList);
					removeFromList(element, movieChoiceList);
				}
			}
			if (e.getSource() == addBookButton) {
				if (bookList.getSelectedIndex() >= 0) {
					String element = bookList.getSelectedValue();
					addToList(element, bookChoiceList);
					removeFromList(element, bookList);
				}
			}
			if (e.getSource() == removeBookButton) {
				if (bookChoiceList.getSelectedIndex() >= 0) {
					String element = bookChoiceList.getSelectedValue();
					addToList(element, bookList);
					removeFromList(element, bookChoiceList);
				}
			}
			if (e.getSource() == addMusicButton) {
				if (musicList.getSelectedIndex() >= 0) {
					String element = musicList.getSelectedValue();
					addToList(element, musicChoiceList);
					removeFromList(element, musicList);
				}
			}
			if (e.getSource() == removeMusicButton) {
				if (musicChoiceList.getSelectedIndex() >= 0) {
					String element = musicChoiceList.getSelectedValue();
					addToList(element, musicList);
					removeFromList(element, musicChoiceList);
				}
			}
			
		}
		
	}

}
