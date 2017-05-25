package root.assign2.mp3Player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import root.assign2.R;

/**
 * A simple MP3 player skeleton for 2DV606 Assignment 2.
 *
 * Created by Oleksandr Shpak in 2013.
 * Ported to Android Studio by Kostiantyn Kucher in 2015.
 * Last modified by Kostiantyn Kucher on 04/04/2016.
 *  Description:
 *     - media playback is happening in the Service class,
 *      but that the user interface comes from the MP3Player class
 *
 *  I, David took the implementation for the MP3 player from the following tutorial:
 *  thumbs up for : https://code.tutsplus.com/tutorials/create-a-music-player-on-android-project-setup--mobile-22764
 *  https://code.tutsplus.com/tutorials/create-a-music-player-on-android-song-playback--mobile-22778
 *  https://code.tutsplus.com/tutorials/create-a-music-player-on-android-user-controls--mobile-22787
 */
public class MP3Player extends AppCompatActivity implements MediaController.MediaPlayerControl {

    // This is an oversimplified approach which you should improve
    // Currently, if you exit/re-enter activity, a new instance of player is created
    // and you can't, e.g., stop the playback for the previous instance,
    // and if you click a song, you will hear another audio stream started
    public final MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<Song> songList;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private MusicController controller;
    private boolean paused=false;
    private boolean playbackPaused=false;
    private AppCompatActivity mp3Player;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mp3Player = this;
        // Initialize the layout
        setContentView(R.layout.activity_mp3_player);
        // Initialize the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Initialize the list of songs
        final ListView listView = (ListView) findViewById(R.id.list_view);
        songList = getSongList();

        // sorting the list of songs in alphabetical order
        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter songAdt = new SongAdapter(this, songList);
        listView.setAdapter(songAdt);

        setController();
    }

    // binding to the MusicService.class, this way the service is not running
    // just in the background, but it is bound to the app
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicSrv=null; // do I need it to set it to null?
            musicBound = false;
        }
    };

    // to the LinearLayout onClick
    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();

        // Do reset the controller and update the playbackPaused flag when playback has been paused.
        if(playbackPaused){
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    //set the controller up
    private void setController(){
        controller = new MusicController(this);

        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.list_view));
        controller.setEnabled(true);
    }

    //play next
    /*
    * If the user interacts with the controls while playback is paused,
    * the MediaPlayer object may behave unpredictably.
    * For this, set and use the playbackPaused flag!!
    * */
    private void playNext(){
        musicSrv.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    //play previous
    // set and use the playbackPaused flag!!
    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        // && evaulates the left hand first, if ok, then the right sides
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
        return musicSrv.isPng(); // Service isPlaying method
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    /**
     * Checks the state of media storage. True if mounted;
     * @return
     */
    private boolean isStorageAvailable()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Reads song list from media storage.
     * @return
     */
    private ArrayList<Song> getSongList()
    {
        ArrayList<Song> songList = new ArrayList<Song>();

        if(!isStorageAvailable()) // Check for media storage
        {
            Toast.makeText(this, R.string.nosd, Toast.LENGTH_SHORT).show();
            return songList;
        }

        Cursor musicCursor = getContentResolver().query( // using content resolver to read music from media storage
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.IS_MUSIC + " > 0 ",
                null,
                null
        );

        if (musicCursor.getCount() > 0)
        {
            musicCursor.moveToFirst();
            do
            {
                Song song = new Song(
                        musicCursor.getLong(0), //id
                        musicCursor.getString(1), //artist
                        musicCursor.getString(2), //album
                        musicCursor.getString(3), //display name
                        musicCursor.getString(4)); // data

                songList.add(song);
            }
            while(musicCursor.moveToNext());
        }
        musicCursor.close();

        return songList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mp3_player, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_end:
                // only the end button will exit the service
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //stopService(playIntent); //use this only if you want stop the player here
        //musicSrv=null; //use this only if you want stop the player here
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent == null){
            playIntent = new Intent(this, MusicService.class);
            // binding the service
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            // starts the service, runs in background
            startService(playIntent); // when disabled, there is no leakage
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        mp3Player.unbindService(musicConnection);
        musicSrv = null;
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        paused = true;
        super.onPause();
        //musicSrv.releaseMPlayer();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }
}
