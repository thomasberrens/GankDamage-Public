package me.tokeee.gankdamage.particles;

import me.tokeee.gankdamage.GankEffect;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;

public class ParticleTrailBuilder {
    private int red;
    private int green;
    private int blue;

    private final Arrow arrow;
    private final ParticleTrailObject particleTrailObject;

    private Effect effect;

    private final ParticleTrailManager particleTrailManager;

    public ParticleTrailBuilder(Arrow arrow) {
        this.arrow = arrow;
        this.particleTrailObject = new ParticleTrailObject();
        particleTrailManager = GankEffect.getInstance().getParticleTrailManager();
    }

    public ParticleTrailBuilder setRed(int value) {
        this.red = value;
        return this;
    }

    public ParticleTrailBuilder setGreen(int value) {
        this.green = value;
        return this;
    }

    public ParticleTrailBuilder setBlue(int value) {
        this.blue = value;
        return this;
    }

    public ParticleTrailBuilder setEffect(Effect effect) {
        this.effect = effect;
        return this;
    }

    public ParticleTrailBuilder withMetadata(String metadata){
        if (!particleTrailManager.getMetadataColorMap().containsKey(metadata)) {
            this.setColor(ParticleColor.LIMEGREEN);
            return this;
        }

        final ParticleColor particleColor = particleTrailManager.getMetadataColorMap().get(metadata);

        this.setColor(particleColor);

        return this;
    }

    public ParticleTrailBuilder setColor(final ParticleColor particleColor) {
        setRed(particleColor.getRed());
        setGreen(particleColor.getGreen());
        setBlue(particleColor.getBlue());
        return this;
    }

    public ParticleTrailObject build() {
        particleTrailObject.setArrow(arrow);
        particleTrailObject.setRed(red);
        particleTrailObject.setGreen(green);
        particleTrailObject.setBlue(blue);
        particleTrailObject.setEffect(effect);

        particleTrailManager.getArrows().add(particleTrailObject);

        return particleTrailObject;
    }
}
