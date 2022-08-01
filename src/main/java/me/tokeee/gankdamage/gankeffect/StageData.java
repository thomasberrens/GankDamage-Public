package me.tokeee.gankdamage.gankeffect;

import lombok.Getter;

public class StageData {

    private @Getter int playerCount;
    private @Getter double damage;
    private @Getter int currentStage;

    public StageData(int playerCount, double damage, int stageCount){
        this.playerCount = playerCount;
        this.damage = damage;
        currentStage = stageCount;
    }
}
