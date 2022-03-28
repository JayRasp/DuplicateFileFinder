/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lu.jojaweb.duplicatefilefinder;

/**
 *
 * @author Joshua
 */
public class FileProperties {
    String fileName;
    long size;
    String absolutePath;
    String duplicateOfFilePath;

    public FileProperties(String fileName, long size, String absolutePath) {
        this.fileName = fileName;
        this.size = size;
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSize() {
        return size;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
    
    public String getPropertiesString() {
        return fileName+","+size;
    }

    public void setDuplicateOfFilePath(String duplicateOfFilePath) {
        this.duplicateOfFilePath = duplicateOfFilePath;
    }
    
    
}
