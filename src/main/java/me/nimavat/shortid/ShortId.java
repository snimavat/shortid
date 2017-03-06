package me.nimavat.shortid;

public class ShortId {

	private static final int version = 6;
	private static final Long REDUCE_TIME = 1459707606518L;
	private static final int clusterWorkerId = 0;

	private static int counter;
	private static int previousSeconds;

	/**
	 * Generate unique id
	 * Returns string id
	 */
	public static String generate() {

		String str = "";

		int seconds = (int) Math.floor((System.currentTimeMillis() - REDUCE_TIME) * 0.001);

		if (seconds == previousSeconds) {
			counter++;
		} else {
			counter = 0;
			previousSeconds = seconds;
		}

		str = str + Encode.encode(Alphabet::lookup, version);
		str = str + Encode.encode(Alphabet::lookup, clusterWorkerId);
		if (counter > 0) {
			str = str + Encode.encode(Alphabet::lookup, counter);
		}
		str = str + Encode.encode(Alphabet::lookup, seconds);

		return str;
	}
}
