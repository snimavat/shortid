package me.nimavat.shortid;

public class ShortId {

	private static final int version = 6;
	private static final Long REDUCE_TIME = 1459707606518L;
	private static int clusterWorkerId = 0;

	private static int counter;
	private static int previousSeconds;


	/**
	 * Set the seed.
	 * Highly recommended if you don't want people to try to figure out your id schema.
	 * exposed as shortid.seed(int)
	 * @param seed Integer value to seed the random alphabet.  ALWAYS USE THE SAME SEED or you might get overlaps.
	 */
	public static void seed(Long seedValue) {
		Alphabet.setSeed(seedValue);
	}

	/**
	 * Set the cluster worker or machine id
	 * exposed as shortid.worker(int)
	 * @param workerId worker must be positive integer.  Number less than 16 is recommended.
	 * returns shortid module so it can be chained.
	 */
	public static void worker(int workerId) {
		clusterWorkerId = workerId;
	}

	/**
	 *
	 * sets new characters to use in the alphabet
	 * returns the shuffled alphabet
	 */
	public static String characters(String newCharacters) {
		if (newCharacters != null) {
			Alphabet.characters(newCharacters);
		}

		return Alphabet.getShuffled();
	}

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
