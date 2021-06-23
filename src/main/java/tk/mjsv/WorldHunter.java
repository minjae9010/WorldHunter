package tk.mjsv;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WorldHunter extends JavaPlugin {
    public static File teamFile = null;
    public static File config = null;
    PluginDescriptionFile pdf = this.getDescription();
    @Override
    public void onEnable() {
        pdf.getCommands().keySet().forEach($->{
            getCommand($).setExecutor(new CmdHandler());
            getCommand($).setTabCompleter(new CmdHandler());
        });
        teamFile = new File(getDataFolder(),"team.yml");
        config = new File(getDataFolder(),"config.yml");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
