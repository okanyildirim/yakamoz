package com.hof.yakamozauth.help.sftp;

import com.jcraft.jsch.ChannelSftp;

@FunctionalInterface
public interface SFTPAction<K, T> {

	T doAction(K configValues, ChannelSftp sftpChannel) throws Exception;

}
