package me.nimavat.shortid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Alphabet {
	private static final String ORIGINAL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";

	static long seed = 1;
	private static final Random random = new Random(seed);

	static String alphabet;
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

	static void setSeed(Long _seed_) {
		random.setSeed(_seed_);
		if (seed != _seed_) {
			reset();
			seed = _seed_;
		}
	}

	private static String shuffle() {
		if (alphabet == null) {
			setCharacters(ORIGINAL);
		}

		List<String> sourceArray = Arrays.asList(alphabet.split(""));
		Collections.shuffle(sourceArray, random);
		return sourceArray.stream().collect(Collectors.joining(""));
	}

	static String getShuffled() {
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
