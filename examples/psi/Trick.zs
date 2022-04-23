import mods.psi.Trick;

/*
* addRecipe(String trick, IIngredient input, IItemStack output, IItemStack minAssembly)
* trick names can be found in https://github.com/VazkiiMods/Psi/blob/1.12/src/main/java/vazkii/psi/common/lib/LibPieceNames.java
* minAssembly is minimum assembly you need to be able to make this recipe
*/
Trick.addRecipe("", <ore:blockIron>, <minecraft:diamond>, <psi:cad_assembly>);

// remove(IItemStack output)
Trick.remove(<psi:material>); // Removes Redstone Dust -> Psi Dust Recipe

Trick.removeAll(); // Removes All Recipes