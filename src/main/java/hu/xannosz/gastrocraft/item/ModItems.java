package hu.xannosz.gastrocraft.item;

import hu.xannosz.gastrocraft.GastroCraft;
import hu.xannosz.gastrocraft.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GastroCraft.MOD_ID);

	//main
	public static final RegistryObject<Item> FLOUR = ITEMS.register("flour",
			() -> new BasicFood(64, ModFoods.normalFood(1, 0.1F)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> PASTA = ITEMS.register("pasta",
			() -> new BasicFood(64, ModFoods.rawPasta(1)));

	public static final RegistryObject<Item> PEAR = ITEMS.register("pear",
			() -> new BasicFood(ModFoods.fastFood(4, 0.3F)));

	public static final RegistryObject<Item> WHITE_GRAPE = ITEMS.register("white_grape",
			() -> new BasicFood(ModFoods.fastFood(4, 0.3F)));

	public static final RegistryObject<Item> RED_GRAPE = ITEMS.register("red_grape",
			() -> new BasicFood(ModFoods.fastFood(4, 0.3F)));

	public static final RegistryObject<Item> RAISIN = ITEMS.register("raisin",
			() -> new BasicFood(ModFoods.normalFood(4, 0.6F)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
			() -> new Item(new Item.Properties().stacksTo(16)));

	//eggs
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> FRIED_EGG = ITEMS.register("fried_egg",
			() -> new BasicFood(ModFoods.fastFood(5, 0.6F)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_OMELETTE = ITEMS.register("raw_omelette",
			() -> new BasicFood(ModFoods.rawFood(1, 0.3F, 0.7F)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> OMELETTE = ITEMS.register("omelette",
			() -> new BasicFood(ModFoods.fastFood(8, 1F)));

	//bakery
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_BUN = ITEMS.register("raw_bun",
			() -> new BasicFood(ModFoods.rawPasta(1)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_BREAD = ITEMS.register("raw_bread",
			() -> new BasicFood(ModFoods.rawPasta(3)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_BAGUETTE = ITEMS.register("raw_baguette",
			() -> new BasicFood(ModFoods.rawPasta(3)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_CRESCENT = ITEMS.register("raw_crescent",
			() -> new BasicFood(ModFoods.rawPasta(3)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> BUN = ITEMS.register("bun",
			() -> new BasicFood(ModFoods.pasta(1)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette",
			() -> new BasicFood(ModFoods.pasta(3)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> CRESCENT = ITEMS.register("crescent",
			() -> new BasicFood(ModFoods.pasta(1)));

	//milk
	public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
			() -> new BasicFood(ModFoods.normalFood(4, 0.6F)));

	public static final RegistryObject<Item> CURD = ITEMS.register("curd", //aludt tej
			() -> new BasicFood(ModFoods.normalFood(2, 0.1F)));

	public static final RegistryObject<Item> COTTAGE = ITEMS.register("cottage",
			() -> new BasicFood(ModFoods.normalFood(4, 0.6F)));

	//strudel
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> STRUDEL_PASTA = ITEMS.register("strudel_pasta",
			() -> new BasicFood(ModFoods.rawPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_COTTAGE_STRUDEL = ITEMS.register("raw_cottage_strudel",
			() -> new BasicFood(ModFoods.rawPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_COCOA_STRUDEL = ITEMS.register("raw_cocoa_strudel",
			() -> new BasicFood(ModFoods.rawPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_APPLE_STRUDEL = ITEMS.register("raw_apple_strudel",
			() -> new BasicFood(ModFoods.rawPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_PEAR_STRUDEL = ITEMS.register("raw_pear_strudel",
			() -> new BasicFood(ModFoods.rawPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_PUMPKIN_STRUDEL = ITEMS.register("raw_pumpkin_strudel",
			() -> new BasicFood(ModFoods.rawPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> COTTAGE_STRUDEL = ITEMS.register("cottage_strudel",
			() -> new BasicFood(ModFoods.filledPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> COCOA_STRUDEL = ITEMS.register("cocoa_strudel",
			() -> new BasicFood(ModFoods.filledPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> APPLE_STRUDEL = ITEMS.register("apple_strudel",
			() -> new BasicFood(ModFoods.filledPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> PEAR_STRUDEL = ITEMS.register("pear_strudel",
			() -> new BasicFood(ModFoods.filledPasta(6)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> PUMPKIN_STRUDEL = ITEMS.register("pumpkin_strudel",
			() -> new BasicFood(ModFoods.filledPasta(6)));

	//fermentation
	public static final RegistryObject<Item> RED_WINE = ITEMS.register("red_wine",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE, false,21));
	public static final RegistryObject<Item> ROSE_WINE = ITEMS.register("rose_wine",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,21));
	public static final RegistryObject<Item> WHITE_WINE = ITEMS.register("white_wine",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,21));

	public static final RegistryObject<Item> POMACE_PALINKA = ITEMS.register("pomace_palinka",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,61));
	public static final RegistryObject<Item> APPLE_PALINKA = ITEMS.register("apple_palinka",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,61));
	public static final RegistryObject<Item> PEAR_PALINKA = ITEMS.register("pear_palinka",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,61));
	public static final RegistryObject<Item> MIX_PALINKA = ITEMS.register("mix_palinka",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,61));
	public static final RegistryObject<Item> METHYL_ALCOHOL = ITEMS.register("methyl_alcohol",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,true,81));
	public static final RegistryObject<Item> ETHYL_ALCOHOL = ITEMS.register("ethyl_alcohol",
			() -> new Alcohol(ModFoods.normalFood(1, 3.6F), Items.GLASS_BOTTLE,false,81));

	//mash
	public static final RegistryObject<Item> WHITE_STUM = ITEMS.register("white_stum", //must
			() -> new FoodInGlass(ModFoods.normalFood(1, 1.3F)));

	public static final RegistryObject<Item> RED_STUM = ITEMS.register("red_stum",
			() -> new FoodInGlass(ModFoods.normalFood(1, 1.3F)));

	public static final RegistryObject<Item> RAW_WHITE_POMACE = ITEMS.register("raw_white_pomace", //törköly
			() -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> RAW_RED_POMACE = ITEMS.register("raw_red_pomace",
			() -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> POMACE = ITEMS.register("pomace",
			() -> new Item(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> APPLE_JUICE = ITEMS.register("apple_juice",
			() -> new FoodInGlass(ModFoods.normalFood(1, 2.6F)));

	public static final RegistryObject<Item> PEAR_JUICE = ITEMS.register("pear_juice",
			() -> new FoodInGlass(ModFoods.normalFood(1, 2.6F)));

	public static final RegistryObject<Item> RAW_APPLE_MASH = ITEMS.register("raw_apple_mash", //cefre
			() -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> APPLE_MASH = ITEMS.register("apple_mash",
			() -> new Item(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> RAW_PEAR_MASH = ITEMS.register("raw_pear_mash",
			() -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> PEAR_MASH = ITEMS.register("pear_mash",
			() -> new Item(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> MIX_MASH = ITEMS.register("mix_mash",
			() -> new Item(new Item.Properties().stacksTo(16)));

	//schnitzel
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> BREAD_CRUMBS = ITEMS.register("bread_crumbs",
			() -> new BasicFood(ModFoods.pasta(1)));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_PORK_SCHNITZEL = ITEMS.register("raw_pork_schnitzel",
			() -> new BasicFood(ModFoods.rawMeat()));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> RAW_CHICKEN_SCHNITZEL = ITEMS.register("raw_chicken_schnitzel",
			() -> new BasicFood(ModFoods.rawMeat()));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> PORK_SCHNITZEL = ITEMS.register("pork_schnitzel",
			() -> new BasicFood(ModFoods.meat()));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> CHICKEN_SCHNITZEL = ITEMS.register("chicken_schnitzel",
			() -> new BasicFood(ModFoods.meat()));

	//sandwiches
	@SuppressWarnings("unused")
	public static final RegistryObject<Item> PORK_SCHNITZEL_BON = ITEMS.register("pork_schnitzel_bon",
			() -> new BasicFood(ModFoods.meatBon()));

	@SuppressWarnings("unused")
	public static final RegistryObject<Item> CHICKEN_SCHNITZEL_BON = ITEMS.register("chicken_schnitzel_bon",
			() -> new BasicFood(ModFoods.meatBon()));

	public static final RegistryObject<Item> WHITE_GRAPE_SEED = ITEMS.register("white_grape_seed",
			() -> new ItemNameBlockItem(ModBlocks.WHITE_GRAPE.get(), new Item.Properties()));

	public static final RegistryObject<Item> RED_GRAPE_SEED = ITEMS.register("red_grape_seed",
			() -> new ItemNameBlockItem(ModBlocks.RED_GRAPE.get(), new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
