package tk.mjsv;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YAML {
    private FileConfiguration teamData;
    private File team = new File("GameData/teamData.yml");

    public void loadData(){
        teamData = YamlConfiguration.loadConfiguration(team);
        if(!team.exists()){
            teamData.set("team","");

        }
    }
}
