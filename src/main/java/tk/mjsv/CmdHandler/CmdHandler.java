package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHunter;

import java.util.List;

public class CmdHandler implements TabExecutor {

    private static final String index = WorldHunter.index;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (label) {
            case "팀":
                TeamHandler.Command(sender, args);
                break;
            case "타이머":
                TimerHandler.Command(sender, args);
                break;
            case "땅":
                WorldHandler.Command(sender, args);
                break;
            case "공지":
                if (sender.isOp()) Bukkit.broadcastMessage(index + args[0]);
                else sender.sendMessage(index + "권한이 부족합니다.");
                break;
            case "남은시간":
                if (Timer.set)
                    sender.sendMessage(index + Timer.setting + "시간: " + Timer.count / 60 + "분 " + Timer.count % 60 + "초");
                else sender.sendMessage(index + "게임이 시작되지 않았습니다.");
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (alias) {
            case "팀":
                return TeamHandler.TabExcutor(sender, args);
            case "타이머":
                break;
        }
        return null;
    }
}
