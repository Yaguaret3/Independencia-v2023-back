package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.RepresentationEnum;
import com.megajuegos.independencia.enums.VoteTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewVoteRequest {

    private VoteTypeEnum voteTypeEnum;
    private RepresentationEnum representationEnum;
}
