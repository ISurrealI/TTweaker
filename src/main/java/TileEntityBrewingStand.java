import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import surreal.ttweaker.crafttweaker.BrewingFuel;
import surreal.ttweaker.core.TTHooks;

public class TileEntityBrewingStand extends net.minecraft.tileentity.TileEntityBrewingStand implements ITickable, ISidedInventory {
    private static final int[] SLOTS_FOR_UP = new int[]{3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 3};
    private static final int[] OUTPUT_SLOTS = new int[]{0, 1, 2, 4};
    private NonNullList<ItemStack> brewingItemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private int brewTime;
    private boolean[] filledSlots;
    private Item ingredientID;
    private String customName;
    private int fuel;
    IItemHandler handlerInput = new SidedInvWrapper(this, EnumFacing.UP);
    IItemHandler handlerOutput = new SidedInvWrapper(this, EnumFacing.DOWN);
    IItemHandler handlerSides = new SidedInvWrapper(this, EnumFacing.NORTH);

    public TileEntityBrewingStand() {
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.brewing";
    }

    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setName(String name) {
        this.customName = name;
    }

    public int getSizeInventory() {
        return this.brewingItemStacks.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.brewingItemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void update() {
        ItemStack itemstack = (ItemStack)this.brewingItemStacks.get(4);

        if (fuel <= 0 && !itemstack.isEmpty())
            TTHooks.handleFuel(this, itemstack);

        boolean flag = this.canBrew();
        boolean flag1 = this.brewTime > 0;
        ItemStack itemstack1 = (ItemStack)this.brewingItemStacks.get(3);
        if (flag1) {
            --this.brewTime;
            boolean flag2 = this.brewTime == 0;
            if (flag2 && flag) {
                this.brewPotions();
                this.markDirty();
            } else if (!flag) {
                this.brewTime = 0;
                this.markDirty();
            } else if (this.ingredientID != itemstack1.getItem()) {
                this.brewTime = 0;
                this.markDirty();
            }
        } else if (flag && this.fuel > 0) {
            --this.fuel;
            this.brewTime = 400;
            this.ingredientID = itemstack1.getItem();
            this.markDirty();
        }

        if (!this.world.isRemote) {
            boolean[] aboolean = this.createFilledSlotsArray();
            if (!Arrays.equals(aboolean, this.filledSlots)) {
                this.filledSlots = aboolean;
                IBlockState iblockstate = this.world.getBlockState(this.getPos());
                if (!(iblockstate.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }

                for(int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; ++i) {
                    iblockstate = iblockstate.withProperty(BlockBrewingStand.HAS_BOTTLE[i], aboolean[i]);
                }

                this.world.setBlockState(this.pos, iblockstate, 2);
            }
        }
    }

    public boolean[] createFilledSlotsArray() {
        boolean[] aboolean = new boolean[3];

        for(int i = 0; i < 3; ++i) {
            if (!((ItemStack)this.brewingItemStacks.get(i)).isEmpty()) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    private boolean canBrew() {
        return BrewingRecipeRegistry.canBrew(this.brewingItemStacks, (ItemStack)this.brewingItemStacks.get(3), OUTPUT_SLOTS);
    }

    private void brewPotions() {
        if (!ForgeEventFactory.onPotionAttemptBrew(this.brewingItemStacks)) {
            ItemStack itemstack = (ItemStack)this.brewingItemStacks.get(3);
            BrewingRecipeRegistry.brewPotions(this.brewingItemStacks, (ItemStack)this.brewingItemStacks.get(3), OUTPUT_SLOTS);
            itemstack.shrink(1);
            BlockPos blockpos = this.getPos();
            if (itemstack.getItem().hasContainerItem(itemstack)) {
                ItemStack itemstack1 = itemstack.getItem().getContainerItem(itemstack);
                if (itemstack.isEmpty()) {
                    itemstack = itemstack1;
                } else {
                    InventoryHelper.spawnItemStack(this.world, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), itemstack1);
                }
            }

            this.brewingItemStacks.set(3, itemstack);
            this.world.playEvent(1035, blockpos, 0);
            ForgeEventFactory.onPotionBrewed(this.brewingItemStacks);
        }
    }

    public static void registerFixesBrewingStand(DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(net.minecraft.tileentity.TileEntityBrewingStand.class, new String[]{"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.brewingItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.brewingItemStacks);
        this.brewTime = compound.getShort("BrewTime");
        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }

        this.fuel = compound.getByte("Fuel");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setShort("BrewTime", (short)this.brewTime);
        ItemStackHelper.saveAllItems(compound, this.brewingItemStacks);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }

        compound.setByte("Fuel", (byte)this.fuel);
        return compound;
    }

    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.brewingItemStacks.size() ? (ItemStack)this.brewingItemStacks.get(index) : ItemStack.EMPTY;
    }

    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.brewingItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.brewingItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index >= 0 && index < this.brewingItemStacks.size()) {
            this.brewingItemStacks.set(index, stack);
        }
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
        }
    }

    public void openInventory(EntityPlayer player) {
    }

    public void closeInventory(EntityPlayer player) {
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 3) {
            return BrewingRecipeRegistry.isValidIngredient(stack);
        } else {
            Item item = stack.getItem();
            if (index == 4) {
                return BrewingFuel.hasKey(stack);
            } else {
                return BrewingRecipeRegistry.isValidInput(stack) && this.getStackInSlot(index).isEmpty();
            }
        }
    }

    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.UP) {
            return SLOTS_FOR_UP;
        } else {
            return side == EnumFacing.DOWN ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
        }
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (index == 3) {
            return stack.getItem() == Items.GLASS_BOTTLE;
        } else {
            return true;
        }
    }

    public String getGuiID() {
        return "minecraft:brewing_stand";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerBrewingStand(playerInventory, this);
    }

    public int getField(int id) {
        switch(id) {
            case 0:
                return this.brewTime;
            case 1:
                return this.fuel;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch(id) {
            case 0:
                this.brewTime = value;
                break;
            case 1:
                this.fuel = value;
        }
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == null || capability != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T)super.getCapability(capability, facing);
        } else if (facing == EnumFacing.UP) {
            return (T)this.handlerInput;
        } else {
            return (T)(facing == EnumFacing.DOWN ? this.handlerOutput : this.handlerSides);
        }
    }

    public int getFieldCount() {
        return 2;
    }

    public void clear() {
        this.brewingItemStacks.clear();
    }
}

