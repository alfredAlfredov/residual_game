package solly.spotlight;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        registerItemModel(SpotlightItems.FLASHLIGHT, "flashlight");
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // register renderer
        MinecraftForge.EVENT_BUS.register(new SpotlightRenderer());
    }

    private void registerItemModel(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(
            item, 0,
            new ModelResourceLocation(SpotlightMod.MODID + ":" + name, "inventory")
        );
    }
}

