package hu.xannosz.gastrocraft;

import hu.xannosz.gastrocraft.block.ModBlocks;
import hu.xannosz.gastrocraft.blockentity.ModBlockEntities;
import hu.xannosz.gastrocraft.item.ModItems;
import hu.xannosz.gastrocraft.loot.ModLootTables;
import hu.xannosz.gastrocraft.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GastroCraft.MOD_ID)
public class GastroCraft {
	public static final String MOD_ID = "gastrocraft";

	public GastroCraft() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);
		ModBlockEntities.register(modEventBus);
		ModMenuTypes.register(modEventBus);
		ModLootTables.register(modEventBus);
		ModCreativeModeTab.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {

	}

	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			MenuScreens.register(ModMenuTypes.MILL_MENU.get(), MillScreen::new);
			MenuScreens.register(ModMenuTypes.DRYER_MENU.get(), DryerScreen::new);
			MenuScreens.register(ModMenuTypes.PRESS_MENU.get(), PressScreen::new);
			MenuScreens.register(ModMenuTypes.WINE_BARREL_MENU.get(), WineBarrelScreen::new);
			MenuScreens.register(ModMenuTypes.DISTILLER_MENU.get(), DistillerScreen::new);
		}
	}
}
