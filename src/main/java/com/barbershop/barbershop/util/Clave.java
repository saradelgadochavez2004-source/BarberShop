package com.barbershop.barbershop.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Hash PBKDF2 de contraseñas. Acepta claves antiguas en texto plano y las migra al iniciar sesión. */
public final class Clave {
    private static final int ITERACIONES = 120000;
    private Clave() {}

    public static String hash(String clave) {
        byte[] sal = new byte[16];
        new SecureRandom().nextBytes(sal);
        Base64.Encoder b = Base64.getEncoder();
        return "pbkdf2$" + ITERACIONES + "$" + b.encodeToString(sal) + "$" + b.encodeToString(derivar(clave, sal, ITERACIONES));
    }

    public static boolean esHash(String guardada) { return guardada != null && guardada.startsWith("pbkdf2$"); }

    public static boolean verificar(String clave, String guardada) {
        if (clave == null || guardada == null) return false;
        if (!esHash(guardada)) return guardada.equals(clave);
        String[] p = guardada.split("\\$");
        Base64.Decoder d = Base64.getDecoder();
        return MessageDigest.isEqual(d.decode(p[3]), derivar(clave, d.decode(p[2]), Integer.parseInt(p[1])));
    }

    private static byte[] derivar(String clave, byte[] sal, int it) {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(new PBEKeySpec(clave.toCharArray(), sal, it, 256)).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
