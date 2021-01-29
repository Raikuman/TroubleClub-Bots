package com.reliquary.crow.commands.music;

import com.reliquary.crow.commands.manager.CommandContext;
import com.reliquary.crow.commands.manager.CommandInterface;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class Leave implements CommandInterface {

	@Override
	public void handle(CommandContext ctx) {

		final TextChannel channel = ctx.getChannel();
		final Member self = ctx.getSelfMember();
		final GuildVoiceState selfVoiceState = self.getVoiceState();

		// Check if the bot is in a voice channel
		if (selfVoiceState.inVoiceChannel()) {
			channel.sendMessage("I'm already in a voice channel: `" + selfVoiceState.getChannel() + "`")
				.delay(Duration.ofSeconds(10))
				.flatMap(Message::delete)
				.queue();
			return;
		}

		// Check if a user is in a voice channel
		final Member member = ctx.getMember();
		final GuildVoiceState memberVoiceState = member.getVoiceState();

		if (!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("You must be in a voice channel to use this command")
				.delay(Duration.ofSeconds(10))
				.flatMap(Message::delete)
				.queue();
			return;
		}

		// Leave voice channel
		AudioManager audioManager = ctx.getGuild().getAudioManager();
		audioManager.closeAudioConnection();
	}

	@Override
	public String getInvoke() {
		return "leave";
	}

	@Override
	public String getHelp() {
		return "Leaves the voice channel";
	}

	@Override
	public String getUsage() {
		return "";
	}

	@Override
	public String getCategory() {
		return "music";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<>(Collections.singletonList(
			"fuckoff"
		));
	}
}