package com.megajuegos.independencia.dto.request.player;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiveCardRequest {

    private Long playerToId;
    private Long cardId;
}
