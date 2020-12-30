package me.cohutt.hat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Hatcommand implements CommandExecutor {
    String setMessage;

    String stackSizeMessage;

    String noPermissionMessage;

    String consoleMessage;

    public Hatcommand(String set, String stacksize, String nopermission, String console) {
        this.setMessage = ChatColor.translateAlternateColorCodes('&', set);
        this.stackSizeMessage = ChatColor.translateAlternateColorCodes('&', stacksize);
        this.noPermissionMessage = ChatColor.translateAlternateColorCodes('&', nopermission);
        this.consoleMessage = ChatColor.translateAlternateColorCodes('&', console);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            ItemStack held = inv.getItemInHand();
            ItemStack helm = inv.getHelmet();
            if(held.getAmount() == 1 || held.getType() == Material.AIR) {
                inv.setHelmet(held);
                inv.setItemInHand(helm);
                player.updateInventory();
                sender.sendMessage(this.setMessage);
            }else {
                sender.sendMessage(this.stackSizeMessage);
            }
        }else {
            sender.sendMessage(this.consoleMessage);
        }
        return true;
    }
}
