package cn.yyx.research.aerospikehandle;

import java.util.Map;
import java.util.TreeMap;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;

public class AeroClientManager {
	
	Map<Integer, AerospikeClient> clientManager = new TreeMap<Integer, AerospikeClient>();
	Map<Integer, Parameters> paramManager = new TreeMap<Integer, Parameters>();
	
	public AerospikeClient GetClient(Integer id)
	{
		return clientManager.get(id);
	}
	
	public Parameters GetParameters(Integer id)
	{
		return paramManager.get(id);
	}
	
	public AeroClientManager() {
	}
	
	public void ANewClient(Integer id, Parameters params)
	{
		ClientPolicy policy = new ClientPolicy();
		policy.user = params.user;
		policy.password = params.password;
		policy.failIfNotConnected = true;
		
		params.policy = policy.readPolicyDefault;
		params.writePolicy = policy.writePolicyDefault;
		
		AerospikeClient client = null;
		try {
			client = new AerospikeClient(policy, params.host, params.port);
		} catch (Exception e) {
			System.err.println("new AerospikeClient run into exception.");
			e.printStackTrace();
			System.exit(1);
		} catch (Error e) {
			System.err.println("new AerospikeClient run into error.");
			e.printStackTrace();
			System.exit(1);
		}
		
		clientManager.put(id, client);
		paramManager.put(id, params);
	}
	
	public void CloseClient(Integer id)
	{
		clientManager.get(id).close();
		clientManager.remove(id);
		paramManager.remove(id);
	}

	public boolean Contains(Integer id) {
		if (clientManager.containsKey(id))
		{
			return true;
		}
		return false;
	}
	
}