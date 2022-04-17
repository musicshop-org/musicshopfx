package at.fhv.musicshopfx;


import javafx.scene.control.CheckBox;

public class TopicLine {

    private final String topicName;
    private final CheckBox publishCheckbox;

    public TopicLine(String topicName) {
        this.topicName = topicName;
        this.publishCheckbox = new CheckBox();
    }

    public String getTopicName() {
        return topicName;
    }

    public CheckBox getPublishCheckbox() {
        return publishCheckbox;
    }
}
