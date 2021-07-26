package tk.mjsv.WorldEvent;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.ChunkEvent
import org.bukkit.entity.Player;



public class WorldEvent implements Listener {

    @EventHandler
    public void onChunk(ChunkEvent event) {
        Player player = event.getPlayer();
        if (player.getChunk().isNull()) {
            player.sendMessage("중립지역 입니다.");
        }
    }

}