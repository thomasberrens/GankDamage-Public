package me.tokeee.gankdamage.gankeffect;

import lombok.Getter;
import lombok.Setter;
import me.tokeee.gankdamage.GankEffect;
import me.tokeee.gankdamage.events.EventExaggerator;
import me.tokeee.gankdamage.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class GankDamage implements Listener {
    private final @Getter Map<UUID, GPlayer> gankMap = new HashMap<UUID, GPlayer>();

    private @Getter boolean enabled = false;

    private final List<StageData> stageDataList = new ArrayList<>();

    public GankDamage(){
        stageDataList.add(new StageData(GankValue.getPlayerCountStage1(), GankValue.getStage1(), 1));
        stageDataList.add(new StageData(GankValue.getPlayerCountStage2(), GankValue.getStage2(), 2));
        stageDataList.add(new StageData(GankValue.getPlayerCountStage3(), GankValue.getStage3(), 3));

    }

    private @Getter @Setter int maxDiff = GankEffect.getInstance().getConfig().getInt("gank-damage.ThresholdInSeconds");;

    private final EventExaggerator eventExaggerator = GankEffect.getInstance().getEventExaggerator();

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent event){
        if (!enabled) return;

        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;

        final Player victim = (Player) event.getEntity();

        if (!GankEffect.getInstance().getRegionManager().isLocationInGankRegion(victim.getLocation())) return;

        final Player damager = (Player) event.getDamager();

        final UUID victimUUID = victim.getUniqueId();
        final UUID damagerUUID = damager.getUniqueId();

        final long currentTime = System.currentTimeMillis();
        final double damage = event.getDamage();

        if (!gankMap.containsKey(victimUUID)){

            final GPlayer gPlayer = new GPlayer();

            gankMap.put(victimUUID, gPlayer);
            gPlayer.addNewDamager(damager, currentTime);

            return;
        }

        final GPlayer gPlayer = gankMap.get(victimUUID);

        // TODO make the variable names more readable
        final long lastTimeStamp = gPlayer.getLastTimeStamp();
        final long lowestTimeStamp = gPlayer.getLowestTimeStamp();

        final long diffInSeconds = TimeUtil.secondDifference(lastTimeStamp, currentTime);
        final long diffBetweenHitsInSecs = TimeUtil.secondDifference(lowestTimeStamp, currentTime);


        if (diffInSeconds > maxDiff){
            gankMap.remove(victimUUID);
            gPlayer.clearMap();
            return;
        }

        if (diffBetweenHitsInSecs > maxDiff){
           final Player player = Bukkit.getPlayer(gPlayer.getLateHitter());

           gPlayer.removeDamagerByPlayer(player);
        }

        final int damagerCount = gPlayer.getDamagerCount();
        final double extraDamage = calculateExtraDamage(damagerCount, damage, victim);

        event.setDamage(damage + extraDamage);

        gPlayer.addNewDamager(damager, currentTime);
    }

    public void toggleGankmode(CommandSender sender){
        this.enabled = !this.enabled;

        if(!this.enabled) {
            sender.sendMessage(ChatColor.GOLD + "Gank Mode = " + ChatColor.RED+ "DISABLED");
            gankMap.values().forEach(GPlayer::clearMap);
            gankMap.clear();
        } else {
            sender.sendMessage(ChatColor.GOLD + "Gank Mode = " + ChatColor.GREEN + "ENABLED");
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event){
        removePlayerFromGankMap(event.getEntity());
    }

    @EventHandler
    private void onLogout(PlayerQuitEvent event){
        removePlayerFromGankMap(event.getPlayer());
    }

    private void removePlayerFromGankMap(Player player){
        if (!gankMap.containsKey(player.getUniqueId())) return;
        final GPlayer gPlayer = gankMap.get(player.getUniqueId());

        gPlayer.clearMap();
        gankMap.remove(player.getUniqueId());
    }

    private double calculateExtraDamage(int damagerCount, double damage, Player victim){
        double extraDamage = 0;
        if (damagerCount > GankValue.getPlayerCountStage3()){
            final GPlayer gPlayer = gankMap.get(victim.getUniqueId());
            final Player player = Bukkit.getPlayer(gPlayer.getLateHitter());

            gPlayer.removeDamagerByPlayer(player);
            damagerCount = GankValue.getPlayerCountStage3();
        }

        for (final StageData stageData : stageDataList) {
            if (stageData.getPlayerCount() == damagerCount) {
                extraDamage = damage * stageData.getDamage();
                eventExaggerator.callGankStage(victim, stageData.getCurrentStage());
                break;
            }
        }

        return extraDamage;
    }
}
