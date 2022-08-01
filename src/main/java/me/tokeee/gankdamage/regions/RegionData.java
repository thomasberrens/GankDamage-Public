package me.tokeee.gankdamage.regions;

import lombok.Getter;
import org.bukkit.Location;

import java.io.Serializable;
import org.bukkit.util.Vector;

public class RegionData implements Serializable {
 //   private @Getter final Vector pointA;
 //   private @Getter final Vector pointB;
    private @Getter final String worldName;
    private @Getter final double aX;
    private @Getter final double aY;
    private @Getter final double aZ;
    private @Getter final double bX;
    private @Getter final double bY;
    private @Getter final double bZ;

    public RegionData(final Location pointA, final Location pointB, final String worldName) {

        this.aX = pointA.getX();
        this.aY = pointA.getY();
        this.aZ = pointA.getZ();

        this.bX = pointB.getX();
        this.bY = pointB.getY();
        this.bZ = pointB.getZ();

       // this.pointA = pointA.toVector();
     //   this.pointB = pointB.toVector();

        this.worldName = worldName;

    }
}
