package sample;

public class Match {

    private String Match_No; //1st ODI or 2nd ODI....
    private String Stadium_Name;
    private String Date;
    private String Status;

    public Match(String match_No, String stadium_Name, String date, String status) {
        Match_No = match_No;
        Stadium_Name = stadium_Name;
        Date = date;
        Status = status;
    }

    public String getMatch_No() {
        return Match_No;
    }

    public void setMatch_No(String match_No) {
        Match_No = match_No;
    }

    public String getStadium_Name() {
        return Stadium_Name;
    }

    public void setStadium_Name(String stadium_Name) {
        Stadium_Name = stadium_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
