package it.health.fsemate.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class User {
  @Id
  @Size(min = 16, max = 16)
  private String cf;

  @NotBlank
  private String password;

  @NotBlank
  private String pinCode;

  @NotBlank
  private String identificativoOrganizzazione;

  @NotBlank
  private String strutturaUtente;

  @NotBlank
  private String ruoloUtente;
}
