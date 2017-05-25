package root.assign2.mp3Player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import root.assign2.R;

/**
 * Created by grace on 08/01/17.
 * https://developer.android.com/guide/components/services.html
 * https://developer.android.com/guide/components/bound-services.html
 */

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{

    //media player
    private MediaPlayer mPlayer;
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosition;
    private final IBinder musicBind = new MusicBinder();
    private String songTitle="";
    private static final int NOTIFY_ID=1;

    public void onCreate(){
        //create the service
        super.onCreate();
        //initialize position
        songPosition = 0;
        //create player
        mPlayer = new MediaPlayer();

        initMusicPlayer();
    }

    public void initMusicPlayer(){
        // The wake lock will let playback continue when the device becomes idle
        mPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
    }

    public void setList(ArrayList<Song> theSongs){
        songs = theSongs;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }


    /* release the service when the service instance in unbound
        This will execute when the user exits the app,
        at which point we will stop the service.
    * */
    @Override
    public boolean onUnbind(Intent intent){
        mPlayer.stop();
        mPlayer.release();
        return false;
    }

    /*
    * The onCompletion method will fire when a track ends, including cases where
    * the user has chosen a new track or skipped to the next/previous tracks
    * as well as when the track reaches the end of its playback.
    * In the latter case, continue playback by playing the next track.
    * */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mPlayer.getCurrentPosition()>0){
            mp.reset();
            playNext(); // call the playNext method if the current track has reached its end.
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();

        Intent notificationIntent = new Intent(this, MP3Player.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // continue playback even when the user navigates away from the app
        // display a notification showing the title of the track being played
        // Clicking the notification will take the user back into the app
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification not = builder.build();

        // stop the service in onDestroy() when the service instance is destroyed
        startForeground(NOTIFY_ID, not);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    public void setSong(int songIndex){
        songPosition = songIndex;
    }

    public void playSong(){
        mPlayer.reset();
        //get song
        Song playSong = songs.get(songPosition);
        // set the song title
        songTitle=playSong.getTitle();
        //get id
        long currSong = playSong.getId();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);

        try{
            mPlayer.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        mPlayer.prepareAsync();
    }

    public int getPosn(){
        return mPlayer.getCurrentPosition();
    }

    public int getDur(){
        return mPlayer.getDuration();
    }

    public boolean isPng(){
        return mPlayer.isPlaying();
    }

    public void pausePlayer(){
        mPlayer.pause();
    }

    public void seek(int posn){
        mPlayer.seekTo(posn);
    }

    public void go(){
        mPlayer.start();
    }

    public void playPrev(){
        songPosition--;
        if(songPosition < 0)
            songPosition = songs.size()-1;
        playSong();
    }
    //skip to next
    public void playNext(){
        songPosition++;
        if(songPosition >= songs.size())
            songPosition = 0;
        playSong();
    }
}
