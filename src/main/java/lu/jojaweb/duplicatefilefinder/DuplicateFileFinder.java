package lu.jojaweb.duplicatefilefinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * This program goes through all the files in the file system across all drives
 * and shows when a file has the same name, type and size
 *
 * @author Joshua
 */
public class DuplicateFileFinder {

    boolean printDuplicateInfo = false;
    boolean printDirectoryScanInfo = true;
    boolean printFileScanInfo = true;

    String[] drives = {"C"};
    JTextArea outputArea;
    ArrayList<FileProperties> scannedFiles = new ArrayList<>();
    ArrayList<FileProperties> duplicateFiles = new ArrayList<>();
    JLabel scannedFilesCountLabel;
    JLabel duplicateFilesCountLabel;
    private boolean showDetails = false;
    private JList<Object> originalFilePathJList;
    private JList<Object> duplicateFilePathJList;
    private Timer updateJListsTimer;

    public DuplicateFileFinder() {
        this.updateJListsTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateJLists();
            }
        });
    }

    void scanAllFilesOnDrive(String driveLetter) {
        scannedFiles = new ArrayList<>();
        duplicateFiles = new ArrayList<>();
        updateJListsTimer.start();
        scanAllFilesInPath(driveLetter + ":\\");
        updateJListsTimer.stop();
    }

    void scanAllFilesInPaths(String[] paths) {
        scannedFiles = new ArrayList<>();
        duplicateFiles = new ArrayList<>();
        updateJListsTimer.start();
        for (String path : paths) {
            scanAllFilesInPath(path);
        }
        updateJListsTimer.stop();
    }

    void scanAllFilesInPath(String path) {
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
            long startTime = System.currentTimeMillis();
            if (child.isFile()) {
                String absolutePath = child.getAbsolutePath();
                long size = -1;
                try {
                    size = Files.size(child.toPath());
                } catch (IOException ex) {
                    System.err.println("Unable to determine filesize for " + absolutePath);
                }
                FileProperties currentFileProperties = new FileProperties(absolutePath, size);
                //System.out.println(scannedFiles.size() + ": \n\tAbsolutePath:\t" + currentFileProperties.getAbsolutePath() + "\n\tFileName:\t" + currentFileProperties.getFileName() + "\n\tSize:\t\t" + currentFileProperties.getSize());
                boolean isDuplicate = false;
                for (FileProperties fp : scannedFiles) {
                    if (currentFileProperties.getPropertiesString().equals(fp.getPropertiesString())) {
                        currentFileProperties.setDuplicateOfFilePath(fp.getAbsolutePath());
                        isDuplicate = true;
                        break;
                    }
                }
                scannedFiles.add(currentFileProperties);
                if (isDuplicate) {
                    duplicateFiles.add(currentFileProperties);
                    if (printDuplicateInfo) {
                        System.out.println("\n\n\n" + duplicateFiles.size() + ": \n\tAbsolutePath:\t" + currentFileProperties.getAbsolutePath() + "\n\tFileName:\t" + currentFileProperties.getFileName() + "\n\tType:\t\t" + currentFileProperties.getType() + "\n\tSize:\t\t" + currentFileProperties.getSize());
                        System.out.println("Duplicate of: " + currentFileProperties.duplicateOfFilePath);
                    }
                }

                if (printFileScanInfo) {
                    System.out.println("Checked if \"" + child.getAbsolutePath() + "\" is duplicate(" + (System.currentTimeMillis() - startTime) + "ms)");
                }

            } else {
                if (printDirectoryScanInfo) {
                    System.out.println("Scanning directory: " + child.getAbsolutePath());
                }
                scanAllFilesInPath(child.getAbsolutePath());
                if (printDirectoryScanInfo) {
                    System.out.println("Scanned \"" + child.getAbsolutePath() + "\" (" + (System.currentTimeMillis() - startTime) + "ms)");
                }
            }
            updateFields();
        }
    }

    void updateFields() {
        scannedFilesCountLabel.setText(scannedFiles.size() + "");
        duplicateFilesCountLabel.setText(duplicateFiles.size() + "");
        updateDetailsTextArea();
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
        if (showDetails) {
            updateDetailsTextArea();
        }
    }

    void initOriginalFilePathList(JList<Object> originalFilePathList) {
        this.originalFilePathJList = originalFilePathList;
    }

    void initDuplicateFilePathList(JList<Object> duplicateFilePathList) {
        this.duplicateFilePathJList = duplicateFilePathList;
    }

    private void updateDetailsTextArea() {
        if (showDetails) {
            int sizeMinus1000Index = 0;
            if (scannedFiles.size() > 1000) {
                sizeMinus1000Index = scannedFiles.size() - 1000;
            }
            String last1000FilesString = "";
            for (int i = sizeMinus1000Index; i < scannedFiles.size(); i++) {
                FileProperties fp = scannedFiles.get(i);
                String absolutePathString = fp.getAbsolutePath();
                if (fp.isDuplicate()) {
                    absolutePathString = "<p style=\"\">" + absolutePathString + "</p>";
                }
                last1000FilesString += ("\n" + i + 1 + ": " + absolutePathString);
            }
            outputArea.setText(last1000FilesString);
        }
    }

    private void updateJLists() {        
        ArrayList<FileProperties> duplicateFilesClone = (ArrayList<FileProperties>) duplicateFiles.clone();
        ArrayList<String> originalFilePathArray = new ArrayList<>();
        ArrayList<String> duplicateFilePathArray = new ArrayList<>();
        for (FileProperties fp : duplicateFilesClone) {
            if (!fp.isDeleted()) {
                originalFilePathArray.add(fp.duplicateOfFilePath);
                duplicateFilePathArray.add(fp.absolutePath);
            }
        }
        int[] originalFilePathJListSelectedIndices=originalFilePathJList.getSelectedIndices();
        int[] duplicateFilePathJListSelectedIndices=duplicateFilePathJList.getSelectedIndices();
        originalFilePathJList.setListData(originalFilePathArray.toArray());
        duplicateFilePathJList.setListData(duplicateFilePathArray.toArray());
        originalFilePathJList.setSelectedIndices(originalFilePathJListSelectedIndices);
        duplicateFilePathJList.setSelectedIndices(duplicateFilePathJListSelectedIndices);
        originalFilePathJList.repaint();
    }

    public void setFileDeleted(String path) {
        for (FileProperties fp : duplicateFiles) {
            if (fp.absolutePath.equals(path) || fp.duplicateOfFilePath.equals(path)) {
                fp.setDeleted(true);
                break;
            }
        }
        updateJLists();
    }

}
