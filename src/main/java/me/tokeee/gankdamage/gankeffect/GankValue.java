package me.tokeee.gankdamage.gankeffect;

import lombok.Getter;
import lombok.Setter;
import me.tokeee.gankdamage.GankEffect;

public class GankValue {

    private @Getter static final double stage1 = GankEffect.getInstance().getConfig().getDouble("gank-stage-1.damage");
    private @Getter static final double stage2 = GankEffect.getInstance().getConfig().getDouble("gank-stage-2.damage");
    private @Getter static final double stage3 = GankEffect.getInstance().getConfig().getDouble("gank-stage-3.damage");

    private @Getter static final int playerCountStage1 = GankEffect.getInstance().getConfig().getInt("gank-stage-1.playerCount");
    private @Getter static final int playerCountStage2 = GankEffect.getInstance().getConfig().getInt("gank-stage-2.playerCount");
    private @Getter static final int playerCountStage3 = GankEffect.getInstance().getConfig().getInt("gank-stage-3.playerCount");
}
