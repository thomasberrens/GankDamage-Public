package me.tokeee.gankdamage.particles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;

@Getter @Setter
public class ParticleTrailObject {
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private Arrow arrow;
    private long timeThrown;
    private Effect effect;

    public ParticleTrailObject(){
        this.timeThrown = System.currentTimeMillis();
    }
}
