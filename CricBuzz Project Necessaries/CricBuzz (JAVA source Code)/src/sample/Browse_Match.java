package sample;

public class Browse_Match {

    private String Match_Title;
    private String Format;
    private String Date;
    private String Time;
    private String Stadium;
    private String Status;
    private String Tournament_Title;

    public Browse_Match(String match_Title, String format, String date, String time, String stadium, String status, String tournament_Title) {
        Match_Title = match_Title;
        Format = format;
        Date = date;
        Time = time;
        Stadium = stadium;
        Status = status;
        Tournament_Title = tournament_Title;
    }

    public String getMatch_Title() {
        return Match_Title;
    }

    public void setMatch_Title(String match_Title) {
        Match_Title = match_Title;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStadium() {
        return Stadium;
    }

    public void setStadium(String stadium) {
        Stadium = stadium;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTournament_Title() {
        return Tournament_Title;
    }

    public void setTournament_Title(String tournament_Title) {
        Tournament_Title = tournament_Title;
    }
}
