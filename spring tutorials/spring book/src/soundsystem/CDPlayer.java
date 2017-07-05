package soundsystem;

import com.sun.media.jfxmedia.Media;
import com.sun.media.jfxmedia.MediaPlayer;
import com.sun.media.jfxmedia.control.VideoRenderControl;
import com.sun.media.jfxmedia.effects.AudioEqualizer;
import com.sun.media.jfxmedia.effects.AudioSpectrum;
import com.sun.media.jfxmedia.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by grace on 06/02/17.
 */
@Component
public class CDPlayer  {

    private CompactDisc cd;

    @Autowired
    public CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    public void play() {
        cd.play();
    }

    @Autowired
    public void setCompactDisc(CompactDisc cd) {
        this.cd = cd;
    }
}
