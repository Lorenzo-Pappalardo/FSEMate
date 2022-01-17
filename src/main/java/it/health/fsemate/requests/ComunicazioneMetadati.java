package it.health.fsemate.requests;

import it.health.fsemate.user.User;
import it.finanze.ini.fse.wsdl.comunicazionemetadati.ComunicazioneMetadatiPT;
import it.finanze.ini.fse.wsdl.comunicazionemetadati.FseComunicazioneMetadati;
import it.finanze.ini.fse.xsd.comunicazionemetadatiricevuta.ComunicazioneMetadatiRicevuta;
import it.finanze.ini.fse.xsd.comunicazionemetadatirichiesta.ComunicazioneMetadatiRichiesta;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;

@MTOM(enabled = true)
public class ComunicazioneMetadati extends SpecificRequest {
  private final User user;

  public ComunicazioneMetadati(User user) {
    super("ComunicazioneMetadati");
    this.user = user;
  }

  public ComunicazioneMetadatiRicevuta start(ComunicazioneMetadatiRichiesta request)
      throws IOException {
    httpsConnection.connect();

    URL wsdlURL = FseComunicazioneMetadati.WSDL_LOCATION;

    FseComunicazioneMetadati ss = new FseComunicazioneMetadati(wsdlURL, SERVICE);
    ComunicazioneMetadatiPT port = ss.getFseComunicazioneMetadati();

    BindingProvider bindingProvider = (BindingProvider) port;
    bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, getRequestHeaders(user));

    SOAPBinding soapBinding = (SOAPBinding) bindingProvider.getBinding();
    soapBinding.setMTOMEnabled(true);

    System.out.println("Invoking ComunicazioneMetadati...");
    ComunicazioneMetadatiRicevuta comunicazioneMetadatiRicevuta = port.comunicazioneMetadati(request);

    httpsConnection.disconnect();

    return comunicazioneMetadatiRicevuta;
  }
}
