package com.megajuegos.independencia.dto.request.revolucionario;

import com.megajuegos.independencia.enums.VoteTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class VoteRequest {

    private VoteTypeEnum voteType;
}
