package me.tokeee.gankdamage.commands;

import me.tokeee.gankdamage.GankEffect;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionRemove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("gankdamage.admin") || !commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }


        if (strings.length == 0 || !GankEffect.getInstance().getRegionManager().getRegionDataMap().containsKey(strings[0])){
            commandSender.sendMessage(ChatColor.RED + "You have to give a region name (that does exist). It is case sensitive! For example: /regionremove gankRegIoN");
            return true;
        }

        GankEffect.getInstance().getRegionManager().getRegionDataMap().remove(strings[0]);
        commandSender.sendMessage(ChatColor.GREEN + "Succesfully removed region: " + strings[0]);
        return true;
    }
}
