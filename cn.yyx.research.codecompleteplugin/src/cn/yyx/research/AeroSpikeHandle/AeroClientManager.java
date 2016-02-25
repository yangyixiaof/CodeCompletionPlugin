package cn.yyx.research.AeroSpikeHandle;

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
		
		AerospikeClient client = new AerospikeClient(policy, params.host, params.port);
		
		clientManager.put(id, client);
		paramManager.put(id, params);
	}
	
	public void CloseClient(Integer id)
	{
		clientManager.get(id).close();
		clientManager.remove(id);
		paramManager.remove(id);
	}
	
}