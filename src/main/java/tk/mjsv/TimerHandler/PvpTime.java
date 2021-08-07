package tk.mjsv.TimerHandler;

import org.bukkit.entity.Player;
import tk.mjsv.WorldHunter;

import java.util.HashMap;

public class PvpTime implements Runnable{
    private static final String index = WorldHunter.index;
    public static HashMap<Player, Integer> PvpTime = new HashMap<>();

    public void run(){
        if(!PvpTime.isEmpty()) {
            for (Player player : PvpTime.keySet()) {
                PvpTime.put(player, PvpTime.get(player) - 1);
                if (PvpTime.get(player) <= 0) {
                    PvpTime.remove(player);
                    player.sendMessage(index + "이제부터 명령어를 사용하실 수 있습니다.");
                }
            }
        }
    }

}
