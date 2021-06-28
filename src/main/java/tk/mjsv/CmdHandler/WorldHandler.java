package tk.mjsv.CmdHandler;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.mjsv.WorldHunter;

public class WorldHandler {
    private static final String index = WorldHunter.index;
    public static void Command(CommandSender sender,String[] args) {
        if (args.length==0){
            sender.sendMessage(index+" /땅 구매");
        }
        if(args[1].equals("구매")){
            Player p = (Player) sender;
            Chunk c = p.getChunk();

        }
    }

}
