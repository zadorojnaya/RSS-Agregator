package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Naya on 11.03.14.
 */
public class SessionData {
    public String loginLog;
    public String passwordLog;
    public String loginReg;
    public String passwordReg;
    public String secondPass;
    public String button;
    public String URL;
    public String addName;
    public String removeName;
    public String feedsButton;
    public HttpSession session;
    public HttpServletRequest request;
}
