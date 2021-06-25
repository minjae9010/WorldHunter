package tk.mjsv.CmdHandler;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamHandler {
    private static final String index = WorldHunter.index;
    public static void Command(CommandSender sender,String[] args){
        if(args.length==0){
            sender.sendMessage(index+" /팀 생성 (팀이름)");
            sender.sendMessage(index+" /팀 초대 <플레이어>");
        }
        else if (args[0].equals("생성")){
            if (args[1].isEmpty())
                sender.sendMessage(index+" 팀이름을 입력해주세요");
            else{
                YAML.teamData.set("team."+args[2]+".Owner",sender.getName());
                ArrayList<OfflinePlayer> tl = YAML.teamHash.get(args[2]);
                tl.add((OfflinePlayer) sender);
                YAML.teamHash.put(args[2],tl);
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
