package com.example;
import java.io.File;
import java.io.FileNotFoundException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileLoadingProcessor implements Processor {
	private Logger log = Logger.getLogger(this.getClass());

@Value("${imagepath}")
private String imagepath;	
@Override
public void process(Exchange exchange) throws Exception {
	
	String fielName = exchange.getIn().getHeader("imagename", String.class);
	log.info("################## fielName ######" + fielName);
	  
       String filePath = imagepath;
	    //URI uri = new URI(filePath.concat(filename));
	    File file = new File(filePath+fielName);

	    if (!file.exists()) {
	        throw new FileNotFoundException(String.format("File %s not found", filePath+fielName));
	    }

	    exchange.getIn().setBody(FileUtils.readFileToByteArray(file));
	    exchange.getIn().setHeader( Exchange.CONTENT_TYPE, "image/jpeg"); 
}
}