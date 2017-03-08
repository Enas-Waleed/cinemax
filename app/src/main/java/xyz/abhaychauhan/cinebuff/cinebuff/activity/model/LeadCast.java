package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;

/**
 * Created by abhay on 08/03/17.
 */

public class LeadCast {

    private String characterName;
    private String creditId;
    private int id;
    private String name;
    private String profilePath;

    public LeadCast(String characterName, String creditId, int id, String name,
                    String profilePath){
        this.characterName = characterName;
        this.creditId = creditId;
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
    }

    public String getCharacterName(){
        return characterName;
    }

    public String getCreditId(){
        return creditId;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getProfilePath(){
        return profilePath;
    }

}
