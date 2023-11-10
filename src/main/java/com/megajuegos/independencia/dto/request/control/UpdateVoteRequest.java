package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.VoteTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVoteRequest {

    private VoteTypeEnum voteType;
}
