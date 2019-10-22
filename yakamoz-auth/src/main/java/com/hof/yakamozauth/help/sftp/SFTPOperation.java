package com.hof.yakamozauth.help.sftp;

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Session;

public class SFTPOperation<K extends SFTPConfigValues, T> {

	private static Logger logger = LogManager.getLogger(SFTPOperation.class);

	public T doOperation(K configValues, SFTPAction<K, T> sftpAction) {
		// TODO OPEN connection
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			JSch jsch = new JSch();
			String preferredAuthentications = configValues.getSftpPreferredAuthentications();
			session = jsch.getSession(configValues.getSftpUsername(), configValues.getSftpHost(), configValues.getSftpPort());
			session.setConfig("PreferredAuthentications", preferredAuthentications);

			if (configValues.getSftpKnownHost() != null)
				jsch.setKnownHosts(configValues.getSftpKnownHost());

			if (configValues.getSftpIdentityKey() != null)
				jsch.addIdentity(configValues.getSftpIdentityKey());

			if (configValues.getSftpPassword() != null)
				session.setPassword(configValues.getSftpPassword());

			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(configValues.getSftpConnectionTimeout());
			sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			// DO Action
			T result = sftpAction.doAction(configValues, sftpChannel);
			sftpChannel.exit();
			session.disconnect();
			return result;
		} catch (BaseRuntimeException bre) {
			throw bre;
		} catch (JSchException e) {
			throw new CmsDomainException(DomainErrorCodes.SFTP_CONNECTION_ERROR, e, e.getMessage());
		} catch (SftpException e) {
			throw new CmsDomainException(DomainErrorCodes.SFTP_ERROR, e, e.getMessage());
		} catch (Exception ex) {
			throw new CmsDomainException(DomainErrorCodes.SFTP_ERROR, ex, ex.getMessage());
		} finally {
			if (sftpChannel != null && sftpChannel.isConnected()) {
				sftpChannel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
	}

}
