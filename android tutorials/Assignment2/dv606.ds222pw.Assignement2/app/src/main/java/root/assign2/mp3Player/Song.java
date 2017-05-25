package root.assign2.mp3Player;

/**
 * A simple class representing MP3 songs (tracks) in a playlist.
 *
 * Created by Oleksandr Shpak in 2013.
 * Ported to Android Studio by Kostiantyn Kucher in 2015.
 * Last modified by Kostiantyn Kucher on 10/09/2015.
 */
public class Song {

    private long id;
    private final String artist;
    private final String album;
    private final String title;
    private final String path;

    private Song next = null;

    public Song(Long id, String artist, String album, String name, String path)
    {
        this.id = id;
        this.artist = artist;
        this.album = album;
        this.title = name;
        this.path = path;
    }

    public Long getId() { return id; }

    public String getArtist()
    {
        return artist;
    }

    public String getAlbum()
    {
        return album;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPath()
    {
        return path;
    }

    public void setNext(Song song)
    {
        next = song;
    }

    public Song getNext()
    {
        return next;
    }

    @Override
    public String toString()
    {
        String result = null;
        if (path != null)
            result = path;

        if (title != null)
            result = title;

        if (artist != null)
            result = artist + " - " + result;

        if (album != null)
            result = result + " (" + album + ")";

        return result;
    }
}
