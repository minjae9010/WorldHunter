package tk.mjsv.TimerHandler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class TimerData {

    private static final File time = new File("GameData/timerData.yml");
    public static FileConfiguration timeData = YamlConfiguration.loadConfiguration(time);

    public String loadData() {
        StringBuilder str = new StringBuilder();
        try {
            if (!time.exists()) {
                timeData.save(time);
            }
            int nBuffer;

            BufferedReader br = new BufferedReader(new FileReader(time));
            while ((nBuffer = br.read()) != -1) {
                str.append((char) nBuffer);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public void updateData(String psec, String wsec) {
        try {
            if (!time.exists()) {
                timeData.save(time);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(time));
            String times = psec + "|" + wsec;
            bw.write(times, 0, times.length());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
