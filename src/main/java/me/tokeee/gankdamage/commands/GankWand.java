package me.tokeee.gankdamage.commands;

import me.tokeee.gankdamage.GankEffect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GankWand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only a player can use this command.");
            return true;
        }

        if (!commandSender.hasPermission("gankdamage.admin") || !commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        final Player player = (Player) commandSender;

        if (strings.length == 0 || GankEffect.getInstance().getRegionManager().getRegionDataMap().containsKey(strings[0])){
            player.sendMessage(ChatColor.RED + "You have to give a region name (that doesn't exist). For example: /gankwand gankregion");
            return true;
        }

        if(player.getInventory().firstEmpty() == -1){
            player.sendMessage(ChatColor.RED + "Your inventory is full!");
            return true;
        }

        player.getInventory().addItem(wandItem(strings[0]));
        player.updateInventory();

        commandSender.sendMessage(ChatColor.GREEN + "Succesfully gave gankwand for region: " + strings[0]);

        return true;
    }

    private ItemStack wandItem(final String name){
        final ItemStack itemStack = new ItemStack(Material.DIAMOND_AXE);

        final ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "GankRegion Wand");

        final ArrayList<String> lore = new ArrayList<>();

        lore.add(name);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);


        return itemStack;
    }
}
