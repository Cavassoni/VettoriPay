package com.cavassoni.vettoripay.domain.mongodb.entity;

import com.cavassoni.vettoripay.domain.mysql.entity.Wallet;
import com.cavassoni.vettoripay.domain.mongodb.type.TransactionStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Transação")
public class Transaction {
    @Id
    @Schema(description = "Identificador da transação")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payer_account_id")
    @Schema(description = "Conta do usuário que enviou o dinheiro")
    private TransactionWallet payerWallet;

    @ManyToOne
    @JoinColumn(name = "payee_account_id")
    @Schema(description = "Conta do usuário que recebeu o dinheiro")
    private TransactionWallet payeeWallet;

    @Schema(description = "Valor")
    private BigDecimal value;

    @Schema(description = "Data da transação")
    private OffsetDateTime transactionDate;

    @Schema(description = "Mensagem")
    private String message;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação")
    private TransactionStatusType situationType;

    @Schema(description = "Observação")
    private String observationSituation;

}
