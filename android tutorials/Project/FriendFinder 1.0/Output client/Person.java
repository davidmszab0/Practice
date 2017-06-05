import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;



public class Person {
	
	private int personId;
	private String firstName;
	private String lastName;
	private String age;
	private String gender;
	private String nationality;
	private String city;
	private String region;
	private String program;
	private String university;
	private String phoneNumber;
	private String email;
	private String religion;
	private String[] interests;
	private String[] languages;
	private String[] movieGenres;
	private String[] bookGenres;
	private String[] musicGenres;
	private Date dob;
	
	public Person() {
		
	}
	
	public Person(int personId, String firstName, String lastName,
			String gender, String nationality, String city, String region,
			String program, String university, String phoneNumber,
			String email, String religion, String[] interests,
			String[] languages, String[] movieGenres, String[] bookGenres,
			String[] musicGenres, Date dob) {
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.nationality = nationality;
		this.city = city;
		this.region = region;
		this.program = program;
		this.university = university;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.religion = religion;
		this.interests = interests;
		this.languages = languages;
		this.movieGenres = movieGenres;
		this.bookGenres = bookGenres;
		this.musicGenres = musicGenres;
		this.dob = dob;
		this.age = calculateAge(dob);
	}
	
	public Person(int personId, String firstName, String lastName, String age,
			String gender, String nationality, String city, String region,
			String program, String university, String phoneNumber,
			String email, String religion, String[] interests,
			String[] languages, String[] movieGenres, String[] bookGenres,
			String[] musicGenres) {
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.nationality = nationality;
		this.city = city;
		this.region = region;
		this.program = program;
		this.university = university;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.religion = religion;
		this.interests = interests;
		this.languages = languages;
		this.movieGenres = movieGenres;
		this.bookGenres = bookGenres;
		this.musicGenres = musicGenres;
	}
	
	public void printInfo() {
		System.out.println(personId + "\t" + firstName + "\t" + lastName);
	}
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String[] getInterests() {
		return interests;
	}
	public void setInterests(String[] interests) {
		this.interests = interests;
	}
	public String[] getLanguages() {
		return languages;
	}
	public void setLanguages(String[] languages) {
		this.languages = languages;
	}
	public String[] getMovieGenres() {
		return movieGenres;
	}
	public void setMovieGenres(String[] movieGenres) {
		this.movieGenres = movieGenres;
	}
	public String[] getBookGenres() {
		return bookGenres;
	}
	public void setBookGenres(String[] bookGenres) {
		this.bookGenres = bookGenres;
	}
	public String[] getMusicGenres() {
		return musicGenres;
	}
	public void setMusicGenres(String[] musicGenres) {
		this.musicGenres = musicGenres;
	}
	public void setDob(Date dob) {
		this.dob = dob;
		setAge(calculateAge(dob));
	}
	public void setDob(String dob) {
		try {
			this.dob = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dob).getTime());
		} catch (Exception e) {
			
		}
		
	}
	public Date getDob() {
		return dob;
	}
	
	public static String calculateAge(Date dob) {
		String[] date = dob.toString().split("-");
		Calendar dobCal = new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
		Calendar nowCal = new GregorianCalendar();
		int years = nowCal.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
	    if ((dobCal.get(Calendar.MONTH) > nowCal.get(Calendar.MONTH)) || (dobCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH) && dobCal.get(Calendar.DAY_OF_MONTH) > nowCal.get(Calendar.DAY_OF_MONTH))) {
	      years--;
	    }
	    
	    return "" + years;
	}
	
}
