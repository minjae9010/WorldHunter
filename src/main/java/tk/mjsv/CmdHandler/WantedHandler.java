package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHunter;

public class WantedHandler {
    private static final String index = WorldHunter.index;

    public static void Command(CommandSender sender, String[] args) {
        if (args[0].length() == 0) {
            sender.sendMessage(index + "/지명수배 추가 <플레이어> <분> <초>");
            sender.sendMessage(index + "/지명수배 차감 <플레이어> <분> <초>");
            sender.sendMessage(index + "/지명수배 해제 <플레이어>");
        }
        if (args[0].equals("추가")) {
            if (args[1].isEmpty()) sender.sendMessage(index + "플레이어를 입력해주세요.");
            if (args[2].isEmpty()) sender.sendMessage(index + "시간을 입력해주세요.");
            else if (args[3].isEmpty()) {
                int seconds = Integer.parseInt(args[2]) * 60;
                Timer.wanted.put(Bukkit.getPlayer(args[1]), true);
                Timer.wantedTime.put(Bukkit.getPlayer(args[1]), Timer.wantedTime.getOrDefault(Bukkit.getPlayer(args[1]), 0) + seconds);
                sender.sendMessage(index + args[1] + "님의 지명수배 시간을 " + args[2] + "분 추가 했습니다.");
            } else {
                int seconds = Integer.parseInt(args[2]) * 60 + Integer.parseInt(args[3]);
                Timer.wanted.put(Bukkit.getPlayer(args[1]), true);
                Timer.wantedTime.put(Bukkit.getPlayer(args[1]), Timer.wantedTime.getOrDefault(Bukkit.getPlayer(args[1]), 0) + seconds);
                sender.sendMessage(index + args[1] + "님의 지명수배 시간을 " + args[2] + "분 " + args[3] + "초 추가 했습니다.");
            }
        } else if (args[0].equals("차감")) {
            if (args[1].isEmpty()) sender.sendMessage(index + "플레이어를 입력해주세요.");
            else if (!Timer.wanted.getOrDefault(Bukkit.getPlayer(args[1]), false)) sender.sendMessage(index + "해당 플레이어는 지명수배 상태가 아닙니다.");
            else if (args[2].isEmpty()) sender.sendMessage(index + "시간을 입력해주세요.");
            else if (args[3].isEmpty()) {
                int seconds = Integer.parseInt(args[2]) * 60;
                Timer.wanted.put(Bukkit.getPlayer(args[1]), true);
                Timer.wantedTime.put(Bukkit.getPlayer(args[1]), Timer.wantedTime.getOrDefault(Bukkit.getPlayer(args[1]), 0) - seconds);
                sender.sendMessage(index + args[1] + "님의 지명수배 시간을 " + args[2] + "분 차감 했습니다.");
            } else {
                int seconds = Integer.parseInt(args[2]) * 60 + Integer.parseInt(args[3]);
                Timer.wanted.put(Bukkit.getPlayer(args[1]), true);
                Timer.wantedTime.put(Bukkit.getPlayer(args[1]), Timer.wantedTime.getOrDefault(Bukkit.getPlayer(args[1]), 0) - seconds);
                sender.sendMessage(index + args[1] + "님의 지명수배 시간을 " + args[2] + "분 " + args[3] + "초 차감 했습니다.");
            }
        } else if (args[0].equals("해제")) {
            if (args[1].isEmpty()) sender.sendMessage(index + "지명수배를 해제할 플레이어를 입력해주세요.");
            else if (!Timer.wanted.getOrDefault(Bukkit.getPlayer(args[1]), false)) sender.sendMessage(index + "이미 지명수배가 해제된 플레이어 입니다.");
            else {
                Timer.wanted.put(Bukkit.getPlayer(args[1]), false);
                sender.sendMessage(index + args[1] + "님의 지명수배를 해제하였습니다.");
            }
        }
    }
}