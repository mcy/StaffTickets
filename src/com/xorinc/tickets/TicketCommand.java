package com.xorinc.tickets;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicketCommand implements CommandExecutor {

	private TicketPlugin plugin;
	private static final String CMD_HELP = "/stafftickets <post|read|remove> [args]";
	private static final int NUM_OF_MSG_PER_PAGE = 10;
	
	public TicketCommand(TicketPlugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1){
			sender.sendMessage(ChatColor.RED + CMD_HELP);
			return true;
		}
		
		String name = "Server Console";
		
		if(sender instanceof Player){
			name = ((Player) sender).getName();
		}
			
		
		
		if(args[0].equalsIgnoreCase("post")){
			if(!sender.hasPermission("stafftickets.post")){
				sender.sendMessage(ChatColor.RED + "You may not post staff tickets.");
				return true;
			}
			
			String message = "[" + name + "]"; 
			
			for(int i = 1; i < args.length; i++){
				message += " " + args[i];
			}
						
			if(args.length < 2){
				sender.sendMessage(ChatColor.RED + "Please provide a message for your ticket.");
				return true;
			}
			
			if(plugin.createTicket(message)){
				sender.sendMessage(ChatColor.GOLD + "Ticket posted!");
			}	
			else{
				sender.sendMessage(ChatColor.RED + "That ticket already exists.");
			}	
		}
		
		else if(args[0].equalsIgnoreCase("read")){
			if(!sender.hasPermission("stafftickets.read")){
				sender.sendMessage(ChatColor.RED + "You may not read staff tickets.");
				return true;
			}
			
			if(args.length < 2){
				showTickets(sender, 1);
				return true;
			}
			
			try{
				showTickets(sender, Integer.parseInt(args[1]));
			}	
			catch(NumberFormatException e){
				sender.sendMessage(ChatColor.RED + args[1] + " is not a valid page number.");
			}
		}
		
		else if(args[0].equalsIgnoreCase("remove")){
			if(!sender.hasPermission("stafftickets.remove")){
				sender.sendMessage(ChatColor.RED + "You may not remove staff tickets.");
				return true;
			}
			
			
			if(args.length < 2){
				sender.sendMessage(ChatColor.RED + "Please provide a ticket number to remove.");
				return true;
			}
			
			try{
				if(plugin.getTickets().size() <= Integer.parseInt(args[1]) - 1){
					sender.sendMessage(ChatColor.RED + "That ticket does not exist.");
				}	
				else{
					plugin.removeTicket(Integer.parseInt(args[1]) - 1);
					sender.sendMessage(ChatColor.GOLD + "Ticket #" + Integer.parseInt(args[1]) + " removed.");
				}
			}	
			catch(NumberFormatException e){
				sender.sendMessage(ChatColor.RED + args[1] + " is not a valid ticket number.");
			}
		}
		
		else
			sender.sendMessage(ChatColor.RED + CMD_HELP);
		
		return true;
	}
	
	
	public void showTickets(CommandSender sender, int page){
		List<String> tickets = plugin.getTickets();
		
		if(tickets.size() == 0){
			sender.sendMessage(ChatColor.GOLD + "There are currently no tickets.");
			return;
		}
		
		try{
			tickets.get((page - 1) * NUM_OF_MSG_PER_PAGE);
		}
		catch(IndexOutOfBoundsException e){
			sender.sendMessage(ChatColor.RED + "That page does not exist.");
			return;
		}
		
		sender.sendMessage(ChatColor.GOLD + "==========" + ChatColor.RED + "Page " + page + "/" + (tickets.size()/NUM_OF_MSG_PER_PAGE + 1) + ChatColor.GOLD + "==========");
		
		for(int i = 0; i < NUM_OF_MSG_PER_PAGE; i++){
			try{
				sender.sendMessage(ChatColor.GOLD + "" + ((page - 1) * NUM_OF_MSG_PER_PAGE + i + 1) + ": " + tickets.get(((page - 1) * NUM_OF_MSG_PER_PAGE + i)));
			}
			catch(IndexOutOfBoundsException e){
				break;
			}
		}
	}
}
