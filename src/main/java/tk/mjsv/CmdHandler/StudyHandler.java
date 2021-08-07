package tk.mjsv.CmdHandler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tk.mjsv.utile.StudyData;
import tk.mjsv.WorldHunter;

import java.util.Objects;

public class StudyHandler implements Listener {
    private static final String index = WorldHunter.index;
    private static Inventory main;
    private static Inventory iron;
    private static Inventory gold;
    private static Inventory diamond;

    public static void Command(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        main = Bukkit.createInventory(null, 27, "연구");
        iron = Bukkit.createInventory(null, 27, "철광석 연구");
        gold = Bukkit.createInventory(null, 27, "금광석 연구");
        diamond = Bukkit.createInventory(null, 27, "다이아몬드 원석 연구");

        main.setItem(11, new ItemStack(Material.IRON_ORE));
        main.setItem(13, new ItemStack(Material.GOLD_ORE));
        main.setItem(15, new ItemStack(Material.DIAMOND_ORE));

        iron.setItem(11, new ItemStack(Material.IRON_AXE));
        iron.setItem(13, new ItemStack(Material.IRON_PICKAXE));
        iron.setItem(15, new ItemStack(Material.IRON_SWORD));

        gold.setItem(11, new ItemStack(Material.GOLDEN_AXE));
        gold.setItem(13, new ItemStack(Material.GOLDEN_PICKAXE));
        gold.setItem(15, new ItemStack(Material.GOLDEN_SWORD));

        diamond.setItem(11, new ItemStack(Material.DIAMOND_AXE));
        diamond.setItem(13, new ItemStack(Material.DIAMOND_PICKAXE));
        diamond.setItem(15, new ItemStack(Material.DIAMOND_SWORD));

        p.openInventory(main);
    }

    @EventHandler
    public void onClicked(InventoryClickEvent e) {

        Inventory inv = e.getClickedInventory();
        String data = "";

        if (e.getCurrentItem() != null) {
            if (inv.getClass().getName().equals(main.getClass().getName())) {
                if (e.getCurrentItem().getType() == Material.IRON_ORE) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(iron);
                } else if (e.getCurrentItem().getType() == Material.GOLD_ORE) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(gold);
                } else if (e.getCurrentItem().getType() == Material.DIAMOND_ORE) {
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(diamond);
                }
            }
            if (inv.getClass().getName().equals(iron.getClass().getName())) {
                if (Objects.requireNonNull(e.getCurrentItem()).getType() == Material.IRON_AXE) {
                    e.setCancelled(true);
                    data = new StudyData().findData(e.getWhoClicked().getName(), "iron_axe");
                    int index1 = data.indexOf("=");
                    if (Boolean.parseBoolean(data.substring(index1 + 1))) {
                        e.getWhoClicked().sendMessage(index + "호오 철도끼를 연구 하셨군요? 철도끼를 드리도록 하죠.");
                        e.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_AXE));
                    } else {
                        e.getWhoClicked().sendMessage(index + "음.. 연구를 하지 않았군.. 지금은 개발중인 상태니 무료로 연구 해주겠네.");
                        new StudyData().updateData(e.getWhoClicked().getName(), "iron_axe", "true", false);
                    }
                }
            }
        }


    }
}
