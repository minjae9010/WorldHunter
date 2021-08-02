package tk.mjsv.EventHanler;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkEvent;


public class WorldEvent implements Listener {

    @EventHandler
    public void onChunk(ChunkEvent e) {
        Chunk chunk = e.getChunk();
        if (chunk.equals(null)) {
            String s = "중립지역 입니다.";
        }
    }

}