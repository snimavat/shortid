package me.nimavat.shortid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Alphabet {
	private static final String ORIGINAL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";

	static String alphabet;
	static int previousSeed;

	static String shuffled;

	private static void reset() {
		shuffled = null;
	}

	private static void setCharacters(String _alphabet_) {
		if (_alphabet_ == null) {
			if (alphabet != ORIGINAL) {
				alphabet = ORIGINAL;
				reset();
			}
		}

		if (_alphabet_.equals(alphabet)) {
			return;
		}


		if (_alphabet_.length() != ORIGINAL.length()) {
			throw new RuntimeException("Custom alphabet for shortid must be " + ORIGINAL.length() + " unique characters. You submitted " + _alphabet_.length() + " characters: " + _alphabet_);
		}

		List<String> characters = Arrays.asList(_alphabet_.split(""));
		List duplicates = Arrays.asList(_alphabet_.split("")).stream().filter((String c) -> Collections.frequency(characters, c) > 1).collect(Collectors.toList());


		if (duplicates.size() > 0) {
			throw new RuntimeException("Custom alphabet for shortid must be " + ORIGINAL.length() + " unique characters. These characters were not unique: " + duplicates.stream().collect(Collectors.joining(", ")));
		}

		alphabet = _alphabet_;
		reset();
	}

	static String characters(String _alphabet_) {
		setCharacters(_alphabet_);
		return alphabet;
	}

	void setSeed(int seed) {
		RandomFromSeed.setSeed(seed);
		if (previousSeed != seed) {
			reset();
			previousSeed = seed;
		}
	}

	private static String shuffle() {
		if (alphabet == null) {
			setCharacters(ORIGINAL);
		}

		List<String> sourceArray = Arrays.asList(alphabet.split(""));
		Long r = Math.round(Math.floor(RandomFromSeed.nextValue()));
		Collections.shuffle(sourceArray, new Random(r));
		return sourceArray.stream().collect(Collectors.joining(""));
	}

	private static String getShuffled() {
		if (shuffled != null) {
			return shuffled;
		}
		shuffled = shuffle();
		return shuffled;
	}

	static Character lookup(int index) {
		String alphabetShuffled = getShuffled();
		return alphabetShuffled.charAt(index);
	}

}
