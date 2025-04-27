package mc.toriset;

import mc.toriset.data.SharedCounter;
import mc.toriset.data.SharedData;
import org.bukkit.plugin.java.JavaPlugin;

public class WhiteLib extends JavaPlugin {
    protected static WhiteLib instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        getLogger().info("WhiteLib disabled, clearing shared data storage");
        SharedData.clear();
        SharedCounter.clear();
    }

    public static WhiteLib getInstance() {
        return instance;
    }
}
