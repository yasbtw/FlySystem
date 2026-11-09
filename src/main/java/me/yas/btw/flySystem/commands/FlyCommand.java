package me.yas.btw.flySystem.commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import static me.yas.btw.flySystem.FlySystem.plugin;

public class FlyCommand implements CommandExecutor, Listener {
    private ArrayList<Player> players = new ArrayList<>();
    FileConfiguration config = plugin().getConfig();
    private String startMsg = load("start", "&l[Fly] &rYou are now able to fly.");
    private String stopMsg = load("stop","&l[Fly} &rYou are no longer able to fly.");
    private String permission = load("permission", "flysystem.fly");
    private String nopermission = load("no-permission", "&cYou have no permission to execute this command.");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){
            if (args.length == 0){
                Fly(player);
            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    if (player.hasPermission("flysystem.flyothers")){
                        Fly(target);
                    }else{
                        player.sendMessage(nopermission);
                    }
                }
            }
        }
        return true;
    }

    private String load(String part, String def){
        return ChatColor.translateAlternateColorCodes('&',config.getString(part, def));
    }

    private void Fly(Player player){
        if (player.hasPermission(permission)){
            if (!players.contains(player)){
                player.setAllowFlight(true);
                player.sendMessage(startMsg);
                players.add(player);
            }else{
                player.setAllowFlight(false);
                player.sendMessage(stopMsg);
                players.remove(player);
            }
        } else{
        player.sendMessage(nopermission);
    }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        players.remove(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        players.remove(e.getPlayer());
    }
}
