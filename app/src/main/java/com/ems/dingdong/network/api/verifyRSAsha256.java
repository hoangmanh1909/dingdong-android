package com.ems.dingdong.network.api;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class verifyRSAsha256 {

    public static String main(String plainText) {
        //  "-----END PUBLIC KEY-----";
        String publicKey = "  MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIteJ3exVZg0Wr7D\n" +
                "7KaWNITfjj+biG6g+mWrgaHimpyFEUeq0hPtBD2DjA0L7d0/TwyffMqSwbGgB2Fj\n" +
                "bRcLqChDgd8RvaEFU/Mx5FQzBmbb8c2ZOADF8m8hY4Xg+h/un6sJ8kdLjx58hMoV\n" +
                "JTm2sy2Lg0sHK9MSmRgLrEVJKU/LAgMBAAECgYBaItD/1o93WxT9oBWkQC1DaprD\n" +
                "hMIeWrrXmq7Clp5McLuWUGKCRJ6jcjrYDUkP+OwVS+kX0wa27LsZP4bEiuN8ApcZ\n" +
                "5v3R66UaGvad7SQKdA2pgf0ODSj0MFHzQ7rbRFWlFhdWYyUpAOgkb6CeLI92JCHB\n" +
                "6U2VrKbqF8akRjKjsQJBAMF7HK/5LnfE4S0uucYuBNXBoGJuD2NfmqqEjBphxpRn\n" +
                "aGxENffo7cUfaVJcIRWvhfXonhxi6rOJAMQaeeNmWL8CQQC4ZsONlMzyYUQ+kM3G\n" +
                "w9N8aL9US6n7aK0yylKDbr3JM0l3ciaSVQ05/gGEBsA/nqqFrAiLpmchu1xxQFSR\n" +
                "GN/1AkAp5S2mES/1sUUNEpQZjLdxTdcb2TctznLgP4lS4R8t3WJoJzEEeISb7ZxR\n" +
                "wC9N0c8RG4i5HtYxgBYRYKZKDkxjAkACaJU1TDRBFjQl/Q4zAmvIvDWDjFl0BzH7\n" +
                "79iUDuY7sofLH5qRXrsFfuPWLaBlNFVV2aFi8ZF3R1M1x3lTS9fhAkA4fhhKY5X5\n" +
                "IIZ8z/tukw8/AmjNvZ4BpF9Vi4IffU32B0YsjxPE/PJhkUDBF4pkUbCufoo4zcyg\n" +
                "FZ1edGBvRSm6";
        byte[] b1 = Base64.decode(publicKey, Base64.DEFAULT);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(kf.generatePrivate(spec));
            privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] s = privateSignature.sign();
            return Base64.encodeToString(s, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return "";
    }


}
