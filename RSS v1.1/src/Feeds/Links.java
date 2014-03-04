package Feeds;

/**
 * Created by Naya on 04.03.14.
 */
public class Links {
   private  String URL;
    private  String Name;
    public Links Links(String url, String name){
        URL = url;
        Name = name;
        return this;
    }
    public Links(){

    }
    public String getURL(){
        return URL;
    }

    public String getName(){
        return Name;
    }
    public void setURL(String url){
        URL = url;
    }
    public void setName(String name){
        Name = name;
    }
}
