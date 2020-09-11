package com.gaga.jdbc.dao.stockDTO;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SslUtils {

    /**
     * 忽略HTTPS請求的SSL證書，必須在openConnection之前調用
     * */
    private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager trustManager =new miTM();
        trustAllCerts[0] = trustManager;
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }

    static class miTM implements TrustManager, X509TrustManager{

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }
        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public boolean isSeverTrusted(X509Certificate[] certs){
            return true;
        }
        public boolean isClientTrusted(X509Certificate[] certs){
            return  true;
        }
    }
    public static void ignoreSSL() throws KeyManagementException, NoSuchAlgorithmException {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String urlHostName, SSLSession sslSession) {
                System.out.println("Warming:URL Host: "+urlHostName+"vs."+sslSession.getPeerHost());
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }
}
