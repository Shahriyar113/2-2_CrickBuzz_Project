package sample;

public class Bowler {

    private String Bowler_Name;
    private double Over_Bowled;
    private int Maiden;
    private int Wicket_Taken;
    private int Run_Given;
    private double Economy;

    public Bowler(String bowler_Name, double over_Bowled, int maiden, int wicket_Taken, int run_Given, double economy) {
        Bowler_Name = bowler_Name;
        Over_Bowled = over_Bowled;
        Maiden = maiden;
        Wicket_Taken = wicket_Taken;
        Run_Given = run_Given;
        Economy = economy;
    }

    public String getBowler_Name() {
        return Bowler_Name;
    }

    public void setBowler_Name(String bowler_Name) {
        Bowler_Name = bowler_Name;
    }

    public double getOver_Bowled() {
        return Over_Bowled;
    }

    public void setOver_Bowled(double over_Bowled) {
        Over_Bowled = over_Bowled;
    }

    public int getMaiden() {
        return Maiden;
    }

    public void setMaiden(int maiden) {
        Maiden = maiden;
    }

    public int getWicket_Taken() {
        return Wicket_Taken;
    }

    public void setWicket_Taken(int wicket_Taken) {
        Wicket_Taken = wicket_Taken;
    }

    public int getRun_Given() {
        return Run_Given;
    }

    public void setRun_Given(int run_Given) {
        Run_Given = run_Given;
    }

    public double getEconomy() {
        return Economy;
    }

    public void setEconomy(double economy) {
        Economy = economy;
    }
}
