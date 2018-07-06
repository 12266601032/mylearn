package com.example.lcc.basic.java.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

public class PKCS12Test {
    static {
        // 系统添加BC加密算法 以后系统中调用的算法都是BC的算法
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String args[]) throws NoSuchAlgorithmException,
            InvalidKeyException, SecurityException, SignatureException,
            KeyStoreException, CertificateException, IOException {

        String certPath = "d:/hnzd.pfx";

        // 创建KeyStore
        KeyStore store = KeyStore.getInstance("PKCS12");
        store.load(null, null);

        /* RSA算法产生公钥和私钥 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();
        // 组装证书
        String issuer = "C=CN,ST=BJ,L=BJ,O=HNZD,OU=HNZD,CN=HNZD";
        String subject = issuer;

        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

        Calendar expire = Calendar.getInstance();
        expire.add(Calendar.YEAR,10);

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(new X500Principal(issuer));
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 50000));
        certGen.setNotAfter(expire.getTime());
        certGen.setSubjectDN(new X500Principal(subject));
        certGen.setPublicKey(keyPair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

        X509Certificate cert = certGen.generateX509Certificate(keyPair.getPrivate());
        System.out.println(cert.toString());
        //System.out.println(keyPair.getPrivate());
        //store.setCertificateEntry(alias, cert);

        store.setKeyEntry("123456", keyPair.getPrivate(),
                "123456".toCharArray(), new X509Certificate[]{cert});

        FileOutputStream fout = new FileOutputStream(certPath);
        store.store(fout, "123456".toCharArray());
        fout.close();
    }

}
