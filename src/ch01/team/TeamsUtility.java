package ch01.team;

import java.util.*;

public class TeamsUtility {
    private Map<String, Team> teamMap;

    public TeamsUtility(){
        teamMap = new HashMap<String, Team>();
    }

    public Team getTeam(String name){
        return teamMap.get(name);
    }

    public List<Team> getTeams(){
        List<Team> list = new ArrayList<Team>();
        Set<String> keys = teamMap.keySet();
        for (String key : keys)
            list.add(teamMap.get(key));
        return list;
    }

    public void make_test_teams(){
        List<Team> teams = new ArrayList<Team>();
        Player chico = new Player("Leonard Marx", "Chico");
        Player groucho = new Player("Julius Marx", "Groucho");
        Player harpo = new Player("Adolph Marx", "Harpo");
        List<Player> mb = new ArrayList<Player>();
        mb.add(chico);
        mb.add(groucho);
        mb.add(harpo);

        Team marxBrothers = new Team("Marx Brothers", mb);
        teams.add(marxBrothers);
        storeTeams(teams);
    }

    private void storeTeams(List<Team> teams){
        for (Team team : teams)
            teamMap.put(team.getName(), team);
    }
}
