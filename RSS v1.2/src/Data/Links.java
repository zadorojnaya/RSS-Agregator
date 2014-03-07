package Data;

/**
 * Created by Naya on 06.03.14.
 */
public class Links {
    private  String URL;
    private  String Name;
    public Links Links(String url, String name){
        URL = url;
        Name = name;
        return this;
    }

    public String getURL() {
        return URL;
    }

    public String getName() {
        return Name;
    }

}
