package tk.mjsv.EventHanler;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.mjsv.WorldHunter;

public class EventH implements Listener {
    private static String index = WorldHunter.index;
    @EventHandler
    public void onPlayerJOin(PlayerJoinEvent e){
        e.joinMessage(Component.text(index+" "+e.getPlayer()+"님이 WorldHunter에 접속하셨습니다"));
    }

}
