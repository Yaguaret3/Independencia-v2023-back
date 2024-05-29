package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.revolucionario.VoteRequest;
import com.megajuegos.independencia.dto.response.CongresoResponse;
import com.megajuegos.independencia.dto.response.RevolucionarioResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.Log;
import com.megajuegos.independencia.entities.Votation;
import com.megajuegos.independencia.entities.Vote;
import com.megajuegos.independencia.entities.card.RepresentationCard;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import com.megajuegos.independencia.enums.LogTypeEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.RevolucionarioRepository;
import com.megajuegos.independencia.service.RevolucionarioService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RevolucionarioServiceImpl implements RevolucionarioService {

    private final VoteRepository voteRepository;
    private final VotationRepository votationRepository;
    private final UserUtil userUtil;
    private final RevolucionarioRepository revolucionarioRepository;
    private final CongresoRepository congresoRepository;
    private final GameIdUtil gameIdUtil;
    private final LogRepository logRepository;
    private final CardRepository cardRepository;

    @Override
    public RevolucionarioResponse getData() {
        RevolucionarioData revolucionarioData = getPlayerData();
        return RevolucionarioResponse.toDtoResponse(revolucionarioData);
    }

    @Override
    public GameDataTinyResponse getGameData() {
        RevolucionarioData revolucionarioData = getPlayerData();
        return GameDataTinyResponse.toTinyResponse(revolucionarioData.getGameData());
    }

    @Override
    public List<CongresoResponse> getCongresosData() {
        // TODO Estps tienen que ser tiny congresos

        return congresoRepository.findAll().stream()
                .map(CongresoResponse::toDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void vote(VoteRequest request) {

        RevolucionarioData revolucionarioData = getPlayerData();

        Votation votation = revolucionarioData.getCongreso().getVotations().stream()
                .filter(Votation::getActive).findAny()
                .orElseThrow(VotationNotFoundException::new);

        validateVoteAlreadySent(votation, revolucionarioData);

        List<RepresentationCard> cards = revolucionarioData.getCards().stream()
                .filter(RepresentationCard.class::isInstance)
                .map(RepresentationCard.class::cast)
                .collect(Collectors.toList());

        Vote vote = Vote.builder()
                .revolucionarioData(revolucionarioData)
                .voteType(request.getVoteType())
                .representacion(cards)
                .votation(votation)
                .build();
        voteRepository.save(vote);

        cards.forEach(c -> c.getVotes().add(vote));
        cardRepository.saveAll(cards);

        votation.getVotes().add(vote);
        votationRepository.save(votation);

        Log log = Log.builder()
                .turno(revolucionarioData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Votaste %s sobre el tema: %s", request.getVoteType(), votation.getPropuesta()))
                .player(revolucionarioData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void propose(String proposal) {

        RevolucionarioData revolucionarioData = getPlayerData();

        Congreso congreso = revolucionarioData.getCongreso();

        validatePresidente(revolucionarioData);
        validateNoActiveVotations(congreso);

        Votation newVotation = Votation.builder()
                .propuesta(proposal)
                .active(true)
                .congreso(congreso)
                .build();

        votationRepository.save(newVotation);

        congreso.getVotations().add(newVotation);

        congresoRepository.save(congreso);

        Log log = Log.builder()
                .turno(revolucionarioData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Propusiste que se vote %s", proposal))
                .player(revolucionarioData)
                .build();

        logRepository.save(log);

    }

    @Override
    public void closeVotation() {
        RevolucionarioData revolucionarioData = getPlayerData();

        Congreso congreso = revolucionarioData.getCongreso();

        validatePresidente(revolucionarioData);
        Votation votation = validateActiveVotation(congreso);

        votation.setActive(false);
        votationRepository.save(votation);
        Log log = Log.builder()
                .turno(revolucionarioData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Cerraste las votaciones sobre el tema: %s", votation.getPropuesta()))
                .player(revolucionarioData)
                .build();

        logRepository.save(log);

    }

    /*---------------------------------------------------------------------------
                    Métodos privados
     ---------------------------------------------------------------------------*/

    private void validatePresidente(RevolucionarioData revolucionarioData){
        if(!revolucionarioData.isPresidente()){
            throw new NotCongressPresidentException(revolucionarioData.getUser().getUsername());
        }
    }
    private void validateNoActiveVotations(Congreso congreso){
        congreso.getVotations().forEach(v -> {
            if(v.getActive()){
                throw new VotationStillActiveException();
            }
        });
    }
    private Votation validateActiveVotation(Congreso congreso){
        return congreso.getVotations().stream()
                .filter(Votation::getActive)
                .findAny()
                .orElseThrow(NoActiveVotationException::new);
    }
    private void validateVoteAlreadySent(Votation votation, RevolucionarioData revolucionarioData){

        votation.getVotes().forEach(v -> {
            if(v.getRevolucionarioData() == revolucionarioData){
                throw new VoteAlreadySentException();
            }
        });
    }
    private RevolucionarioData getPlayerData(){
        Long playerId = userUtil.getCurrentUser().getPlayerDataList().stream()
                .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                .findFirst()
                .map(PlayerData::getId)
                .orElseThrow(PlayerNotFoundException::new);
        return revolucionarioRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
