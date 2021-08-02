package tk.mjsv.recipes;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class RecipeFrunce {
    public static void enableFurnceRecipe(Server s) {
        s.addRecipe(IronOre());
        s.addRecipe(GoldOre());
    }
    private static FurnaceRecipe IronOre(){
        ItemStack Iron = new ItemStack(Material.IRON_NUGGET);
        FurnaceRecipe IronOrepice = new FurnaceRecipe(Iron,Material.RAW_IRON);
        IronOrepice.setExperience((float) 0.7);
        return IronOrepice;
    }
    private static FurnaceRecipe GoldOre(){
        ItemStack result = new ItemStack(Material.GOLD_NUGGET,2);
        FurnaceRecipe recipe = new FurnaceRecipe(result,Material.RAW_GOLD);
        recipe.setExperience(1);
        return recipe;
    }
}
