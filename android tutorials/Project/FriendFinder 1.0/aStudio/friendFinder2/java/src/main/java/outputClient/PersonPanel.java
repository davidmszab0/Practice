package outputClient;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Font;
import java.awt.Color;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;

import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/** Class for a panel to display the data of a person
 */
public class PersonPanel extends JPanel {

	// Variable declarations
	JTextPane txtpnInterests = new JTextPane();
	JTextPane txtpnMovies = new JTextPane();
	JTextPane txtpnBooks = new JTextPane();
	JTextPane txtpnMusic = new JTextPane();
	JTextPane txtpnLanguages = new JTextPane();
	JPanel panelPersonal = new JPanel();
	JPanel panelUpper = new JPanel();
	JPanel panelLower = new JPanel();
	JPanel panelFavorites = new JPanel();
	JPanel panelInterests = new JPanel();
	JPanel panelMovies = new JPanel();
	JPanel panelBooks = new JPanel();
	JPanel panelMusic = new JPanel();
	JLabel lblInterests = new JLabel("Interests:");
	JLabel lblMovies = new JLabel("Movies genres:");
	JLabel lblBooks = new JLabel("Books genres:");
	JLabel lblMusicGenres = new JLabel("Music genres:");
	JLabel lblName = new JLabel("Name:");
	JLabel lblAge = new JLabel("Age:");
	JLabel lblPhone = new JLabel("Phone: ");
	JLabel lblEmail = new JLabel("E-mail: ");
	JLabel lblRegion = new JLabel("Region: ");
	JLabel lblCity = new JLabel("City:");
	JLabel lblUniversity = new JLabel("University: ");
	JLabel lblProgram = new JLabel("Program: ");
	JLabel lblNationality = new JLabel("Nationality: ");
	JLabel lblReligion = new JLabel("Religion: ");
	JLabel lblLanguages = new JLabel("Languages:");
	private final JPanel panelSchool = new JPanel();
	private final JPanel panelCulturals = new JPanel();
	private final JPanel panelContact = new JPanel();
	private final JLabel lblImage = new JLabel("");
	private final JLabel lblStars = new JLabel("");
	private final JLabel lblPerson = new JLabel("");
	private final JLabel lblMusic = new JLabel("");
	private final JLabel lblBook = new JLabel("");
	private final JLabel lblFilm = new JLabel("");
	private final JLabel lblGender = new JLabel("");
	
	/** Constructor that creates a panel with a persons data */
	public PersonPanel(Person person) {
		this(); // Call default constructor to set the layout
		
		// Setting the text in the labels from the data in object person
		lblName.setText(person.getFirstName() + " " + person.getLastName());
		lblAge.setText("Age: " + person.getAge());
		lblPhone.setText("Phone: " + person.getPhoneNumber());
		lblEmail.setText("E-mail: " + person.getEmail());
		lblRegion.setText("Region: " + person.getRegion());
		lblCity.setText("City: " + person.getCity());
		lblUniversity.setText("University: " + person.getUniversity());
		lblProgram.setText("Program: " + person.getProgram());
		lblNationality.setText("Nationality: " + person.getNationality());
		lblReligion.setText("Religion: " + person.getReligion());
		
		lblImage.setIcon(new ImageIcon(getClass().getResource("/pics/gradHat2.png")));
		lblStars.setIcon(new ImageIcon(getClass().getResource("/pics/stars.png")));
		lblPerson.setIcon(new ImageIcon(getClass().getResource("/pics/person.png")));
		lblMusic.setIcon(new ImageIcon(getClass().getResource("/pics/flyingscore.png")));
		lblBook.setIcon(new ImageIcon(getClass().getResource("/pics/book.png")));
		lblFilm.setIcon(new ImageIcon(getClass().getResource("/pics/film.png")));
		if (person.getGender().equals("M"))
			lblGender.setIcon(new ImageIcon(getClass().getResource("/pics/gender_male.png")));
		else
			lblGender.setIcon(new ImageIcon(getClass().getResource("/pics/gender_female.png")));
		
		String[] languagesArray = person.getLanguages();
		String languages = "";
		if (languagesArray.length > 0) {
			for (int i = 0; i < languagesArray.length - 1; i++)
				languages += languagesArray[i] + ", ";
			languages += languagesArray[languagesArray.length - 1];
			txtpnLanguages.setText(languages);
		}
		
		String[] interestsArray = person.getInterests();
		String interests = "";
		if (interestsArray.length > 0) {
			for (int i = 0; i < interestsArray.length - 1; i++)
				interests += interestsArray[i] + ", ";
			interests += interestsArray[interestsArray.length - 1];
			txtpnInterests.setText(interests);
		}
		
		String movies = "";
		String[] moviesArray = person.getMovieGenres();
		if (moviesArray.length > 0) {
			for (int i = 0; i < moviesArray.length - 1; i++)
				movies += moviesArray[i] + ", ";
			
			movies += moviesArray[moviesArray.length - 1];
			txtpnMovies.setText(movies);
		}
		
		String books = "";
		String[] booksArray = person.getBookGenres();
		if (booksArray.length > 0) {
			for (int i = 0; i < booksArray.length - 1; i++)
				books += booksArray[i] + ", ";
			books += booksArray[booksArray.length - 1];
			txtpnBooks.setText(books);
		}
		
		String music = "";
		String[] musicArray = person.getMusicGenres();
		if (musicArray.length > 0) {
			for (int i = 0; i < musicArray.length - 1; i++)
				music += musicArray[i] + ", ";
			music += musicArray[musicArray.length - 1];
			txtpnMusic.setText(music);
		}
		
		
	}
	
	/** Default constructor that sets the layout of the panel */
	public PersonPanel() {
		
		txtpnLanguages.setEditable(false);
		txtpnLanguages.setBackground(OutputFrame.COLOR_MIDDLE);
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new GridLayout(1, 0, 0, 0));
		
		
		add(panelPersonal);
		panelPersonal.setLayout(new GridLayout(2, 0, 0, 0));
		panelUpper.setBackground(OutputFrame.COLOR_DARK);
		
		
		panelPersonal.add(panelUpper);
		GroupLayout gl_panelUpper = new GroupLayout(panelUpper);
		gl_panelUpper.setHorizontalGroup(
			gl_panelUpper.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUpper.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelUpper.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panelContact, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panelSchool, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
					.addContainerGap(1, Short.MAX_VALUE))
		);
		gl_panelUpper.setVerticalGroup(
			gl_panelUpper.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelUpper.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelContact, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelSchool, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		lblName.setOpaque(true);
		lblName.setBackground(OutputFrame.COLOR_LIGHT);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_panelContact = new GroupLayout(panelContact);
		gl_panelContact.setHorizontalGroup(
			gl_panelContact.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelContact.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelContact.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEmail)
						.addComponent(lblPhone)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAge, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(196)
					.addComponent(lblPerson, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelContact.setVerticalGroup(
			gl_panelContact.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelContact.createSequentialGroup()
					.addContainerGap(11, Short.MAX_VALUE)
					.addGroup(gl_panelContact.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelContact.createSequentialGroup()
							.addGroup(gl_panelContact.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblName)
								.addComponent(lblAge)
								.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addComponent(lblEmail)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPhone)
							.addContainerGap())
						.addComponent(lblPerson, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
		);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelContact.setBackground(OutputFrame.COLOR_MIDDLE);
		panelContact.setLayout(gl_panelContact);
		panelSchool.setBackground(OutputFrame.COLOR_MIDDLE);
		GroupLayout gl_panelSchool = new GroupLayout(panelSchool);
		gl_panelSchool.setHorizontalGroup(
			gl_panelSchool.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchool.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSchool.createParallelGroup(Alignment.LEADING)
						.addComponent(lblProgram)
						.addComponent(lblUniversity))
					.addPreferredGap(ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
		);
		lblUniversity.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProgram.setFont(new Font("Tahoma", Font.BOLD, 11));
		gl_panelSchool.setVerticalGroup(
			gl_panelSchool.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelSchool.createSequentialGroup()
					.addGap(5)
					.addComponent(lblUniversity)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(lblProgram)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panelSchool.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblImage, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
		);
		panelSchool.setLayout(gl_panelSchool);
		panelUpper.setLayout(gl_panelUpper);
		panelLower.setBackground(OutputFrame.COLOR_DARK);
		
		
		panelPersonal.add(panelLower);
		GroupLayout gl_panelLower = new GroupLayout(panelLower);
		gl_panelLower.setHorizontalGroup(
			gl_panelLower.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelLower.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelLower.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCulturals, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
						.addGroup(gl_panelLower.createSequentialGroup()
							.addComponent(lblLanguages)
							.addContainerGap(283, Short.MAX_VALUE))
						.addComponent(txtpnLanguages, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)))
		);
		gl_panelLower.setVerticalGroup(
			gl_panelLower.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelLower.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelCulturals, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLanguages)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnLanguages, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		GroupLayout gl_panelCulturals = new GroupLayout(panelCulturals);
		gl_panelCulturals.setHorizontalGroup(
			gl_panelCulturals.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCulturals.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCulturals.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCity)
						.addGroup(gl_panelCulturals.createSequentialGroup()
							.addComponent(lblNationality)
							.addGap(89)
							.addComponent(lblReligion))
						.addComponent(lblRegion))
					.addContainerGap(122, Short.MAX_VALUE))
		);
		gl_panelCulturals.setVerticalGroup(
			gl_panelCulturals.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCulturals.createSequentialGroup()
					.addGap(5)
					.addComponent(lblCity)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblRegion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelCulturals.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNationality)
						.addComponent(lblReligion))
					.addContainerGap())
		);
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRegion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNationality.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReligion.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelCulturals.setBackground(OutputFrame.COLOR_MIDDLE);
		panelCulturals.setLayout(gl_panelCulturals);
		lblLanguages.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelLower.setLayout(gl_panelLower);
		
		
		add(panelFavorites);
		panelFavorites.setLayout(new GridLayout(4, 0, 0, 0));
		
		
		panelInterests.setBackground(OutputFrame.COLOR_DARK);
		panelFavorites.add(panelInterests);
		
		
		lblInterests.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnInterests.setEditable(false);
		
		txtpnInterests.setBackground(OutputFrame.COLOR_MIDDLE);
		GroupLayout gl_panelInterests = new GroupLayout(panelInterests);
		gl_panelInterests.setHorizontalGroup(
			gl_panelInterests.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInterests.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelInterests.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelInterests.createSequentialGroup()
							.addComponent(lblInterests)
							.addPreferredGap(ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
							.addComponent(lblStars, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtpnInterests, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelInterests.setVerticalGroup(
			gl_panelInterests.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInterests.createSequentialGroup()
					.addGroup(gl_panelInterests.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelInterests.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblInterests))
						.addComponent(lblStars, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnInterests, GroupLayout.PREFERRED_SIZE, 44, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelInterests.setLayout(gl_panelInterests);
		
		
		panelMovies.setBackground(OutputFrame.COLOR_DARK);
		panelFavorites.add(panelMovies);
		
		
		lblMovies.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnMovies.setEditable(false);
		
		txtpnMovies.setBackground(OutputFrame.COLOR_MIDDLE);
		GroupLayout gl_panelMovies = new GroupLayout(panelMovies);
		gl_panelMovies.setHorizontalGroup(
			gl_panelMovies.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMovies.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelMovies.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnMovies, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
						.addGroup(gl_panelMovies.createSequentialGroup()
							.addComponent(lblMovies)
							.addPreferredGap(ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
							.addComponent(lblFilm, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelMovies.setVerticalGroup(
			gl_panelMovies.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMovies.createSequentialGroup()
					.addGroup(gl_panelMovies.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMovies.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblMovies))
						.addComponent(lblFilm, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnMovies, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelMovies.setLayout(gl_panelMovies);
		
		
		panelBooks.setBackground(OutputFrame.COLOR_DARK);
		panelFavorites.add(panelBooks);
		
		
		lblBooks.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnBooks.setEditable(false);
		
		txtpnBooks.setBackground(OutputFrame.COLOR_MIDDLE);
		GroupLayout gl_panelBooks = new GroupLayout(panelBooks);
		gl_panelBooks.setHorizontalGroup(
			gl_panelBooks.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBooks.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelBooks.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBooks.createSequentialGroup()
							.addComponent(lblBooks)
							.addPreferredGap(ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
							.addComponent(lblBook, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtpnBooks, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelBooks.setVerticalGroup(
			gl_panelBooks.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBooks.createSequentialGroup()
					.addGroup(gl_panelBooks.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBooks.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblBooks))
						.addComponent(lblBook, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnBooks, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelBooks.setLayout(gl_panelBooks);
		
		
		panelMusic.setBackground(OutputFrame.COLOR_DARK);
		panelFavorites.add(panelMusic);
		
		
		lblMusicGenres.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnMusic.setEditable(false);
		
		txtpnMusic.setBackground(OutputFrame.COLOR_MIDDLE);
		GroupLayout gl_panelMusic = new GroupLayout(panelMusic);
		gl_panelMusic.setHorizontalGroup(
			gl_panelMusic.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelMusic.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelMusic.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnMusic, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
						.addGroup(gl_panelMusic.createSequentialGroup()
							.addComponent(lblMusicGenres)
							.addPreferredGap(ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
							.addComponent(lblMusic, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelMusic.setVerticalGroup(
			gl_panelMusic.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMusic.createSequentialGroup()
					.addGroup(gl_panelMusic.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblMusicGenres)
						.addComponent(lblMusic, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(txtpnMusic, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelMusic.setLayout(gl_panelMusic);

	}
}
