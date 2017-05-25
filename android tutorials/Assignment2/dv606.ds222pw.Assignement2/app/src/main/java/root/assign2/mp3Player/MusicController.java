package root.assign2.mp3Player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.MediaController;

/**
 * Created by grace on 08/01/17.
 */

public class MusicController extends MediaController {

    Context c;

    public MusicController(Context context) {
        super(context);
        this.c = context;
    }

    public void hide() {

    }
    /*@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if(keyCode == KeyEvent.KEYCODE_BACK){
            ((MP3Player)c).onBackPressed();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }*/
}
