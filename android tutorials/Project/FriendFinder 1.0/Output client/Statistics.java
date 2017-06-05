
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class Statistics extends JPanel {

	
	public Statistics() {

		setLayout(null);
		
		String[] interest = Database.statistics("SELECT interest_name, Counter.c FROM interests "
				+ "INNER JOIN (SELECT interest_id, count(*) as c from persons_interests GROUP BY "
				+ "interest_id ORDER BY c desc limit 3) as Counter ON Counter.interest_id = "
				+ "interests.interest_id");
		
		String[] movies = Database.statistics("SELECT movie_genre, Counter.c FROM movie_genres " 
						+ "INNER JOIN (SELECT movie_genre_id, count(*) as c from persons_movie_genres GROUP BY "
						+ " movie_genre_id ORDER BY c desc limit 3) as Counter ON Counter.movie_genre_id = "
						+ "movie_genres.movie_genre_id");
		
		String[] books = Database.statistics("SELECT book_genre, Counter.c FROM book_genres "
				+ "INNER JOIN (SELECT book_genre_id, count(*) as c from persons_book_genres GROUP BY "
				+ "book_genre_id ORDER BY c desc limit 3) as Counter ON Counter.book_genre_id = "
				+ "book_genres.book_genre_id");
		
		String[] music = Database.statistics("SELECT music_genre, Counter.c FROM music_genres " 
				+ "INNER JOIN (SELECT music_genre_id, count(*) as c from persons_music_genres GROUP BY " 
				+ "music_genre_id ORDER BY c desc limit 3) as Counter ON Counter.music_genre_id = "
				+ "music_genres.music_genre_id");
		
		String[] cities = Database.statistics("SELECT city_name, Counter.c FROM cities " 
				+ "INNER JOIN (SELECT persons_city, count(*) as c from persons GROUP BY " 
				+ "persons_city ORDER BY c desc limit 3) as Counter ON Counter.persons_city = "
				+ "cities.city_id");
		
		int avgAge = Database.avgAge();
		
		JLabel lblFriendfinderStatistics = new JLabel("FriendFinder statistics");
		lblFriendfinderStatistics.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriendfinderStatistics.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFriendfinderStatistics.setBounds(0, 0, 250, 30);
		lblFriendfinderStatistics.setOpaque(true);
		lblFriendfinderStatistics.setBackground(OutputFrame.COLOR_MIDDLE);
		add(lblFriendfinderStatistics);
		
		JLabel lblTopCities = new JLabel("Top 3 Cities:");
		lblTopCities.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopCities.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopCities.setBounds(39, 41, 161, 17);
		add(lblTopCities);
		
		JPanel panelCities = new JPanel();
		panelCities.setBounds(49, 62, 147, 57);
		panelCities.setBackground(OutputFrame.COLOR_MIDDLE);
		add(panelCities);
		panelCities.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel[] lblCity = new JLabel[3];
		for (int i = 0; i < lblCity.length; i++) {
			lblCity[i] = new JLabel(cities[i]);
			panelCities.add(lblCity[i]);
		}
		
		JLabel lblTopInterests = new JLabel("Top 3 Interests:");
		lblTopInterests.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopInterests.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopInterests.setBounds(39, 121, 161, 17);
		add(lblTopInterests);
		
		JPanel panelInterests = new JPanel();
		panelInterests.setBounds(49, 142, 147, 57);
		panelInterests.setBackground(OutputFrame.COLOR_MIDDLE);
		add(panelInterests);
		panelInterests.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel[] lblInterest = new JLabel[3];
		for (int i = 0; i < lblInterest.length; i++) {
			lblInterest[i] = new JLabel(interest[i]);
			panelInterests.add(lblInterest[i]);
		}
		
		JLabel lblTopMovie = new JLabel("Top 3 Movie genres:");
		lblTopMovie.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopMovie.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopMovie.setBounds(39, 200, 161, 17);
		add(lblTopMovie);
		
		JPanel panelMovies = new JPanel();
		panelMovies.setBounds(49, 221, 147, 57);
		panelMovies.setBackground(OutputFrame.COLOR_MIDDLE);
		add(panelMovies);
		panelMovies.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel[] lblMovie = new JLabel[3];
		for (int i = 0; i < lblMovie.length; i++) {
			lblMovie[i] = new JLabel(movies[i]);
			panelMovies.add(lblMovie[i]);
		}
		
		JLabel lblTopBook = new JLabel("Top 3 Book genres:");
		lblTopBook.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopBook.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopBook.setBounds(39, 279, 161, 17);
		add(lblTopBook);
		
		JPanel panelBooks = new JPanel();
		panelBooks.setBounds(49, 300, 147, 57);
		panelBooks.setBackground(OutputFrame.COLOR_MIDDLE);
		add(panelBooks);
		panelBooks.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel[] lblBook = new JLabel[3];
		for (int i = 0; i < lblBook.length; i++) {
			lblBook[i] = new JLabel(books[i]);
			panelBooks.add(lblBook[i]);
		}
		
		JLabel lblTopMusic = new JLabel("Top 3 Music genres:");
		lblTopMusic.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopMusic.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopMusic.setBounds(39, 357, 161, 17);
		add(lblTopMusic);
		
		JPanel panelMusic = new JPanel();
		panelMusic.setBounds(49, 378, 147, 57);
		panelMusic.setBackground(OutputFrame.COLOR_MIDDLE);
		add(panelMusic);
		panelMusic.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel[] lblMusic = new JLabel[3];
		for (int i = 0; i < lblMusic.length; i++) {
			lblMusic[i] = new JLabel(music[i]);
			panelMusic.add(lblMusic[i]);
		}
		
		JLabel lblAverageAge = new JLabel("Average age:");
		lblAverageAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAverageAge.setBounds(59, 456, 85, 17);
		add(lblAverageAge);
		
		JLabel lblAge = new JLabel(avgAge + "");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setBounds(154, 457, 60, 14);
		add(lblAge);
		

	}
	
	
}