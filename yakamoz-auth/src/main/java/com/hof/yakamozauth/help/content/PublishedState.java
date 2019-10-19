package com.hof.yakamozauth.help.content;

import java.util.Set;

public class PublishedState extends AbstractState {

	@Override
	public Notification next(Content content) {
		Notification notification = checkReadyForPublish(content);
		if (!notification.isValid()) {
			content.setState(ContentState.IN_PROGRESS);
			notification.setNextState(ContentState.IN_PROGRESS);
			return notification;
		}

		if (content.getActiveLicence() == null) {
			notification.addNotification("There is no active licence!");
			content.setState(ContentState.READY_FOR_PUBLISH);
			notification.setNextState(ContentState.READY_FOR_PUBLISH);
			return notification;
		}

		return notification;
	}

	@Override
	public Notification previous(Content content) {
		Notification notification = new Notification();
		content.setState(ContentState.IN_PROGRESS);
		notification.setNextState(ContentState.IN_PROGRESS);
		return notification;
	}

	@Override
	public Notification next(LiveChannel channel) {
		Notification notification = checkReadyForPublish(channel);
		if (!notification.isValid()) {
			channel.setState(ContentState.IN_PROGRESS);
			notification.setNextState(ContentState.IN_PROGRESS);
			return notification;
		}

		if (channel.getActiveLicence() == null) {
			notification.addNotification("Channel has no active licence!");
			channel.setState(ContentState.READY_FOR_PUBLISH);
			notification.setNextState(ContentState.READY_FOR_PUBLISH);
			return notification;
		}

		return notification;
	}

	@Override
	public Notification previous(LiveChannel channel) {
		Notification notification = new Notification();
		channel.setState(ContentState.IN_PROGRESS);
		notification.setNextState(ContentState.IN_PROGRESS);
		return notification;
	}

	@Override
	public Notification addCategories(Content content, Set<Category> categories2Add) {
		if (categories2Add.isEmpty()) {
			throw new ContentUpdateException("You cannot remove all categories in PUBLISHED state!");
		}

		return super.addCategories(content, categories2Add);
	}

	@Override
	public void removeLicence(Content content, Licence licence) {
		Licence activeLicence = content.getActiveLicence();
		if (licence.equals(activeLicence)) {
			throw new ContentUpdateException("You cannot remove active licence in PUBLISHED state!");
		}
		super.removeLicence(content, licence);
	}

	@Override
	public void addCategory(Content content, Category category) {
		throw new ContentUpdateException("You cannot add category in PUBLISHED state!");
	}

	@Override
	public void addCountry(Content content, Country country) {
		throw new ContentUpdateException("You cannot add country in PUBLISHED state!");
	}

	@Override
	public void removeCountry(Content content, Country country) {
		throw new ContentUpdateException("You cannot remove country in PUBLISHED state!");
	}

	@Override
	public void addTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage contentTitleDescriptionLanguage) {
		throw new ContentUpdateException("You cannot add Title&Description in PUBLISHED state!");
	}

	@Override
	public void fillDefinitions(Content content, Set<Definition> definitions) {
		throw new ContentUpdateException("You cannot set content definition in PUBLISHED state!");
	}

	@Override
	public void fillMaturityLevel(Content content, MaturityLevel maturityLevel) {
		throw new ContentUpdateException("You cannot set maturity level in PUBLISHED state!");
	}

	@Override
	public void setMaterialOperation(Content content, MaterialOperation materialOperation) {
		throw new ContentUpdateException("You cannot switch raw media file in PUBLISHED state!");
	}

	@Override
	public void removeTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage titleNDescription) {

		if (titleNDescription.getLanguage().getIsDefault()) {
			throw new ContentUpdateException("You cannot remove Title & Description in PUBLISHED state!");
		}
	}

	@Override
	public void removePoster(Content content, ContentPoster contentPoster) {
		if (contentPoster.getLanguage().getIsDefault() && contentPoster.getPosterDefinition().getDefaultPoster()) {
			throw new ContentUpdateException("You cannot remove Poster with Default Language and Default Poster Definition in PUBLISHED state!");
		}
		super.removePoster(content, contentPoster);
	}

	@Override
	public void update(LiveChannel channel, String title, Integer no, PublishPoint publishPoint, String externaId, String url, ChannelType channelType,
	                   Definition definition, ChannelInput channelInput, Scale scale, Boolean NPVR, Boolean TSRV, Boolean encrypted, MaturityLevel maturityLevel) {
		throw new ContentUpdateException("You cannot update channel in PUBLISHED state!");
	}

	@Override
	public void fillEncodingParameters(Content content, EncodingParameters encodingParameters) {
		throw new ContentUpdateException("You cannot update content in PUBLISHED state!");
	}
}
