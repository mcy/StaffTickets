package com.xorinc.tickets;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;


public class TicketList {
	
	private static final String CONFIG_NAME = "tickets.yml";
	private TicketPlugin plugin;
	
	public TicketList(TicketPlugin plugin){
		this.plugin = plugin;
	}
	
	public List<String> loadTickets(){
		if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    	
        File f = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME + "!");
                return null;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('/');
        conf.options().header("DO NOT EDIT ME!" + System.getProperty("line.separator"));
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Tickets will not be loaded!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return null;
        }
        
        List<String> tickets = null;
        
        if(conf.isList("tickets"))
        	tickets = conf.getStringList("tickets");
                
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return tickets;
        
	}
	
	public boolean saveTickets(List<String> tickets){
		if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    	
        File f = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME + "! No config options were loaded!");
                return false;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('/');
        conf.options().header("DO NOT EDIT ME!" + System.getProperty("line.separator"));
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to save to " + CONFIG_NAME);
            plugin.getLogger().severe("Tickets will not be saved!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return false;
        }
        
        
        conf.set("tickets", tickets);
                
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
        
	}
}
