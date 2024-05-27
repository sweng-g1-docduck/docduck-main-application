package com.docduck.application.files;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;
import com.docduck.application.xmlreader.XMLReader;

public class FTPHandler {

    private static XMLBuilder xmlBuilder;
    private static GUIBuilder guiBuilder;
    private static FTPHandler instance = null;
    private FTPClient ftp;
    private ScheduledExecutorService executor;
    private final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    private final boolean debug = true;
    private final static String SERVER_IP = "81.101.49.54";
    private final static String USERNAME = "docduck";
    private final static String PASSWORD = "sweng";
    private final static String FILE_STORE = "%AppData%/Roaming/com.dockduck/resources/";
    
    private FTPHandler() {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    /**
     * Creates instance of FTPHandler
     * @return instance of FTPHandler
     * @author rw1834
     */
    public static FTPHandler createInstance() {

        if (instance == null) {
            instance = new FTPHandler();
        }
        return instance;
    }
    
    /**
     * Gets instance of FTPHandler
     * @return instance of FTPHandler
     * @author rw1834
     */
    public static FTPHandler getInstance() {
        return instance;
    }
    
    /**
     * Updates references to other builders
     * @author rw1834
     */
    public void updateInstances() {
        xmlBuilder = XMLBuilder.getInstance();
        guiBuilder = GUIBuilder.getInstance();
    }
    
    public void connect() {
        try {
            if (ftp == null) {
                prepareClient();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Downloads files from server and starts application onto login page.
     * @author rw1834
     */
    public void start() {

        try {

            prepareClient();

            if (debug) {
                System.out.println("Remote system is " + ftp.getSystemType());
                System.out.println("Current directory is " + ftp.printWorkingDirectory());
            }

            FTPFile[] ftpFiles = ftp.listFiles();

            if (ftpFiles != null) {

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

                    OutputStream output = new FileOutputStream(FILE_STORE + ftpName);
                    ftp.retrieveFile(ftpName, output);
                    output.close();
                    File localFile = new File(FILE_STORE + ftpName);
                    localFile.setLastModified(ftpTime.getTime());
                }
            }
            scheduleFileUpdates(5.0);
        }
        catch (Exception ex) {
            guiBuilder.OfflinePage();
        }
    }

    private void prepareClient() throws IOException {
        ftp = new FTPClient();

        ftp.connect(SERVER_IP);

        if (!ftp.login(USERNAME, PASSWORD)) {
            ftp.logout();
        }
        int reply = ftp.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
        }
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    /**
     * Starts file update scheduler.
     *
     * @author rw1834
     */
    private void scheduleFileUpdates(Double updateDelay) {
        Runnable updateFiles = this::updateFiles;

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateFiles, updateDelay.longValue(), updateDelay.longValue(), TimeUnit.SECONDS);
    }
    
    /**
     * Stops file update scheduler
     * @author rw1834
     */
    public void stopFileUpdates() {

        if (executor != null) {
            executor.shutdown();
        }
    }
    
    /**
     * Check and update files to latest version to/from server.
     * @author rw1834
     */
    private void updateFiles() {

        if (ftp.isAvailable() && ftp.isConnected()) {

            try {
                File local = new File("FILE_STORE");
                FTPFile[] ftpFiles = ftp.listFiles();
                File[] localFiles = local.listFiles();

                if (ftpFiles != null) {

                    for (FTPFile ftpFile : ftpFiles) {

                        if (!ftpFile.isFile()) {
                            continue;
                        }

                        String ftpName = ftpFile.getName();
                        String stringTime = ftp.getModificationTime(ftpName);
                        Date ftpDate = df.parse(stringTime);
                        Instant ftpTime = ftpDate.toInstant().truncatedTo(ChronoUnit.SECONDS);

                        if (localFiles != null) {

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
                ftp.connect(SERVER_IP);

                // login to server
                if (!ftp.login(USERNAME, PASSWORD)) {
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

    /**
     * Upload file to server
     * @param localFilename - name of file within resources folder
     * @author rw1834
     */
    private void uploadFile(String localFilename) {

        if (ftp.isAvailable() && ftp.isConnected()) {

            try {
                File localFile = new File(FILE_STORE + localFilename);
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
    
    /**
     * Upload file to server
     * @param filename - name of file.
     * @param filePath - path of file.
     * @author rw1834
     */
    public void uploadFileFromPath(String filePath, String filename) {
        if (ftp.isAvailable() && ftp.isConnected()) {
            try {
                File localFile = new File(filePath);
                InputStream inputStream = new FileInputStream(filePath);
                ftp.storeFile(filename, inputStream);
                inputStream.close();
                Date localTime = new Date(localFile.lastModified());
                String timeString = df.format(localTime);
                ftp.setModificationTime(filename, timeString);

                if (debug) {
                    System.out.println("Uploaded " + filename + " to server with time " + localTime);
                }
                downloadFile(filename);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Downloads file from server to resources folder
     * @param ftpFilename - file name within server
     * @author rw1834
     */
    private void downloadFile(String ftpFilename) {

        if (ftp.isAvailable() && ftp.isConnected()) {

            try {
                FTPFile[] ftpFiles = ftp.listFiles();

                if (ftpFiles != null) {

                    for (FTPFile ftpFile : ftpFiles) {

                        if (ftpFile.getName().equalsIgnoreCase(ftpFilename)) {
                            String stringTime = ftp.getModificationTime(ftpFile.getName());
                            Date ftpTime = df.parse(stringTime);
                            OutputStream output = new FileOutputStream(FILE_STORE + ftpFilename);
                            ftp.retrieveFile(ftpFilename, output);
                            output.close();
                            File localFile = new File(FILE_STORE + ftpFilename);
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