package tk.mjsv.TimerHandler;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.mjsv.CmdHandler.TimerHandler;
import tk.mjsv.CmdHandler.WarHandler;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

public class warTimer implements Runnable{
    private static final int WarMaxTime = 10;
    int warTime = WarMaxTime;
    int TaskId;
    String attackTeam;
    String sheldTeam;
    Chunk c;
    public warTimer(String team,Chunk c){
        this.attackTeam = team;
        this.sheldTeam = YAML.getLandTeam(c);
        this.c = c;
    }
    @Override
    public void run() {
        if(TimerHandler.Tset.equals("전쟁")){
            int sheldPlayer = 0;
            int attackPlayer = 0;
            for(Entity e:c.getEntities()){
                if(e instanceof Player){
                    if(YAML.getPlayerTeam(e.getName()).equals(sheldTeam)) sheldPlayer += 1;
                    else if(YAML.getPlayerTeam(e.getName()).equals(attackTeam)) attackPlayer +=1;
                }
            }
            if(attackPlayer-sheldPlayer>=3){
                if(warTime==WarMaxTime){
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령하기 시작했습니다. (남은시간 : 10분)"));
                    warTime-=1;
                }else if(warTime<=5&warTime!=0){
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령중입니다. (남은시간 : "+warTime+"분)"));
                }else if(warTime==0){
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령성공하셨습니다."));
                    TaskId = WarHandler.TaskList.get(YAML.getLandLoc(c));
                    WarHandler.pvpList.remove(YAML.getLandLoc(c));
                    WarHandler.TaskList.remove(YAML.getLandLoc(c));
                    YAML.subLandTeam(c);
                    YAML.setLandTeam(attackTeam,c);
                    Bukkit.getScheduler().cancelTask(TaskId);
                }
            }
        }else Bukkit.getScheduler().cancelTask(WarHandler.TaskList.get(YAML.getLandLoc(c)));
    }
}
