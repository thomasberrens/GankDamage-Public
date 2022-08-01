package me.tokeee.gankdamage;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lombok.SneakyThrows;
import me.tokeee.gankdamage.commands.GankWand;
import me.tokeee.gankdamage.commands.RegionList;
import me.tokeee.gankdamage.commands.RegionRemove;
import me.tokeee.gankdamage.commands.ToggleGank;
import me.tokeee.gankdamage.events.EventManager;
import me.tokeee.gankdamage.gankeffect.GankDamage;
import me.tokeee.gankdamage.gankeffect.GankParticles;
import me.tokeee.gankdamage.particles.ParticleTrailManager;
import me.tokeee.gankdamage.regions.RegionData;
import me.tokeee.gankdamage.regions.RegionManager;
import me.tokeee.gankdamage.regions.RegionRecord;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class GankEffect extends JavaPlugin {

    private static GankEffect instance;
    private @Getter EventManager eventManager;
    private final FileConfiguration config = this.getConfig();
    private @Getter ParticleTrailManager particleTrailManager;
    private @Getter GankDamage gankDamage;
    private @Getter RegionManager regionManager;

    public static GankEffect getInstance(){
        return instance;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        initializeConfig();

        initializeManagers();

        registerEvents();

        registerCommands();

        final File file = new File("plugins/GankDamage/regionData.json");
        if (!file.exists()) return;

        loadRegionData();


    }

    private void loadRegionData() throws IOException, ClassNotFoundException, ParseException {
        final JSONParser parser = new JSONParser();
        final JSONObject obj = (JSONObject) parser.parse(new FileReader("plugins/GankDamage/regionData.json"));

        for (final Object key : obj.keySet()) {
            final String keyString = (String) key;
            final String value = (String) obj.get(keyString);

            final RegionData regionData = (RegionData) fromString(value);
            regionManager.getRegionDataMap().put(keyString, regionData);

        }
    }

    /** Read the object from Base64 string. */
    private static Object fromString( String s ) throws IOException,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }


    @SneakyThrows
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        final JSONObject json = new JSONObject();

        for (final String key : regionManager.getRegionDataMap().keySet()) {
            final RegionData regionData = regionManager.getRegionDataMap().get(key);

            json.put(key, toString(regionData));
        }

        try {
            final FileWriter file = new FileWriter("plugins/GankDamage/regionData.json");
            final String jsonString = json.toJSONString();
            file.write(jsonString);
            file.close();

        } catch (IOException io) {
            // Fail
        }
    }

    private void initializeConfig(){
        config.options().copyDefaults(true);
        config.addDefault("gank-stage-1.damage", 0.06);
        config.addDefault("gank-stage-2.damage", 0.15);
        config.addDefault("gank-stage-3.damage", 0.25);

        config.addDefault("gank-stage-1.playerCount", 2);
        config.addDefault("gank-stage-2.playerCount", 3);
        config.addDefault("gank-stage-3.playerCount", 4);

        config.addDefault("gank-damage.ThresholdInSeconds", 7);
        saveConfig();
    }

    private void initializeManagers() {
        eventManager = new EventManager();
        gankDamage = new GankDamage();
        regionManager = new RegionManager();
        particleTrailManager = new ParticleTrailManager();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(gankDamage, this);
        Bukkit.getPluginManager().registerEvents(regionManager, this);
        Bukkit.getPluginManager().registerEvents(new GankParticles(), this);
    }

    private void registerCommands() {
        this.getCommand("togglegank").setExecutor(new ToggleGank());
        this.getCommand("gankwand").setExecutor(new GankWand());
        this.getCommand("regionremove").setExecutor(new RegionRemove());
        this.getCommand("regionlist").setExecutor(new RegionList());
    }
}
