package com.cavassoni.vettoripay.domain.mongodb.entity;

import com.cavassoni.vettoripay.domain.mysql.entity.User;
import com.cavassoni.vettoripay.domain.mongodb.type.NotificationStatusType;
import com.cavassoni.vettoripay.domain.mongodb.type.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Notificação")
public class Notification {
    @Id
    @Schema(description = "Identificador da notificação")
    private UUID id;

    @Schema(description = "Usuário")
    private OperationUser user;

    @Schema(description = "Mensagem da notificação")
    private String message;

    @Schema(description = "Data de envio da notificação")
    private OffsetDateTime shippingDate;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo da notificação")
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação da notificação")
    private NotificationStatusType situationType;
}
