package tk.mjsv;

import org.bukkit.command.CommandSender;

public class TeamHandler {
    private static String index = CmdHandler.index;
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
