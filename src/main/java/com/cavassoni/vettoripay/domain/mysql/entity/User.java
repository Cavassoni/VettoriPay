package com.cavassoni.vettoripay.domain.mysql.entity;

import com.cavassoni.vettoripay.domain.mysql.type.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "user")
@Entity
@Schema(description = "Usuário")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Identificador do usuário")
    private UUID id;

    @NotNull
    @Max(150)
    @Column(length = 150, nullable = false)
    @Schema(description = "Nome do usuário", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Size(min = 11, max = 14)
    @Column(unique = true, length = 14, nullable = false)
    @Schema(description = "CPF", requiredMode = RequiredMode.REQUIRED)
    private String cpf;

    @Max(100)
    @Column(unique = true, length = 100)
    @Schema(description = "E-mail")
    private String email;

    @Max(16)
    @Column(unique = true, length = 16)
    @Schema(description = "Telefone")
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    @Schema(description = "Tipo de usuário")
    private UserType userType;

    @Max(100)
    @Column(length = 100)
    @Schema(description = "Senha")
    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST})
    @Schema(description = "Conta do usuário")
    @JsonManagedReference
    private Wallet wallet;

}
