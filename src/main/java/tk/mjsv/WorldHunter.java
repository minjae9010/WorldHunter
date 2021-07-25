package tk.mjsv;


import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import tk.mjsv.CmdHandler.CmdHandler;
import tk.mjsv.CmdHandler.StudyHandler;
import tk.mjsv.EventHanler.EventH;
import tk.mjsv.recipes.RecipeFrunce;

import java.util.Objects;


public final class WorldHunter extends JavaPlugin {
    public static String index = "§f[§eWorld§aHunter§f] ";
    PluginDescriptionFile pdf = this.getDescription();
    @Override
    public void onEnable() {
        pdf.getCommands().keySet().forEach($->{
            Objects.requireNonNull(getCommand($)).setExecutor(new CmdHandler());
            Objects.requireNonNull(getCommand($)).setTabCompleter(new CmdHandler());
        });
        YAML.loadData();
        Bukkit.getPluginManager().registerEvents(new EventH(),this);
        Bukkit.getPluginManager().registerEvents(new StudyHandler(),this);
        RecipeFrunce.enableFurnceRecipe(getServer());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
