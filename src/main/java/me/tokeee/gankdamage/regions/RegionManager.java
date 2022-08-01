package me.tokeee.gankdamage.regions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RegionManager implements Listener {
    private @Getter @Setter HashMap<String, RegionData> regionDataMap = new HashMap<>();
    private @Getter final HashMap<String, BufferData> bufferDataMap = new HashMap<>();

    @EventHandler
    private void BreakEvent(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if(!player.isOp() || !player.hasPermission("gankdamage.admin")) return;
        if (player.getItemInHand() == null || !player.getItemInHand().getType().equals(Material.DIAMOND_AXE)) return;

        final ItemStack itemInHand = player.getItemInHand();

        final String strippedName = ChatColor.stripColor(itemInHand.getItemMeta().getDisplayName());

        if (!strippedName.equals("GankRegion Wand")) return;

        if (!itemInHand.hasItemMeta() || itemInHand.getItemMeta().getLore().isEmpty()) return;

        final String regionName = itemInHand.getItemMeta().getLore().get(0);

        final Location location = event.getBlock().getLocation();

        if (bufferDataMap.containsKey(regionName)) {

            final RegionData regionData = createRegionData(regionName, location);

            regionDataMap.put(regionName, regionData);
            player.sendMessage(ChatColor.BLUE + "Added point B on location: " + location);
            bufferDataMap.remove(regionName);
            event.setCancelled(true);
            return;
        }

        final BufferData bufferData = new BufferData(location);

        player.sendMessage(ChatColor.BLUE + "Added point A on location: " + location);
        bufferDataMap.put(regionName, bufferData);

        event.setCancelled(true);


    }

    private RegionData createRegionData(final String regionName, final Location location){
        final BufferData bufferData = bufferDataMap.get(regionName);
        return new RegionData(bufferData.getPointA(), location, bufferData.getPointA().getWorld().getName());

    }


    public boolean isLocationInRegion(final Location location) {
        for (final RegionData regionData : regionDataMap.values()) {
            final Location pointA = new Location(Bukkit.getWorld(regionData.getWorldName()), regionData.getAX(), regionData.getAY(), regionData.getAZ());
            final Location pointB = new Location(Bukkit.getWorld(regionData.getWorldName()), regionData.getBX(), regionData.getBY(), regionData.getBZ());


            int x1 = Math.min(pointA.getBlockX(), pointB.getBlockX());
            int z1 = Math.min(pointA.getBlockZ(), pointB.getBlockZ());
            int x2 = Math.max(pointA.getBlockX(), pointB.getBlockX());
            int z2 = Math.max(pointA.getBlockZ(), pointB.getBlockZ());

            if (location.getX() >= x1 && location.getX() <= x2 && location.getZ() >= z1 && location.getZ() <= z2){
                return true;
         }
        }

        return false;
    }
}
