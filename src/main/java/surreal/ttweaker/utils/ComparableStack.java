package surreal.ttweaker.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import org.apache.logging.log4j.LogManager;

public class ComparableStack {

    private final Item item;
    private final int meta;
    private final NBTTagCompound compound;

    public ComparableStack(Item item) {
        this.item = item;
        this.meta = 0;
        this.compound = null;
    }

    public ComparableStack(ItemStack stack) {
        this.item = stack.getItem();
        this.meta = stack.getMetadata();
        this.compound = stack.getTagCompound();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparableStack cs) {
            return this == obj || (item == cs.item && meta == cs.meta && NBTUtil.areNBTEquals(compound, cs.compound, true));
        }

        return false;
    }

    @Override
    public int hashCode() {
        int i = this.item.hashCode() * (this.meta + 1);

        if (this.compound != null) {
            i *= this.compound.toString().hashCode();
        }

        return i;
    }

    @Override
    public String toString() {
        return item.getRegistryName() + "|" + meta + "|" + compound;
    }
}
