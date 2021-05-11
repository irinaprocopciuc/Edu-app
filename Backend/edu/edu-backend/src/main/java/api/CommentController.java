package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CommentDetails;
import model.RegisterDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CommentService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/addCommentToFile")
    public ResponseEntity<String> addCommentToFile(@Valid @RequestBody CommentDetails commentDetails, HttpServletResponse response) throws JsonProcessingException, SQLException {

        Map<String, Object> map = new HashMap<>();

        Map<String,String> addResponse = commentService.addComment(commentDetails);

        if(addResponse.containsKey("error")){
            map.put("error",addResponse.get("error"));
            map.put("code",addResponse.get("code"));
            map.put("status",HttpStatus.valueOf(Integer.parseInt((String)addResponse.get("code"))));
            return new ResponseEntity<String>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(addResponse),HttpStatus.valueOf(Integer.parseInt((String)map.get("code"))));
        }

        map.put("message",addResponse.get("message"));
        map.put("code",addResponse.get("code"));
        map.put("status",HttpStatus.valueOf(Integer.parseInt((String)addResponse.get("code"))));

        return new ResponseEntity<String>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(addResponse),HttpStatus.valueOf(Integer.parseInt((String)map.get("code"))));
    }

    @GetMapping(path = "/getComments/fileName={fileName}&courseName={courseName}")
    public ResponseEntity<String> getTrips(@PathVariable("fileName") String fileName,@PathVariable("courseName") String courseName) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();

        List<Map<String, String>> commentsList = commentService.getComments(fileName,courseName);
        if (commentsList != null) {
            map.put("status", HttpStatus.OK);
            map.put("code", "200");
            map.put("message", "Comments retrieved!");
            map.put("response", commentsList);
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.OK);
        } else {
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("code", "400");
            map.put("message", "Could not retrieve comments!");
            map.put("response", "");
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.NOT_FOUND);
        }
    }


}