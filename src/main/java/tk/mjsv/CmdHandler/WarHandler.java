package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.TimerHandler.warTimer;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;
import tk.mjsv.utile.ChunkLoc;
import java.util.Hashtable;

public class WarHandler {
    private static final String index = WorldHunter.index;
    public static Hashtable<String,Integer> TaskList = new Hashtable<>();
    public static Hashtable<String,String> pvpList = new Hashtable<>();

    public static void Command(CommandSender sender, String[] args) {
        String player = sender.getName();
        Chunk c = ((Player) sender).getChunk();
        int TaskId;
        if (args.length == 0) {
            sender.sendMessage(index + "/전쟁 선포");
            sender.sendMessage(index + "/전쟁 현황");
            sender.sendMessage(index+"/전쟁 강제종료 (x,z)");
        }
        else if (args[0].equals("선포")){
            if (YAML.getTeamOwner(YAML.getPlayerTeam(player)).equals(player)) {
                if (Timer.setting.equals("전쟁")) {
                    if (!pvpList.containsKey(YAML.getLandLoc(c).toString())) {
                        if (((Player) sender).getInventory().contains(Material.DIAMOND, 5)) {
                            ((Player) sender).getInventory().removeItem(new ItemStack(Material.DIAMOND,5));
                            ChunkLoc loc = YAML.getLandLoc(c);
                            TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(TimerHandler.pl, new warTimer(YAML.getPlayerTeam(player), c), 0, 20);
                            pvpList.put(loc.toString(), YAML.getPlayerTeam(player));
                            TaskList.put(loc.toString(), TaskId);
                        } else sender.sendMessage(index + "자원이 모자랍니다");
                    }else sender.sendMessage(index + "이미 전쟁중입니다");
                }else sender.sendMessage(index+"아직 전쟁 시간이 아닙니다");
            }else sender.sendMessage(index+"당신은 팀에 주인이 아닙니다");
        }
        else if(args[0].equals("현황")){
            sender.sendMessage(index+"전쟁중인 좌표");
            pvpList.forEach((key,value)->
                    sender.sendMessage("    위치 : "+key+" 공격팀 : "+value));
        }
        else if(args[0].equals("강제종료")){
            if(sender.isOp()) {
                if (args.length == 1)
                    sender.sendMessage(index + "좌표를 입력해주세요");
                else {
                    if (pvpList.containsKey(args[1])) {
                        Bukkit.getScheduler().cancelTask(TaskList.get(args[1]));
                        pvpList.remove(args[1]);
                        pvpList.remove(args[1]);
                        sender.sendMessage(index + "정상적으로 강제 종료 되었습니다.");
                    } else sender.sendMessage(index + "전쟁중인 좌표가 아닙니다");
                }
            }else sender.sendMessage("권한이 없습니다");
        }
    }
}
