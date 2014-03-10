package Data;

/**
 * Created by Naya on 06.03.14.
 */
public class Links {
    private  String URL;
    private  String name;
    public Links Links(String url, String Name){
        URL = url;
        name = Name;
        return this;
    }

    public String getURL() {
        return URL;
    }

    public String getName() {
        return name;
    }

}
