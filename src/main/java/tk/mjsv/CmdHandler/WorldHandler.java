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

public class WorldHandler {
    private static final String index = WorldHunter.index;
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
                if (args[0].equals("구매")) {
                    if(p.getWorld().getName().equals(YAML.getCenterWorld())) {
                        Boolean buy = false;
                        Boolean message = true;
                        Inventory i = p.getInventory();
                        switch(YAML.getLandRange(c)){
                            case 0:
                            case 1:
                                sender.sendMessage(index+"관리국 소유의 땅입니다.");
                                message=false;
                                break;
                            case 2:
                                if(i.contains(Material.IRON_INGOT,32)){
                                    if(i.contains(Material.ACACIA_LOG,16) |
                                        i.contains(Material.BIRCH_LOG,16)|
                                        i.contains(Material.DARK_OAK_LOG,16)|
                                        i.contains(Material.JUNGLE_LOG,16)|
                                        i.contains(Material.SPRUCE_LOG,16)|
                                        i.contains(Material.OAK_LOG)) buy=true;
                                }break;
                            case 3:
                                if(i.contains(Material.IRON_INGOT,16)&i.contains(Material.GOLD_INGOT,16)){
                                    buy = true;
                                };
                                break;
                            case 4:
                                if(i.contains(Material.GOLD_INGOT,16)&i.contains(Material.DIAMOND,3)) {
                                    buy = true;
                                }
                                break;
                            case 5:
                                if(i.contains(Material.GOLD_INGOT,32)&i.contains(Material.EMERALD,3)) {
                                    buy = true;
                                }
                                break;
                            case 6:
                                if(i.contains(Material.EMERALD,5)) {
                                    buy = true;
                                }
                                break;
                            default:
                                sender.sendMessage(index+"구입 불가한 위치입니다.");
                                message=false;
                        }
                        if(buy){
                            if (YAML.setLandTeam(team, c)){
                                sender.sendMessage(index + YAML.getLandLoc(c) + " 땅을 구입하셨습니다");
                                switch(YAML.getLandRange(c)){
                                    case 2:
                                        if(i.contains(Material.IRON_INGOT,32)) {
                                            if (i.contains(Material.ACACIA_LOG, 16)) {
                                                i.removeItem(new ItemStack(Material.ACACIA_LOG));
                                            } else if (i.contains(Material.BIRCH_LOG, 16)) {
                                                i.removeItem(new ItemStack(Material.BIRCH_LOG, 16));
                                            } else if (i.contains(Material.DARK_OAK_LOG, 16)) {
                                                i.removeItem(new ItemStack(Material.DARK_OAK_LOG, 16));
                                            } else if (i.contains(Material.JUNGLE_LOG, 16)) {
                                                i.removeItem(new ItemStack(Material.JUNGLE_LOG, 16));
                                            } else if (i.contains(Material.SPRUCE_LOG, 16)) {
                                                i.removeItem(new ItemStack(Material.SPRUCE_LOG, 16));
                                            } else if (i.contains(Material.OAK_LOG, 16)) {
                                                i.removeItem(new ItemStack(Material.OAK_LOG, 16));
                                            } else break;
                                            i.removeItem(new ItemStack(Material.IRON_INGOT, 32));
                                        }
                                        break;
                                    case 3:
                                        i.removeItem(new ItemStack(Material.IRON_INGOT,16));
                                        i.removeItem(new ItemStack(Material.GOLD_INGOT,16));
                                        break;
                                    case 4:
                                        i.removeItem(new ItemStack(Material.GOLD_INGOT,16));
                                        i.removeItem(new ItemStack(Material.DIAMOND,3));
                                        break;
                                    case 5:
                                        i.removeItem(new ItemStack(Material.GOLD_INGOT,32));
                                        i.removeItem(new ItemStack(Material.EMERALD,3));
                                        break;
                                    case 6:
                                        i.removeItem(new ItemStack(Material.EMERALD,5));
                                        break;
                                }
                            }
                            else sender.sendMessage(index + YAML.getLandLoc(c) + "땅을 누군가에게 소유되어있습니다");
                        }else if(message) sender.sendMessage(index+"자원이 모자랍니다 가격표를 다시 봐주세요");
                        } else sender.sendMessage(index+"해당 월드는 땅따먹기 월드가 아닙니다");
                }
                if (args[0].equals("스폰설정")) {
                    if(YAML.getLandTeam(c).equals(team)){
                        if (YAML.setLandSpawn(team, p.getLocation())) sender.sendMessage(index + "현재 좌표가 땅 스폰으로 설정되었습니다.");
                        else sender.sendMessage(index + "땅 스폰 설정을 실패하였습니다.");
                    }
                    else sender.sendMessage(index+"해당 땅은 소유자의 땅이 아닙니다");
                    
                }
            }
            if (p.isOp()) {
                if (args[0].equals("중앙")) {
                    YAML.setCenterLand(c);
                    sender.sendMessage(index + "땅 중앙이 설정되었습니다");
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
