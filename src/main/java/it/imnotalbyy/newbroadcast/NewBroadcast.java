package it.imnotalbyy.newbroadcast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NewBroadcast extends JavaPlugin implements CommandExecutor {
    private String messagePrefix;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        messagePrefix = getConfig().getString("message-prefix", "&4Broadcast &7» ");

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[NewBroadcast]" + ChatColor.GREEN + " by ImNotAlbyy");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[NewBroadcast]" + ChatColor.GREEN + " Plugin Enabled!");
        this.getCommand("broadcast").setExecutor(this);
        this.getCommand("bc").setExecutor(this);
        this.getCommand("newbroadcast").setExecutor(this);
        this.getCommand("newbc").setExecutor(this);

            // All you have to do is adding the following two lines in your onEnable method.
            // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
            int pluginId = 22188; // <-- Replace with the id of your plugin!
            Metrics metrics = new Metrics(this, pluginId);

        }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!.");
            return true;
        }

        if (!sender.hasPermission("newbroadcast.use")) {
            sender.sendMessage(ChatColor.RED + "You don't have the permission the execute this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Correct usage: /broadcast <message>");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String word : args) {
            message.append(word).append(" ");
        }

        String broadcastMessage = ChatColor.translateAlternateColorCodes('&', messagePrefix + message.toString());
        broadcastMessage = broadcastMessage.replaceAll("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\s()<>]+|\\(([^\s()<>]+|(\\([^\s()<>]+\\)))*\\))+(?:\\(([^\s()<>]+|(\\([^\s()<>]+\\)))*\\)|[^\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))", ChatColor.AQUA + "$1" + ChatColor.RESET);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(broadcastMessage);
        }

        return true;
    }
}