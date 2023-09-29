package com.cavassoni.vettoripay.domain.mysql.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "wallet")
@Entity
@Schema(description = "Carteira do usuário")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Identificador da carteira")
    private UUID id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @Schema(description = "Usuário", requiredMode = RequiredMode.REQUIRED)
    @JsonBackReference
    private User user;

    @NotNull
    @PositiveOrZero
    @DecimalMax(value = "999999999999.99")
    @Column(nullable = false, precision = 14, scale = 2)
    @Schema(description = "Saldo da carteira", requiredMode = RequiredMode.REQUIRED)
    private BigDecimal balance;

}
