package tk.mjsv;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YAML {
    public static FileConfiguration teamData;
    public static FileConfiguration ChunkData;
    private static final File team = new File("GameData/teamData.yml");
    private static final File Chunk = new File("GameData/ChunkData.yml");
    public static HashMap<String, ArrayList<OfflinePlayer>> teamHash = new HashMap<>();
    public static List<String> teamList=null;
    public static List<String> ChunkList=null;

    public static void loadData(){
        teamData = YamlConfiguration.loadConfiguration(team);
        ChunkData = YamlConfiguration.loadConfiguration(Chunk);
        try {
            if (!team.exists()) {
                teamData.save(team);
                teamData.load(team);


            } else {
                teamList = teamData.getStringList("teamlist");
                for (String tl : teamList) {
                    ArrayList<OfflinePlayer> sl = teamHash.get(tl);
                    for (String pl : teamData.getStringList("team."+tl+".Player")) {
                        sl.add(Bukkit.getOfflinePlayerIfCached(pl));
                    }
                    teamHash.put(tl, sl);
                }
            }
            if(!Chunk.exists()){
                ChunkData.save(Chunk);
                ChunkData.load(Chunk);
            }else{
                ChunkList = ChunkData.getStringList("finishBuy");
            }
        } catch (InvalidConfigurationException|IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveData(){
        continue;
    }
    public static ArrayList<OfflinePlayer> getTeamList(String s){
        ArrayList<OfflinePlayer> tl;
        if(teamHash.containsKey(s)) {
            tl = teamHash.get(s);
            return tl;
        }
        else{
            return null;
        }
    }
    public static void addTeamList(String s,OfflinePlayer p){
        ArrayList<OfflinePlayer> tl;
        if(teamHash.containsKey(s)) {
            tl = teamHash.get(s);
            tl.add(p);
            teamHash.put(s,tl);
        }
    }
    public static void subTeamList(String s,OfflinePlayer p){
        ArrayList<OfflinePlayer> tl;
        if(teamHash.containsKey(s)) {
            tl = teamHash.get(s);
            tl.remove(p);
            teamHash.put(s,tl);
        }
    }
}
