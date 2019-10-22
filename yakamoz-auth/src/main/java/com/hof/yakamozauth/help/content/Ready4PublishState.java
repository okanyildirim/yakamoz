package com.hof.yakamozauth.help.content;
import java.util.Set;

public class Ready4PublishState extends AbstractState {
	
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
			return notification;
		}

		content.setState(ContentState.PUBLISHED);
		notification.setNextState(ContentState.PUBLISHED);

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
			return notification;
		}

		channel.setState(ContentState.PUBLISHED);
		notification.setNextState(ContentState.PUBLISHED);

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
	public void removeLicence(Content content, Licence licence) {
		super.removeLicence(content, licence);

		if (!content.hasValidLicence()) {
			content.setState(ContentState.IN_PROGRESS);
		}
	}

	@Override
	public Notification addCategories(Content content, Set<Category> categories2Add) {
		Notification notification = super.addCategories(content, categories2Add);
		if (categories2Add.isEmpty()) {
			notification.setNextState(ContentState.IN_PROGRESS);
			content.setState(ContentState.IN_PROGRESS);
		}

		return notification;
	}

	@Override
	public void setMaterialOperation(Content content, MaterialOperation materialOperation) {
		super.setMaterialOperation(content, materialOperation);
		if (!materialOperation.getVideo()) {
			content.setState(ContentState.IN_PROGRESS);
		}
	}

	@Override
	public void removeTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage titleNDescription) {
		super.removeTitleDescriptionLanguage(content, titleNDescription);
		if (titleNDescription.getLanguage().getIsDefault()) {
			content.setState(ContentState.IN_PROGRESS);
		}
	}

	@Override
	public void removePoster(Content content, ContentPoster contentPoster) {
		super.removePoster(content, contentPoster);

		if (contentPoster.getLanguage().getIsDefault() && contentPoster.getPosterDefinition().getDefaultPoster()) {
			content.setState(ContentState.IN_PROGRESS);
		}
	}

	@Override
	public void fillEncodingParameters(Content content, EncodingParameters encodingParameters) {
		super.fillEncodingParameters(content, encodingParameters);
		if (encodingParameters.getPathWebtv()== null || encodingParameters.getPathWebtv().trim().equals("")){
			content.setState(ContentState.IN_PROGRESS);
		}
	}

}
