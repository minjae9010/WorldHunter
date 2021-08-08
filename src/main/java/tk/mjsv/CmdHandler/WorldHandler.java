package tk.mjsv.CmdHandler;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;
import tk.mjsv.utile.ChunkLoc;

import java.util.ArrayList;
import java.util.List;

public class WorldHandler {
    private static final String index = WorldHunter.index;
    public static List<ChunkLoc> Gb = new ArrayList<>();
    public static void Command(CommandSender sender,String[] args) {
        Player p = (Player) sender;
        String team = YAML.getPlayerTeam(p.getName());
        Chunk c = p.getChunk();
        if(args.length==0){
            sender.sendMessage(index+"/땅 구매");
            sender.sendMessage(index+"/땅 스폰설정");
            sender.sendMessage(index+"/땅 가기");
            if(p.isOp()) {
                sender.sendMessage(index + "/땅 중앙");
                sender.sendMessage(index + "/땅 주인변경 <팀>");
                sender.sendMessage(index + "/땅 그린벨트 <true/false>");
            }
        }
        else {
            if (p.getName().equals(YAML.getTeamOwner(team))) {
                if(args[0].equals("구매")){
                    if(c.getWorld().getName().equals(YAML.getCenterWorld())){
                        if(YAML.getLandTeam(c)==null){
                            boolean msg = true;
                            boolean buy = false;
                            Inventory i = p.getInventory();
                            switch(YAML.getLandRange(c)){
                                case 0:
                                case 1:
                                    sender.sendMessage("구입이 불가능한 땅입니다");
                                    msg = false;
                                    break;
                                case 6:
                                    if(i.contains(Material.OAK_LOG,16)&i.contains(Material.IRON_INGOT,32)) buy = true;
                                    break;
                                case 5:
                                    if(i.contains(Material.IRON_NUGGET,16)&i.contains(Material.GOLD_INGOT,16)) buy = true;
                                    break;
                                case 4:
                                    if(i.contains(Material.GOLD_INGOT,16)&i.contains(Material.DIAMOND,3)) buy = true;
                                    break;
                                case 3:
                                    if(i.contains(Material.GOLD_INGOT,32)&i.contains(Material.EMERALD,3)) buy = true;
                                    break;
                                case 2:
                                    if(i.contains(Material.EMERALD,6)) buy = true;
                                    break;
                                default:
                                    sender.sendMessage(index+"구매가 불가능한 땅입니다.");
                            }
                            if(Gb.contains(YAML.getLandLoc(c))){
                                sender.sendMessage(index+"그린벨트로 지정된 땅입니다");
                                msg=false;
                            }
                            if(buy){
                                switch (YAML.getLandRange(c)) {
                                    case 6 -> {
                                        i.removeItem(new ItemStack(Material.OAK_LOG, 16));
                                        i.removeItem(new ItemStack(Material.IRON_INGOT, 32));
                                    }
                                    case 5 -> {
                                        i.removeItem(new ItemStack(Material.IRON_NUGGET, 16));
                                        i.removeItem(new ItemStack(Material.GOLD_INGOT, 16));
                                    }
                                    case 4 -> {
                                        i.removeItem(new ItemStack(Material.GOLD_INGOT, 16));
                                        i.removeItem(new ItemStack(Material.DIAMOND, 3));
                                    }
                                    case 3 -> {
                                        i.removeItem(new ItemStack(Material.GOLD_INGOT, 32));
                                        i.removeItem(new ItemStack(Material.EMERALD, 3));
                                    }
                                    case 2 -> i.removeItem(new ItemStack(Material.EMERALD, 6));
                                }
                                YAML.setLandTeam(team,c);
                                sender.sendMessage(index+"땅 구입을 완료하셨습니다");
                            }else if(msg) sender.sendMessage(index+"자원이 모자라 구매하지 못했습니다.");
                        }else sender.sendMessage(index+"이미 구매완료된 땅입니다.");
                    }else sender.sendMessage(index+"땅따먹기 월드가 아닙니다.");
                }
                if (args[0].equals("스폰설정")) {
                    if(YAML.getLandTeam(c)!=null){
                        if(YAML.getLandTeam(c).equals(team)) {
                            if (YAML.setLandSpawn(team, p.getLocation()))
                                sender.sendMessage(index + "현재 좌표가 땅 스폰으로 설정되었습니다.");
                            else sender.sendMessage(index + "땅 스폰 설정을 실패하였습니다.");
                        }
                    }
                    else sender.sendMessage(index+"해당 땅은 소유자의 땅이 아닙니다");
                    
                }
            }
            if (p.isOp()) {
                if (args[0].equals("중앙")) {
                    YAML.setCenterLand(c);
                    sender.sendMessage(index + "땅 중앙이 설정되었습니다");
                }
                if(args[0].equals("주인변경")){
                    if(args.length==1) sender.sendMessage(index+"팀을 입력해주세요");
                    else if(YAML.subLandTeam(c)|YAML.getLandLoc(c)==null){
                        if(YAML.setLandTeam(args[1],c)) sender.sendMessage(index+"성공적으로 변경되었습니다.");
                        else sender.sendMessage(index+"팀변경에 실패했습니다.");
                    }else sender.sendMessage(index+"땅 뺴기를 실패했습니다");
                }
                if(args[0].equals("그린벨트")){
                    if(args.length==1) {
                        for (ChunkLoc l : Gb) {
                            sender.sendMessage(l.toString());
                        }
                    }
                    if(args.length==2){
                        if(args[1].equals("true")&YAML.getLandTeam(c)==null&!Gb.contains(YAML.getLandLoc(c))){
                            Gb.add(YAML.getLandLoc(c));
                            sender.sendMessage(index+"그린벨트 설정이 완료되었습니다.");
                        }else if(args[1].equals("false")|Gb.contains(YAML.getLandLoc(c))){
                            Gb.remove(YAML.getLandLoc(c));
                            sender.sendMessage(index+"그린벨트가 해제되었습니다.");
                        }else sender.sendMessage(index+"이미 지정 또는 주인이 있는 땅이거나 true/false 를 잘못입력하였습니다");
                    }
                }
            }
        }
        if(args.length==1){
            if(args[0].equals("가기")){
                Location loc = YAML.getLandSpawn(team);
                if(loc!=null){
                    p.teleport(loc);
                    sender.sendMessage(index+"땅에 도착하셨습니다.");
                }
                else sender.sendMessage(index+"땅 스폰이 설정되있지 않습니다");
            }
        }
    }

}
