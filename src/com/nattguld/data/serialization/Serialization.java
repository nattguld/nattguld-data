package com.nattguld.data.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 
 * @author randqm
 *
 */

public class Serialization {
    
    
    /**
     * Serializes an object.
     * 
     * @param o The object to serialize.
     * 
     * @param path The storage path.
     */
    public static void serialize(Object o, String path) {
    	try (OutputStream file = new FileOutputStream(path);
    			OutputStream buffer = new BufferedOutputStream(file);
    			ObjectOutput output = new ObjectOutputStream(buffer);
    			) {
    		output.writeObject(o);
    		
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    }
    
    /**
     * Deserializes an object.
     * 
     * @param path The stored object.
     */
    public static Object deserialize(String path) {
    	try (InputStream file = new FileInputStream(path);
    			InputStream buffer = new BufferedInputStream(file);
    			ObjectInput input = new ObjectInputStream (buffer);
    			){
    		Object o = input.readObject();
    		return o;
    		
    	} catch (ClassNotFoundException | IOException ex) {
    		ex.printStackTrace();
    		return null;
    	}
    }

}
