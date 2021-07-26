package tk.mjsv.CmdHandler;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHunter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CmdHandler implements TabExecutor {

    private static final String index = WorldHunter.index;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (label) {
            case "팀":
                try {
                    TeamHandler.Command(sender, args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "타이머":
                TimerHandler.Command(sender, args);
                break;
            case "땅":
                WorldHandler.Command(sender, args);
                break;
            case "공지":
                if (sender.isOp()) {
                    StringBuilder sb = new StringBuilder();
                    for (String str : args) sb.append(str).append(" ");
                    Bukkit.broadcastMessage(index + sb);
                } else sender.sendMessage(index + "권한이 부족합니다.");
                break;
            case "남은시간":
                if (Timer.set)
                    sender.sendMessage(index + Timer.setting + "시간: " + Timer.count / 60 + "분 " + Timer.count % 60 + "초");
                else sender.sendMessage(index + "게임이 시작되지 않았습니다.");
                break;
            case "수분":
                if (Timer.set) {
                    if (Timer.playerWater.getOrDefault((Player) sender, 100) > 80)
                        sender.sendMessage(index + ChatColor.DARK_GREEN + sender.getName() + "님의 수분: " + Timer.playerWater.getOrDefault((Player) sender, 100) + "%");
                    else if (Timer.playerWater.getOrDefault((Player) sender, 100) > 50)
                        sender.sendMessage(index + ChatColor.GREEN + sender.getName() + "님의 수분: " + Timer.playerWater.getOrDefault((Player) sender, 100) + "%");
                    else if (Timer.playerWater.getOrDefault((Player) sender, 100) > 30)
                        sender.sendMessage(index + ChatColor.YELLOW + sender.getName() + "님의 수분: " + Timer.playerWater.getOrDefault((Player) sender, 100) + "%");
                    else
                        sender.sendMessage(index + ChatColor.RED + sender.getName() + "님의 수분: " + Timer.playerWater.getOrDefault((Player) sender, 100) + "%");
                } else sender.sendMessage(index + "게임이 시작되지 않았습니다.");
                break;
//            case "연구":
//                StudyHandler.Command(sender, args);
            case "지명수배":
                WantedHandler.Command(sender, args);
                break;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (alias) {
            case "팀":
                return TeamHandler.TabExcutor(sender, args);
            case "타이머":
                return TimerHandler.TabExcutor(sender, args);
        }
        return null;
    }
}
