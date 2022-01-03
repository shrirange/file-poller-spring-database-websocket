package com.example.filepoller;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.DirectoryPollerBuilder;
import com.github.drapostolos.rdp4j.JavaIoFileAdapter;

@SpringBootApplication
@EnableScheduling
public class FilePollerSpringDatabaseWebsocketApplication {
	
	//FOR /L %G IN (3,1,100) DO md folder%G

	public static void main(String[] args) {
		SpringApplication.run(FilePollerSpringDatabaseWebsocketApplication.class, args);
	}

	@Bean
	public DirectoryPollerBuilder getDirectoryPollerBuilder(@Autowired MyListener listener, @Value(value = "${baseFolder}") String folderName) {

		return DirectoryPoller.newBuilder().addPolledDirectory(new JavaIoFileAdapter(new File(folderName)))
				.addListener(listener).setPollingInterval(2, TimeUnit.SECONDS);

	}
	
}
