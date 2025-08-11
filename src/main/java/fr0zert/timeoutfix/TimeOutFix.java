package fr0zert.timeoutfix;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeOutFix implements ModInitializer {
	public static final String MOD_ID = "TimeOutFix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigLoader.INSTANCE = new ConfigLoader();
	}
}