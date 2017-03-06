package me.nimavat.shortid;

class RandomFromSeed {

	private static int seed = 1;

	/**
	 * return a random number based on a seed
	 * @returns {number}
	 */
	static double nextValue() {
		seed = (seed * 9301 + 49297) % 233280;
		return seed/(233280.0);
	}

	static void setSeed(int _seed_) {
		seed = _seed_;
	}

}
