package com.reliquary.crow.resources.pagination.buttons;

import com.reliquary.crow.managers.componentmanagers.buttons.ButtonContext;
import com.reliquary.crow.managers.componentmanagers.buttons.ButtonInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.UpdateInteractionAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is part of the pagination implementation that handles the right button
 *
 * @version 1.1 2021-16-12
 * @since 1.0
 */
public class PageRight implements ButtonInterface {

	private final String invoke;
	private List<EmbedBuilder> embedBuilderList;

	public PageRight(String invoke) {
		this.invoke = invoke;
		this.embedBuilderList = new ArrayList<>();
	}

	@Override
	public void handle(ButtonContext ctx) {

		String[] pages =
			Objects.requireNonNull(
				ctx.getEvent().getMessage().getEmbeds().get(0).getTitle()
			).trim().split("\\s+")[1].split("/");

		int currentPage = Integer.parseInt(pages[0]);
		int lastPage = Integer.parseInt(pages[1]);
		int newPage;

		if (currentPage != lastPage) {
			newPage = currentPage + 1;
		} else {
			ctx.getEvent().deferEdit().queue();
			return;
		}

		// Set buttons
		List<Button> buttonList = ctx.getButtons();
		if (newPage == lastPage) {

			buttonList.set(buttonList.indexOf(ctx.getEvent().getButton()),
				Objects.requireNonNull(ctx.getEvent().getButton()).asDisabled());

		} else {

			for (Button button : buttonList) {
				if (button.isDisabled()) {
					buttonList.set(buttonList.indexOf(button), button.asEnabled());
				}
			}

		}

		embedBuilderList = setEmbedBuilderList(ctx);

		UpdateInteractionAction updateAction = ctx.getUpdateInteraction();
		updateAction = updateAction.setActionRow(buttonList);
		updateAction = updateAction.setEmbeds(embedBuilderList.get(newPage - 1).build());
		updateAction.queue();
	}

	@Override
	public String getButtonId() {
		return invoke + "right";
	}

	@Override
	public Emoji getEmoji() {
		return Emoji.fromMarkdown("➡️");
	}

	@Override
	public String getLabel() {
		return null;
	}

	/**
	 * This method provides a way to dynamically set the embed list when the invoke is called
	 * @param ctx Provides the button context
	 * @return Returns the list of embeds
	 */
	public List<EmbedBuilder> setEmbedBuilderList(ButtonContext ctx) {
		return null;
	}
}