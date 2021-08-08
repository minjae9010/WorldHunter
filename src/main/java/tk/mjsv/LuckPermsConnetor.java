package tk.mjsv;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LuckPermsConnetor {
    private static LuckPerms api = Bukkit.getServer().getServicesManager().load(LuckPerms.class);

    public static String getPlayerGroup(String player){
        Player p = Bukkit.getPlayer(player);
        for (Group group:api.getGroupManager().getLoadedGroups()){
            if(p.hasPermission("group."+group.getName())){
                return group.getName();
            }
        }
        return null;
    }
    public static void setTeamPrefix(String group,String prefix){
        Group g = api.getGroupManager().getGroup(group);
        PrefixNode preNode = PrefixNode.builder(YAML.getTeamPrefix(group),100).build();
        PrefixNode node = PrefixNode.builder(prefix,100).build();
        g.data().clear(NodeType.PREFIX::matches);
        g.data().add(node);
        api.getGroupManager().saveGroup(g);
    }
    public static void addPlayerTeam(String team,String player){
        UUID uuid = Bukkit.getPlayer(player).getUniqueId();
        Group group = api.getGroupManager().getGroup(team);
        api.getUserManager().modifyUser(uuid,user -> {
            user.data().clear(NodeType.INHERITANCE::matches);
            Node node = InheritanceNode.builder(group).build();
            user.data().add(node);
        });

    }
    public static void createTeam(String team){
        api.getGroupManager().createAndLoadGroup(team);

    }

}
