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
    public static  FileConfiguration teamData;
    private static final File team = new File("GameData/teamData.yml");
    public static HashMap<String, ArrayList<OfflinePlayer>> teamHash = new HashMap<>();
    public static List<String> teamList=null;

    public static void loadData(){
        teamData = YamlConfiguration.loadConfiguration(team);
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
        } catch (InvalidConfigurationException|IOException e) {
            e.printStackTrace();
        }
    }
}
