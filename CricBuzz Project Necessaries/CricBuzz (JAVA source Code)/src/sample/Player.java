package sample;

//this class is for showing players in the searched list

public class Player {

    private String Player_Name;
    private String Country;
    private int Age;

    public Player(String player_Name, String country, int age) {
        Player_Name = player_Name;
        Country = country;
        Age = age;
    }

    public String getPlayer_Name() {
        return Player_Name;
    }

    public void setPlayer_Name(String player_Name) {
        Player_Name = player_Name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
