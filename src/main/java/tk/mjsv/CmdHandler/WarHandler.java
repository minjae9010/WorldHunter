package tk.mjsv.CmdHandler;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;
import tk.mjsv.utile.ChunkLoc;

import java.util.HashMap;

public class WarHandler {
    private static final String index = WorldHunter.index;
    public static HashMap<ChunkLoc,Integer> TaskList;
    public static HashMap<ChunkLoc,String> pvpList;

    public static void Command(CommandSender sender, String[] args) {
        String player = sender.getName();
        Chunk c = ((Player)sender).getChunk();
        if(args.length==0){
            sender.sendMessage(index+"/전쟁 선포");
        }
        else if(YAML.getTeamOwner(YAML.getLandTeam(c)).equals(player)){
            if(pvpList.containsKey(YAML.getLandLoc(c))) sender.sendMessage(index+"이미 전쟁중입니다");
            else if(TimerHandler.Tset.equals("평화")) sender.sendMessage(index+"아직 평화시간입니다");
            else if(TimerHandler.Tset.equals("전쟁")){
                pvpList.put(YAML.getLandLoc(c),YAML.getPlayerTeam(player));
                TaskList.put(YAML.getLandLoc(c),Bukkit.getScheduler().scheduleSyncRepeatingTask(TimerHandler.pl, new Timer(),0,1200));
                Bukkit.broadcast(Component.text("\n"+index+YAML.getPlayerTeam(player)+"팀이 ("+YAML.getLandLoc(c)+"))에 점령 선포를 하였습니다.\n"));
            }
        }
    }
}
