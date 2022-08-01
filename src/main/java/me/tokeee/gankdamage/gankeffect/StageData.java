package me.tokeee.gankdamage.gankeffect;

import lombok.Getter;

public class StageData {

    private final @Getter int playerCount;
    private final @Getter double damage;
    private final @Getter int currentStage;

    public StageData(int playerCount, double damage, int stageCount){
        this.playerCount = playerCount;
        this.damage = damage;
        currentStage = stageCount;
    }
}
