package tk.mjsv;


import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import tk.mjsv.CmdHandler.CmdHandler;
import tk.mjsv.EventHanler.EventHandler;

import java.util.Objects;


public final class WorldHunter extends JavaPlugin {
    PluginDescriptionFile pdf = this.getDescription();
    @Override
    public void onEnable() {
        pdf.getCommands().keySet().forEach($->{
            Objects.requireNonNull(getCommand($)).setExecutor(new CmdHandler());
            Objects.requireNonNull(getCommand($)).setTabCompleter(new CmdHandler());
        });
        YAML.loadData();
        Bukkit.getPluginManager().registerEvents(new EventHandler(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
