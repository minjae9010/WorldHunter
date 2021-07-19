package tk.mjsv.TimerHandler;

import org.bukkit.Bukkit;
import tk.mjsv.CmdHandler.TimerHandler;
import tk.mjsv.WorldHunter;

public class Timer implements Runnable {
    private static final String index = WorldHunter.index;
    public static int count = -1;
    private static String str;
    public static boolean set = false;
    public static String setting = "없음";


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
            set = false;
            Bukkit.getScheduler().cancelTasks(TimerHandler.pl);
        }
        count--;
        switch (setting) {
            case "평화":
                new TimerData().updateData(Integer.toString(count), Integer.toString(TimerHandler.Wseconds));
                break;
            case "전쟁":
                new TimerData().updateData("0", Integer.toString(count));
        }
    }
}