package ch01.team;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class Teams {
    private TeamsUtility utils;

    public Teams(){
        utils = new TeamsUtility();
        utils.make_test_teams();
    }

    @WebMethod
    public Team getTeam(String name) {
        return utils.getTeam(name);
    }

    @WebMethod
    public List<Team> getTeams(){
        return utils.getTeams();
    }

}
