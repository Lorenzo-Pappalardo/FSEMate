package it.health.fsemate.requests;

import it.health.fsemate.requests.types.ComunicazioneMetadatiRequestFields;
import it.health.fsemate.requests.types.EsitoCaricamentoDocumentoRequestFields;
import it.health.fsemate.requests.types.RecuperoDocumentoRequestFields;
import it.health.fsemate.requests.types.RicercaDocumentiRequestFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class RequestsController {
  @Autowired
  private RequestsService requestsService;

  @PostMapping("/ComunicazioneMetadati")
  public ResponseEntity<Object> sendDocument(@Valid @RequestBody ComunicazioneMetadatiRequestFields fields) {
    try {
      return ResponseEntity.ok(requestsService.sendDocument(fields));
    } catch (Exception e) {
      // TO-DO: Make the exception actually be returned to the client
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/EsitoCaricamentoDocumento")
  public ResponseEntity<Object> getOutcome(@Valid @RequestBody EsitoCaricamentoDocumentoRequestFields fields) {
    try {
      return ResponseEntity.ok(requestsService.getOutcome(fields));
    } catch (IOException e) {
      // TO-DO: Make the exception actually be returned to the client
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/RicercaDocumenti")
  public ResponseEntity<Object> getDocuments(@Valid @RequestBody RicercaDocumentiRequestFields fields) {
    try {
      return ResponseEntity.ok(requestsService.getDocuments(fields));
    } catch (IOException e) {
      // TO-DO: Make the exception actually be returned to the client
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/RecuperoDocumento")
  public ResponseEntity<Object> getDocument(@Valid @RequestBody RecuperoDocumentoRequestFields fields) {
    try {
      return ResponseEntity.ok(requestsService.getDocument(fields));
    } catch (Exception e) {
      e.printStackTrace();
      // TO-DO: Make the exception actually be returned to the client
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
