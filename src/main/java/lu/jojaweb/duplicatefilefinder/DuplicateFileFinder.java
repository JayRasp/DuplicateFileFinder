package lu.jojaweb.duplicatefilefinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 * This program goes through all the files in the file system across all drives
 * and shows when a file has the same name, type and size
 *
 * @author Joshua
 */
public class DuplicateFileFinder {

    String[] drives = {"C"};
    JTextArea outputArea;
    ArrayList<FileProperties> scannedFiles = new ArrayList<>();
    ArrayList<FileProperties> duplicateFiles = new ArrayList<>();
    JLabel scannedFilesCountLabel;
    JLabel duplicateFilesCountLabel;
    private boolean showDetails = false;
    private JList<Object> originalFilePathList;
    private JList<Object> duplicateFilePathList;

    void scanAllFilesOnDrive(String driveLetter) {
        scanAllFiles(driveLetter + ":\\");
    }

    void scanAllFilesInPaths(String[] paths) {
        for (String path : paths) {
            scanAllFiles(path);
        }
    }

    void scanAllFiles(String path) {
        File[] f;
        if (path != null && path.length() != 0) {
            f = new File(path).listFiles();
        } else {
            System.err.println("PATH IS NULL");
            return;
        }
        if (f == null) {
            return;
        }
        for (File child : f) {
            if (child.isFile()) {
                String absolutePath = child.getAbsolutePath();
                String fileName = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1);
                long size = -1;
                try {
                    size = Files.size(child.toPath());
                } catch (IOException ex) {
                    System.err.println("Unable to determine filesize for " + absolutePath);
                }
                FileProperties currentFileProperties = new FileProperties(fileName, size, absolutePath);
                //System.out.println(scannedFiles.size() + ": \n\tAbsolutePath:\t" + currentFileProperties.getAbsolutePath() + "\n\tFileName:\t" + currentFileProperties.getFileName() + "\n\tSize:\t\t" + currentFileProperties.getSize());
                boolean isDuplicate = false;
                for (FileProperties fp : scannedFiles) {
                    if (currentFileProperties.getPropertiesString().equals(fp.getPropertiesString())) {
                        currentFileProperties.setDuplicateOfFilePath(fp.getAbsolutePath());
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    scannedFiles.add(currentFileProperties);
                    //System.out.println("File is not duplicate");
                } else {
                    
                System.out.println("\n\n\n"+duplicateFiles.size() + ": \n\tAbsolutePath:\t" + currentFileProperties.getAbsolutePath() + "\n\tFileName:\t" + currentFileProperties.getFileName() + "\n\tSize:\t\t" + currentFileProperties.getSize());
                    duplicateFiles.add(currentFileProperties);
                    System.out.println("Duplicate of: "+currentFileProperties.duplicateOfFilePath);
                }

            } else {
                //System.out.println("Scanning directory: " + child.getAbsolutePath().substring(child.getAbsolutePath().lastIndexOf("\\") + 1));
                scanAllFiles(child.getAbsolutePath());
            }
            updateFields();
        }
    }

    void updateFields() {
        scannedFilesCountLabel.setText(scannedFiles.size()+duplicateFiles.size()+ "");
        updateDetailsTextArea();
        duplicateFilesCountLabel.setText(duplicateFiles.size() + "");   
        ArrayList<String> originalFilePathArray=new ArrayList<>();
        ArrayList<String> duplicateFilePathArray=new ArrayList<>();
        for(FileProperties fp : duplicateFiles){
            originalFilePathArray.add(fp.duplicateOfFilePath);
            duplicateFilePathArray.add(fp.absolutePath);
        }
        originalFilePathList.setListData(originalFilePathArray.toArray());
        duplicateFilePathList.setListData(duplicateFilePathArray.toArray());
        
    }

    void initOutputField(JTextArea jTextArea1) {
        outputArea = jTextArea1;
    }

    void initScannedFilesCountLabel(JLabel scannedFilesCountLabel) {
        this.scannedFilesCountLabel = scannedFilesCountLabel;
    }

    void initDuplicateFilesCountLabel(JLabel duplicateFilesCountLabel) {
        this.duplicateFilesCountLabel = duplicateFilesCountLabel;
    }

    public void toggleShowDetails() {
        showDetails = !showDetails;
        if(showDetails){
            updateDetailsTextArea();
        }
    }

    void initOriginalFilePathList(JList<Object> originalFilePathList) {
        this.originalFilePathList = originalFilePathList;
    }

    void initDuplicateFilePathList(JList<Object> duplicateFilePathList) {
        this.duplicateFilePathList = duplicateFilePathList;
    }

    private void updateDetailsTextArea() {
        if (showDetails) {
            int sizeMinus1000Index = 0;
            if (scannedFiles.size() > 1000) {
                sizeMinus1000Index = scannedFiles.size() - 1000;
            }
            String last1000FilesString = "";
            for (int i = sizeMinus1000Index; i < scannedFiles.size(); i++) {
                String absolutePathString = scannedFiles.get(i).getAbsolutePath();
                last1000FilesString += (i + ": " + absolutePathString + "\n");
            }
            outputArea.setText(last1000FilesString);
        }
    }
}
