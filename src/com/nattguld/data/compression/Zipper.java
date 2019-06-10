package com.nattguld.data.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author randqm
 *
 */

public class Zipper {
	
	
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
	        zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
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
