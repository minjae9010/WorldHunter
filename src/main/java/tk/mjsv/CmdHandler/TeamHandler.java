package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;
import java.util.List;

public class TeamHandler {
    private static final String index = WorldHunter.index;

    public static void Command(CommandSender sender, String[] args){
        if (args.length == 0) {
            sender.sendMessage(index + "/팀 생성 <팀이름>");
            sender.sendMessage(index + "/팀 초대 <플레이어>");
            sender.sendMessage(index+"/팀 목록 <팀이름> : 팀이름을 안쓰면 팀목록이 나옵니다");

            if (sender.isOp()) {
                sender.sendMessage(index + "/팀 설정 <팀이름> <플레이어>");
            }
        } else if (args[0].equals("생성")) {
            if (args.length == 1)
                sender.sendMessage(index + "팀이름을 입력해주세요");
            else {
                if (YAML.addTeam(args[1], sender.getName())) {
                    sender.sendMessage(index + args[1] + "팀이 생성되었습니다.");
                } else sender.sendMessage("팀이 중복되어 생성되지 않았습니다");
            }
        } else if (args[0].equals("삭제")) {
            if (args.length == 1)
                sender.sendMessage(index + "팀이름을 입력해주세요");
            else {
                if (YAML.subTeam(args[1])) {
                    sender.sendMessage(index + args[1] + "팀이 삭제되었습니다.");
                } else sender.sendMessage("팀이 존재하지 않아 삭제되지 않았습니다");
            }
        }else if (args[0].equals("초대")) {
            if (YAML.getPlayerTeam(sender.getName()) != null) {
                if (args.length==1) {
                    sender.sendMessage(index + "초대할 플레이어를 입력해주세요");
                } else if (!Bukkit.getOfflinePlayer(args[1]).isOnline()) {
                    sender.sendMessage(index + "접속하지 않았거나 없는 플레이어 입니다.");
                } else {
                    if(YAML.setPlayerTeam(YAML.getPlayerTeam(sender.getName()),args[1])) sender.sendMessage(index+args[1]+"님을 초대 완료하였습니다.");
                    else sender.sendMessage(index+"플레어가 이미 팀이있거나 소속된 플레이어 입니다");
                }
            } else {
                sender.sendMessage(index + "팀이 존재하지 않습니다.");
            }
        } else if (args[0].equals("추방")) {
            String team = YAML.getPlayerTeam(sender.getName());
            if (YAML.getTeamOwner(team).equals(sender.getName())){
                if(args.length==1) sender.sendMessage(index+"플레이어를 입력해주세요");
                else{
                    if(YAML.subPlayerTeam(team,args[1])) sender.sendMessage(index+args[1]+"님이 추방되었습니다.");
                    else sender.sendMessage(index+args[1]+"님이 팀에 소속되있지 않거나 없는 플레이어 입니다");
                }
            }
            else sender.sendMessage("팀의 주인이 아닙니다");
        } else if (args[0].equals("목록")) {
            if (args.length==1) {
                for (String s : YAML.getTeamList()) {
                    sender.sendMessage(index + s);
                }
            } else {
                String owner = YAML.getTeamOwner(args[1]);
                List<String> player = YAML.getPlayerList(args[1]);
                sender.sendMessage(index+args[1]+"팀의 목록");
                sender.sendMessage("    팀 주인 :\n        "+owner);
                player.remove(owner);
                sender.sendMessage("    팀원 : ");
                if(!player.isEmpty()) {
                    for (String s : player) {
                        sender.sendMessage("        "+s);
                    }
                }
            }
        } else if(args[0].equals("접두사")){
            String p = sender.getName();
            String team = YAML.getPlayerTeam(p);
            String prefix;
            if(args.length==1)sender.sendMessage(index+"사용하실 접두사를 입력해주세요");
            else if(YAML.getTeamOwner(team).equals(sender.getName())){
                prefix = args[1].replace('&','§');
                if(YAML.setTeamPrefix(team,prefix)) sender.sendMessage(index+"팀의 접두사가 "+prefix+"로 설정되었습니다.");
                else sender.sendMessage("설정하지 못했습니다.");
            }else sender.sendMessage(index+"팀의 주인이 아닙니다");
        } else if (args[0].equals("설정")) {
            if (sender.isOp()) {
                if (YAML.getPlayerTeam(sender.getName()) != null) {
                    if (args.length==1) {
                        sender.sendMessage(index + "설정할 팀 이름을 입력해주세요.");
                    } else if (args.length==2) {
                        sender.sendMessage(index + "주인으로 설정할 플레이어를 입력해주세요");
                    } else {
                        if (YAML.getTeamList().contains(args[1])) {
                            YAML.setTeamOwner(args[1],args[2]);
                        } else sender.sendMessage(index + "존재하지 않는 팀 이름 입니다.");
                    }
                }
            }else sender.sendMessage(index+"오피만 사용할 수 있습니다");
        }
    }

    public static List<String> TabExcutor(CommandSender sender, String[] args) {
        List<String> returnList = null;
        if (args.length == 1) {
            returnList = List.of("생성", "삭제","목록","초대","추방","접두사");
        }
        if (args.length == 2){
            switch(args[0]){
                case "목록":
                    returnList = YAML.getTeamList();
            }
        }
        return returnList;
    }
}
