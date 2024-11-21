package dmMachine.security.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESCryptoService {

	private static final String SECRET_KEY = "54121452111";
	private static boolean isEncryption = false;

	public static String encrypt(String plainText) throws Exception {
		if (isEncryption) {
			SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
			return Base64.getEncoder().encodeToString(encryptedBytes);
		}
		return plainText;
	}

	public static String decrypt(String encryptedText) throws Exception {
		if (isEncryption) {
			SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			return new String(decryptedBytes);
		}
		return encryptedText;
	}
}
