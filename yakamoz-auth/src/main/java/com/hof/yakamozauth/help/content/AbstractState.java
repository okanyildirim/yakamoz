package com.hof.yakamozauth.help.content;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

abstract class AbstractState implements State {

	static String INVALID_LICENCE_MESSAGE = "Cannot find valid Licence!";

	@Override
	public void fillEncodingParameters(Content content, EncodingParameters encodingParameters) {
		content.setEncodingParameters(encodingParameters);
	}

	@Override
	public void removeLicence(Content content, Licence licence) {
		Set<Licence> contentLicences = content
				.getLicences()
				.stream()
				.filter(cl -> !cl.equals(licence))
				.collect(Collectors.toSet());
		content.setLicences(contentLicences);
	}

	@Override
	public void update(Content content, ContentType contentType, String title, Integer releaseYear, String imdb, String studio, ContentBOCategory boCategory, Long duration, ScreenFormat screenFormat, PublishPoint publishPoint,
	                   Boolean isLocal, MaturityLevel maturityLevel, Set<Definition> definitions, VodType vodType, String externalId) {
		content.setName(title);
		if (releaseYear != null) {
			content.setReleaseYear(releaseYear);
		}
		content.setImdbID(imdb);
		content.setStudio(studio);
		content.setContentBOCategory(boCategory);
		if (duration != null) {
			content.setDuration(duration);
		}
		content.setScreenFormat(screenFormat);
		if (publishPoint != null) {
			content.setPublishPoint(publishPoint);
		}

		content.setIsLocal(isLocal);
		content.setMaturityLevel(maturityLevel);

		if (definitions != null) {
			content.setDefinitions(definitions);
		}
		if (vodType != null) {
			content.setVodType(vodType);
		}
		content.setExternalId(externalId);
	}

	@Override
	public void addCategory(Content content, Category category) {
		Set<Category> categories = content.getCategories();
		if (category.getPublishPoint().getValue() < content.getPublishPoint().getValue()) {
			throw new ContentUpdateException("Publish point must be the same!");
		}
		categories.add(category);
		content.setCategories(categories);
	}

	@Override
	public void addCountry(Content content, Country country) {
		Set<Country> countries = content.getCountries();
		countries.add(country);
		content.setCountries(countries);
	}

	@Override
	public void removeCountry(Content content, Country country) {
		Set<Country> countries = content.getCountries();
		countries.remove(country);
		content.setCountries(countries);
	}

	@Override
	public void addTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage contentTitleDescriptionLanguage) {
		Set<ContentTitleDescriptionLanguage> descriptionLanguages = content.getContentTitleDescriptionLanguages();
		descriptionLanguages.add(contentTitleDescriptionLanguage);
		content.setContentTitleDescriptionLanguages(descriptionLanguages);
	}

	@Override
	public void addPoster(Content content, ContentPoster contentPoster) {
		Set<ContentPoster> posters = content.getPosters();
		posters.add(contentPoster);
		content.setPosters(posters);
	}

	@Override
	public void addGenre(Content content, Set<Genre> genres2Add) {
		content.setGenres(genres2Add);
	}

	@Override
	public void addParentalAdvisory(Content content, Set<ParentalAdvisory> parentalAdvisories2Add) {
		content.setParentalAdvisories(parentalAdvisories2Add);
	}

	@Override
	public void fillDefinitions(Content content, Set<Definition> definitions) {
		content.setDefinitions(definitions);
	}

	@Override
	public void fillMaturityLevel(Content content, MaturityLevel maturityLevel) {
		content.setMaturityLevel(maturityLevel);
	}

	@Override
	public Notification addCategories(Content content, Set<Category> categories2Add) {
		Set<Category> categories = content.getCategories();
		categories.clear();
		categories2Add.stream().filter(Category::isLeafNode).forEach(c -> {
			c.getContents().remove(content);
			c.getContents().add(content);
			categories.add(c);
		});

		return new Notification();
	}

	Notification checkReadyForPublish(Content content) {
		Notification notification = new Notification();
		checkDefaultPosters(notification, content);
		checkMetadata(notification, content);
		checkRawMaterial(notification, content);
		checkCategory(notification, content);
		checkLicence(notification, content);
		checkPublishPoint(notification, content);
		checkPlayUrls(notification, content);
		checkMaturityLevel(notification, content);
		checkEncodingParameters(notification,content);
		return notification;
	}

	private void checkMaturityLevel(Notification notification, Content content) {
		MaturityLevel maturityLevel = content.getMaturityLevel();
		if (maturityLevel == null) {
			notification.addNotification("You should set maturity level!");
		}
	}

	private void checkPlayUrls(Notification notification, Content content) {
		DrmType[] types = DrmType.values();
		Set<ContentPlayUrl> playUrls = content.getContentPlayUrls().stream().filter(p -> p.getPlayUrl() != null && !p.getPlayUrl().trim().isEmpty()).collect(Collectors.toSet());
		for (DrmType drmType : types) {
			if (playUrls.stream().noneMatch(p -> p.getDrmType().equals(drmType))) {
				notification.addNotification(drmType + " play url doesn't exist!");
			}
		}
	}

	Notification checkReadyForPublish(LiveChannel channel) {
		Notification notification = new Notification();
		checkMetadata(notification,channel);
		checkDefaultPosters(notification, channel);
		checkCategory(notification, channel);
		checkLicence(notification, channel);
		checkPublishPoint(notification, channel);
		checkTitleAndChannelNo(notification, channel);
		checkPlayUrls(notification, channel);
		return notification;
	}

	private void checkTitleAndChannelNo(Notification notification, LiveChannel channel) {
		if (channel.getName() == null) {
			notification.addNotification("You should set title!");
		}

		if (channel.getNo() == null) {
			notification.addNotification("You should set channel number!");
		}
	}

	private void checkPublishPoint(Notification notification, Content content) {
		if (content.getPublishPoint() == null) {
			notification.addNotification("You should set a publish point!");
		}
	}

	private void checkLicence(Notification notification, Content content) {
		Boolean hasValidLicence = content.hasValidLicence();
		if (!hasValidLicence) {
			notification.addNotification(INVALID_LICENCE_MESSAGE);
		}
	}

	private void checkEncodingParameters(Notification notification, Content content) {
		if (content.getEncodingParameters()!=null) {
			if(content.getEncodingParameters().getPathWebtv()==null || content.getEncodingParameters().getPathWebtv().trim().equals(""))
			notification.addNotification("You should set webtv path on encoding parameters!");
		}else{
			notification.addNotification("You should set webtv path on encoding parameters!");
		}
	}


	private void checkCategory(Notification notification, Content content) {
		if (content.getCategories().isEmpty()) {
			notification.addNotification("You should set at least one category!");
		}
	}

	private void checkRawMaterial(Notification notification, Content content) {
		String rawMediaFileErrorMessage = "You should check raw video file!";
		Set<VODOperation> operations = content.getVodOperations();

		if (operations.isEmpty()) {
			notification.addNotification(rawMediaFileErrorMessage);
			return;
		}

		VODOperation vodOperation = operations.stream().findFirst().get();
		MaterialOperation materialOperation = vodOperation.getMaterialOperation();
		if (materialOperation == null) {
			notification.addNotification(rawMediaFileErrorMessage);
			return;
		}

		Boolean isVideoSet = materialOperation.getVideo();

		if (isVideoSet == null || !isVideoSet) {
			notification.addNotification(rawMediaFileErrorMessage);
		}
	}

	private void checkDefaultPosters(Notification notification, Content content) {
		boolean isDefaultPosterSet = false;
		Set<ContentPoster> contentPosters = content.getPosters();
		if (contentPosters != null) {
			for (ContentPoster contentPoster : contentPosters) {
				if (contentPoster.getLanguage().getIsDefault() && contentPoster.getPosterDefinition().getDefaultPoster()) {
					isDefaultPosterSet = true;
					break;
				}
			}
		}

		if (!isDefaultPosterSet) {
			notification.addNotification("You should set default poster!");
		}
	}

	private void checkMetadata(Notification notification, Content content) {
		List<ContentTitleDescriptionLanguage> contentTitleDescriptionLanguages = new ArrayList<>(content.getContentTitleDescriptionLanguages());
		boolean isDefaultTitleDescSet = false;
		for (ContentTitleDescriptionLanguage titleDescriptionLanguage : contentTitleDescriptionLanguages) {
			if (titleDescriptionLanguage.getLanguage().getIsDefault()) {
				isDefaultTitleDescSet = true;
				break;
			}
		}

		if (!isDefaultTitleDescSet) {
			notification.addNotification("You should set default title&description!");
		}
	}

	@Override
	public void addLicence(Content content, Licence licence) {

		if (licence.getLicenceType() == LicenceType.CHANNEL && content.getContentType() != ContentType.LIVE_CHANNEL) {
			throw new ContentUpdateException("Licence Type is not LIVE_CHANNEL. You can not add this licence");
		}

		if (licence.getLicenceType() != LicenceType.CHANNEL && content.getContentType() == ContentType.LIVE_CHANNEL) {
			throw new ContentUpdateException("Licence Type is not LIVE_CHANNEL. You can not add this licence");
		}

		Set<Licence> contentLicences = content.getLicences();
		contentLicences.add(licence);
		content.setLicences(contentLicences);
	}

	@Override
	public void setMaterialOperation(Content content, MaterialOperation materialOperation) {

		Set<VODOperation> vodOperations = content.getVodOperations();
		if (!vodOperations.isEmpty()) {
			VODOperation myVodOperation = vodOperations.stream().findFirst().get();
			myVodOperation.setMaterialOperation(materialOperation);
			myVodOperation.setContent(content);
			content.addVodOperation(myVodOperation);
		} else {
			VODOperation vodOperation = new VODOperation();
			vodOperation.setMaterialOperation(materialOperation);
			vodOperation.setContent(content);
			content.addVodOperation(vodOperation);
		}
	}

	@Override
	public void removeTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage titleNDescription) {
		content.getContentTitleDescriptionLanguages().remove(titleNDescription);
	}

	@Override
	public void removePoster(Content content, ContentPoster contentPoster) {
		content.getPosters().remove(contentPoster);
	}

	@Override
	public void update(LiveChannel channel, String title, Integer no, PublishPoint publishPoint, String externaId, String url, ChannelType channelType,
	                   Definition definition, ChannelInput channelInput, Scale scale, Boolean NPVR, Boolean TSRV, Boolean encriypted, MaturityLevel maturityLevel) {

		channel.setName(title);
		channel.setNo(no);
		channel.setPublishPoint(publishPoint);
		channel.setExternalId(externaId);
		channel.setUrl(url);
		channel.setType(channelType);
		channel.setDefinition(definition);
		channel.setInput(channelInput);
		channel.setScale(scale);
		channel.setNpvr(NPVR);
		channel.setTstv(TSRV);
		channel.setEncrypted(encriypted);
		channel.setMaturityLevel(maturityLevel);
	}
}
