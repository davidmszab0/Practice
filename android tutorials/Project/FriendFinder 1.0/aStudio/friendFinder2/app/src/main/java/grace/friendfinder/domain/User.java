package grace.friendfinder.domain;

import java.util.ArrayList;

/**
 * Created by grace on 01/08/17.
 */

public class User {

    String name = "";
    String gender= "";
    ArrayList<String> movieGenres = new ArrayList<>();
    ArrayList<String> musicGenres = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(ArrayList<String> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public ArrayList<String> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(ArrayList<String> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public void addMovieGenresArray (String movieGenre) {
        this.movieGenres.add(movieGenre);
    }
    public void addMusicGenresArray (String musicGenre) {
        this.musicGenres.add(musicGenre);
    }
    @Override
    public String toString() {
        String result = String.format(
                "User[name=%s, gender='%s']%n",
                name, gender);
        return result;
    }
}
