package com.docduck.application.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
 * 
 * @author www.codejava.net
 */
public class FTPHandler {

    private static XMLBuilder xmlBuilder;
    private static GUIBuilder guiBuilder;
    private static FTPHandler instance = null;
    private FTPClient ftp;
    private ScheduledExecutorService executor;
    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    private boolean debug = false;

    private FTPHandler() {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static FTPHandler createInstance() {

        if (instance == null) {
            instance = new FTPHandler();
        }
        return instance;
    }

    public static FTPHandler getInstance() {
        return instance;
    }

    public void updateInstances() {
        xmlBuilder = XMLBuilder.getInstance();
        guiBuilder = GUIBuilder.getInstance();
    }

    public void startApp() {

        try {

            ftp = new FTPClient();

            ftp.connect("81.101.49.54");

            if (!ftp.login("docduck", "sweng")) {
                ftp.logout();
            }
            int reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }

            ftp.enterLocalPassiveMode();

            if (debug) {
                System.out.println("Remote system is " + ftp.getSystemType());
                System.out.println("Current directory is " + ftp.printWorkingDirectory());
            }

            FTPFile[] ftpFiles = ftp.listFiles();

            if (ftpFiles != null && ftpFiles.length > 0) {

                for (FTPFile ftpFile : ftpFiles) {

                    if (!ftpFile.isFile()) {
                        continue;
                    }
                    String ftpName = ftpFile.getName();
                    String stringTime = ftp.getModificationTime(ftpName);
                    Date ftpTime = df.parse(stringTime);

                    if (debug) {
                        System.out.println("File is " + ftpName);
                    }

                    OutputStream output = new FileOutputStream("src/main/resources/" + ftpName);
                    ftp.retrieveFile(ftpName, output);
                    output.close();
                    File localFile = new File("src/main/resources/" + ftpName);
                    localFile.setLastModified(ftpTime.getTime());
                }
            }

            XMLReader myReader = new XMLReader("src/main/resources/docduck-application-slides.xml",
                    "src/main/resources/DocDuckStandardSchema.xsd", true);
            myReader.readXML();
            xmlBuilder.setData(myReader.getData());
            guiBuilder.setData(myReader.getData());
            guiBuilder.LoginPage();
            scheduleFileUpdates(5.0);
        }
        catch (Exception ex) {
            guiBuilder.OfflinePage();
        }
    }

    private void scheduleFileUpdates(Double updateDelay) {
        Runnable updateFiles = new Runnable() {

            @Override
            public void run() {
                updateFiles();
            }
        };

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateFiles, updateDelay.longValue(), updateDelay.longValue(), TimeUnit.SECONDS);
    }

    public void stopFileUpdates() {

        if (executor != null) {
            executor.shutdown();
        }
    }

    private void updateFiles() {

        if (ftp.isAvailable() && ftp.isConnected()) {

            try {
                File local = new File("src/main/resources/");
                FTPFile[] ftpFiles = ftp.listFiles();
                File[] localFiles = local.listFiles();

                if (ftpFiles != null && ftpFiles.length > 0) {

                    for (FTPFile ftpFile : ftpFiles) {

                        if (!ftpFile.isFile()) {
                            continue;
                        }

                        String ftpName = ftpFile.getName();
                        String stringTime = ftp.getModificationTime(ftpName);
                        Date ftpDate = df.parse(stringTime);
                        Instant ftpTime = ftpDate.toInstant().truncatedTo(ChronoUnit.SECONDS);

                        if (localFiles != null && localFiles.length > 0) {

                            for (File localFile : localFiles) {
                                String localName = localFile.getName();

                                if (localName.equalsIgnoreCase(ftpName)) {
                                    Date localDate = new Date(localFile.lastModified());
                                    Instant localTime = localDate.toInstant().truncatedTo(ChronoUnit.SECONDS);

                                    if (debug) {
                                        System.out.println("FTP File is " + ftpName + " Time: " + ftpDate);
                                        System.out.println("Local File is " + localName + " Time: " + localDate);

                                        if (localTime.compareTo(ftpTime) > 0) {
                                            System.out.println("Local file is newer than FTP file");
                                        }
                                        else if (localTime.compareTo(ftpTime) < 0) {
                                            System.out.println("FTP file is newer than Local file");
                                        }
                                        else {
                                            System.out.println("Both files are the same version\n");
                                        }
                                    }

                                    if (localTime.compareTo(ftpTime) > 0) {
                                        uploadFile(localName);
                                    }
                                    else if (localTime.compareTo(ftpTime) < 0) {
                                        downloadFile(ftpName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {

            try {
                System.out.println("FTP has disconnected, attempting reconnect");
                ftp.connect("81.101.49.54");

                // login to server
                if (!ftp.login("docduck", "sweng")) {
                    ftp.logout();
                }
                int reply = ftp.getReplyCode();

                // FTPReply stores a set of constants for FTP reply codes.
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                }
                ftp.enterLocalPassiveMode();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void uploadFile(String localFilename) {

        if (ftp.isAvailable() && ftp.isConnected()) {

            try {
                File localFile = new File("src/main/resources/" + localFilename);
                InputStream inputStream = new FileInputStream(localFile);
                ftp.storeFile(localFilename, inputStream);
                inputStream.close();
                Date localTime = new Date(localFile.lastModified());
                String timeString = df.format(localTime);
                ftp.setModificationTime(localFilename, timeString);

                if (debug) {
                    System.out.println("Uploaded " + localFilename + " to server with time " + localTime);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void downloadFile(String ftpFilename) {

        if (ftp.isAvailable() && ftp.isConnected()) {

            try {
                FTPFile[] ftpFiles = ftp.listFiles();

                if (ftpFiles != null && ftpFiles.length > 0) {

                    for (FTPFile ftpFile : ftpFiles) {

                        if (ftpFile.getName().equalsIgnoreCase(ftpFilename)) {
                            String stringTime = ftp.getModificationTime(ftpFile.getName());
                            Date ftpTime = df.parse(stringTime);
                            OutputStream output = new FileOutputStream("src/main/resources/" + ftpFilename);
                            ftp.retrieveFile(ftpFilename, output);
                            output.close();
                            File localFile = new File("src/main/resources/" + ftpFilename);
                            localFile.setLastModified(ftpTime.getTime());

                            if (debug) {
                                System.out.println("Downloaded " + ftpFilename + " from server with time " + ftpTime);
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}