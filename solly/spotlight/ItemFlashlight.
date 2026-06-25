package solly.spotlight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFlashlight extends Item {

    // nbt tag
    private static final String TAG_ON = "spotlight_on";

    public ItemFlashlight() {
        setMaxStackSize(1);
        setRegistryName(SpotlightMod.MODID, "flashlight");
        setUnlocalizedName(SpotlightMod.MODID + ".flashlight");
        setCreativeTab(net.minecraft.creativetab.CreativeTabs.TOOLS);
    }

    // turn flashlight 
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
                                                     EntityPlayer player,
                                                     EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {
            boolean current = isOn(stack);
            setOn(stack, !current);
            // sound
            world.playSound(
                null,
                player.posX, player.posY, player.posZ,
                net.minecraft.util.SoundEvent.REGISTRY.getObject(
                    new net.minecraft.util.ResourceLocation("minecraft:block.lever.click")
                ),
                net.minecraft.util.SoundCategory.PLAYERS,
                0.5f, current ? 0.9f : 1.1f
            );
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    // --- utils ---

    public static boolean isOn(ItemStack stack) {
        return !stack.isEmpty()
            && stack.hasTagCompound()
            && stack.getTagCompound().getBoolean(TAG_ON);
    }

    public static void setOn(ItemStack stack, boolean value) {
        if (stack.isEmpty()) return;
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
        }
        stack.getTagCompound().setBoolean(TAG_ON, value);
    }
}

