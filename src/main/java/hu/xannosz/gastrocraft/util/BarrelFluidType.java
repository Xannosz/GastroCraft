package hu.xannosz.gastrocraft.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BarrelFluidType {
	RED_STUM(true,false), ROSE_STUM(true,false), WHITE_STUM(true,false),
	RED_WINE(false,false), ROSE_WINE(false,false), WHITE_WINE(false,false),
	RAW_APPLE_MASH(false,true), RAW_PEAR_MASH(false,true), RAW_POMACE_MASH(false,true), RAW_MIX_MASH(false,true),
	APPLE_MASH(false,false), PEAR_MASH(false,false), POMACE_MASH(false,false), MIX_MASH(false,false);

	private final boolean isStum;
	private final boolean isMash;
}
