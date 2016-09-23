package com.hengyi.japp.common;

import com.hengyi.japp.common.codec.Base58;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.Validate;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by jzb on 15-12-5.
 */
public final class J_codec {

//    public static final String uuid64(UUID uuid) {
//        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
//        bb.putLong(uuid.getMostSignificantBits());
//        bb.putLong(uuid.getLeastSignificantBits());
//        return Base64.encodeBase64URLSafeString(bb.array());
//    }

    static final String uuid58(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base58.encode(bb.array());
    }

    public static final byte[] password(final String password, final byte[] salt, final int iterationCount, final int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        Validate.notBlank(password);
        final PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keySize);
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    public static final String password(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        final byte[] salt = new byte[16];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
        final int iterationCount = 30000;
        final int keySize = 160;
        final byte[] hash = password(password, salt, iterationCount, keySize);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(255);
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.write(hash.length);
        oos.write(salt.length);
        oos.write(hash);
        oos.write(salt);
        oos.writeInt(iterationCount);
        oos.writeInt(keySize);
        oos.flush();
        return Base64.encodeBase64String(baos.toByteArray());
    }

    public static final boolean checkPassword(final String encryptPassword, final String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        Validate.notBlank(encryptPassword);
        final InputStream is = new ByteArrayInputStream(Base64.decodeBase64(encryptPassword));
        final int version = is.read();
        if (version != 255) {
            throw new IOException("version mismatch");
        }
        final ObjectInputStream ois = new ObjectInputStream(is);
        final byte[] hash = new byte[ois.read()];
        final byte[] salt = new byte[ois.read()];
        ois.read(hash);
        ois.read(salt);
        final int iterationCount = ois.readInt();
        final int keySize = ois.readInt();
        final byte[] _hash = password(password, salt, iterationCount, keySize);
        return Arrays.equals(hash, _hash);
    }
}
