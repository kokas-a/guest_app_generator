package part_generator;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import part_generator.part_param_container.ARINC_653_Module;

public class ParserXML {
	
	private File xml;
	
    public ParserXML(File appXML) {
    	xml = appXML;
    }
    
    public int init(){
    	
   	 try {
   		 JAXBContext context = JAXBContext.newInstance(ARINC_653_Module.class);
   	   
   		 Unmarshaller unmarshaller = context.createUnmarshaller();
   		   
   		ARINC_653_Module customer = (ARINC_653_Module) unmarshaller.unmarshal(xml);
   		
   		return 0;
	        
   	 } catch (JAXBException e) {
            e.printStackTrace();
            return -1;
        }
   	  	
   }
	
}
