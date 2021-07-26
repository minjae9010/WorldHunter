package tk.mjsv;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class YAML {
    public static FileConfiguration teamData;
    public static FileConfiguration ChunkData;
    private static final File team = new File("GameData/teamData.yml");
    private static final File Chunk = new File("GameData/ChunkData.yml");
    public static HashMap<String, ArrayList<OfflinePlayer>> teamHash = new HashMap<>();
    public static List<String> teamList = new ArrayList<>();
    public static HashMap<String, OfflinePlayer> teamOwner = new HashMap<>();
    public static List<String> ChunkList = new ArrayList<>();

    public static String loadData() {
        teamData = YamlConfiguration.loadConfiguration(team);
        StringBuilder str = new StringBuilder();
        try {
            if (!team.exists()) {
                teamData.save(team);
            }
            int nBuffer;

            BufferedReader br = new BufferedReader(new FileReader(team));
            while ((nBuffer = br.read()) != -1) {
                str.append((char) nBuffer);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public static ArrayList<OfflinePlayer> getTeamList(String s) {
        ArrayList<OfflinePlayer> tl;
        if (teamHash.containsKey(s)) {
            tl = teamHash.get(s);
            return tl;
        } else {
            return null;
        }
    }

    public static String getPlayerTeam(Player p) throws IOException {
        String playerTeam;

        String sLine;

        BufferedReader br = new BufferedReader(new FileReader(team));
        while ((sLine = br.readLine()) != null) {
            if (sLine.contains(p.getUniqueId().toString())) {
                int index = sLine.indexOf(":");
                playerTeam = sLine.substring(0, index);
                br.close();
                return playerTeam;
            }
        }
        br.close();

        return null;
    }

    public static void addTeamList(String s, OfflinePlayer p, String data, Player sender) throws IOException {
        if (getPlayerTeam((Player) p) == null) {
            String sLine;

            BufferedReader br = new BufferedReader(new FileReader(team));
            while ((sLine = br.readLine()) != null) {
                if (sLine.contains(s)) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(team));
                    int tmpIndex = sLine.indexOf("{");
                    String tmp1 = sLine.substring(0, tmpIndex);
                    String tmp2 = sLine.substring(tmpIndex + 1);
                    if (tmp2.contains(",")) {
                        int commaIndex = tmp2.lastIndexOf(",");
                        String tmp3 = tmp2.substring(0, commaIndex + 1);
                        String tmp4 = p.getUniqueId() + ",";
                        tmp3 += tmp4 + "}";
                        tmp2 = "{" + tmp3;
                    } else {
                        tmp2 = "{" + p.getUniqueId() + ",}";
                    }

                    tmp1 += tmp2;

                    int index = data.indexOf(s);

                    if (index <= 1) {
                        String tmp4 = data.substring(index);
                        int index2 = tmp4.indexOf("\n");
                        String done;
                        if (tmp4.substring(index2).length() <= 1) done = tmp1 + "\n";
                        else done = tmp1 + tmp4.substring(index2);
                        bw.write(done, 0, done.length());
                    } else {
                        String one = data.substring(0, index);
                        String temp = data.substring(index);
                        int index2 = temp.indexOf('\n');
                        String two;
                        if (temp.substring(index2).length() <= 1) two = "\n";
                        else two = temp.substring(index2);
                        String done = one + tmp1 + two;
                        bw.write(done, 0, done.length());
                    }
                    bw.flush();
                    bw.close();
                }
            }
            sender.sendMessage(WorldHunter.index + p.getName() + "님이 초대 되었습니다.");
        }
        sender.sendMessage(WorldHunter.index + p.getName() + "님은 이미 팀에 소속 되어 있습니다.");
    }

    public static void subTeamList(String s, OfflinePlayer p, String data) throws IOException {
        String sLine;

        BufferedReader br = new BufferedReader(new FileReader(team));
        while ((sLine = br.readLine()) != null) {
            if (sLine.contains(s)) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(team));
                String tmp1 = sLine.replace(p.getUniqueId() + ",", "");

                int index = data.indexOf(s);

                if (index <= 1) {
                    String tmp4 = data.substring(index);
                    int index2 = tmp4.indexOf("\n");
                    String done;
                    if (tmp4.substring(index2).length() <= 1) done = tmp1 + "\n";
                    else done = tmp1 + tmp4.substring(index2);
                    bw.write(done, 0, done.length());
                } else {
                    String one = data.substring(0, index);
                    String temp = data.substring(index);
                    int index2 = temp.indexOf('\n');
                    String two;
                    if (temp.substring(index2).length() <= 1) two = "\n";
                    else two = temp.substring(index2);
                    String done = one + tmp1 + two;
                    bw.write(done, 0, done.length());
                }
                bw.flush();
                bw.close();
            }
        }
    }

    public static void createTeam(String s, OfflinePlayer p, String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(team));
        String done = data + s + ":" + p.getUniqueId() + "{}\n";
        bw.write(done, 0, done.length());
        bw.flush();
        bw.close();
    }

    public static void removeTeam(String s, String data) throws IOException {
        String sLine;

        BufferedReader br = new BufferedReader(new FileReader(team));
        while ((sLine = br.readLine()) != null) {
            if (sLine.contains(s)) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(team));
                int index = data.indexOf(s);

                if (index == 0) {
                    int index2 = data.indexOf("\n");
                    String done;
                    if (data.substring(index2).length() <= 1) done = "";
                    else done = data.substring(index2 + 1);
                    bw.write(done, 0, done.length());
                } else {
                    String one = data.substring(0, index);
                    String temp = data.substring(0, index + 1);
                    int index2 = temp.indexOf('\n');
                    String two;
                    if (temp.substring(index2).length() <= 1) two = "";
                    else two = temp.substring(index2);
                    String done = one + two;
                    bw.write(done, 0, done.length());
                }
                bw.flush();
                bw.close();
            }
        }
    }

    public static boolean isValidTeam(String s) throws IOException {
        String sLine;

        BufferedReader br = new BufferedReader(new FileReader(team));
        while ((sLine = br.readLine()) != null) {
            if (sLine.contains(s)) {
                return true;
            }
        }
        br.close();

        return false;
    }

    public static Player getOwner(String s) throws IOException {
        Player owner;

        String sLine;

        BufferedReader br = new BufferedReader(new FileReader(team));
        while ((sLine = br.readLine()) != null) {
            if (sLine.contains(s)) {
                int index = sLine.indexOf(":");
                int index2 = sLine.indexOf("{");
                owner = Bukkit.getPlayer(UUID.fromString(sLine.substring(index + 1, index2)));
                br.close();
                return owner;
            }
        }
        br.close();

        return null;
    }

    public static void setOwner(String s, Player p, String data, Player sender, Player oldOwner) throws IOException {
        if (oldOwner == p) sender.sendMessage(WorldHunter.index + "해당 플레이어는 이미 팀장입니다.");
        else {
            String sLine;

            BufferedReader br = new BufferedReader(new FileReader(team));
            while ((sLine = br.readLine()) != null) {
                if (sLine.contains(s)) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(team));
                    int tmpIndex = sLine.indexOf(":");
                    int tmpIndex2 = sLine.indexOf("{");
                    String tmp1 = sLine.substring(0, tmpIndex);
                    tmp1 += p.getUniqueId();
                    String tmp2 = sLine.substring(tmpIndex2);

                    tmp2 = tmp2.replace(p.getUniqueId() + ",", oldOwner + ",");

                    tmp1 += tmp2;

                    int index = data.indexOf(s);

                    if (index <= 1) {
                        String tmp4 = data.substring(index);
                        int index2 = tmp4.indexOf("\n");
                        String done;
                        if (tmp4.substring(index2).length() <= 1) done = tmp1 + "\n";
                        else done = tmp1 + tmp4.substring(index2);
                        bw.write(done, 0, done.length());
                    } else {
                        String one = data.substring(0, index);
                        String temp = data.substring(index);
                        int index2 = temp.indexOf('\n');
                        String two;
                        if (temp.substring(index2).length() <= 1) two = "\n";
                        else two = temp.substring(index2);
                        String done = one + tmp1 + two;
                        bw.write(done, 0, done.length());
                    }
                    bw.flush();
                    bw.close();
                }
            }
            sender.sendMessage(WorldHunter.index + p.getName() + "님이 새로운 팀장이 되었습니다.");
        }
    }

}
