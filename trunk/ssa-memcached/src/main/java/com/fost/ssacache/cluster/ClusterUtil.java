/**
 * 
 */
package com.fost.ssacache.cluster;

import com.fost.ssacache.ClientAdapter;

/**
 * @author Janly
 *
 */
public final class ClusterUtil {

	public static ClientAdapter getMasterClientAdapter(java.util.Map<String, java.util.List<ClientAdapter>> groupAdapterMap,String key){
		int keyCode=key.hashCode();
		int mod=keyCode%groupAdapterMap.size();
		Object[] keys=groupAdapterMap.keySet().toArray();
		java.util.List<ClientAdapter> list=groupAdapterMap.get(keys[mod]);
		mod=keyCode%list.size();
		return list.get(mod);
	}
}
