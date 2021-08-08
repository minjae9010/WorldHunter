package tk.mjsv.EventHanler;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tk.mjsv.CmdHandler.TimerHandler;
import tk.mjsv.CmdHandler.WarHandler;
import tk.mjsv.CmdHandler.WorldHandler;
import tk.mjsv.TimerHandler.PvpTime;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;
import java.util.List;
import java.util.Set;

public class EventH implements Listener {
    private static final String index = WorldHunter.index;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.text(index + e.getPlayer().getName() + "님이 WorldHunts에 접속하셨습니다"));
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        if(PvpTime.PvpTime.containsKey(e.getPlayer())){
            PvpTime.PvpTime.remove(e.getPlayer());
            e.getPlayer().setHealth(0D);
            e.getPlayer().kick(Component.text(index+"pvp중 탈주 감지\n사망처리 되었습니다"));
            e.quitMessage(Component.text(index+e.getPlayer().getName()+"님이 pvp중 탈주가 감지되서 사망처리 되었습니다."));
        }else e.quitMessage(Component.text(index+" "+e.getPlayer().getName()+"님이 WorldHunts에 나가셨습니다"));
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
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
        if(player.isOp()) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getChunk().getWorld().getName().equals(YAML.getCenterWorld())) {
                    if (!YAML.getLandTeam(e.getClickedBlock().getChunk()).equals(YAML.getPlayerTeam(e.getPlayer().getName()))|!WarHandler.pvpList.get(YAML.getLandLoc(e.getClickedBlock().getChunk())).equals(YAML.getPlayerTeam(player.getName()))) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (!p.isOp()){
            if (!i.getType().equals(Material.AIR)) {
                String s = ((TextComponent)i.getItemMeta().displayName()).content();
                if (s==null|!s.contains(index)) {
                    e.setCancelled(true);
                }
            }
            if (e.getBlock().getChunk().getWorld().getName().equals(YAML.getCenterWorld())) {
                if (!YAML.getLandTeam(e.getBlock().getChunk()).equals(YAML.getPlayerTeam(e.getPlayer().getName()))|!WarHandler.pvpList.get(YAML.getLandLoc(e.getBlock().getChunk())).equals(YAML.getPlayerTeam(p.getName()))) {
                    e.setCancelled(true);
                }
            }

        }
    }
    @EventHandler
    public void onPlayerChat(AsyncChatEvent e){
        Player p = e.getPlayer();
        String t = YAML.getPlayerTeam(p.getName());
        String prefix = YAML.getTeamPrefix(t);
        TextComponent m = (TextComponent) e.message();
        String message = m.content();
        List<String> tpl;
        List<Player> opl;
        @NotNull Set<Audience> V = e.viewers();
        if(t!=null) {
            if (message.indexOf('!')!=0) {
                tpl = YAML.getPlayerList(t);
                opl = (List<Player>) Bukkit.getOnlinePlayers();
                for(Player pl:opl){
                    if(!pl.isOp()) {
                        if (!tpl.contains(pl.getName())) {
                            V.remove(pl);
                        }
                    }
                }
                e.renderer((rp, rd, msg, vw) ->
                        Component.text()
                                .append(Component.text(prefix+" "))
                                .append(rd)
                                .append(Component.text(" : "))
                                .append(msg)
                                .build()
                );
            }else {
                e.message(Component.text(message.substring(1)));
                e.renderer((rp, rd, msg, vw) ->
                        Component.text()
                                .append(Component.text("§f[§e전쳇§f] "))
                                .append(Component.text(prefix+" "))
                                .append(rd)
                                .append(Component.text(" : "))
                                .append(msg)
                                .build()
                );
            }
        }else {
            e.renderer((rp, rd, msg, vw) ->
                    Component.text()
                            .append(rd)
                            .append(Component.text(" : "))
                            .append(msg)
                            .build()
            );
        }
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(PvpTime.PvpTime.containsKey(p)){
            p.sendMessage(index+"pvp 중이라 명령어를 사용하지 못하십니다.");
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityByEntityDamage(EntityDamageByEntityEvent e){
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        Player p;
        if(TimerHandler.Tset.equals("전쟁")) {
            if (victim instanceof Player & attacker instanceof Player) {
                p = (Player) victim;
                if (!p.isOp()) {
                    if (!PvpTime.PvpTime.containsKey(p)) p.sendMessage(index + "pvp가 감지되었습니다 이제부터 명령어를 사용하지 못하십니다");
                    PvpTime.PvpTime.put(p, 60);
                }
                p = (Player) attacker;
                if (!p.isOp()) {
                    if (!PvpTime.PvpTime.containsKey(p)) p.sendMessage(index + "pvp가 감지되었습니다 이제부터 명령어를 사용하지 못하십니다");
                    PvpTime.PvpTime.put(p, 60);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(!PvpTime.PvpTime.containsKey(e.getEntity().getPlayer())){
            PvpTime.PvpTime.remove(e.getEntity().getPlayer());
        }
        if(e.getEntity().getPlayer().isOp()){
            e.setKeepInventory(true);
            e.setKeepLevel(true);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(e.getTo().getWorld().getName().equals(YAML.getCenterWorld())){
            String team = YAML.getLandTeam(e.getFrom().getChunk());
            if(YAML.getLandRange(e.getFrom().getChunk())<=1) team = "관리국";
            if(team==null) team = "없음";
            if(WorldHandler.Gb!=null) {
                if (WorldHandler.Gb.contains(YAML.getLandLoc(e.getTo().getChunk()))) e.getPlayer().sendActionBar(Component.text("§f[§a현재 청크§f] 위치 : " + YAML.getLandLoc(e.getFrom().getChunk()) + "  그린벨트"));
                else e.getPlayer().sendActionBar(Component.text("§f[§a현재 청크§f] 위치 : "+YAML.getLandLoc(e.getFrom().getChunk())+"  소유자 : "+team));
            }
            else e.getPlayer().sendActionBar(Component.text("§f[§a현재 청크§f] 위치 : "+YAML.getLandLoc(e.getFrom().getChunk())+"  소유자 : "+team));
            if(YAML.getLandRange(e.getTo().getChunk())<=1){
                if(PvpTime.PvpTime.containsKey(e.getPlayer().getName())){
                    e.getPlayer().sendMessage(index+"pvp모드중에는 관리국에 들아가실수 없습니다");
                    e.setCancelled(true);
                }
            }
        }
    }


}
