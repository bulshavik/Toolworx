package me.lyneira.MachinaCore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

/**
 * Abstract class for the blueprint of a {@link Movable} machina. It is intended
 * to be implemented as a singleton.
 * 
 * @author Lyneira
 */
public abstract class MovableBlueprint implements MachinaBlueprint {
    private final List<BlueprintModule> modules;

    /**
     * Constructs a new MovableBlueprint using the given
     * {@link BlueprintFactory} List.
     * 
     * @param blueprintSouth
     */
    protected MovableBlueprint(final List<BlueprintFactory> blueprintsSouth) {
        modules = new ArrayList<BlueprintModule>(blueprintsSouth.size());

        for (BlueprintFactory blueprint : blueprintsSouth) {
            modules.add(new BlueprintModule(blueprint));
        }
    }

    /**
     * Detects whether all non-key blocks for the given rotation are present in
     * relation to the anchor.
     * 
     * @param anchor
     *            {@link BlockLocation} to detect at
     * @param yaw
     *            {@link BlockRotation} of the blueprint to detect for.
     * @param module
     *            The module to detect.
     * @return True if the non-key blocks are present.
     */
    public boolean detectOther(final BlockLocation anchor, final BlockRotation yaw, final int module) {
        return modules.get(module).detectOther(anchor, yaw);
    }

    /**
     * Returns the {@link BlockVector} from the blueprint at the given index.
     * 
     * @param index
     *            The index to retrieve from
     * @param module
     *            The module to retrieve from.
     * @return The {@link BlockVector} at the given index
     */
    public BlockVector getByIndex(final int index, final BlockRotation yaw, final int module) {
        return modules.get(module).getByIndex(index, yaw);
    }

    /**
     * Returns an array of bytes containing the block data for this blueprint.
     * 
     * @param anchor
     *            The anchor for which to grab data from the blocks
     * @param yaw
     *            {@link BlockRotation} of the blueprint
     * @param module
     *            The module to get data from.
     * @return An array of bytes of block data
     */
    public byte[] getBlockData(final BlockLocation anchor, final BlockRotation yaw, final int module) {
        return modules.get(module).getBlockData(anchor, yaw);
    }

    /**
     * Sets the data for the blocks around anchor using the given data byte
     * array.
     * 
     * @param anchor
     *            The anchor for which to set data
     * @param data
     *            The byte array to use
     * @param yaw
     *            {@link BlockRotation} of the blueprint
     * @param module
     *            The module to set data for.
     */
    public void setBlockData(final BlockLocation anchor, final byte[] data, final BlockRotation yaw, final int module) {
        modules.get(module).setBlockData(anchor, data, yaw);
    }

    /**
     * Returns a {@link List} of {@link ItemStack} arrays grabbed from the
     * inventories in this blueprint. The inventories are cleared in the
     * process.
     * 
     * @param anchor
     *            The anchor for which to grab inventory from the blocks
     * @param yaw
     *            {@link BlockRotation} of the blueprint
     * @param module
     *            The module to grab inventory for.
     * @return A {@link List} of {@link ItemStack} arrays
     */
    public ItemStack[][] getBlockInventories(final BlockLocation anchor, final BlockRotation yaw, final int module) {
        return modules.get(module).getBlockInventories(anchor, yaw);
    }

    /**
     * Sets the inventory for the blocks around the anchor using the given array
     * of inventories
     * 
     * @param anchor
     *            The anchor for which to set inventory
     * @param inventories
     *            The inventory array to use
     * @param yaw
     *            {@link BlockRotation} of the blueprint
     * @param module
     *            The module to set inventory for.
     */
    protected void setBlockInventories(final BlockLocation anchor, final ItemStack[][] inventories, final BlockRotation yaw, final int module) {
        modules.get(module).setBlockInventories(anchor, inventories, yaw);
    }

    /**
     * Unifies the {@link BlueprintBlock}s for the given moduleIndices into a
     * new array and returns it.
     * 
     * @param moduleIndices
     *            The indices of the modules to unify
     * @return A new array of {@link BlueprintBlock}s
     */
    final BlueprintBlock[] unifyBlueprint(final List<Integer> moduleIndices) {
        int size = 0;
        for (int m : moduleIndices) {
            size += modules.get(m).size;
        }
        BlueprintBlock[] result = new BlueprintBlock[size];
        int i = 0;
        for (int m : moduleIndices) {
            BlueprintModule module = modules.get(m);
            for (BlueprintBlock block : module.blueprint) {
                result[i] = block;
                i++;
            }
        }
        return result;
    }

    /**
     * Unifies the {@link BlockVector}s for the given yaw and moduleIndices and
     * assigns it to the given {@link BlockVector} array
     * 
     * @param moduleIndices
     *            The indices of the modules to unify.
     * @param yaw
     *            The yaw of the vectors to unify
     * @param vectors
     *            The array to place the vectors in
     * @return A new array of {@link BlockVector}s
     */
    final void unifyVectors(final List<Integer> moduleIndices, final BlockRotation yaw, BlockVector[] vectors) {
        int i = 0;
        for (int m : moduleIndices) {
            BlueprintModule module = modules.get(m);
            for (BlockVector vector : module.blueprintVectors.get(yaw)) {
                vectors[i] = vector;
                i++;
            }
        }
    }
}