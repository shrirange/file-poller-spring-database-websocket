package com.example.filepoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.drapostolos.rdp4j.DirectoryListener;
import com.github.drapostolos.rdp4j.FileAddedEvent;
import com.github.drapostolos.rdp4j.FileModifiedEvent;
import com.github.drapostolos.rdp4j.FileRemovedEvent;
import com.github.drapostolos.rdp4j.InitialContentEvent;
import com.github.drapostolos.rdp4j.InitialContentListener;
import com.github.drapostolos.rdp4j.IoErrorCeasedEvent;
import com.github.drapostolos.rdp4j.IoErrorListener;
import com.github.drapostolos.rdp4j.IoErrorRaisedEvent;

@Component
public class MyListener implements DirectoryListener, IoErrorListener, InitialContentListener {
	
	@Autowired
	private Websocketservice webservice;
	
	@Autowired
	private FilesInformationRepository filesInformationRepository;

	public MyListener() {
	}

	public void fileAdded(FileAddedEvent event) {
		System.out.println("Added: " + event.getFileElement());
		webservice.sendMessage("Added: " + event.getFileElement());
		FilesInformation fi = new FilesInformation();
		fi.setFolderName(event.getPolledDirectory().toString());
		fi.setFileName(event.getFileElement().getName()); 
		filesInformationRepository.save(fi);
	}

	public void fileRemoved(FileRemovedEvent event) {
		System.out.println("Removed: " + event.getFileElement());
	}

	public void fileModified(FileModifiedEvent event) {
		System.out.println("Modified: " + event.getFileElement());
	}

	public void ioErrorCeased(IoErrorCeasedEvent event) {
		System.out.println("I/O error ceased.");
	}

	public void ioErrorRaised(IoErrorRaisedEvent event) {
		System.out.println("I/O error raised!");
		event.getIoException().printStackTrace();
	}

	public void initialContent(InitialContentEvent event) {
		System.out.println("initial Content: ^");
	}
}