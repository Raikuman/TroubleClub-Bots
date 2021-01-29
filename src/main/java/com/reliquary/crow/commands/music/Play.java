package com.reliquary.crow.commands.music;

import com.reliquary.crow.commands.manager.CommandContext;
import com.reliquary.crow.commands.manager.CommandInterface;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@SuppressWarnings("ConstantConditions")
public class Play implements CommandInterface {

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

		// Check if the bot has permission to join the voice channel
		if (!self.hasPermission(Permission.VOICE_CONNECT)) {
			channel.sendMessage("I don't have permission to join `" + memberVoiceState.getChannel().toString() + "`")
				.delay(Duration.ofSeconds(10))
				.flatMap(Message::delete)
				.queue();
			return;
		}

		// Check if args are empty
		if (ctx.getArgs().isEmpty()) {
			channel.sendMessage("You must enter a valid link or search for a video")
				.delay(Duration.ofSeconds(10))
				.flatMap(Message::delete)
				.queue();
			return;
		}

		// Youtube Search
		String link = String.join(" ", ctx.getArgs());

		if (isUrl(link)) {
			link = "ytsearch:" + link;
		}

		// Get audio manager for guild
		final AudioManager audioManager = ctx.getGuild().getAudioManager();
		final VoiceChannel memberChannel = memberVoiceState.getChannel();

		// Join audio channel
		audioManager.openAudioConnection(memberChannel);



	}

	@Override
	public String getInvoke() {
		return "play";
	}

	@Override
	public String getHelp() {
		return "Play a song from a link or playlist";
	}

	@Override
	public String getUsage() {
		return "<link>";
	}

	@Override
	public String getCategory() {
		return "music";
	}

	/*
	isUrl
	Check if the string is a url
	 */
	private boolean isUrl(String url) {

		try {
			new URI(url);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}