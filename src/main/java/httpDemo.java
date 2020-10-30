import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider;
import java.security.cert.X509Certificate;

public class httpDemo {
    public static void main(String[] args) throws IOException {
        String url = "https://cloud.pensees-systems.com";
        System.out.println(httpGet(url));
    }


    public static String httpGet(String httpUrl) throws IOException {
        BufferedReader input = null;
        StringBuilder sb = null;
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL(httpUrl);
            try {
                // trust all hosts
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                if (url.getProtocol().toLowerCase().equals("https")) {
                    https.setHostnameVerifier(DO_NOT_VERIFY);
                    con = https;
                } else {
                    con = (HttpURLConnection) url.openConnection();
                }
                input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                sb = new StringBuilder();
                String s;
                while ((s = input.readLine()) != null) {
                    sb.append(s).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } finally {
            // close buffered
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // disconnecting releases the resources held by a connection so they may be closed or reused
            if (con != null) {
                con.disconnect();
            }
        }

        return sb == null ? null : sb.toString();
    }

    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        final String TAG = "trustAllHosts";
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) /*throws CertificateException*/ {
//                Log.i(TAG, "checkClientTrusted");
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) /*throws CertificateException*/ {
//                Log.i(TAG, "checkServerTrusted");
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
           /* Provider p = new DemoProvider("tls",1.1,"https1.1");
            p.load(new FileInputStream(new File("D:\\kyf\\java-workspace\\game\\java_demo\\src\\main\\java\\cloud.crt")));*/
            //SSLContext sc = SSLContext.getInstance("TLS", p);
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*static {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(new FileInputStream(new File("CERTIFICATE_LOCATION")));

// Create TrustStore
        KeyStore trustStoreContainingTheCertificate = KeyStore.getInstance("JKS");
        trustStoreContainingTheCertificate.load(null, null);

        trustStoreContainingTheCertificate.setCertificateEntry("ANY_CERTIFICATE_ALIAS", certificate);

// Create SSLContext
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStoreContainingTheCertificate);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        System.out.println(sslSocketFactory);
}*/
class DemoProvider extends Provider{

    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     *
     * @param name    the provider name.
     * @param version the provider version number.
     * @param info    a description of the provider and its services.
     */
    protected DemoProvider(String name, double version, String info) {
        super(name, version, info);
    }
}
