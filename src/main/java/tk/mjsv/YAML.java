package tk.mjsv;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.mjsv.utile.ChunkLoc;

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
        List<String> teamList = getTeamList();
        if(getPlayerTeam(p)==null) {
            if (!teamList.contains(team)) {
                teamList.add(team);
                teamData.set("team.list", teamList);
                teamData.set("team." + team + ".owner", p);
                LuckPermsConnetor.createTeam(team);
                setPlayerTeam(team,p);
                saveData();
                return true;
            } else return false;
        }else return false;
    }
    public static boolean subTeam(String team){
        List<String> tl = getTeamList();
        if(tl.contains(team)){
            tl.remove(team);
            teamData.set("team.list",tl);
            teamData.set("team."+team+".owner",null);
            teamData.set("team."+team+".player",null);
            teamData.set("team."+team+".prefix",null);
            ChunkData.set("land.team."+team+".land",null);
            ChunkData.set("land.team."+team+".spawn.x",null);
            ChunkData.set("land.team."+team+".spawn.y",null);
            ChunkData.set("land.team."+team+".spawn.z",null);
            ChunkData.set("land.team."+team+".spawn.yaw",null);
            ChunkData.set("land.team."+team+".spawn.pitch",null);
            return true;
        }else return false;
    }
    public static String getTeamOwner(String team){
        List<String> tl = getTeamList();
        String owner = null;
        if(tl.contains(team)){
            owner = teamData.getString("team."+team+".owner");
        }
        return  owner;
    }
    public static void setTeamOwner(String team,String p){
        teamData.set("team."+team+".owner",p);
        saveData();

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
            LuckPermsConnetor.addPlayerTeam(team,player);
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
    public static boolean setTeamPrefix(String team,String prefix){
        List<String> tl = getTeamList();
        if(tl.contains(team)){
            teamData.set("team."+team+".prefix",prefix);
            LuckPermsConnetor.setTeamPrefix(team,prefix);
            saveData();
            return true;
        }else return false;
    }
    public static String getTeamPrefix(String team){
        String prefix = teamData.getString("team."+team+".prefix");
        return prefix;
    }
    public static void setCenterLand(Chunk c){
        String world = c.getWorld().getName();
        int x = c.getX();
        int z = c.getZ();

        ChunkData.set("land.center.world",world);
        ChunkData.set("land.center.x",x);
        ChunkData.set("land.center.z",z);
        saveData();
    }
    public static String getCenterLand(){
        String World = ChunkData.getString("land.center.world");
        int x = ChunkData.getInt("land.center.x");
        int z = ChunkData.getInt("land.center.z");
        String ChunkLoc = World+","+x+","+z;
        return ChunkLoc;
    }
    public static String getCenterWorld(){
        String World = ChunkData.getString("land.center.world");
        return World;
    }
    public static ChunkLoc getCenterLoc() {
        int x = ChunkData.getInt("land.center.x");
        int z = ChunkData.getInt("land.center.z");
        return new ChunkLoc(x,z);
    }
    public static ChunkLoc getLandLoc(Chunk c){
        ChunkLoc center = getCenterLoc();
        int x = center.getX()-c.getX();
        int z = center.getZ()-c.getZ();
        return new ChunkLoc(x,z);
    }
    public static int getLandRange(Chunk c){
        ChunkLoc land = getLandLoc(c);
        int max = Integer.max(Math.abs(land.getX()),Math.abs(land.getZ()));
        return max;
    }
    public static boolean setLandTeam(String team ,Chunk c){
        ChunkLoc land = getLandLoc(c);
        String loc = land.toString();
        boolean returnBoolean=false;
        if(getLandTeam(c)==null){
            if(getTeamList().contains(team)){
                List<String> tl = getTeamLand(team);
                tl.add(loc);
                ChunkData.set("land.team."+team+".land",tl);
                returnBoolean = true;
            }
        }
        saveData();
        return returnBoolean;
    }
    public static boolean subLandTeam(Chunk c){
        ChunkLoc land = getLandLoc(c);
        String loc = land.toString();
        String team = getLandTeam(c);
        boolean returnBoolean=false;
        if(getLandTeam(c)!=null){
            if(getTeamList().contains(team)){
                List<String> tl = getTeamLand(team);
                tl.remove(loc);
                ChunkData.set("land.team."+getLandTeam(c)+".land",tl);
                returnBoolean = true;
            }
        }
        saveData();
        return returnBoolean;
    }
    public static String getLandTeam(Chunk c){
        ChunkLoc land = getLandLoc(c);
        String loc = land.toString();
        String team = null;
        List<String> tl = getTeamList();
        List<String> ll;
        for(String s:tl){
            ll=getTeamLand(s);
            if(ll.contains(loc)) team=s;
        }
        return team;
    }
    public static List<String> getTeamLand(String team){
        List<String> tl = getTeamList();
        List<String> ll = null;
        if(tl.contains(team)){
            ll = ChunkData.getStringList("land.team."+team+".land");
        }
        return ll;
    }
    public static boolean setLandSpawn(String team,Location loc){
        if(getTeamList().contains(team)){
            ChunkData.set("land.team."+team+".spawn.x",loc.getX());
            ChunkData.set("land.team."+team+".spawn.y",loc.getY());
            ChunkData.set("land.team."+team+".spawn.z",loc.getZ());
            ChunkData.set("land.team."+team+".spawn.yaw",loc.getYaw());
            ChunkData.set("land.team."+team+".spawn.pitch",loc.getPitch());
            saveData();
            return true;
        }
        else return false;

    }
    public static Location getLandSpawn(String team){
        Location loc=null;
        if(getTeamList().contains(team)){
            loc = new Location(
                    Bukkit.getWorld(getCenterWorld()),
                    ChunkData.getDouble("land.team."+team+".spawn.x"),
                    ChunkData.getDouble("land.team."+team+".spawn.y"),
                    ChunkData.getDouble("land.team."+team+".spawn.x"),
                    (float) ChunkData.getDouble("land.team."+team+".spawn.yaw"),
                    (float) ChunkData.getDouble("land.team."+team+".spawn.pitch"));
        }
        saveData();
        return loc;
    }





}
