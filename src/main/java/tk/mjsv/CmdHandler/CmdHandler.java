package tk.mjsv.CmdHandler;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHunter;
import java.util.List;

public class CmdHandler implements TabExecutor {

    private static final String index = WorldHunter.index;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (label) {
            case "팀":
                TeamHandler.Command(sender, args);
                break;
            case "타이머":
                TimerHandler.Command(sender, args);
                break;
            case "땅":
                WorldHandler.Command(sender, args);
                break;
            case "공지":
                if (sender.isOp()) Bukkit.broadcastMessage("\n"+index + String.join(" ",args)+"\n");
                else sender.sendMessage(index + "권한이 부족합니다.");
                break;
            case "남은시간":
                if (Timer.set)
                    sender.sendMessage(index + Timer.setting + "시간: " + Timer.count / 60 + "분 " + Timer.count % 60 + "초");
                else sender.sendMessage(index + "게임이 시작되지 않았습니다.");
                break;
//            case "수분":
//                if (Timer.set) {
//                    if (Timer.hm.getOrDefault((Player) sender, 100) > 80) sender.sendMessage(index + ChatColor.DARK_GREEN + sender.getName() + "님의 수분: " + Timer.hm.getOrDefault((Player) sender, 100) + "%");
//                    else if (Timer.hm.getOrDefault((Player) sender, 100) > 50) sender.sendMessage(index + ChatColor.GREEN + sender.getName() + "님의 수분: " + Timer.hm.getOrDefault((Player) sender, 100) + "%");
//                    else if (Timer.hm.getOrDefault((Player) sender, 100) > 30) sender.sendMessage(index + ChatColor.YELLOW + sender.getName() + "님의 수분: " + Timer.hm.getOrDefault((Player) sender, 100) + "%");
//                    else sender.sendMessage(index + ChatColor.RED + sender.getName() + "님의 수분: " + Timer.hm.getOrDefault((Player) sender, 100) + "%");
//                }
//                else sender.sendMessage(index + "게임이 시작되지 않았습니다.");
//                break;
            case "연구":
                StudyHandler.Command(sender, args);
                break;
            case "초대":
                if (args.length==1){
                    Player p = (Player) sender;
                    Inventory i = p.getInventory();
                    ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                    ItemStack dia = new ItemStack(Material.DIAMOND);
                    ItemStack eme = new ItemStack(Material.EMERALD);
                    boolean check = false;
                    if(i.contains(Material.GOLD_INGOT,20)&i.contains(Material.EMERALD,1)){
                        gold.setAmount(20);
                        eme.setAmount(1);
                        i.removeItem(gold);
                        i.removeItem(eme);
                        check = true;
                    }else if(i.contains(Material.GOLD_INGOT,32)&i.contains(Material.DIAMOND,5)){
                        gold.setAmount(32);
                        dia.setAmount(5);
                        i.removeItem(gold);
                        i.removeItem(dia);
                        check = true;
                    }
                    if(check){
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        op.setWhitelisted(true);
                        Bukkit.broadcastMessage(index+" "+sender.getName()+"님꼐서 "+args[0]+"님은 WorldHunts에 초대하셨습니다");
                    }
                    else sender.sendMessage(index+" 초대하기 위한 재료가 모자랍니다(금20+에메랄드,금32+다이아5)");
                }
                break;
            case "부여":
                Player p = (Player)sender;
                ItemStack Is =p.getInventory().getItemInMainHand();
                ItemMeta Im = Is.getItemMeta();
                Material Ims = Is.getType();
                if(!Im.hasEnchants()) {
                    switch (Ims) {
                        case STONE_PICKAXE:
                        case STONE_AXE:
                        case STONE_SHOVEL:
                        case GOLDEN_PICKAXE:
                        case GOLDEN_AXE:
                        case STONE_SWORD:
                        case WOODEN_AXE:
                        case WOODEN_PICKAXE:
                        case IRON_PICKAXE:
                            Im.displayName(Component.text(index+" "+Ims.name().replace("_"," ")));
                            sender.sendMessage(Im.getLocalizedName());
                            Is.setItemMeta(Im);
                            sender.sendMessage(index+"부여가 완료되었습니다");
                            break;
                        default:
                            sender.sendMessage(index+"부여가 불가능한 아이템 입니다");
                    }
                }else{
                    sender.sendMessage(index,"인첸트가된 아이템은 부여가 불가능합니다");
                }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> returnList = null;
        switch (alias) {
            case "팀":
                returnList = TeamHandler.TabExcutor(sender,args);
                break;
            case "타이머":
                returnList = TimerHandler.TabExcutor(sender, args);
                break;
        }
        return returnList;
    }
}
