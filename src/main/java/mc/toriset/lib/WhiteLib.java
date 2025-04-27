package mc.toriset.lib;

import org.bukkit.plugin.java.JavaPlugin;

public class WhiteLib extends JavaPlugin {
    protected static WhiteLib instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public static WhiteLib getInstance() {
        return instance;
    }
}
