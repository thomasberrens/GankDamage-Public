package me.tokeee.gankdamage.events.gank;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GankStage extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public GankStage(Player player, int gankStage) {
        this.player = player;
        this.gankStage = gankStage;

    }

    private final @Getter int gankStage;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer(){ return this.player; }
}
