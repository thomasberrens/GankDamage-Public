package me.tokeee.gankdamage.gankeffect;

import me.tokeee.gankdamage.events.gank.GankStage;
import me.tokeee.gankdamage.particles.ParticleColor;
import me.tokeee.gankdamage.utils.ParticleUtil;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class GankParticles implements Listener {

    private final HashMap<Integer, ParticleColor> gankParticleColors = new HashMap<>();

    public GankParticles() {
        gankParticleColors.put(1, ParticleColor.YELLOW);
        gankParticleColors.put(2, ParticleColor.ORANGE);
        gankParticleColors.put(3, ParticleColor.RED);
    }


    @EventHandler
    private void Stage(GankStage event){
        final Player player = event.getPlayer();

        final ParticleColor particleColor = gankParticleColors.get(event.getGankStage());

        assert particleColor != null;

        ParticleUtil.runColoredParticlesCubeAtPlayer(player, 10, particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue(),
                Effect.COLOURED_DUST);

    }
}
