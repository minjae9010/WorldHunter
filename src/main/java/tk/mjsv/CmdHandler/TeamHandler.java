package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.yaml.snakeyaml.Yaml;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamHandler {
    private static final String index = WorldHunter.index;
    public static void Command(CommandSender sender,String[] args){
        if(args.length==0){
            sender.sendMessage(index+" /팀 생성 <팀이름>");
            sender.sendMessage(index+" /팀 초대 <플레이어>");
            if(sender.isOp()){
                sender.sendMessage(index+" /팀 주인변경 <팀이름> <플레이어>");
                sender.sendMessage(index+" /팀 설정 <팀이름> <플레이어>");
            }
        }
        else if (args[0].equals("생성")){
            if (args[1].isEmpty())
                sender.sendMessage(index+" 팀이름을 입력해주세요");
            else{
            }

        }
        else if (args[0].equals("초대")){
            if(args[1].isEmpty()){
                sender.sendMessage(index+" 초대할 플레이어를 입력해주세요");
            }
            else if(Bukkit.getPlayer(args[2])==null){
                sender.sendMessage(index+" 접속하지 않았거나 없는 플레이어 입니다.");
            }else{
            }
        }
    }
    public static List<String> TabExcutor(CommandSender sender, String[] args){
        if(args.length==0){
            if (sender.hasPermission("WorldHunter.MakeTeam"))
                return Arrays.asList("생성","삭제");
        }
        return null;
    }
}
