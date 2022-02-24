package com.example.filepoller;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
@Transactional
public class MyListener implements DirectoryListener, IoErrorListener, InitialContentListener {
	
	@Autowired
	private Websocketservice webservice;
	
	@Autowired
	private FilesInformationRepository filesInformationRepository;
	
	private AuthorRepository authorRepository;

	public MyListener(AuthorRepository authorRepository ) {
		this.authorRepository = authorRepository;
	}

	public void fileAdded(FileAddedEvent event) {
		System.out.println("Added: " + event.getFileElement());
		webservice.sendMessage("Added: " + event.getFileElement());
		FilesInformation fi = new FilesInformation();
		fi.setFolderName(event.getPolledDirectory().toString());
		fi.setFileName(event.getFileElement().getName()); 
		//filesInformationRepository.findById(fi.getFilesInformationId());
		List<Author> listofAuthor = authorRepository.findAll();
		for (Iterator iterator = listofAuthor.iterator(); iterator.hasNext();) {
			Author author = (Author) iterator.next();
			author.setAuthorName(author.getAddress().getAddress1() + event.getFileElement().getName());
			authorRepository.save(author);
		}
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