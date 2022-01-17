package it.health.fsemate.requests.types;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class EsitoCaricamentoDocumentoRequestFields {
  private List<String> identificativiTemporaneiDocumenti;

  @NotBlank
  private String dataRicercaDA;

  @NotBlank
  private String dataRicercaA;
}
