package tk.mjsv.StudyHandler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class StudyData {
    private static final File study = new File("GameData/studyData.yml");
    public static FileConfiguration studyData = YamlConfiguration.loadConfiguration(study);

    public String loadData() {
        StringBuilder str = new StringBuilder();
        try {
            if (!study.exists()) {
                studyData.save(study);
            }
            int nBuffer;

            BufferedReader br = new BufferedReader(new FileReader(study));
            while ((nBuffer = br.read()) != -1) {
                str.append((char) nBuffer);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public String findData(String player, String item) {
        StringBuilder str = new StringBuilder();
        try {
            if (!study.exists()) {
                studyData.save(study);
            }
            String sLine = null;

            BufferedReader br = new BufferedReader(new FileReader(study));
            while ((sLine = br.readLine()) != null) {
                if (sLine.contains(player + "|" + item)) {
                    return sLine;
                }
            }
            br.close();
            updateData(player, item, "false", true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return player + "|" + item + "=false";
    }

    public void updateData(String player, String item, String isStudied, boolean isNew) {
        try {
            if (!study.exists()) {
                studyData.save(study);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(study));
            String study = player + "|" + item + "=" + isStudied + "\n";
            int index = loadData().indexOf(player + "|" + item);

            if (isNew || index <= 0) {
                String done = loadData() + study;
                bw.write(done, 0, done.length());
            } else {
                String one = loadData().substring(0, index);
                String temp = loadData().substring(0, index + 1);
                int index2 = temp.indexOf('\n');
                String two = temp.substring(index2);
                String done = one + study + two;
                bw.write(done, 0, done.length());
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
