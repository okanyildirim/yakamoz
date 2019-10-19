package com.hof.yakamozauth.help.content.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/deal")
@EnableAutoConfiguration
@CrossOrigin("*")
public class DealController {
	private static Logger logger = LogManager.getLogger(DealController.class);

	@Autowired
	DealRepository dealRepository;

	@Autowired
	DealFileRepository dealFileRepository;

	@Autowired
	ConfigValues configValues;
/*
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Long> createDeal(@RequestBody DealRequest dealRequest) {
		DealResponse dealResponse = dealRequest.getDealResponse();

		logger.debug("ENTER|createDeal| dealResponse:" + dealResponse);

		dealResponse.setStatus(DealConsts.STATUS_ACTIVE.getText());
		Deal dealEn = DealAssembler.pojoToEntity(dealResponse);

		LocalDateTime dealStartDate = dealEn.getStartDate();
		LocalDateTime dealEndDate = dealEn.getEndDate();

		Boolean exists = dealRepository.existsByDealNameAndStatusNot(dealEn.getName(), DealConsts.STATUS_DELETED.getText());
		if (exists) {
			throw new CmsBusinessException(5000, "There is already deal with this name/id.\nDeal name must be unique.");
		}

		if (dealStartDate.getTime() >= dealEndDate.getTime()) {
			throw new CmsBusinessException(5000, "Deal end date must be later from start date!");
		}

		Deal deal = dealRepository.save(dealEn);
		logger.debug("CHKVAL|createDeal|dealId:" + deal.getId());

		return ApiResponse.of(deal.getId());
	}

	@RequestMapping(value = "/getbyid/{cid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse<DealResponse> getDealById(@PathVariable("cid") Long dealId) {
		logger.debug("ENTER|getDeal|DealID:" + dealId);
		Deal deal = dealRepository.findById(dealId).orElseThrow(() -> new NotFoundException("Deal not found with id : " + dealId));

		DealResponse dealResponse = DealAssembler.entityToPojo(deal);
		return ApiResponse.of(dealResponse);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	@ResponseBody
	public ApiResponse<DealResponse> editDeal(@RequestBody DealRequest dealRequest) {
		DealResponse dealResponse = dealRequest.getDealResponse();
		logger.debug("ENTER|editDeal|username:" + dealRequest.getUsername() + "|dealResponse:" + dealResponse);

		Deal tempDeal = dealRepository.findById(dealResponse.getDealID()).orElse(null);
		Deal deal = DealAssembler.pojoToEntity(dealResponse);
		deal.setCreatedBy(tempDeal.getCreatedBy());
		deal.setCreatedDate(tempDeal.getCreatedDate());
		deal.setLicences(tempDeal.getLicences());
		deal.setStatus(tempDeal.getStatus());
		Date dealStartDate = deal.getStartDate();
		Date dealEndDate = deal.getEndDate();

		Boolean exists = dealRepository.existsByDealNameAndStatusNotAndIdNot(deal.getName(), DealConsts.STATUS_DELETED.getText(), tempDeal.getId());
		if (exists) {
			throw new CmsBusinessException("There is alrady deal with this name/id.\nDeal name must be unique.");
		}
		if (dealStartDate.getTime() >= dealEndDate.getTime()) {
			throw new CmsBusinessException("Deal end date must be later from start date!");
		}

		dealRepository.save(deal);
		return ApiResponse.empty();
	}

	@RequestMapping(value = "/deletepermanently/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResponse<Long> deleteDealPermanenetly(@PathVariable("id") Long id) {
		logger.debug("ENTER|deleteDealPermanenetly|DealID : " + id);
		dealRepository.deleteById(id);

		return ApiResponse.of(id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResponse<Void> deleteDeal(@PathVariable("id") Long id) {
		logger.debug("ENTER|deleteDeal|dealID : " + id);

		Deal deal = dealRepository.findById(id).orElseThrow(() -> new NotFoundException("This deal cannot be found with id : " + id));

		try {
			dealRepository.delete(deal);
		} catch (DataIntegrityViolationException e) {
			throw new CmsBusinessException("You have to delete active licence before removing the deal.");
		}

		return ApiResponse.of(ApiResponse.SUCCESS, "Deal is deleted successfully.");
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse<Set<DealResponse>> getAllDeals() {
		logger.debug("ENTER|getAllDeals");

		Set<Deal> deals = dealRepository.findAllByStatusNotOrderById(DealConsts.STATUS_DELETED.getText());
		Set<DealResponse> dealResponses = DealAssembler.entityToPojo(deals);

		return ApiResponse.of(dealResponses);
	}

	@RequestMapping(value = "/dealupload", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Void> dealUpload(@RequestParam("crtid") String crtid,
	                                    @RequestParam("file") CommonsMultipartFile file) {

		logger.debug("ENTER|dealUpload|dealId : " + crtid);

		Long dealId = Long.parseLong(crtid);

		Deal deal = dealRepository.findById(dealId).orElse(null);

		try {
			String dealPath = configValues.getDealFilePath() + "/" + file.getName();
			File fileTemp = new File(dealPath);
			Files.copy(file.getInputStream(), fileTemp.toPath(), StandardCopyOption.REPLACE_EXISTING);

			String dealUrl = configValues.getDealFileUrl() + "/" + file.getName();

			DealFile dealFile = new DealFile();
			dealFile.setFileName(file.getName());
			dealFile.setFileLocation(dealUrl);
			dealFile.setDeal(deal);

			dealFileRepository.save(dealFile);

		} catch (IOException e) {
			throw new CmsRuntimeException(e);
		}

		return ApiResponse.of(ApiResponse.SUCCESS, "Deal file was uploaded");
	}*/



	// Posters
	@PostMapping(value = "{contentId}/posters")
	public ApiResponse<Void> addPoster(@PathVariable Long contentId,
	                                   @RequestParam(required = false) Long contentPosterId,
	                                   @RequestParam Long languageId,
	                                   @RequestParam Long posterDefinitionId,
	                                   @RequestParam String externalPosterUrl,
	                                   @RequestParam(required = false) MultipartFile file) {

		ContentPoster contentPoster = null;
		Content content = contentRepository.findById(contentId).orElse(null);
		Language language = languageRepository.findById(languageId).orElse(null);
		PosterDefinition posterDefinition = posterDefinitionRepository.findById(posterDefinitionId).orElse(null);

		if (content == null) {
			throw new CmsBusinessException(1001, "Content is not found with this id!");
		}

		if (language == null) {
			throw new CmsBusinessException(1001, "Language is not found with this id!");
		}

		if (contentPosterId == null) {
			//insert
			if (file == null) {
				throw new CmsBusinessException(1001, "Poster image is empty!");
			}
			contentPoster = new ContentPoster();

			if (posterDefinition == null) {
				throw new CmsBusinessException(1001, "Poster Definition is not found with this id!");
			}
			if (content.getPosters().stream().anyMatch(poster -> poster.getLanguage().equals(language) && poster.getPosterDefinition().equals(posterDefinition)))
				throw new CmsBusinessException(1003, "Duplicate poster.");

			contentPoster.setPosterDefinition(posterDefinition);

			Utility.imageValidate(file, posterDefinition.getWidth(), posterDefinition.getHeight(), posterDefinition.getMaxSize());

		} else {
			//update
			contentPoster = contentPosterRepository.findById(contentPosterId).orElse(null);
			if (contentPoster == null) {
				throw new CmsBusinessException(1001, "Poster is not found with this id!");
			}

			if (content.getPosters().stream().anyMatch(poster -> poster.getLanguage().equals(language) && poster.getPosterDefinition().equals(posterDefinition) && !poster.getId().equals(contentPosterId)))
				throw new CmsBusinessException(1003, "Duplicate poster.");

			if (file != null) {
				posterService.deletePosterFromCDN(Content.class.getSimpleName().toLowerCase() + "_" +content.getGuid().trim(), contentPoster.getFileName());
				contentPosterRepository.delete(contentPoster);

				ContentPoster newContentPoster = new ContentPoster();
				newContentPoster.setPosterDefinition(contentPoster.getPosterDefinition());

				contentPoster = newContentPoster;
			}


		}

		contentPoster.setLanguage(language);
		contentPoster.setExternalUrl(externalPosterUrl);
		contentPoster.setContent(content);

		contentService.addPosterService(contentPoster, file);

		return ApiResponse.empty();
	}


	public void addPosterService(ContentPoster contentPoster, MultipartFile file) {
		if (file != null) {
			String imageType = ".jpg";
			if (file.getContentType().equals("image/png")) {
				imageType = ".png";
			}
			contentPoster.setFileSize(Utility.getFileSize(file.getSize()));
			contentPoster.setFileName(contentPoster.getGuid() + imageType);

			String mainFolderName = Content.class.getSimpleName().toLowerCase() + "_" + contentPoster.getContent().getGuid().trim();
			String posterViewURL = posterService.getPosterViewURLAfterPosterUpload(file, mainFolderName, contentPoster.getFileName());

			contentPoster.setUrl(posterViewURL);
		}
		contentPosterRepository.save(contentPoster);
	}


	public String getPosterViewURLAfterPosterUpload(MultipartFile file, String mainFolderName, String fileName) {
		SFTPOperation<PosterSFTPConfigValues, String> operation = new SFTPOperation();
		return operation.doOperation(posterSFTPConfigValues, (config, channel) -> addPosterConfigFile(config, channel, file, mainFolderName, fileName));
	}

	private String addPosterConfigFile(PosterSFTPConfigValues configValues, ChannelSftp sftpChannel, MultipartFile file, String mainFolderName, String fileName) {
		try {
			sftpChannel.cd(configValues.getSftpPosterUploadFolder());
			String mainFolder = mainFolderName.trim() + "_" + executionContextService.getCurrentTenantCode().toLowerCase();
			try {
				sftpChannel.cd(mainFolder);
			} catch (final SftpException e) {
				// dir does not exist.
				if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
					sftpChannel.mkdir(mainFolder);
					//755 octal = 493 dec
					sftpChannel.chmod(493, mainFolder);
					sftpChannel.cd(mainFolder);
				}
			}
			sftpChannel.put(file.getInputStream(), fileName);

			return configValues.getSftpPosterViewFolder() + "/" + mainFolder + "/" + fileName;

		} catch (Exception e) {
			throw new CmsRuntimeException(1005L, "There was an error when poster upload.", e);
		}
	}
}
