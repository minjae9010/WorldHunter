package tk.mjsv.EventHanler;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.WorldHandler.WorldData;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

import java.io.IOException;

public class EventH implements Listener {
    private static final String index = WorldHunter.index;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(index + e.getPlayer().getName() + "님이 WorldHunter에 접속하셨습니다");
//        e.joinMessage(Component.text(index+" "+e.getPlayer().getName()+"님이 WorldHunter에 접속하셨습니다"));
    }

    @EventHandler
    public void onPlayerDrink(PlayerItemConsumeEvent e) {
        if (((PotionMeta) (e.getItem().getItemMeta())).getBasePotionData().getType() == PotionType.WATER) {
            Timer.hm.put(e.getPlayer(), 100);
            e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
            e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (Timer.set) {
            Timer.hm.put(e.getEntity(), 100);
            e.getEntity().removePotionEffect(PotionEffectType.BLINDNESS);
            e.getEntity().removePotionEffect(PotionEffectType.SLOW);
        }
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e) throws IOException {

        if (Timer.setting.equals("평화")) {
            if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                if (e.getEntity().isDead()) {
                    Chunk c = e.getDamager().getChunk();
                    if (WorldData.findData(c.getX(), c.getZ()) == null) {
                        Timer.wanted.put((Player) e.getEntity(), true);
                        Timer.wantedTime.put((Player) e.getEntity(), 900);

                        Bukkit.broadcastMessage(index + e.getDamager().getName() + "님이" + e.getEntity().getName() + "님을 중립구역에서 살해 했으므로 15분 동안 지명수배를 겁니다.");
                    } else {
                        int teamIndex = WorldData.findData(c.getX(), c.getZ()).indexOf("|");
                        String teamName = WorldData.findData(c.getX(), c.getZ()).substring(0, teamIndex);

                        if (!teamName.equals(YAML.getPlayerTeam((Player) e.getDamager()))) {
                            if (teamName.equals("aka")) {
                                Timer.wanted.put((Player) e.getEntity(), true);
                                Timer.wantedTime.put((Player) e.getEntity(), 30000);

                                Bukkit.broadcastMessage(index + e.getDamager().getName() + "님이" + e.getEntity().getName() + "님을 관리국에서 살해 했으므로 평화시간 동안 지명수배를 겁니다.");
                            } else {
                                Timer.kills.put((Player) e.getDamager(), Timer.kills.getOrDefault((Player) e.getDamager(), 0) + 1);

                                if (Timer.kills.get((Player) e.getDamager()) < 2) {
                                    Timer.wanted.put((Player) e.getEntity(), true);
                                    Timer.wantedTime.put((Player) e.getEntity(), 1800);

                                    Bukkit.broadcastMessage(index + e.getDamager().getName() + "님이" + e.getEntity().getName() + "님을 적국에서 살해 했으므로 30분 동안 지명수배를 겁니다.");
                                } else {
                                    Timer.wanted.put((Player) e.getEntity(), true);
                                    Timer.wantedTime.put((Player) e.getEntity(), 30000);

                                    Bukkit.broadcastMessage(index + e.getDamager().getName() + "님이" + e.getEntity().getName() + "님을 적국에서 살해 했으므로 평화시간 동안 지명수배를 겁니다.");
                                }
                            }
                        }
                    }
                }
            }
            if (e.getEntity() instanceof Player) {
                if (Timer.wanted.getOrDefault((Player) e.getEntity(), false)) {
                    Bukkit.broadcastMessage(index + e.getEntity().getName() + "님이 지명수배 상태에서 죽었습니다.");
                }
            }
        }
    }
}
