package model;

public class Thesis {

    private final String idTheme;
    private final String idUser;

    public Thesis(String idTheme, String idUser) {
        this.idTheme = idTheme;
        this.idUser = idUser;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public String getIdUser() {
        return idUser;
    }
}
