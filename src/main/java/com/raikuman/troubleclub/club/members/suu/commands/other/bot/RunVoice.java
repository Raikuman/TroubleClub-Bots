package com.raikuman.troubleclub.club.members.suu.commands.other.bot;

import com.raikuman.botutilities.commands.manager.CategoryInterface;
import com.raikuman.botutilities.commands.manager.CommandContext;
import com.raikuman.botutilities.commands.manager.CommandInterface;
import com.raikuman.botutilities.configs.ConfigIO;
import com.raikuman.botutilities.configs.EnvLoader;
import com.raikuman.troubleclub.club.category.OtherCategory;
import com.raikuman.troubleclub.club.statemanager.managers.voice.VoiceStateManager;

import java.util.List;

/**
 * Handles force running the voice state
 *
 * @version 1.0 2023-22-02
 * @since 1.0
 */
public class RunVoice implements CommandInterface {
	@Override
	public void handle(CommandContext ctx) {
		if (Boolean.parseBoolean(ConfigIO.readConfig("state", "disableStateCommands")))
			return;

		if (!ctx.getEventMember().getId().equals(EnvLoader.get("ownerid")))
			return;

		VoiceStateManager.getInstance().updateConnectionsState();
	}

	@Override
	public String getInvoke() {
		return "runvoice";
	}

	@Override
	public String getUsage() {
		return "";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public List<String> getAliases() {
		return List.of("rv");
	}

	@Override
	public CategoryInterface getCategory() {
		return new OtherCategory();
	}
}