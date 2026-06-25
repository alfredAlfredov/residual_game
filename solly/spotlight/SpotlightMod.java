package solly.spotlight;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid   = SpotlightMod.MODID,
    name    = SpotlightMod.NAME,
    version = SpotlightMod.VERSION
)
public class SpotlightMod {

    public static final String MODID   = "spotlight";
    public static final String NAME    = "Spotlight";
    public static final String VERSION = "1.0.0";

    @SidedProxy(
        clientSide = "solly.spotlight.ClientProxy",
        serverSide = "solly.spotlight.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}

