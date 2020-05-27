package Server;

import java.io.Serializable;

public class InfoClient implements Serializable {

    private String username;
    private String password;
    private String region;

    public InfoClient(){
        this.username = "";
        this.password = "";
        this.region = "";
    }

    public InfoClient(String username,String password, String region){
        this.username = username;
        this.password = password;
        this.region = region;
    }

    public boolean check_password(String pass){
        return this.password.equals(pass);
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRegion() {
        return region;
    }

    public void setPassword(String pass){
        this.password = pass;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setRegion(String region){
        this.region = region;
    }

}
