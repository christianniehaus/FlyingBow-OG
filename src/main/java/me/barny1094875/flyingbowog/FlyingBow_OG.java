package me.barny1094875.flyingbowog;

import de.tr7zw.nbtinjector.NBTInjector;
import me.barny1094875.flyingbowog.Commands.CommandsTabCompleter;
import me.barny1094875.flyingbowog.Commands.GiveCommand;
import me.barny1094875.flyingbowog.Listeners.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;


public final class FlyingBow_OG extends JavaPlugin implements Listener {

    private static FlyingBow_OG plugin;


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // create the flying bow item
        ItemStack flyingBowItem = new ItemStack(Material.BOW);
        ItemMeta bowMeta = flyingBowItem.getItemMeta();
        bowMeta.displayName(Component.text("Flying Bow")
                .color(TextColor.color(0, 255, 255))
                .decorate(TextDecoration.ITALIC));
        bowMeta.lore(Collections.singletonList(Component.text("Shoot this bow to FLY")));
        flyingBowItem.setItemMeta(bowMeta);

        // adding a useless itemFlag to identify this as a flying bow
        // this was easier for me than adding an attribute, and no other bow should have
        // this flag
        flyingBowItem.addItemFlags(ItemFlag.HIDE_DYE);


        // add the recipe into the game
        ShapedRecipe flyingBowRecipe = new ShapedRecipe(new NamespacedKey(this, "flying_bow"),
                flyingBowItem);
        flyingBowRecipe.shape("sd ", "sed", "sd ");
        flyingBowRecipe.setIngredient('s', Material.STICK);
        flyingBowRecipe.setIngredient('e', Material.ELYTRA);
        flyingBowRecipe.setIngredient('d', Material.DIAMOND);


        Bukkit.addRecipe(flyingBowRecipe);

        getServer().getPluginManager().registerEvents(new OnEntityShootBowEvent(), this);
        getServer().getPluginManager().registerEvents(new OnGenericGameEvent(), this);
        getServer().getPluginManager().registerEvents(new OnEntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPrepareAnvilEvent(), this);
        getServer().getPluginManager().registerEvents(new OnAnvilUpdateResultEvent(), this);

        getCommand("flyingbow").setExecutor(new GiveCommand());
        getCommand("flyingbow").setTabCompleter(new CommandsTabCompleter());

    }

    public static FlyingBow_OG getPlugin(){
        return plugin;
    }
}
