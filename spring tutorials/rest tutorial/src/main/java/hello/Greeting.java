package hello;

import javax.annotation.PostConstruct;

/**
 * Created by grace on 15/02/17.
 */
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @PostConstruct
    public void setup() {
        System.out.println("hello my dear! ");
    }
}
