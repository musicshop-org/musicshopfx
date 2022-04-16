package at.fhv.musicshopfx;

import java.awt.*;

public class TopicLine {

    private final String topicName;
    private final Checkbox publishCheckbox;

    public TopicLine(String topicName) {
        this.topicName = topicName;
        this.publishCheckbox = new Checkbox();
    }

    public String getTopicName() {
        return topicName;
    }

    public Checkbox getPublishCheckbox() {
        return publishCheckbox;
    }
}
