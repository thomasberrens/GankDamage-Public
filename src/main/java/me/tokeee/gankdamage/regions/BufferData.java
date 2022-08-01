package me.tokeee.gankdamage.regions;

import lombok.Getter;
import org.bukkit.Location;

public class BufferData {
    private @Getter final Location pointA;

    public BufferData(final Location pointA) {
        this.pointA = pointA;
    }
}
