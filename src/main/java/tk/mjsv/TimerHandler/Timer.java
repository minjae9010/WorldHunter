package tk.mjsv.TimerHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.mjsv.CmdHandler.TimerHandler;
import tk.mjsv.WorldHunter;

import java.util.HashMap;

public class Timer implements Runnable {
    private static final String index = WorldHunter.index;
    public static int count = -1;
    public static int water = 0;
    public static HashMap<Player, Integer> playerWater = new HashMap<>();
    private static String str;
    public static boolean set = false;
    public static String setting = "없음";

    public static HashMap<Player, Boolean> wanted = new HashMap<>();
    public static HashMap<Player, Integer> wantedTime = new HashMap<>();
    public static HashMap<Player, Integer> kills = new HashMap<>();


    @Override
    public void run() {
        if (!set) {
            count = TimerHandler.Pseconds;
            setting = "평화";
            set = true;
        }
        switch (setting) {
            case "평화":
                str = "평화시간";
                break;
            case "전쟁":
                str = "전쟁시간";
        }

        switch (count) {
            case 5400:
                Bukkit.broadcastMessage(index + str + " : 1시간 30분 남았습니다.");
                break;
            case 3600:
                Bukkit.broadcastMessage(index + str + " : 1시간 남았습니다.");
                break;
            case 1800:
                Bukkit.broadcastMessage(index + str + " : 30분 남았습니다.");
                break;
            case 1500:
                Bukkit.broadcastMessage(index + str + " : 25분 남았습니다.");
                break;
            case 1200:
                Bukkit.broadcastMessage(index + str + " : 20분 남았습니다.");
                break;
            case 900:
                Bukkit.broadcastMessage(index + str + " : 15분 남았습니다.");
                break;
            case 600:
                Bukkit.broadcastMessage(index + str + " : 10분 남았습니다.");
                break;
            case 300:
                Bukkit.broadcastMessage(index + str + " : 5분 남았습니다.");
                break;
            case 120:
                Bukkit.broadcastMessage(index + str + " : 2분 남았습니다.");
                break;
        }
        if (count <= 5) {
            Bukkit.broadcastMessage(index + str + " : " + count + "초 남았습니다.");
        }
        if (count == 0) {
            Bukkit.broadcastMessage(index + str + "이 종료되었습니다");
            if (setting.equals("평화")) {
                count = TimerHandler.Wseconds;
                setting = "전쟁";
            } else if (setting.equals("전쟁")) {
                set = false;
                Bukkit.getScheduler().cancelTasks(TimerHandler.pl);
            }
        }
        if (TimerHandler.TimerStop) {
            TimerHandler.TimerStop = false;
            Bukkit.broadcastMessage(index + "관리자가 타이머를 종료 하였습니다");
            Timer.set = false;
            Bukkit.getScheduler().cancelTasks(TimerHandler.pl);
        }
        count--;
        water++;

        for (Player player : wanted.keySet()) {
            if (!wanted.getOrDefault(player, false)) continue;
            wantedTime.put(player, wantedTime.get(player) - 1);
            if (wantedTime.get(player) <= 0) {
                wanted.put(player, false);
                player.sendMessage(index + "당신의 지명수배가 해제 되었습니다.");
            }
        }

        switch (setting) {
            case "평화":
                new TimerData().updateData(Integer.toString(count), Integer.toString(TimerHandler.Wseconds));
                if (water >= 60) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (playerWater.getOrDefault(player, 100) != 0) playerWater.put(player, playerWater.getOrDefault(player, 100) - 1);
                    }
                    water = 0;
                }
                break;
            case "전쟁":
                new TimerData().updateData("0", Integer.toString(count));
                if (water >= 120) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (playerWater.getOrDefault(player, 100) != 0) playerWater.put(player, playerWater.getOrDefault(player, 100) - 1);
                    }
                    water = 0;
                }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerWater.getOrDefault(player, 100) <= 30)
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 32767, 1));
            if (playerWater.getOrDefault(player, 100) <= 15)
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 32767, 1));
            if (playerWater.getOrDefault(player, 100) == 0 && player.getHealth() - 5.0 >= 0) player.damage(5.0);
            else if (playerWater.getOrDefault(player, 100) == 0 && player.getHealth() - 5.0 < 0)
                player.damage(player.getHealth());
        }
    }
}