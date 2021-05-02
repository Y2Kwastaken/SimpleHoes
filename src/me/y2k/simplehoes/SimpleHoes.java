package me.y2k.simplehoes;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class SimpleHoes extends JavaPlugin {
	public static SimpleHoes plugin;
	private HoeStack hstack;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

	@Override
	public void onEnable() {
		plugin = this;
		//initalize configs
		loadConfigs();
		//initalize server events / commands
		Utils.loadMessage();
		getServer().getPluginManager().registerEvents(new HoeBreakEvent(), this);
		getServer().getPluginManager().registerEvents(new HoeModeSwitch(), this);
		getCommand("simplehoes").setExecutor(new HoeCommand());
		hstack = new HoeStack();
		hstack.Stacks();
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	}
	
	@Override
	public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
	}
	
	public void loadConfigs() {
		createConfig("config.yml");
		getConfig().options().copyDefaults(true);
	}
	
	
	/*
	 * Creates Config Files
	 */
    public FileConfiguration getConfigFile(String name) {
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), name));
    }
    
    public void createDirectory(String DirName) {
        File newDir = new File(getDataFolder(), DirName.replace("/", File.separator));
        if (!newDir.exists()){
            newDir.mkdirs();
        }
    }
    
    public void createConfig(String name) {
        File file = new File(getDataFolder(), name);

        if (!new File(getDataFolder(), name).exists()) {
            
            saveResource(name, false);
        }
                
        @SuppressWarnings("static-access")
        FileConfiguration configuration = new YamlConfiguration().loadConfiguration(file);
        if (!file.exists()) {
            try {
                configuration.save(file);
            }            
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createFile(String name) {
        File file = new File(getDataFolder(), name);
        
        if (!file.exists()) {
            try {
                 file.createNewFile();
              } catch(Exception e) {
                 e.printStackTrace();
              }
        }
    }    
    
    public void saveConfig(FileConfiguration config, String name) {
        try {
            config.save(new File(getDataFolder(), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
    
    public static Permission getPermissions() {
        return perms;
    }
    
    public static Chat getChat() {
        return chat;
    }
}
