package tk.mjsv.EventHanler;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

import java.util.Set;

public class EventH implements Listener {
    private static final String index = WorldHunter.index;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.text(index + e.getPlayer().getName() + "님이 WorldHunter에 접속하셨습니다"));
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        e.quitMessage(Component.text(index+" "+e.getPlayer().getName()+"님이 WorldHunter에 나가셨습니다"));
    }

//    @EventHandler
//    public void onPlayerDrink(PlayerItemConsumeEvent e) {
//        if (((PotionMeta) (e.getItem().getItemMeta())).getBasePotionData().getType() == PotionType.WATER) {
//            Timer.hm.put(e.getPlayer(), 100);
//            e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
//            e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
//        }
//    }
//
//    @EventHandler
//    public void onPlayerDeath(PlayerDeathEvent e) {
//        if (Timer.set) {
//            Timer.hm.put(e.getEntity(), 100);
//            e.getEntity().removePotionEffect(PotionEffectType.BLINDNESS);
//            e.getEntity().removePotionEffect(PotionEffectType.SLOW);
//        }
//    }
    @EventHandler
    public void onPlayerItrrute(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Block block = e.getClickedBlock();
            switch(block.getType()){
                case ENCHANTING_TABLE:
                case BLAST_FURNACE:
                case ANVIL:
                case CHIPPED_ANVIL:
                case DAMAGED_ANVIL:
                    if(!player.isOp()){
                        e.setCancelled(true);
                    }
            }
        }
    }
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        Material b = e.getBlock().getType();
        if (!p.isOp()){
            if (!i.getType().equals(Material.AIR)) {
                String s = i.getItemMeta().getDisplayName();
                if (!s.contains(index)|b.equals(Material.DIAMOND_ORE)|b.equals(Material.DEEPSLATE_DIAMOND_ORE)) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerChat(AsyncChatEvent e){
        Player p = e.getPlayer();
        String t = YAML.getPlayerTeam(p.getName());
        TextComponent m = (TextComponent) e.message();
        String message = m.content();
        if(message.indexOf('!')!=0) {
            if (t != null) {
                Set V = e.viewers();
                V.clear();
                for (String s : YAML.getPlayerList(t)) {
                    if (Bukkit.getOfflinePlayer(s).isOnline()) {
                        V.add(Bukkit.getPlayer(s));
                    }
                }
            }
        }else
        e.message(Component.text(message.substring(1)));

    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(Timer.PvpTime.containsKey(p)){
            p.sendMessage(index+"pvp중이라 명령어를 사용하지 못하십니다.");
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityByEntityDamage(EntityDamageByEntityEvent e){
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        Player p;
        if(victim instanceof Player){
            p = (Player) victim;
            if(Timer.PvpTime.containsKey(p)) p.sendMessage(index+"pvp가 감지되었습니다 이제부터 명령어를 사용하지 못하십니다");
            Timer.PvpTime.put((Player)victim,100);
            if(attacker instanceof Player){
                p = (Player) attacker;
                if(Timer.PvpTime.containsKey(p)) p.sendMessage(index+"pvp가 감지되었습니다 이제부터 명령어를 사용하지 못하십니다");
                Timer.PvpTime.put((Player)victim,100);
            }
        }
    }


}
