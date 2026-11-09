package me.yas.btw.flySystem;
import me.yas.btw.flySystem.commands.FlyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlySystem extends JavaPlugin {
    private static FlySystem plugin;
    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getLogger().info("FlySystem made by @yasbtw on github.");
        FlyCommand instance = new FlyCommand();
        getCommand("fly").setExecutor(instance);
        getServer().getPluginManager().registerEvents(instance, this);
    }
    public static FlySystem plugin(){
        return plugin;
    }
}
