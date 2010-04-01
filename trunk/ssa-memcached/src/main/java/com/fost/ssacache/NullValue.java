/**
 * 
 */
package com.fost.ssacache;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Janly
 *
 */
public class NullValue implements Externalizable{

	public int hashCode() {
		return 1;
	}

	public boolean equals(final Object obj) {
		if (obj == null) { return false;}
		return (obj instanceof NullValue);
	}

	public void writeExternal(ObjectOutput out) throws IOException { }

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException { }

}
