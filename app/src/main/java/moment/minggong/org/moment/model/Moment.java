package moment.minggong.org.moment.model;

import java.io.Serializable;

public class Moment implements Serializable{
    private String content;
    private Image[] images;
    private User sender;
    private Moment[] comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Moment[] getComments() {
        return comments;
    }

    public void setComments(Moment[] comments) {
        this.comments = comments;
    }
}
