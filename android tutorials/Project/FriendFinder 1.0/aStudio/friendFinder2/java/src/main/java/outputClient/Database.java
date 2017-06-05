package outputClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/** Class for interacting with the database */
public class Database {

	private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/friendfinder";
	private final static String DB_USER = "input_user";
	private final static String DB_PASSWORD = "input_user";
	private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	
	/** Method to connect to database */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DB_DRIVER);
		Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		return c;
	}
	
	/** Method to get the person id, first name, last name and email in a string array list for all tuples in the database starting with a given string for a given column */
	public static ArrayList<String> getTuplesStartsWith(String column, String str) {
		ArrayList<String> list = new ArrayList<String>();
		String query = "SELECT person_id, first_name, last_name, email FROM persons WHERE " + column + " LIKE '"+ str + "%'";
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			int nrColumns = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				String temp = "";
				for (int i = 1; i <= nrColumns; i++) {
					temp = temp + rs.getString(i) + "; ";

				}
				list.add(temp);
			}
			
			// Closing connection
			rs.close();
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			System.out.println("getTuplesStartsWith error: " + e);
		}
		return list;
	}
	
	/** Method to get an array list of the ids */
	public static ArrayList<Integer> getPersonIDs(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT person_id FROM persons");
			while(rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println("getPersonIDs error: " + e);
		}
		
		return list;
	}
	
	/** Method to get the number of persons in the database */
	public static int getNumberOfPersons(){
		int number = 0;
		
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(person_id) FROM persons");
			rs.next();
			number = rs.getInt(1);
			
			rs.close();
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			System.out.println("getNumberOfPersons error: " + e);
		}
		
		return number;
	}
	
	/** Method to get the data for a person from the database in to a Person object */
	public static Person getPerson(int personId){
		Person person = new Person();
		person.setPersonId(personId);
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SELECT first_name, last_name, birth_date, gender, phonenumber, email FROM persons WHERE person_id = " + personId);
			if(rs.next()) {
				person.setFirstName(rs.getString("first_name"));
				person.setLastName(rs.getString("last_name"));
				person.setDob(rs.getDate("birth_date"));
				person.setGender(rs.getString("gender"));
				person.setEmail(rs.getString("email"));
				person.setPhoneNumber(rs.getString("phonenumber"));
			}
				
			rs = statement.executeQuery("SELECT nationality_name FROM persons INNER JOIN nationalities ON nationalities.nationality_id = persons.persons_nationality WHERE person_id = " + personId);
			if (rs.next())
				person.setNationality(rs.getString("nationality_name"));
			
			rs = statement.executeQuery("SELECT city_name FROM persons INNER JOIN cities ON cities.city_id = persons.persons_city WHERE person_id = " + personId);
			if (rs.next())
				person.setCity(rs.getString("city_name"));
			rs = statement.executeQuery("SELECT religion_name FROM persons INNER JOIN religions ON religions.religion_id = persons.persons_religion WHERE person_id = " + personId);
			if(rs.next())
				person.setReligion(rs.getString("religion_name"));
			rs = statement.executeQuery("SELECT program_name FROM persons INNER JOIN programs ON programs.program_id = persons.persons_program WHERE person_id = " + personId);
			if(rs.next())
				person.setProgram(rs.getString("program_name"));
			
			rs = statement.executeQuery("SELECT region_name FROM cities INNER JOIN regions ON regions.region_id = cities.citys_region WHERE city_name = '" + person.getCity() + "'");
			if(rs.next())
				person.setRegion(rs.getString("region_name"));
			
			rs = statement.executeQuery("SELECT university_name FROM programs INNER JOIN universities ON universities.university_id = programs.programs_university WHERE program_name = '" + person.getProgram() + "'");
			if(rs.next())
				person.setUniversity(rs.getString("university_name"));
			
			String[] list;
			rs = statement.executeQuery("SELECT COUNT(*) FROM persons_interests WHERE person_id = " + personId);
			if (rs.next()) {
				list = new String[rs.getInt(1)];
				if(list.length > 0) {
					rs = statement.executeQuery("SELECT interest_name FROM persons "
							+ "INNER JOIN persons_interests ON persons_interests.person_id = persons.person_id "
							+ "INNER JOIN interests ON interests.interest_id = persons_interests.interest_id "
							+ "WHERE persons.person_id = " + personId);
					int i = 0;
					while(rs.next()) {
						list[i] = rs.getString(1);
						i++;
					}
				}
				person.setInterests(list);
			}
			
			rs = statement.executeQuery("SELECT COUNT(*) FROM persons_languages WHERE person_id = " + personId);
			if (rs.next()) {
				list = new String[rs.getInt(1)];
			
				if (list.length > 0) {
					rs = statement.executeQuery("SELECT language_name FROM persons "
							+ "INNER JOIN persons_languages ON persons_languages.person_id = persons.person_id "
							+ "INNER JOIN languages ON languages.language_id = persons_languages.language_id "
							+ "WHERE persons.person_id = " + personId);
					int i = 0;
					while(rs.next()) {
						list[i] = rs.getString(1);
						i++;
					}
				}
				person.setLanguages(list);
			}
			
			rs = statement.executeQuery("SELECT COUNT(*) FROM persons_movie_genres WHERE person_id = " + personId);
			if (rs.next()) {
				list = new String[rs.getInt(1)];
				if (list.length > 0){
					rs = statement.executeQuery("SELECT movie_genre FROM persons "
							+ "INNER JOIN persons_movie_genres ON persons_movie_genres.person_id = persons.person_id "
							+ "INNER JOIN movie_genres ON movie_genres.movie_genre_id = persons_movie_genres.movie_genre_id "
							+ "WHERE persons.person_id = " + personId);
					int i = 0;
					while(rs.next()) {
						list[i] = rs.getString(1);
						i++;
					}
				}
				person.setMovieGenres(list);
			}
			
			rs = statement.executeQuery("SELECT COUNT(*) FROM persons_book_genres WHERE person_id = " + personId);
			if (rs.next()) {
				list = new String[rs.getInt(1)];

				if (list.length > 0) {
					rs = statement.executeQuery("SELECT book_genre FROM persons "

					+ "INNER JOIN persons_book_genres ON persons_book_genres.person_id = persons.person_id "
					+ "INNER JOIN book_genres ON book_genres.book_genre_id = persons_book_genres.book_genre_id "
					+ "WHERE persons.person_id = " + personId);
					int i = 0;
					while(rs.next()) {
						list[i] = rs.getString(1);
						i++;
					}
				}
				person.setBookGenres(list);
			}
			
			rs = statement.executeQuery("SELECT COUNT(*) FROM persons_music_genres WHERE person_id = " + personId);
			if (rs.next()) {
				list = new String[rs.getInt(1)];
				if(list.length > 0) {
					rs = statement.executeQuery("SELECT music_genre FROM persons "
							+ "INNER JOIN persons_music_genres ON persons_music_genres.person_id = persons.person_id "
							+ "INNER JOIN music_genres ON music_genres.music_genre_id = persons_music_genres.music_genre_id "
							+ "WHERE persons.person_id = " + personId);
					int i = 0;
					while(rs.next()) {
						list[i] = rs.getString(1);
						i++;
					}
				}
				person.setMusicGenres(list);
			}
			
			rs.close();
			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println("getPerson error: " + e);
		}


		return person;
	}
	
	/** Method to get a string array from a query containing statistics */
	public static String[] statistics(String query) {
		String[] list = null;
		
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			list = new String[3];
			int i = 0;
			while (rs.next()) {
				list[i] = rs.getString(1) + " (" + rs.getString(2) + ")";
				i++;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
	
	/** Method to delete a person from the database */
	
	public static void deletePerson(int person_id) {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM persons WHERE person_id = " + person_id);
			statement.executeUpdate("DELETE FROM persons_interests WHERE person_id = " + person_id);
			statement.executeUpdate("DELETE FROM persons_languages WHERE person_id = " + person_id);
			statement.executeUpdate("DELETE FROM persons_movie_genres WHERE person_id = " + person_id);
			statement.executeUpdate("DELETE FROM persons_book_genres WHERE person_id = " + person_id);
			statement.executeUpdate("DELETE FROM persons_music_genres WHERE person_id = " + person_id);
			
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("deletePerson error: " + e);
		}

	}
	
	/** Method to add a new person to the database */
	public static void addPerson(Person person) {
		
		try {
			Connection connection = getConnection();
			String query = "INSERT INTO persons (first_name, last_name, birth_date, gender, persons_nationality, persons_city, persons_program, phonenumber, email, persons_religion) VALUES ('"
					+ person.getFirstName()	+ "', '"
					+ person.getLastName() 	+ "', '"
					+ person.getDob() + "', '"
					+ person.getGender() + "', "
					+ getIdOfAttribute(connection, "nationalities", "nationality_id", person.getNationality(), "nationality_name") + ", "
					+ getIdOfAttribute(connection, "cities", "city_id", person.getCity(), "city_name") + ", "
					+ getIdOfAttribute(connection, "programs", "program_id", person.getProgram(), "program_name") + ", '"
					+ person.getPhoneNumber() + "', '"
					+ person.getEmail()	+ "', " 
					+ getIdOfAttribute(connection, "religions", "religion_id", person.getReligion(), "religion_name") + ")";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			String pId = statement.executeQuery("SELECT last_insert_rowid()").getString(1);
			if(person.getInterests().length > 0) {
				for (int i = 0; i < person.getInterests().length; i++) {
					int id = getIdOfAttribute(connection, "interests", "interest_id", person.getInterests()[i], "interest_name");
					statement.executeUpdate("INSERT INTO persons_interests VALUES ('" + pId + "', '" + id + "')");
				}
			}
			if(person.getLanguages().length > 0) {
				for (int i = 0; i < person.getLanguages().length; i++) {
					int Id = getIdOfAttribute(connection, "languages", "language_id", person.getLanguages()[i], "language_name");
					statement.executeUpdate("INSERT INTO persons_languages VALUES ('" + pId + "', '" + Id + "')");
				}
			}
			if(person.getMovieGenres().length > 0) {
				for (int i = 0; i < person.getMovieGenres().length; i++) {
					int id = getIdOfAttribute(connection, "movie_genres", "movie_genre_id", person.getMovieGenres()[i], "movie_genre");
					statement.executeUpdate("INSERT INTO persons_movie_genres VALUES ('" + pId + "', '" + id + "')");
				}
			}
			if(person.getBookGenres().length > 0) {
				for (int i = 0; i < person.getBookGenres().length; i++) {
					int id = getIdOfAttribute(connection, "book_genres", "book_genre_id", person.getBookGenres()[i], "book_genre");
					statement.executeUpdate("INSERT INTO persons_book_genres VALUES ('" + pId + "', '" + id + "')");
				}
			}
			if(person.getMusicGenres().length > 0) {
				for (int i = 0; i < person.getMusicGenres().length; i++) {
					int id = getIdOfAttribute(connection, "music_genres", "music_genre_id", person.getMusicGenres()[i], "music_genre");
					statement.executeUpdate("INSERT INTO persons_music_genres VALUES ('" + pId + "', '" + id + "')");
				}
			}
			
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Add person error " + e);
		}
	}
	
	
	
	/** Method to edit the values of a person in the database */
	public static void editPerson(Person person) {
		
		try {
			Connection connection = getConnection();
			
			String query = "UPDATE persons SET" 
					+ " first_name = '" + person.getFirstName() + "',"
					+ " last_name = '" + person.getLastName() + "',"
					+ " birth_date = '" + person.getDob() + "',"
					+ " gender = '" + person.getGender() + "',"
					+ " persons_nationality = " + getIdOfAttribute(connection, "nationalities", "nationality_id", person.getNationality(), "nationality_name") + ","
					+ " persons_city = " + getIdOfAttribute(connection, "cities", "city_id", person.getCity(), "city_name") + ","
					+ " persons_program = " + getIdOfAttribute(connection, "programs", "program_id", person.getProgram(), "program_name") + ","
					+ " persons_religion = " + getIdOfAttribute(connection, "religions", "religion_id", person.getReligion(), "religion_name") + ","
					+ " phonenumber = '" + person.getPhoneNumber() + "'," 
					+ " email = '" + person.getEmail() + "' "
					+ " WHERE person_id = " + person.getPersonId();
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);

			statement.executeUpdate("DELETE FROM persons_interests WHERE person_id = " + person.getPersonId());
			if(person.getInterests().length > 0) {
				for (int i = 0; i < person.getInterests().length; i++) {
					int id = getIdOfAttribute(connection, "interests", "interest_id", person.getInterests()[i], "interest_name");
					statement.executeUpdate("INSERT INTO persons_interests VALUES ('" + person.getPersonId() + "', '" + id + "')");
				}
			}

			statement.executeUpdate("DELETE FROM persons_languages WHERE person_id = " + person.getPersonId());
			if(person.getLanguages().length > 0) {
				for (int i = 0; i < person.getLanguages().length; i++) {
					int Id = getIdOfAttribute(connection, "languages", "language_id", person.getLanguages()[i], "language_name");
					statement.executeUpdate("INSERT INTO persons_languages VALUES ('" + person.getPersonId() + "', '" + Id + "')");
				}
			}

			statement.executeUpdate("DELETE FROM persons_movie_genres WHERE person_id = " + person.getPersonId());
			if(person.getMovieGenres().length > 0) {
				for (int i = 0; i < person.getMovieGenres().length; i++) {
					int id = getIdOfAttribute(connection, "movie_genres", "movie_genre_id", person.getMovieGenres()[i], "movie_genre");
					statement.executeUpdate("INSERT INTO persons_movie_genres VALUES ('" + person.getPersonId() + "', '" + id + "')");
				}
			}

			statement.executeUpdate("DELETE FROM persons_book_genres WHERE person_id = " + person.getPersonId());
			if(person.getBookGenres().length > 0) {
				for (int i = 0; i < person.getBookGenres().length; i++) {
					int id = getIdOfAttribute(connection, "book_genres", "book_genre_id", person.getBookGenres()[i], "book_genre");
					statement.executeUpdate("INSERT INTO persons_book_genres VALUES ('" + person.getPersonId() + "', '" + id + "')");
				}
			}

			statement.executeUpdate("DELETE FROM persons_music_genres WHERE person_id = " + person.getPersonId());
			if(person.getMusicGenres().length > 0) {
				for (int i = 0; i < person.getMusicGenres().length; i++) {
					int id = getIdOfAttribute(connection, "music_genres", "music_genre_id", person.getMusicGenres()[i], "music_genre");
					statement.executeUpdate("INSERT INTO persons_music_genres VALUES ('" + person.getPersonId() + "', '" + id + "')");
				}
			}

			statement.close();
			connection.close();
			
		} catch (Exception e) {
			System.out.println("editPerson error: " + e);
		}
	}
	
	/** Method to get the id of an attribute */
	private static int getIdOfAttribute(Connection connection, String table, String id_attribute, String element, String element_attribute) {
		int id = 0;
		String query = "SELECT " + id_attribute + " FROM " + table + " WHERE " + element_attribute + "= '" + element + "'";

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query); 
			if (rs.next())
				id = rs.getInt(1);
			
			rs.close();
			statement.close();
			
		} catch (Exception e) {
			System.out.println("GetId error " + e);
		}

		return id;
	}
	
	/** Add newly added attributes to the database (HE) */
	public static void addNewElement (String table, String element, String elementID) {
		
		try {
			Connection connection = getConnection();
			String query = "INSERT INTO " + table + "(" + elementID + ") VALUES('" + element + "')";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("addNewElement error: " + e);
		}
	}
	
	/** Method to add a new program */
	public static void addProgram(String program_name, String university) {
		
		try {
			Connection connection = getConnection();
			String query = "INSERT INTO programs (program_name, programs_university) VALUES ('" + program_name + "', " + getIdOfAttribute(connection, "universities", "university_id", university, "university_name") + ")";
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("addProgram error: " + e);
		}
	}
	
	/** Method to add a new city */
	public static void addCity(String city_name, String region) {
		
		try {
			Connection connection = getConnection();
			String query = "INSERT INTO cities (city_name, citys_region) VALUES ('" + city_name + "', " + getIdOfAttribute(connection, "regions", "region_id", region, "region_name") + ")";
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("addCity error: " + e);
		}
	}
	
	/** Method to get an array list of all programs */
	public static ArrayList<String> getAllPrograms(String university) {
		ArrayList<String> list = new ArrayList<String>();

		try {
			Connection connection = getConnection();
			String query = "SELECT program_name FROM programs WHERE programs_university = " + 
					getIdOfAttribute(connection, "universities", "university_id", university, "university_name");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next())
				list.add(rs.getString(1));
			
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("getAllPrograms error: " + e);
		}
		return list;
	}
	
	/** MEthod to get an array list of the persons from a query */
	public static ArrayList<Person> getPersonsFiltered(String query) {
		ArrayList<Person> persons = new ArrayList<Person>();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				int id = rs.getInt(1);
				persons.add(getPerson(id));
			}
			
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error in getPersonFiltered: " + e);
		}
		
		
		return persons;
		
	}

	/** Method to get an array list of al city names */
	public static ArrayList<String> getAllCityNames(String region) {
		ArrayList<String> cities = new ArrayList<String>();


		try {
			Connection connection = getConnection();
			String query = "SELECT city_name FROM cities WHERE citys_region = " + 
					getIdOfAttribute(connection, "regions", "region_id", region, "region_name");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				cities.add((String) rs.getObject(1));
			}
			
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("getAllCityNames error: " + e);
		}
		return cities;
	}
	
	/** Method to get an array of a certain attribute of a person */
	public static String[] getLists(String table, String attribute) {
		String[] list = null;

		try {
			Connection connection = getConnection();
			String query = "SELECT " + attribute + " FROM " + table;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + table);
			int count = (rs.next()) ? rs.getInt(1) : 0; 
			rs = statement.executeQuery(query);
			list = new String[count];
			int i = 0;
			while(rs.next()) {
				list[i] = rs.getString(1);
				i++;
			}

			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("getLists error: " + e);
		}

		return list;
	}
	
	/** Method to get the average age of the persons in the database */
	public static int avgAge() {
		int avgAge = 0; 
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT birth_date from persons");
			int i = 0;
			int sum = 0;
			Calendar calendar = new GregorianCalendar();
			while(rs.next()) {
				Date dob = rs.getDate(1);
				calendar.setTime(dob);
				sum += calendar.get(Calendar.YEAR);
				i++;
			}
			calendar = new GregorianCalendar();
			
			avgAge = calendar.get(Calendar.YEAR) - sum / i;
			
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return avgAge;
	}
}
