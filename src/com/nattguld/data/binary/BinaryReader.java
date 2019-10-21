package com.nattguld.data.binary;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

import com.nattguld.data.IResourceReader;

/**
 * 
 * @author randqm
 *
 */

public class BinaryReader implements IResourceReader, AutoCloseable {
	
	/**
	 * The data input stream.
	 */
	private final DataInputStream in;
	
	
	/**
	 * Creates a new binary reader.
	 * 
	 * @param in The input stream.
	 */
	public BinaryReader(DataInputStream in) {
		this.in = in;
	}
	
	/**
	 * Reads a byte.
	 * 
	 * @return The byte.
	 * 
	 * @throws IOException
	 */
	public byte readByte() throws IOException {
		return in.readByte();
	}
	
	/**
	 * Reads a string.
	 * 
	 * @return The string.
	 * 
	 * @throws IOException 
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	public String readString() throws UnsupportedEncodingException, IOException {
		int length = in.readInt();
		
		byte[] data = new byte[length];
		
		in.readFully(data);
		
		return new String(data, "UTF-8");
	}
	
	/**
	 * Reads a long.
	 * 
	 * @return The long.
	 * 
	 * @throws IOException
	 */
	public long readLong() throws IOException {
		return in.readLong();
	}
	
	/**
	 * Reads an integer.
	 * 
	 * @return The integer.
	 * 
	 * @throws IOException
	 */
	public int readInt() throws IOException {
		return in.readInt();
	}
	
	/**
	 * Reads a short.
	 * 
	 * @return The short.
	 * 
	 * @throws IOException
	 */
	public short readShort() throws IOException {
		return in.readShort();
	}
	
	/**
	 * Reads a double.
	 * 
	 * @return The double.
	 * 
	 * @throws IOException
	 */
	public double readDouble() throws IOException {
		return in.readDouble();
	}
	
	/**
	 * Reads a boolean.
	 * 
	 * @return The boolean.
	 * 
	 * @throws IOException
	 */
	public boolean readBool() throws IOException {
		return in.readBoolean();
	}
	
	/**
	 * Reads a float.
	 * 
	 * @return The float.
	 * 
	 * @throws IOException
	 */
	public float readFloat() throws IOException {
		return in.readFloat();
	}
	
	/**
	 * Reads a char.
	 * 
	 * @return The chat.
	 * 
	 * @throws IOException
	 */
	public char readChar() throws IOException {
		return in.readChar();
	}
	
	/**
	 * Populates a list.
	 * 
	 * @param list The list to store the elements in.
	 * 
	 * @param c The class type of the list elements.

	 * @throws Exception
	 */
	public void readList(List<Object> list, Class<?> c) throws Exception {
		int size = in.readInt();
		
		for (int i = 0; i < size; i ++) {
			if (c == Character.class) {
				list.add(readChar());
				
			} else if (c == String.class) {
				list.add(readString());
				
			} else if (c == Byte.class) {
				list.add(readByte());
				
			} else if (c == Float.class) {
				list.add(readFloat());
				
			} else if (c == Double.class) {
				list.add(readDouble());
				
			} else if (c == Integer.class) {
				list.add(readInt());
				
			} else if (c == Long.class) {
				list.add(readLong());
				
			} else if (c == Boolean.class) {
				list.add(readBool());
				
			} else {
				throw new Exception("Data type " + c.getSimpleName() + " not supported.");
			}
		}
	}
	
	@Override
	public void close() throws Exception {
		if (Objects.nonNull(in)) {
			in.close();
		}
	}

	/**
	 * Retrieves the data input stream.
	 * 
	 * @return The data input stream.
	 */
	public DataInputStream getDataInputStream() {
		return in;
	}

}
