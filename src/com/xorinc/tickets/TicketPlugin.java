package com.xorinc.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class TicketPlugin extends JavaPlugin {

	private List<String> tickets = null;	
	private TicketList list = new TicketList(this);
	
	@Override
	public void onEnable(){
		
		List<String> loadedTickets = list.loadTickets();
		if(loadedTickets == null){
			tickets = new ArrayList<String>();
			getLogger().warning("No tickets loaded.");
		}
		else{
			tickets = loadedTickets;
			getLogger().info(tickets.size() + " tickets loaded.");
		}
		
		getCommand("stafftickets").setExecutor(new TicketCommand(this));
		
	}
	
	@Override
	public void onDisable(){
		
		if(list.saveTickets(tickets)){
			getLogger().info(tickets.size() + " tickets successfully saved.");
		}
		else{
			getLogger().info("No tickets saved.");
		}
	}
	
	public List<String> getTickets(){
		return Collections.unmodifiableList(tickets);
	}
	
	public String getTicket(int index){
		return tickets.get(index);
	}
	
	public boolean createTicket(String ticket){
		if(tickets.contains(ticket))
			return false;
		else
			return tickets.add(ticket);
	}

	public void removeTicket(int index){
		tickets.remove(index);
	}
}
