package com.hof.yakamozauth.help.content.controller.ftpconfig;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FtpConfigService {

	private final FtpConfigRepository ftpConfigRepository;

	public void createFtpConfig(FtpConfigRequest request) {

		if (ftpConfigRepository.existsByOperationType(request.getOperationType())){
			throw new CmsBusinessException(request.getOperationType() + " operation type is already defined!");
		}
		FtpConfig ftpConfig = new FtpConfig();

		ftpConfig.setHost(request.getHost());
		ftpConfig.setPort(request.getPort());
		ftpConfig.setFolderDirectory(request.getFolderDirectory());
		ftpConfig.setUsername(request.getUsername());
		ftpConfig.setPrefferedAuthentication(request.getPrefferedAuthentication());
		ftpConfig.setFtpType(request.getFtpType());
		ftpConfig.setOperationType(request.getOperationType());

		if(request.getPrefferedAuthentication().equals(PrefferedAuthentication.PASSWORD)){
			ftpConfig.setPassword(request.getPassword());
		}

		if (request.getPrefferedAuthentication().equals(PrefferedAuthentication.PRIVATE_KEY)){
			ftpConfig.setKnownHost(request.getKnownHost());
			ftpConfig.setIdentityKey(request.getIdentityKey());
		}

		ftpConfigRepository.save(ftpConfig);
	}

	public List<FtpConfigResponse> getFtpConfigs() {
		List<FtpConfigResponse> response = new ArrayList<>();
		List<FtpConfig> ftpConfigs = ftpConfigRepository.findAllByOrderById();

		ftpConfigs.forEach(f->response.add(new FtpConfigResponse().entityParseToFtpResponse(f)));

		return response;
	}

	public FtpConfigResponse getFtpConfig(Long id) {
		FtpConfig ftpConfig = ftpConfigRepository.findById(id).orElseThrow(()-> new  NotFoundException("Ftp Config is not found!"));
		return new FtpConfigResponse().entityParseToFtpResponse(ftpConfig);
	}

	public void updateFtpConfig(Long id, FtpConfigRequest request) {

		FtpConfig ftpConfig = ftpConfigRepository.findById(id).orElseThrow(()-> new  NotFoundException("Ftp Config is not found!"));

		if (Utility.isNotNullAndEmptyString(request.getHost())){
			ftpConfig.setHost(request.getHost());
		}
		if (Utility.isNotNullAndEmptyString(request.getPort())){
			ftpConfig.setPort(request.getPort());
		}
		if (Utility.isNotNullAndEmptyString(request.getFolderDirectory())){
			ftpConfig.setFolderDirectory(request.getFolderDirectory());
		}
		if (Utility.isNotNullAndEmptyString(request.getUsername())){
			ftpConfig.setUsername(request.getUsername());
		}
		if (request.getPrefferedAuthentication()!= null){
			ftpConfig.setPrefferedAuthentication(request.getPrefferedAuthentication());

			if(request.getPrefferedAuthentication().equals(PrefferedAuthentication.PASSWORD)){
				ftpConfig.setIdentityKey(null);
				ftpConfig.setKnownHost(null);
				ftpConfig.setPassword(request.getPassword());
			}

			if (request.getPrefferedAuthentication().equals(PrefferedAuthentication.PRIVATE_KEY)){
				ftpConfig.setPassword(null);
				ftpConfig.setKnownHost(request.getKnownHost());
				ftpConfig.setIdentityKey(request.getIdentityKey());
			}

		}
		if (request.getFtpType()!=null){
			ftpConfig.setFtpType(request.getFtpType());
		}
		if (request.getOperationType()!=null){
			if(ftpConfigRepository.existsByOperationTypeAndIdNot(request.getOperationType(),id)){
				throw new CmsBusinessException(request.getOperationType() + " operation oype is already defined!");
			}
			ftpConfig.setOperationType(request.getOperationType());
		}
		ftpConfigRepository.save(ftpConfig);
	}

	public void delete(Long id) {
		 if (!ftpConfigRepository.existsById(id)){
		 	throw new  NotFoundException("Ftp Config is not found!");
		 }
		ftpConfigRepository.deleteById(id);
	}

	public void test(FtpConfigRequest ftpInfo) {
		FtpTestConfigValues testConfigValues = new FtpTestConfigValues();
		testConfigValues.setSftpHost(ftpInfo.getHost());
		testConfigValues.setSftpPort(Integer.parseInt(ftpInfo.getPort()));
		testConfigValues.setSftpUsername(ftpInfo.getUsername());
		testConfigValues.setSftpPassword(ftpInfo.getPassword());
		testConfigValues.setSftpFileUploadFolder(ftpInfo.getFolderDirectory());
		testConfigValues.setSftpIdentityKey(ftpInfo.getIdentityKey());
		testConfigValues.setSftpKnownHost(ftpInfo.getKnownHost());
		testConfigValues.setSftpPreferredAuthentications(ftpInfo.getPrefferedAuthentication().getValue().toLowerCase().replaceAll("\\s+",""));
		testConfigValues.setSftpConnectionTimeout(3000);

		SFTPOperation<FtpTestConfigValues,Boolean> operation =  new SFTPOperation();
		
		operation.doOperation(testConfigValues,((configValues, sftpChannel) -> connectTest(configValues,sftpChannel)));
	}

	private Boolean connectTest(FtpTestConfigValues configValues, ChannelSftp sftpChannel) {
		try {
			sftpChannel.cd(configValues.getSftpFileUploadFolder());
		} catch (SftpException e) {
			throw new RuntimeException( "Connection failed!",e);
		}
		return true;
	}
}
