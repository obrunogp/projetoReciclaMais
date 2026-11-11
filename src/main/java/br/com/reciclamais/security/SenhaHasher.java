package br.com.reciclamais.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.HexFormat;

public final class SenhaHasher {

    private static final int ITERACOES = 120_000;
    private static final int TAMANHO_CHAVE_BITS = 256;
    private static final int TAMANHO_SALT_BYTES = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    private SenhaHasher() {
    }

    public static String hash(String senha) {
        byte[] salt = new byte[TAMANHO_SALT_BYTES];
        RANDOM.nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, ITERACOES, TAMANHO_CHAVE_BITS);
        try {
            byte[] chave = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return "pbkdf2_sha256$" + ITERACOES + "$" + HexFormat.of().formatHex(salt) + "$" + HexFormat.of().formatHex(chave);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Não foi possível proteger a senha.", exception);
        } finally {
            spec.clearPassword();
        }
    }
}
