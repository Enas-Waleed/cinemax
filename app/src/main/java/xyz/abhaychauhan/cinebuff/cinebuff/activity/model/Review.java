package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;

/**
 * Created by abhay on 08/03/17.
 */

public class Review {

    private String id;
    private String author;
    private String content;
    private String url;

    public Review(String id, String author, String content, String url){
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId(){
        return this.id;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getContent(){
        return this.content;
    }

    public String getUrl(){
        return this.url;
    }

}
