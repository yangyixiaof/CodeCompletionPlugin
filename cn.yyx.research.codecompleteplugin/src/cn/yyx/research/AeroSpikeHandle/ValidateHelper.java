package cn.yyx.research.AeroSpikeHandle;

import java.util.Arrays;

public class ValidateHelper {
	public static void validateSize(int expected, int received) throws Exception {
		if (received != expected) {
			throw new Exception(String.format("Size mismatch: expected=%s received=%s", expected, received));
		}
	}

	public static void validate(Object expected, Object received) throws Exception {
		if (!received.equals(expected)) {
			throw new Exception(String.format("Mismatch: expected=%s received=%s", expected, received));
		}
	}

	public static void validate(byte[] expected, byte[] received) throws Exception {
		if (!Arrays.equals(expected, received)) {
			throw new Exception(String.format("Mismatch: expected=%s received=%s", Arrays.toString(expected),
					Arrays.toString(received)));
		}
	}
}
