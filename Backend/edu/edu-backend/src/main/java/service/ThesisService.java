package service;

import dao.Inteface.ThesisInterface;
import model.Thesis;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ThesisService {

    private final ThesisInterface conn;

    public ThesisService(@Qualifier("Thesis") ThesisInterface connection) {
        this.conn = connection;
    }

    public List<Map<String, String>> getAllThesis() {
        return conn.getAllThesis();
    }

    public List<Map<String, String>> getChosenTheme(String idStudent) {
        return conn.getChosenTheme(idStudent);
    }

    public int chooseTheme(Thesis thesis) { return conn.chooseTheme(thesis); };
}
