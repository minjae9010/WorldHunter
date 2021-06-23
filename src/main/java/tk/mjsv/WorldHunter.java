package tk.mjsv;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldHunter extends JavaPlugin {

    PluginDescriptionFile pdf = this.getDescription();
    @Override
    public void onEnable() {
        pdf.getCommands().keySet().forEach($->{
            getCommand($).setExecutor(new CmdHandler());
            getCommand($).setTabCompleter(new CmdHandler());
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
