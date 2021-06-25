package tk.mjsv;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YAML {
    private static  FileConfiguration teamData;
    private static final File team = new File("GameData/teamData.yml");
    public static HashMap<String, ArrayList<OfflinePlayer>> teamHash = new HashMap<>();
    public static List<String> teamList=null;

    public static void loadData(){
        teamData = YamlConfiguration.loadConfiguration(team);

        if(!team.exists()){
            try {
                team.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning(e.toString());
            }
        }
        else{
            teamList = teamData.getStringList("teamlist");
            for(String tl:teamList) {
                ArrayList<OfflinePlayer> sl = teamHash.get(tl);
                for (String pl:teamData.getStringList(tl)){
                    sl.add(Bukkit.getOfflinePlayerIfCached(pl));
                }
                teamHash.put(tl,sl);
            }
        }
    }
}
