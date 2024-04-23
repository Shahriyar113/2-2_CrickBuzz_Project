package sample;

public class Tour {

    private String Tournament_Title;
    private String StartDate;
    private String EndDate;
    private String Status;
    private String Host;

    public Tour(String tournament_Title, String startDate, String endDate, String status, String host) {
        Tournament_Title = tournament_Title;
        StartDate = startDate;
        EndDate = endDate;
        Status = status;
        Host = host;
    }

    public String getTournament_Title() {
        return Tournament_Title;
    }

    public void setTournament_Title(String tournament_Title) {
        Tournament_Title = tournament_Title;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }
}
