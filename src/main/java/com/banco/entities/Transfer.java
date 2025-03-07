package com.banco.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private Date date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String beneficiaryName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String destinationAccount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String payerName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String payerAccount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String concept;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private TransferStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private BigDecimal fee;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private String externalReference;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    private BigDecimal amount;
}
