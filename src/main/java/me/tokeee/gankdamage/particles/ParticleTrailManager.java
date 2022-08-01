package me.tokeee.gankdamage.particles;

import lombok.Getter;
import me.tokeee.gankdamage.GankEffect;
import me.tokeee.gankdamage.potionbow.PotionBowValue;
import me.tokeee.gankdamage.utils.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParticleTrailManager {
    private final @Getter List<ParticleTrailObject> arrows;
    private final @Getter BukkitRunnable setupTask;
    private final @Getter List<ParticleTrailObject> removalCache;

    private @Getter final HashMap<String, ParticleColor> metadataColorMap = new HashMap<>();

    public ParticleTrailManager(){
        this.arrows = new ArrayList<>();
        this.removalCache = new ArrayList<>();
        this.setupTask = particleTrailTask();
        this.setupTask.runTaskTimer(GankEffect.getInstance(), 0L, 0L);

        initializeMetadataColors();

    }

    private void initializeMetadataColors() {
        metadataColorMap.put(PotionBowValue.POISON, ParticleColor.LIMEGREEN);
        metadataColorMap.put(PotionBowValue.WITHER, ParticleColor.GRAY);
    }

    private BukkitRunnable particleTrailTask() {
        final int timeAfterRemoval = 7; // 7 sec

        return new BukkitRunnable() {
            @Override
            public void run() {

                if (!removalCache.isEmpty()) {
                    removalCache.forEach(arrows::remove);
                }

                if (arrows.isEmpty()) return;
                arrows.forEach((particleTrailObject) -> {

                    final Arrow arrow = particleTrailObject.getArrow();
                    if (arrow == null){
                        removeParticleTrail(particleTrailObject);
                        throw new RuntimeException("Particle trail arrow is null, ParticleTrailmanager");
                    }

                    final Location loc = arrow.getLocation();
                    ParticleUtil.runColoredParticles(loc, 1, particleTrailObject.getRed(), particleTrailObject.getGreen(),
                            particleTrailObject.getBlue(), particleTrailObject.getEffect());

                    boolean afterTime = particleTrailObject.getTimeThrown() + 1000 * timeAfterRemoval < System.currentTimeMillis();

                    if (afterTime || !isArrowValid(arrow)) {
                        removeParticleTrail(particleTrailObject);
                    }
                });
            }
        };
    }

    private boolean isArrowValid(final Arrow arrow) {
        return !arrow.isDead() && arrow.isValid() && !arrow.isOnGround();
    }

    private void removeParticleTrail(final ParticleTrailObject particleTrailObject) {
        removalCache.add(particleTrailObject);
    }
}
