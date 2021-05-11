package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.util.Date;

public class EditComment {

    @Size(min = 1, max = 5)
    private final String commentId;

    @Size(min = 1, max = 1)
    private final String userID;

    @Size(min = 8, max = 258)
    private final String message;

    private final Date date;

    public EditComment(@JsonProperty("commentId") String commentId, @JsonProperty("userId") String userID, @JsonProperty("message") String message,
                          @JsonProperty("date")Date date) {
        this.commentId = commentId;
        this.userID = userID;
        this.message = message;
        this.date = date;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}

