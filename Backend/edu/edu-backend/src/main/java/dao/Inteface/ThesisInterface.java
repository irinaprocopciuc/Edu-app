package dao.Inteface;

import model.Thesis;

import java.util.List;
import java.util.Map;

public interface ThesisInterface {
    public List<Map<String, String>> getAllThesis();

    public List<Map<String, String>> getChosenTheme(String idStudent);

    public int chooseTheme(Thesis thesis);
}
