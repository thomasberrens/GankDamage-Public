package me.tokeee.gankdamage.events;

import me.tokeee.gankdamage.events.gank.GankStage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EventManager {

    public void callGankStage(Player player, int stageCount){
        final GankStage stage = new GankStage(player, stageCount);
        Bukkit.getPluginManager().callEvent(stage);
    }

}
