package fr.univtln.madapm.votemanager.crypto.keygen;

import fr.univtln.madapm.votemanager.crypto.aes.CAESCrypt;
import junit.framework.TestCase;

import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by civars169 on 21/05/15.
 * copyright Christian
 */

/**
 * Classe de test sur la génération de clef de cryptage privé et public avec paramètres
 */
public class CTestCryptoKeyTest extends TestCase {

    public void testMain() throws Exception {
        System.out.println("Test de clef à échanger avec l'appli");


        String lData = "eafusfgigfyugfrdi+-*/)àé'(çà-èçà&²<!:;,*ùù$";

        CKeyGenerator keyGenerator = new CKeyGenerator();
        System.out.println(keyGenerator);
        SecretKeySpec lClef = keyGenerator.specificKeyKeyGen(BigInteger.probablePrime(128, new SecureRandom()).toByteArray());

        CAESCrypt aesCrypt = new CAESCrypt();
        byte[] lCryptData = aesCrypt.encrypt(lClef, lData);

        System.out.println(aesCrypt.decrypt(lClef, lCryptData));


        System.out.println("Fin du test");
    }
}