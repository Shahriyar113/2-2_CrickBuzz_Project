package sample;


public class Ranking {

    public int Rank;
    public String Team_name;

    public Ranking(int rank, String team_name) {
        Rank = rank;
        Team_name = team_name;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getTeam_name() {
        return Team_name;
    }

    public void setTeam_name(String team_name) {
        Team_name = team_name;
    }
}
