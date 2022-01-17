package it.health.fsemate.requests;

import it.health.fsemate.user.User;

import javax.net.ssl.*;
import javax.xml.namespace.QName;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.*;

public abstract class SpecificRequest {
  protected final QName SERVICE;
  protected final HttpsURLConnection httpsConnection;

  protected SpecificRequest(String requestType) {
    this.SERVICE = new QName("http://" + requestType.toLowerCase() + ".wsdl.fse.ini.finanze.it", "fse" + requestType);
    this.httpsConnection = createAndSetHttpUrlConnection(
        "https://fseservicetest.sanita.finanze.it/FseInsServicesWeb/services/fse" + requestType);
  }

  private static HttpsURLConnection createAndSetHttpUrlConnection(final String urlPath) {
    try {
      // Create SSL context and trust all certificates
      SSLContext sslContext = SSLContext.getInstance("SSL");
      TrustManager[] trustAll = new TrustManager[]{new TrustAllCertificates()};
      sslContext.init(null, trustAll, new java.security.SecureRandom());

      // Set trust all certificates context to HttpsURLConnection
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

      // Open HTTPS connection
      URL url = new URL(urlPath);
      HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();

      // Trust all hosts
      httpsConnection.setHostnameVerifier(new TrustAllHosts());

      return httpsConnection;
    } catch (Exception e) {
      System.err.println(e);
      System.exit(1);
    }
    return null;
  }

  protected static Map<String, List<String>> getRequestHeaders(User user) {
    Map<String, List<String>> requestHeaders = new HashMap<>();

    String encoded = Base64.getEncoder().encodeToString((user.getCf() + ':' + user.getPassword()).getBytes());
    requestHeaders.put("Authorization", Collections.singletonList("Basic " + encoded));

    return requestHeaders;
  }

  /**
   * Dummy class implementing X509TrustManager to trust all certificates
   */
  private static final class TrustAllCertificates implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] certs, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] certs, String authType) {
    }

    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }

  /**
   * Dummy class implementing HostnameVerifier to trust all hostnames
   */
  private static final class TrustAllHosts implements HostnameVerifier {
    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }
}
