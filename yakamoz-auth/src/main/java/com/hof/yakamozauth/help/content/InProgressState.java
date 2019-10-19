package com.hof.yakamozauth.help.content;

public class InProgressState extends AbstractState {

	@Override
	public Notification next(Content content) {
		Notification notification = checkReadyForPublish(content);
		if (notification.isValid()) {
			content.setState(ContentState.READY_FOR_PUBLISH);
			notification.setNextState(ContentState.READY_FOR_PUBLISH);
		}
		return notification;
	}

	@Override
	public Notification previous(Content content) {
		return new Notification();
	}

	@Override
	public Notification next(LiveChannel channel) {
		Notification notification = checkReadyForPublish(channel);

		if (notification.isValid()) {
			channel.setState(ContentState.READY_FOR_PUBLISH);
			notification.setNextState(ContentState.READY_FOR_PUBLISH);
		}
		return notification;
	}

	@Override
	public Notification previous(LiveChannel channel) {
		return new Notification();
	}
}
