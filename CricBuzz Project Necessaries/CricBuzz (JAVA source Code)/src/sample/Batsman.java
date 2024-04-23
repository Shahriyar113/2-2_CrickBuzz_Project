package sample;

public class Batsman {

    private String Batsman_Name;
    private String Status;//out or not out
    private int Fours;
    private int Sixes;
    private int Run_Scored;
    private int Bowl_Faced;

    public Batsman(String batsman_Name, String status, int fours, int sixes, int run_Scored, int bowl_Faced) {
        Batsman_Name = batsman_Name;
        Status = status;
        Fours = fours;
        Sixes = sixes;
        Run_Scored = run_Scored;
        Bowl_Faced = bowl_Faced;
    }

    public String getBatsman_Name() {
        return Batsman_Name;
    }

    public void setBatsman_Name(String batsman_Name) {
        Batsman_Name = batsman_Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getFours() {
        return Fours;
    }

    public void setFours(int fours) {
        Fours = fours;
    }

    public int getSixes() {
        return Sixes;
    }

    public void setSixes(int sixes) {
        Sixes = sixes;
    }

    public int getRun_Scored() {
        return Run_Scored;
    }

    public void setRun_Scored(int run_Scored) {
        Run_Scored = run_Scored;
    }

    public int getBowl_Faced() {
        return Bowl_Faced;
    }

    public void setBowl_Faced(int bowl_Faced) {
        Bowl_Faced = bowl_Faced;
    }
}
