package me.tokeee.gankdamage.regions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;


@Getter @AllArgsConstructor @Setter
public class RegionRecord {
    private final Location pointA;
    private final Location pointB;
}
