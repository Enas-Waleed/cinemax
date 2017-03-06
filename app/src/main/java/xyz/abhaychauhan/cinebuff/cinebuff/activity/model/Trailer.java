package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;

/**
 * Created by abhay on 06/03/17.
 */

public class Trailer {

    private String id;
    private String name;
    private String key;
    private String site;
    private String size;

    public Trailer(String id,String name, String key, String site, String size){
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.size = size;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getKey(){
        return this.key;
    }

    public String getSite(){
        return this.site;
    }

    public String getSize(){
        return this.size;
    }

}
