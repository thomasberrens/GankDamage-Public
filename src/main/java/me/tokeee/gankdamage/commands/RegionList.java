package me.tokeee.gankdamage.commands;

import me.tokeee.gankdamage.GankEffect;
import me.tokeee.gankdamage.regions.RegionData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RegionList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("gankdamage.admin") || !commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        for (final String region : GankEffect.getInstance().getRegionManager().getRegionDataMap().keySet()) {
            final RegionData regionData = GankEffect.getInstance().getRegionManager().getRegionDataMap().get(region);
            commandSender.sendMessage(ChatColor.YELLOW + "Region Name: " + region + "." + ChatColor.BLUE + " Coordinates (point A): " + "X: " + regionData.getAX() + " Y: " + regionData.getAY() + " Z: " + regionData.getAZ());
        }
        return true;
    }
}
