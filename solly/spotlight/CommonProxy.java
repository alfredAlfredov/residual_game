package solly.spotlight;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        // bothsides register
        GameRegistry.register(SpotlightItems.FLASHLIGHT);
    }

    public void init(FMLInitializationEvent event) {
        // recipies
    }
}

