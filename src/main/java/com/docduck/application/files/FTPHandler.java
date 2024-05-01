package com.docduck.application.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;
import com.docduck.application.xmlreader.XMLReader;
 
/**
 * A program demonstrates how to upload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPHandler {
	static XMLBuilder xmlBuilder;
	static GUIBuilder guiBuilder;
	
	public FTPHandler() {
		xmlBuilder = XMLBuilder.getInstance();
		guiBuilder = GUIBuilder.getInstance();
	}
	
    public void downloadAllFiles() {
        try {
            //new ftp client
            FTPClient ftp = new FTPClient();
            //try to connect
            ftp.connect("81.101.49.54");
            //login to server
            if (!ftp.login("docduck", "sweng")) {
                ftp.logout();
            }
            int reply = ftp.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes. 
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }

            //enter passive mode
            ftp.enterLocalPassiveMode();
            //get system name
            System.out.println("Remote system is " + ftp.getSystemType());
            System.out.println("Current directory is " + ftp.printWorkingDirectory());

            //get list of filenames
            FTPFile[] ftpFiles = ftp.listFiles();

            if (ftpFiles != null && ftpFiles.length > 0) {
                //loop thru files
                for (FTPFile file : ftpFiles) {
                    if (!file.isFile()) {
                        continue;
                    }
                    System.out.println("File is " + file.getName());
                    //get output stream
                    OutputStream output;
                    output = new FileOutputStream("src/main/resources/" + file.getName());
                    //get the file from the remote system
                    ftp.retrieveFile(file.getName(), output);
                    //close output stream
                    output.close();
                    //delete the file
                    // ftp.deleteFile(file.getName());
                    
                }
            }

            ftp.logout();
            ftp.disconnect();
            XMLReader myReader = new XMLReader("src/main/resources/docduck-application-slides.xml", "src/main/resources/DocDuckStandardSchema.xsd", true);
        	myReader.readXML();
        	xmlBuilder.setData(myReader.getData());
        	guiBuilder.setData(myReader.getData());
        	guiBuilder.LoginPage();
        } catch (Exception ex) {
            guiBuilder.OfflinePage();
        }
    }
}