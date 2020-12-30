package me.cohutt.hat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class Hat extends JavaPlugin {

    @Override
    public void onEnable() {
        YamlConfiguration messages = validateYaml("messages.yml");
        String set = (String)messages.get("set");
        String stacksize = (String)messages.get("stack-size");
        String nopermission = (String)messages.get("no-permission");
        String console = (String)messages.get("console");
        getCommand("hat").setExecutor(new Hatcommand(set, stacksize, nopermission, console));
        Bukkit.getConsoleSender().sendMessage("[Hat] Plugin enabled!");
    }

    private YamlConfiguration validateYaml(String filename) {
        Bukkit.getConsoleSender().sendMessage("[" + getName() + "] Validating " + filename);
        InputStream defaultFile = getResource(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(defaultFile));
        YamlConfiguration newconfig = YamlConfiguration.loadConfiguration(reader);
        YamlConfiguration oldconfig = new YamlConfiguration();
        try {
            File config = new File(getDataFolder(), filename);
            if (config.exists()) {
                oldconfig.load(config);
            } else {
                Bukkit.getConsoleSender().sendMessage("[" + getName() + "] " + filename + " does not exist, creating it now.");
            }
        } catch (Throwable e) {
            Bukkit.getConsoleSender().sendMessage("[" + getName() + "] " + filename + " does not contain a valid configuration, the default configuration will be used instead.");
            return newconfig;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String key : oldconfig.getKeys(true)) {
            if (newconfig.contains(key) && !(oldconfig.get(key) instanceof org.bukkit.configuration.ConfigurationSection))
                newconfig.set(key, oldconfig.get(key));
        }
        try {
            newconfig.save(new File(getDataFolder(), filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage("[" + getName() + "] " + filename + " has been validated.");
        return newconfig;
    }


    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[Hat] Plugin Disabled!");
    }
}
