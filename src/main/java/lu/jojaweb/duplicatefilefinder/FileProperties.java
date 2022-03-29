/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lu.jojaweb.duplicatefilefinder;

import java.io.File;

/**
 *
 * @author Joshua
 */
public class FileProperties implements Comparable<FileProperties> {
    
    String fileName;
    long size;
    String type = "";
    String absolutePath;
    String duplicateOfFilePath;
    boolean deleted = false;
    boolean isDuplicate = false;
    
    public FileProperties(String absolutePath, long size) {
        this.size = size;
        this.absolutePath = absolutePath;
        this.fileName = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1);
        this.type = fileName.substring(fileName.lastIndexOf(".") + 1);
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
        return fileName + "," + size;
    }
    
    public void setDuplicateOfFilePath(String duplicateOfFilePath) {
        this.duplicateOfFilePath = duplicateOfFilePath;
        setIsDuplicate(true);
    }
    
    @Override
    public int compareTo(FileProperties o) {
        return this.fileName.compareTo(o.fileName);
    }
    
    boolean isFileNameAndSizeEqual(FileProperties fp) {
        return this.fileName.equals(fp.fileName) && this.size == fp.size;
    }
    
    @Override
    public String toString() {
        return absolutePath;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isDuplicate() {
        return isDuplicate;
    }
    
    public void setIsDuplicate(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }
    
}
