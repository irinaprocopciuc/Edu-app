package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.EditComment;
import model.Thesis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ThesisService;
import service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/thesis")
@RestController
public class ThesisController {

    private final ThesisService thesisService;

    @Autowired
    public ThesisController(ThesisService thesisService) {
        this.thesisService = thesisService;
    }


    @GetMapping(path = "/getAll")
    public ResponseEntity<String> getAllThesis() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> thesisList = thesisService.getAllThesis();
        if (!thesisList.isEmpty()) {
            map.put("status", HttpStatus.OK);
            map.put("code", "200");
            map.put("message", "Thesis list retrieved!");
            map.put("response", thesisList);
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.OK);
        } else {
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("code", "404");
            map.put("message", "Thesis not found!");
            map.put("response", "");
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getChosen/idStudent={idStudent}")
    public ResponseEntity<String> getChosen(@PathVariable("idStudent") String idStudent) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> thesisList = thesisService.getChosenTheme(idStudent);
        if (!thesisList.isEmpty()) {
            map.put("status", HttpStatus.OK);
            map.put("code", "200");
            map.put("message", "Thesis theme retrieved");
            map.put("response", thesisList);
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.OK);
        } else {
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("code", "404");
            map.put("message", "Student has no chosen theme");
            map.put("response", "");
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/chooseTheme")
    public ResponseEntity<String> chooseTheme(@Valid @RequestBody Thesis thesis) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();

        int result = thesisService.chooseTheme(thesis);
        if (result==0) {
            map.put("status", HttpStatus.OK);
            map.put("code", "200");
            map.put("message", "Successfully updated with chosen theme!");
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.OK);
        } else {
            List<String> message= new ArrayList<String>();
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("code", "400");
            map.put("error", message.get(result * -1 - 1));
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
                    HttpStatus.NOT_FOUND);
        }
    }
}
