package com.ems.dingdong.utiles

import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

class RSAUtil {
    companion object{
        @JvmStatic
        fun signature(plainText: String): String {
            val privateKey = """
               MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIteJ3exVZg0Wr7D
               7KaWNITfjj+biG6g+mWrgaHimpyFEUeq0hPtBD2DjA0L7d0/TwyffMqSwbGgB2Fj
               bRcLqChDgd8RvaEFU/Mx5FQzBmbb8c2ZOADF8m8hY4Xg+h/un6sJ8kdLjx58hMoV
               JTm2sy2Lg0sHK9MSmRgLrEVJKU/LAgMBAAECgYBaItD/1o93WxT9oBWkQC1DaprD
               hMIeWrrXmq7Clp5McLuWUGKCRJ6jcjrYDUkP+OwVS+kX0wa27LsZP4bEiuN8ApcZ
               5v3R66UaGvad7SQKdA2pgf0ODSj0MFHzQ7rbRFWlFhdWYyUpAOgkb6CeLI92JCHB
               6U2VrKbqF8akRjKjsQJBAMF7HK/5LnfE4S0uucYuBNXBoGJuD2NfmqqEjBphxpRn
               aGxENffo7cUfaVJcIRWvhfXonhxi6rOJAMQaeeNmWL8CQQC4ZsONlMzyYUQ+kM3G
               w9N8aL9US6n7aK0yylKDbr3JM0l3ciaSVQ05/gGEBsA/nqqFrAiLpmchu1xxQFSR
               GN/1AkAp5S2mES/1sUUNEpQZjLdxTdcb2TctznLgP4lS4R8t3WJoJzEEeISb7ZxR
               wC9N0c8RG4i5HtYxgBYRYKZKDkxjAkACaJU1TDRBFjQl/Q4zAmvIvDWDjFl0BzH7
               79iUDuY7sofLH5qRXrsFfuPWLaBlNFVV2aFi8ZF3R1M1x3lTS9fhAkA4fhhKY5X5
               IIZ8z/tukw8/AmjNvZ4BpF9Vi4IffU32B0YsjxPE/PJhkUDBF4pkUbCufoo4zcyg
               FZ1edGBvRSm6
               """.trimIndent()
            try {
                val b1 = Base64.getMimeDecoder().decode(privateKey)
                val spec = PKCS8EncodedKeySpec(b1)
                val kf: KeyFactory
                kf = KeyFactory.getInstance("RSA")
                val privateSignature = Signature.getInstance("SHA256withRSA")
                privateSignature.initSign(kf.generatePrivate(spec))
                privateSignature.update(plainText.toByteArray(StandardCharsets.UTF_8))
                val s = privateSignature.sign()
                return Base64.getEncoder().encodeToString(s)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: SignatureException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            }
            return ""
        }
    }

}