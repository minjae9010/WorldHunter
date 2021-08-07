package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tk.mjsv.TimerHandler.Timer;
import tk.mjsv.TimerHandler.TimerData;
import tk.mjsv.WorldHunter;

import java.util.Arrays;
import java.util.List;

public class TimerHandler {
    private static final String index = WorldHunter.index;
    private static final String label = "타이머";
    public static WorldHunter pl = WorldHunter.getPlugin(WorldHunter.class);

    public static int seconds = 0;
    public static int Pseconds = 0;
    public static int Wseconds = 0;
    public static boolean TimerStop = false;
    public static String Tset = "없음";

    public static void Command(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                if (args[0].equals("설정")) {
                    sender.sendMessage(index + " §e/" + label + "설정 <내용> <분> <초>");
                }
                if (args[0].equals("시작")) {
                    if (!(Pseconds == 0) & !(Wseconds == 0)) {
                        if (Timer.set) {
                            sender.sendMessage(index + "이미 타이머가 시작되었습니다");
                        } else {
                            new TimerData().updateData(Integer.toString(Pseconds), Integer.toString(Wseconds));
                            sender.sendMessage(index + "타이머가 시작됩니다");
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Timer(), 0, 20);
                        }
                    } else {
                        if (Pseconds == 0) {
                            sender.sendMessage(index + "평화시간이 설정되지 않았습니다.");
                        }
                        if (Wseconds == 0) {
                            sender.sendMessage(index + "전쟁시간이 설정되지 않았습니다.");
                        }
                    }
                }
                if (args[0].equals("종료")) {
                    if (Timer.set) {
                        sender.sendMessage(index + "타이머가 곧 종료됩니다");
                        TimerStop = true;
                    } else {
                        sender.sendMessage(index + "타이머가 시작되지 않았습니다");
                    }
                }
                if (args[0].equals("자동")) {
                    Pseconds = 7200;
                    Wseconds = 3600;
                    sender.sendMessage(index + "평화시간과 전쟁 시간이 자동으로 설정 되었습니다.");
                }
                if (args[0].equals("다시시작")) {
                    if (Timer.set) {
                        sender.sendMessage(index + "이미 타이머가 시작되었습니다");
                    } else {
                        Pseconds = Integer.parseInt(new TimerData().loadData().substring(0, new TimerData().loadData().indexOf("|")));
                        Wseconds = Integer.parseInt(new TimerData().loadData().substring(new TimerData().loadData().indexOf("|") + 1));
                        sender.sendMessage(index + "타이머가 시작됩니다");
                        Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Timer(), 0, 20);
                    }
                }
            } else if (args.length >= 2 & args.length <= 6) {
                if (args[0].equals("설정")) {
                    if (args.length == 2) {
                        Tset = args[1];
                        sender.sendMessage(index + "시간을 정해주세요");
                    } else if (args.length == 3) {
                        Tset = args[1];
                        seconds = Integer.parseInt(args[2]) * 60;
                        sender.sendMessage(index + Tset + " " + seconds / 60 + "분 이 설정되었습니다");
                    } else if (args.length == 4) {
                        Tset = args[1];
                        seconds = Integer.parseInt(args[2]) * 60 + Integer.parseInt(args[3]);
                        sender.sendMessage(index + Tset + " " + seconds / 60 + "분 " + seconds % 60 + "초가 설정되었습니다");
                    }
                    if (!Tset.equals("없음"))
                        switch (Tset) {
                            case "전쟁":
                                Wseconds = seconds;
                                break;
                            case "평화":
                                Pseconds = seconds;
                                break;
                            default:
                                sender.sendMessage(index + "없는 타입입니다");
                        }
                }
                if (args[0].equals("추가")) {
                    if (args.length == 2) {
                        Tset = args[1];
                        sender.sendMessage(index + "시간을 정해주세요");
                    } else if (args.length == 3) {
                        Tset = args[1];
                        seconds = Integer.parseInt(args[2]) * 60;
                        sender.sendMessage(index + Tset + " " + seconds / 60 + "분 이 추가되었습니다");
                    } else if (args.length == 4) {
                        Tset = args[1];
                        seconds = Integer.parseInt(args[2]) * 60 + Integer.parseInt(args[3]);
                        sender.sendMessage(index + Tset + " " + seconds / 60 + "분 " + seconds % 60 + "초가 추가되었습니다");
                    }
                    if (!Tset.equals("없음"))
                        switch (Tset) {
                            case "전쟁":
                                if (Tset.equals(Timer.setting)) {
                                    Timer.count += seconds;
                                    Bukkit.broadcastMessage(index + "관리자가 전쟁 시간을 " + seconds + "초 추가 하였습니다.");
                                } else sender.sendMessage(index + "현재는 전쟁 시간이 아닙니다.");
                                break;
                            case "평화":
                                if (Tset.equals(Timer.setting)) {
                                    Timer.count += seconds;
                                    Bukkit.broadcastMessage(index + "관리자가 평화 시간을 " + seconds + "초 추가 하였습니다.");
                                } else sender.sendMessage(index + "현재는 평화 시간이 아닙니다.");
                                break;
                            default:
                                sender.sendMessage(index + "없는 타입입니다");
                        }
                }
            } else {
                sender.sendMessage(index + "§e/" + label + " 설정 <타입> <분> <초>");
                sender.sendMessage(index + "§e/" + label + " 자동");
                sender.sendMessage(index + "§e/" + label + " 시작");
                sender.sendMessage(index + "§e/" + label + " 종료");
                sender.sendMessage(index + "§e/" + label + " 다시시작");
                sender.sendMessage(index + "§e/" + label + " 추가 <타입> <분> <초>");
            }
        }
    }

    public static List<String> TabExcutor(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                return Arrays.asList("설정", "시작", "종료", "자동", "다시시작", "추가");
            } else if (args.length == 2) {
                if (args[0].equals("설정") || args[0].equals("추가")) return Arrays.asList("평화", "전쟁");
            }
        }
        return null;
    }
}
