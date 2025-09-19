/*
 * Copyright 2025 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.storage.hash.imp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import se.uu.ub.cora.storage.StorageException;
import se.uu.ub.cora.storage.hash.CoraDigestor;

public class CoraDigestorImp implements CoraDigestor {

	private String sha256Algorithm = "SHA-256";

	@Override
	public String stringToSha256Hex(String valueToHash) {
		MessageDigest digest = tryToGetDigestAlgorithm();
		return hashValueUsingDigest(digest, valueToHash);
	}

	private String hashValueUsingDigest(MessageDigest digest, String valueToHash) {
		byte[] valueAsBytes = valueToHash.getBytes(StandardCharsets.UTF_8);
		final byte[] hashAsBytes = digest.digest(valueAsBytes);
		return bytesToHex(hashAsBytes);
	}

	private MessageDigest tryToGetDigestAlgorithm() {
		try {
			return MessageDigest.getInstance(sha256Algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw StorageException
					.withMessageAndException("Failed to get " + sha256Algorithm + " algorithm", e);
		}
	}

	protected String bytesToHex(byte[] hash) {
		final int initialFactorBytesToHex = 2;
		StringBuilder hexString = new StringBuilder(initialFactorBytesToHex * hash.length);
		bytesToHexInStringBuilder(hash, hexString);
		return hexString.toString();
	}

	private void bytesToHexInStringBuilder(byte[] hash, StringBuilder hexString) {
		for (int i = 0; i < hash.length; i++) {
			byte byteOfHash = hash[i];
			String hex = byteToHex(byteOfHash);
			hexString.append(hex);
		}
	}

	private String byteToHex(byte byteOfHash) {
		String hex = Integer.toHexString(0xff & byteOfHash);
		if (hex.length() == 1) {
			hex = "0" + hex;
		}
		return hex;
	}

	public void onlyForTestSetSha256Algorithm(String algorithm) {
		this.sha256Algorithm = algorithm;
	}

}
