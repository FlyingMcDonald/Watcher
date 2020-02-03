package io.github.ihexon.other;

import io.github.ihexon.common.PrintUtils;

public class HelpClass {

	public HelpClass(){
		StringBuilder msg = new StringBuilder();
		msg.append("WatchMe - A util use to monitor the changes of directory\n");
		msg.append("usage:\n");
		msg.append("watchme [OPTION] [DIRECTOR]\n");
		msg.append("-r                  : recurse monitor, means the all directory and file will be monitor\n" +
					"-d                 : the directory to monitor\n" +
					"--exclude-hidden   : exclude the hidden directory\n" +
					"--log				: output events to log file\n" +
					"--custom-log-file	: custom log file\n" +
					"--help & -h        : show help\n");
		PrintUtils.stdPrintln(msg);
	}

}