package com.hof.yakamozauth.help.content;


import java.util.Set;

public interface State {

	public Notification next(Content content);

	public Notification next(LiveChannel channel);

	public Notification previous(Content content);

	public Notification previous(LiveChannel channel);

	void fillEncodingParameters(Content content, EncodingParameters encodingParameters);

	void update(Content content, ContentType contentType, String title, Integer releaseYear, String imdb, String studio, ContentBOCategory boCategory, Long duration, ScreenFormat screenFormat, PublishPoint publishPoint, Boolean isLocal, MaturityLevel maturityLevel, Set<Definition> definitions, VodType vodType, String externalId);

	void addCategory(Content content, Category category);

	void addCountry(Content content, Country country);

	void removeCountry(Content content, Country country);

	void addTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage contentTitleDescriptionLanguage);

	void addPoster(Content content, ContentPoster contentPoster);

	void addGenre(Content content, Set<Genre> genre);

	void addParentalAdvisory(Content content, Set<ParentalAdvisory> parentalAdvisory);

	void fillDefinitions(Content content, Set<Definition> definitions);

	void fillMaturityLevel(Content content, MaturityLevel maturityLevel);

	void removeLicence(Content content, Licence licence);

	void addLicence(Content content, Licence licence);

	Notification addCategories(Content content, Set<Category> categories);

	void setMaterialOperation(Content content, MaterialOperation materialOperation);

	void removeTitleDescriptionLanguage(Content content, ContentTitleDescriptionLanguage titleNDescription);

	void removePoster(Content content, ContentPoster contentPoster);

	void update(LiveChannel channel, String title, Integer no, PublishPoint publishPoint, String externaId, String url, ChannelType channelType,
	            Definition definition, ChannelInput channelInput, Scale scale, Boolean NPVR, Boolean TSRV, Boolean encriypted, MaturityLevel maturityLevel);
}
