package com.hof.yakamozauth.help.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ConfigValues {

	@Value("${content-server-resource-path}")
	private String contentPath;

	@Value("${content-server-resource-url}")
	private String contentResourceUrl;

	@Value("${watch-folder-path:emre}")
	private String watchFolderPath;

	@Value("${metadata-file-path}")
	private String metadataFilePath;

	@Value("${deal-file-path}")
	private String dealFilePath;

	@Value("${deal-file-url}")
	private String dealFileUrl;

	@Value("${channel-logo-path}")
	private String channelLogoPath;

	@Value("${channel-logo-url}")
	private String channelLogoUrl;

	@Value("${maturity-level-path}")
	private String maturityLevelPath;

	@Value("${maturity-level-url}")
	private String maturityLevelUrl;
}
