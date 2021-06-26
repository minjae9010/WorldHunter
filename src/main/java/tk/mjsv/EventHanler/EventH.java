package tk.mjsv.EventHanler;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.mjsv.WorldHunter;

public class EventH implements Listener {
    private static final String index = WorldHunter.index;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(index+e.getPlayer().getName()+"님이 WorldHunter에 접속하셨습니다");
//        e.joinMessage(Component.text(index+" "+e.getPlayer().getName()+"님이 WorldHunter에 접속하셨습니다"));
    }

}
