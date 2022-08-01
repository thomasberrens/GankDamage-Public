package me.tokeee.gankdamage.commands;

import me.tokeee.gankdamage.GankEffect;
import me.tokeee.gankdamage.gankeffect.GankDamage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleGank implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() || sender.hasPermission("gankdamage.admin")) {
            GankEffect.getInstance().getGankDamage().toggleGankmode(sender);
            return true;
        }
        return false;
    }
}
