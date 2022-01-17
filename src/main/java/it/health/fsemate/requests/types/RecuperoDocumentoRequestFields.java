package it.health.fsemate.requests.types;

import it.finanze.ini.fse.xsd.tipodatiricercadocumenti.MetadatoRicercaType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RecuperoDocumentoRequestFields {
  private MetadatoRicercaType metadato;

  @NotBlank
  private String contestoOperativo;

  @NotBlank
  private String presaInCarico;
}
