package com.nattguld.data.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author randqm
 *
 */

public class Zipper {
	
	
	/**
	 * Unzips a zip file.
	 * 
	 * @param zipFile The zip file.
	 * 
	 * @param outputDir The output directory.
	 */
    public void unzip(File zipFile, File outputDir) {
        byte[] buffer = new byte[1024];
       	
        try {
        	if (!outputDir.exists()) {
        		outputDir.mkdir();
        	}
        	ZipInputStream zis =  new ZipInputStream(new FileInputStream(zipFile));
        	ZipEntry ze = zis.getNextEntry();
       		
        	while (Objects.nonNull(ze)) {
        		String fileName = ze.getName();
        		File newFile = new File(outputDir.getAbsolutePath() + File.separator + fileName);
        		
        		new File(newFile.getParent()).mkdirs();
                 
        		try (FileOutputStream fos = new FileOutputStream(newFile)) {          
        			int len;
        		
        			while ((len = zis.read(buffer)) > 0) {
        				fos.write(buffer, 0, len);
        			}
        		}  
        		ze = zis.getNextEntry();
        	}
        	zis.closeEntry();
        	zis.close();
       		
        } catch(IOException ex) {
        	ex.printStackTrace(); 
        }
    } 
	
	/**
	 * Creates a zip file.
	 * 
	 * @param source The source.
	 * 
	 * @param destination The destination.
	 */
	public static void zip(File source, File destination) {
		try (FileOutputStream fos = new FileOutputStream(destination.getAbsolutePath())) {
			try (ZipOutputStream zos = new ZipOutputStream(fos)) {
				addDirToZipArchive(zos, source, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Adds a directory to a zip archive.
	 * 
	 * @param zos The zip output stream.
	 * 
	 * @param fileToZip The file to zip.
	 * 
	 * @param parrentDirectoryName The directory name.
	 * 
	 * @throws Exception
	 */
	private static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws Exception {
	    if (Objects.isNull(fileToZip) || !fileToZip.exists()) {
	        return;
	    }
	    String zipEntryName = fileToZip.getName();
	    
	    if (Objects.nonNull(parrentDirectoryName) && !parrentDirectoryName.isEmpty()) {
	        zipEntryName = parrentDirectoryName + File.separator + fileToZip.getName();
	    }
	    if (fileToZip.isDirectory()) {
	        for (File file : fileToZip.listFiles()) {
	            addDirToZipArchive(zos, file, zipEntryName);
	        }
	    } else {
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = new FileInputStream(fileToZip);
	        
	        zos.putNextEntry(new ZipEntry(zipEntryName));
	        
	        int length;
	        
	        while ((length = fis.read(buffer)) > 0) {
	            zos.write(buffer, 0, length);
	        }
	        zos.closeEntry();
	        fis.close();
	    }
	}
	
}
