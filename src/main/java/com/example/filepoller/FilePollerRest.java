package com.example.filepoller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.DirectoryPollerBuilder;
import com.github.drapostolos.rdp4j.JavaIoFileAdapter;

@RestController
public class FilePollerRest {

	@Autowired
	private DirectoryPollerBuilder directoryPollerBuilder;

	private static DirectoryPoller directoryPoller;

	@GetMapping("/disable")
	public void disableScheduler() {
		if (directoryPoller != null) {
			System.out.println("Disabled!!");
			directoryPoller.stop();
		}
	}

	@GetMapping("/enable")
	public void enableScheduler() {
		System.out.println("Enabled!!");
		directoryPoller = directoryPollerBuilder.start();
	}

	
	@GetMapping("/changeFolder/{folderName}")
	public void changeFolder(@PathVariable String folderName, 
			@Value(value = "${baseFolder}") String baseFolderName) {
		String str = baseFolderName + "\\" + folderName;
		System.out.println("Change Folder!!" + str);
		directoryPoller.getPolledDirectories().clear();
		directoryPoller.addPolledDirectory(new JavaIoFileAdapter(new File(str)));
	}
}
