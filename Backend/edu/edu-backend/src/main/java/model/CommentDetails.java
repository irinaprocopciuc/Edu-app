package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.util.Date;

public class CommentDetails {

    @Size(min = 4, max = 64)
    private final String fileName;

    @Size(min = 4, max = 64)
    private final String courseName;

    @Size(min = 1, max = 5)
    private final String userId;

    private final String message;

    private final Date date;

    public CommentDetails(@JsonProperty("fileName") String fileName,@JsonProperty("courseName") String courseName,@JsonProperty("userId") String userId,
                          @JsonProperty("message")String message,@JsonProperty("date") Date date) {
        this.fileName = fileName;
        this.courseName = courseName;
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
