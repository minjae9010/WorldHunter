package tk.mjsv.TimerHandler;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.mjsv.CmdHandler.WarHandler;
import tk.mjsv.WorldHunter;
import tk.mjsv.YAML;

public class warTimer implements Runnable{
    private static final int WarMaxTime = 2*60;
    int warTime = WarMaxTime;
    int TaskId;
    String attackTeam;
    String sheldTeam;
    Chunk c;
    int NoPlayerTime = 0;
    public warTimer(String team,Chunk c){
        this.attackTeam = team;
        this.sheldTeam = YAML.getLandTeam(c);
        this.c = c;
        Bukkit.broadcast(Component.text("\n" + WorldHunter.index + attackTeam + "팀이 "+sheldTeam+"의 (" + YAML.getLandLoc(c) + ")에 점령 선포를 하였습니다.\n"));
    }
    @Override
    public void run() {
        if(Timer.setting.equals("전쟁")){
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
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령하기 시작했습니다. (남은시간 : "+warTime/60+"분)\n"));
                }else if(warTime/60<=5&warTime%60==0&warTime!=0){
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령중입니다. (남은시간 : "+warTime/60+"분)\n"));
                }else if(warTime==0){
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령성공하셨습니다.\n"));
                    TaskId = WarHandler.TaskList.get(YAML.getLandLoc(c).toString());
                    WarHandler.pvpList.remove(YAML.getLandLoc(c).toString());
                    WarHandler.TaskList.remove(YAML.getLandLoc(c).toString());
                    YAML.subLandTeam(c);
                    YAML.setLandTeam(attackTeam,c);
                    Bukkit.getScheduler().cancelTask(TaskId);
                }
                warTime-=1;
            }else{
                if(warTime!=WarMaxTime)
                    Bukkit.broadcast(Component.text("\n"+ WorldHunter.index+attackTeam+"팀이 ("+YAML.getLandLoc(c)+") 지역을 점령실패하셨습니다.\n"));
                if(NoPlayerTime==180)
                    Bukkit.broadcast(Component.text("\n"+WorldHunter.index+"("+YAML.getLandLoc(c)+") 지역을 점령하기 위한 플레이어 수가 부족합니다\n"));
                NoPlayerTime++;
                warTime=WarMaxTime;
            }
        }else {
            TaskId = WarHandler.TaskList.get(YAML.getLandLoc(c).toString());
            Bukkit.getScheduler().cancelTask(TaskId);
        }
    }
}
