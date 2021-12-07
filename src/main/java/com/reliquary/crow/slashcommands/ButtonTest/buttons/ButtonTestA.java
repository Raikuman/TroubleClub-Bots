package com.reliquary.crow.slashcommands.ButtonTest.buttons;

import com.reliquary.crow.componentmanagers.buttons.ButtonContext;
import com.reliquary.crow.componentmanagers.buttons.ButtonInterface;
import com.reliquary.crow.componentmanagers.buttons.ButtonResources;
import com.reliquary.crow.resources.other.RandomColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.requests.restaction.interactions.UpdateInteractionAction;

import java.util.ArrayList;

public class ButtonTestA implements ButtonInterface {

	@Override
	public void handle(ButtonContext ctx) {

		if (ctx.getEvent().getButton() == null)
			return;

		// Base test embed
		EmbedBuilder builder = new EmbedBuilder()
			.setTitle("This is a button test")
			.setColor(RandomColor.getRandomColor());
		StringBuilder descriptionBuilder = builder.getDescriptionBuilder();

		descriptionBuilder
			.append("Only a test for buttons");

		UpdateInteractionAction updateAction = ctx.getUpdateInteraction();
		updateAction = updateAction.setEmbeds(builder.build());

		updateAction = updateAction.setActionRows(ButtonResources.getActionRows(
			new ArrayList<>(ButtonResources.setCurrentButtonDisabled(
				ctx.getButtons(), getButtonId()
			))
		));

		updateAction.queue();
	}

	@Override
	public String getButtonId() {
		return "buttontesta";
	}

	@Override
	public Emoji getEmoji() {
		return null;
	}

	@Override
	public String getLabel() {
		return "A";
	}
}
