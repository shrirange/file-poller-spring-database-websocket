package com.example.filepoller;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.DirectoryPollerBuilder;
import com.github.drapostolos.rdp4j.JavaIoFileAdapter;

@SpringBootApplication
public class FilePollerSpringDatabaseWebsocketApplication implements ApplicationRunner{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	
	//FOR /L %G IN (3,1,100) DO md folder%G

	public static void main(String[] args) {
		SpringApplication.run(FilePollerSpringDatabaseWebsocketApplication.class, args);
	}

	@Bean
	public DirectoryPollerBuilder getDirectoryPollerBuilder(@Autowired MyListener listener, @Value(value = "${baseFolder}") String folderName) {

		return DirectoryPoller.newBuilder().addPolledDirectory(new JavaIoFileAdapter(new File(folderName)))
				.addListener(listener).setPollingInterval(2, TimeUnit.SECONDS);

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Author author = new Author();
		author.setAuthorName("Shrirang");
		author.setTest("test");
		author = authorRepository.save(author);
		
		Address address = new Address();
		address.setAddress1("Kothrud");
		address.setCity("Pune");
		Address addr = addressRepository.save(address);
		
		author.setAddress(addr);
		authorRepository.save(author);
		
		
		
	}
	
}
