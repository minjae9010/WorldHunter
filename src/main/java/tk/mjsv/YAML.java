package tk.mjsv;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;

public class YAML {
    public static FileConfiguration teamData;
    public static FileConfiguration ChunkData;
    private static final File team = new File("GameData/teamData.yml");
    private static final File Chunk = new File("GameData/ChunkData.yml");

    public static void loadData() {
        teamData = YamlConfiguration.loadConfiguration(team);
        ChunkData = YamlConfiguration.loadConfiguration(Chunk);
        try {
            if (!team.exists()) {
                teamData.save(team);
            }
            if(!Chunk.exists()){
                ChunkData.save(Chunk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveData(){
        try {
            teamData.save(team);
            ChunkData.save(Chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTeamList() {
        List<String> teamlist = teamData.getStringList("team.list");
        return teamlist;
    }
    public static boolean addTeam(String team,String p){
        List playerList = List.of(p);
        List<String> teamList = getTeamList();
        if(getPlayerTeam(p).isEmpty()) {
            if (!teamList.contains(team)) {
                teamList.add(team);
                teamData.set("team.list", teamList);
                teamData.set("team." + team + ".owner", playerList);
                teamData.set("team." + team + ".player", playerList);
                saveData();
                return true;
            } else return false;
        }else return false;
    }
    public static boolean subTeam(String team){
        List tl = getTeamList();
        if(tl.contains(team)){
            tl.remove(team);
            teamData.set("team.list",tl);
            teamData.set("team."+team+".owner",null);
            teamData.set("team."+team+".player",null);
            return true;
        }else return false;
    }
    public static boolean ownerTeam(String team,String p){
        String owner = teamData.getString("team."+team+".owner");
        if(owner==p) return true;
        else return false;
    }
    public static List<String> getPlayerList(String team){
        List<String> teamList = getTeamList();
        List<String> playerList = null;
        if(teamList.contains(team)){
            playerList = teamData.getStringList("team."+team+".player");
            saveData();

        }
        return playerList;
    }
    public static String getPlayerTeam(String player){
        String team = null;
        List<String> teamList = getTeamList();
        List<String> playerList;
        for(String t:teamList){
            playerList = getPlayerList(t);
            if(playerList.contains(player)){
                team = t;
                break;
            }
        }
        return team;
    }

    public static boolean setPlayerTeam(String team,String player) {
        List<String> pl = getPlayerList(team);
        if(!pl.contains(player)){
            pl.add(player);
            teamData.set("team."+team+".player",pl);
            saveData();
            return true;
        }
        else return false;
    }
    public static boolean subPlayerTeam(String team,String p){
        List<String> pl = getPlayerList(team);
        if(pl.contains((p))){
            pl.remove(p);
            teamData.set("team."+team+".player",pl);
            saveData();
            return true;
        }
        else return false;
    }




}
