package sharedrmi.application.dto;

public class MessageDTO {
    private final String messageTopic;
    private final String messageTitle;
    private final String messageText;

    public MessageDTO(String messageTopic, String messageTitle, String messageText) {
        this.messageTopic = messageTopic;
        this.messageTitle = messageTitle;
        this.messageText = messageText;
    }

    public static MessageDTO.MessageDTOBuilder builder() {
        return new MessageDTO.MessageDTOBuilder();
    }

    public String getMessageTopic() {
        return this.messageTopic;
    }

    public String getMessageTitle() {
        return this.messageTitle;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public static class MessageDTOBuilder {
        private String messageTopic;
        private String messageTitle;
        private String messageText;

        MessageDTOBuilder() {
        }

        public MessageDTO.MessageDTOBuilder messageTopic(String messageTopic) {
            this.messageTopic = messageTopic;
            return this;
        }

        public MessageDTO.MessageDTOBuilder messageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
            return this;
        }

        public MessageDTO.MessageDTOBuilder messageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public MessageDTO build() {
            return new MessageDTO(this.messageTopic, this.messageTitle, this.messageText);
        }

        public String toString() {
            return "MessageDTO.MessageDTOBuilder(messageTopic=" + this.messageTopic + ", messageTitle=" + this.messageTitle + ", messageText=" + this.messageText + ")";
        }

    }
}
