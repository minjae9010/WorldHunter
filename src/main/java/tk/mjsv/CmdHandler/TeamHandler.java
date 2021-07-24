package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamHandler {
    private static final String index = WorldHunter.index;

    public static void Command(CommandSender sender, String[] args) throws IOException {
        if (args.length == 0) {
            sender.sendMessage(index + "/팀 생성 <팀이름>");
            sender.sendMessage(index + "/팀 초대 <플레이어>");
            if (sender.isOp()) {
                sender.sendMessage(index + "/팀 주인변경 <팀이름> <플레이어>");
                sender.sendMessage(index + "/팀 설정 <팀이름> <플레이어>");
            }
        } else if (args[0].equals("생성")) {
            if (args[1].isEmpty())
                sender.sendMessage(index + "팀이름을 입력해주세요");
            else {
                YAML.createTeam(args[1], (OfflinePlayer) sender, YAML.loadData());
            }
        } else if (args[0].equals("초대")) {
            if (YAML.getPlayerTeam((Player) sender) != null) {
                if (args[1].isEmpty()) {
                    sender.sendMessage(index + "초대할 플레이어를 입력해주세요");
                } else if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(index + "접속하지 않았거나 없는 플레이어 입니다.");
                } else {
                    YAML.addTeamList(YAML.getPlayerTeam((Player) sender), Bukkit.getPlayer(args[1]), YAML.loadData(), (Player) sender);
                }
            } else {
                sender.sendMessage(index + "팀이 존재하지 않습니다.");
            }
        } else if (args[0].equals("추방")) {
            if (YAML.getPlayerTeam((Player) sender) != null) {
                if (args[1].isEmpty()) {
                    sender.sendMessage(index + "초대할 플레이어를 입력해주세요");
                } else if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(index + "접속하지 않았거나 없는 플레이어 입니다.");
                } else {
                    YAML.subTeamList(YAML.getPlayerTeam((Player) sender), Bukkit.getPlayer(args[1]), YAML.loadData());
                    sender.sendMessage(index + args[1] + "님이 추방 되었습니다.");
                }
            }
        } else if (args[0].equals("설정")) {
            if (sender.isOp()) {
                if (YAML.getPlayerTeam((Player) sender) != null) {
                    if (args[1].isEmpty()) {
                        sender.sendMessage(index + "설정할 팀 이름을 입력해주세요.");
                    } else if (args[2].isEmpty()) {
                        sender.sendMessage(index + "초대할 플레이어를 입력해주세요");
                    } else if (Bukkit.getPlayer(args[2]) == null) {
                        sender.sendMessage(index + "접속하지 않았거나 없는 플레이어 입니다.");
                    } else {
                        if (YAML.isValidTeam(args[1])) {
                            YAML.addTeamList(args[1], Bukkit.getPlayer(args[2]), YAML.loadData(), (Player) sender);
                        } else sender.sendMessage(index + "존재하지 않는 팀 이름 입니다.");
                    }
                }
            }
        } else if (args[0].equals("주인변경")) {
            if (sender.isOp()) {
                if (args[1].isEmpty()) {
                    sender.sendMessage(index + "설정할 팀 이름을 입력해주세요.");
                } else if (args[2].isEmpty()) {
                    sender.sendMessage(index + "주인으로 지정 할 플레이어를 입력해주세요");
                } else if (Bukkit.getPlayer(args[2]) == null) {
                    sender.sendMessage(index + "접속하지 않았거나 없는 플레이어 입니다.");
                } else {
                    if (!YAML.isValidTeam(args[1]) || YAML.getPlayerTeam(Bukkit.getPlayer(args[2])) == null) {
                        if (!YAML.isValidTeam(args[1])) sender.sendMessage(index + "존재하지 않는 팀 이름 입니다.");
                        else sender.sendMessage(index + "해당 플레이어는 어느 팀에도 소속 되있지 않습니다.");
                    } else if (!YAML.getPlayerTeam(Bukkit.getPlayer(args[2])).equals(args[1])) sender.sendMessage(index + "해당 팀에 소속 되지 않은 플레이어 입니다.");
                    else YAML.setOwner(args[1], Bukkit.getPlayer(args[2]), YAML.loadData(), (Player) sender, YAML.getOwner(args[1]));
                }
            }
        }
    }

    public static List<String> TabExcutor(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("WorldHunter.MakeTeam"))
                return Arrays.asList("생성", "삭제");
        }
        return null;
    }
}
