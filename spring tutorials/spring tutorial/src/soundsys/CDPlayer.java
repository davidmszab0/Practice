package soundsys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
