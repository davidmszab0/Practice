package soundsystem;

import com.sun.media.jfxmedia.MediaPlayer;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import resources.CDPlayerConfig;
import static org.junit.Assert.assertEquals;

/**
 * Created by grace on 06/02/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= CDPlayerConfig.class)
public class CDPlayerTest {
    @Rule
    public final StandardOutputStreamLog log =
            new StandardOutputStreamLog();

    // this would be a more generic CDplayer
    @Autowired(required=false) // NullPointer here
    private MediaPlayer player; // CDPlayer class

    @Autowired
    private CDPlayer cdPlayer;

    @Autowired
    private CompactDisc cd;

    @Test
    public void cdShouldNotBeNull() {

        Assert.assertNotNull(cd);
    }
    @Test
    public void play() {
        cdPlayer.play();
        assertEquals(
                "Playing Sgt. Pepper's Lonely Hearts Club Band" +
                        " by The Beatles\n",
                log.getLog());
    }
}
