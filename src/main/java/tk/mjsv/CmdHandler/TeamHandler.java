package tk.mjsv.CmdHandler;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import tk.mjsv.CmdHandler.CmdHandler;
import tk.mjsv.WorldHunter;

public class TeamHandler {
    private static String index = WorldHunter.index;
    public static void Command(CommandSender sender,String[] args){
        if(args.length==0){
            sender.sendMessage(index+" /팀 생성 (팀이름)");
            sender.sendMessage(index+" /팀 초대 <플레이어>");
        }
        else if (args[0].equals("생성")){
            if (args[1].isEmpty())
                sender.sendMessage(index+" 팀이름을 입력해주세요");
            else{

            }

        }
    }
    public static void TabExcutor(CommandSender sender,String[] args){

    }
}
