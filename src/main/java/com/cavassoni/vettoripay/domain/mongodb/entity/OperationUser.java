package com.cavassoni.vettoripay.domain.mongodb.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Usuário da operação")
public class OperationUser {

    @Id
    @Schema(description = "Identificador do usuário")
    private UUID id;

    @Schema(description = "Nome do usuário")
    private String name;

}
