package me.tokeee.gankdamage.gankeffect;

import lombok.Getter;
import lombok.Setter;
import me.tokeee.gankdamage.GankEffect;

public class GankValue {

    private static final double configStage1 = GankEffect.getInstance().getConfig().getDouble("gank-stage-1.damage");
    private static final double configStage2 = GankEffect.getInstance().getConfig().getDouble("gank-stage-2.damage");
    private static final double configStage3 = GankEffect.getInstance().getConfig().getDouble("gank-stage-3.damage");

    private @Getter static final int playerCountStage1 = GankEffect.getInstance().getConfig().getInt("gank-stage-1.playerCount");
    private @Getter static final int playerCountStage2 = GankEffect.getInstance().getConfig().getInt("gank-stage-2.playerCount");
    private @Getter static final int playerCountStage3 = GankEffect.getInstance().getConfig().getInt("gank-stage-3.playerCount");

    public @Getter @Setter static double stage1 = configStage1;
    public @Getter @Setter static double stage2 = configStage2;
    public @Getter @Setter static double stage3 = configStage3;
}
