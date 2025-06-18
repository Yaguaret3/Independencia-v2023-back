package com.megajuegos.independencia.dto.request.revolucionario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class VoteProposalRequest {


    @Size(max = 100, message = "La propuesta no puede tener más de 100 caracteres.")
    private String proposal;
}
