package com.nattguld.data.binary;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.nattguld.data.IResourceWriter;

/**
 * 
 * @author randqm
 *
 */

public class BinaryWriter implements IResourceWriter {

	/**
	 * The data output stream.
	 */
	private final DataOutputStream out;
	
	
	/**
	 * Creates a new binary writer.
	 * 
	 * @param out The data output stream.
	 */
	public BinaryWriter(DataOutputStream out) {
		this.out = out;
	}
	
	/**
	 * Writes a byte.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException
	 */
	public void writeByte(byte value) throws IOException {
		out.writeByte(value);
	}
	
	/**
	 * Writes a string.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException 
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	public void writeString(String value) throws UnsupportedEncodingException, IOException {
		byte[] bytes = value.getBytes("UTF-8");
		
		out.writeInt(bytes.length);
		out.write(bytes);
	}
	
	/**
	 * Writes a long.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException
	 */
	public void writeLong(long value) throws IOException {
		out.writeLong(value);
	}
	
	/**
	 * Writes an integer.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException
	 */
	public void writeInt(int value) throws IOException {
		out.writeInt(value);
	}
	
	/**
	 * Writes a short.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException
	 */
	public void writeShort(short value) throws IOException {
		out.writeShort(value);
	}
	
	/**
	 * Writes a boolean.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException
	 */
	public void writeBool(boolean value) throws IOException {
		out.writeBoolean(value);
	}
	
	/**
	 * Writes a float.
	 * 
	 * @param o The value.
	 * 
	 * @throws IOException
	 */
	public void writeFloat(float o) throws IOException {
		out.writeFloat(o);
	}
	
	/**
	 * Writes a char.
	 * 
	 * @param value The value.
	 * 
	 * @throws IOException
	 */
	public void writeChar(char value) throws IOException {
		out.writeChar(value);
	}
	
	/**
	 * Writes an object.
	 * 
	 * @param br The object to write.
	 */
	public void writeObject(BinaryResource br) {
		br.write(this);
	}
	
	/**
	 * Writes a list.
	 * 
	 * @param list The list to write.
	 * 
	 * @throws Exception 
	 */
	public void writeList(List<?> list) throws Exception {
		out.writeInt(list.size());
		
		for (Object o : list) {
			if (o instanceof Character) {
				writeChar((char)o);
				
			} else if (o instanceof String) {
				writeString((String)o);
				
			} else if (o instanceof Byte) {
				writeByte((byte)o);
				
			} else if (o instanceof Float) {
				writeFloat((float)o);
				
			} else if (o instanceof Short) {
				writeShort((short)o);
				
			} else if (o instanceof Integer) {
				writeInt((int)o);
				
			} else if (o instanceof Long) {
				writeLong((long)o);
				
			} else if (o instanceof Boolean) {
				writeBool((boolean)o);
				
			} else if (o instanceof BinaryResource) {
				((BinaryResource)o).write(this);
				
			} else {
				throw new Exception("Use write(object)");
			}
		}
	}
	
	public void write(Object o) {
		
	}

}
