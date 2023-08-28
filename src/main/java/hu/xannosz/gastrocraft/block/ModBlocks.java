package hu.xannosz.gastrocraft.block;

import hu.xannosz.gastrocraft.GastroCraft;
import hu.xannosz.gastrocraft.blockentity.*;
import hu.xannosz.gastrocraft.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GastroCraft.MOD_ID);

	public static final RegistryObject<Block> MILL = registerBlock("mill",
			() -> new BaseBlockWithBlockEntity(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
					.strength(1.5F, 6.0F).sound(SoundType.STONE),
					MillBlockEntity::new, MillBlockEntity::tick)
	);

	public static final RegistryObject<Block> DRYER = registerBlock("dryer",
			() -> new BaseBlockWithBlockEntity(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
					.strength(1.5F, 6.0F).sound(SoundType.STONE),
					DryerBlockEntity::new, DryerBlockEntity::tick)
	);

	public static final RegistryObject<Block> PRESS = registerBlock("press",
			() -> new BaseBlockWithBlockEntity(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
					.strength(1.5F, 6.0F).sound(SoundType.WOOD),
					PressBlockEntity::new, PressBlockEntity::tick)
	);

	public static final RegistryObject<Block> WINE_BARREL = registerBlock("wine_barrel",
			() -> new BaseBlockWithBlockEntity(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
					.strength(1.5F, 6.0F).sound(SoundType.WOOD).noOcclusion(),
					WineBarrelBlockEntity::new, WineBarrelBlockEntity::tick)
	);

	public static final RegistryObject<Block> DISTILLER = registerBlock("distiller",
			() -> new BaseBlockWithBlockEntity(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
					.strength(1.5F, 6.0F).sound(SoundType.METAL).noOcclusion(),
					DistillerBlockEntity::new, DistillerBlockEntity::tick)
	);

	public static final RegistryObject<Block> WHITE_GRAPE = registerBlock("white_grape_plant",
			() -> new WhiteGrapePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)
					.strength(0.5F, 1.0F).sound(SoundType.GRASS))
	);

	public static final RegistryObject<Block> RED_GRAPE = registerBlock("red_grape_plant",
			() -> new RedGrapePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)
					.strength(0.5F, 1.0F).sound(SoundType.GRASS))
	);

	@SuppressWarnings("unused")
	public static final RegistryObject<Block> RUDDER = registerBlock("rudder",
			() -> new Rudder(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
					.strength(0.5F, 1.0F).sound(SoundType.WOOD))
	);

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockCreator) {
		RegistryObject<T> block = BLOCKS.register(name, blockCreator);
		registerBlockItem(name, block);
		return block;
	}

	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
