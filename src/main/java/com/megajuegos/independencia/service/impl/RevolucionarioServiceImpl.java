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
import com.megajuegos.independencia.repository.CongresoRepository;
import com.megajuegos.independencia.repository.LogRepository;
import com.megajuegos.independencia.repository.VotationRepository;
import com.megajuegos.independencia.repository.VoteRepository;
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

    @Override
    public RevolucionarioResponse getData() {
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        return RevolucionarioResponse.toDtoResponse(revolucionarioData);
    }

    @Override
    public GameDataTinyResponse getGameData() {
        RevolucionarioData data = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        return GameDataTinyResponse.toTinyResponse(data.getGameData());
    }

    @Override
    public List<CongresoResponse> getCongresosData() {
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        // TODO Estps tienen que ser tiny congresos

        return congresoRepository.findAll().stream()
                .map(CongresoResponse::toDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void vote(VoteRequest request) {

        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        Votation votation = revolucionarioData.getCongreso().getVotations().stream()
                .filter(Votation::getActive).findAny()
                .orElseThrow(VotationNotFoundException::new);

        validateVoteAlreadySent(votation, revolucionarioData);

        Vote vote = Vote.builder()
                .revolucionarioData(revolucionarioData)
                .voteType(request.getVoteType())
                .representacion(revolucionarioData.getCards().stream()
                        .filter(RepresentationCard.class::isInstance)
                        .map(RepresentationCard.class::cast)
                        .collect(Collectors.toList()))
                .votation(votation)
                .build();
        voteRepository.save(vote);

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

        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

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
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

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
            throw new NotCongressPresidentException();
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
                .orElseThrow(() -> new NoActiveVotationException());
    }
    private void validateVoteAlreadySent(Votation votation, RevolucionarioData revolucionarioData){

        votation.getVotes().forEach(v -> {
            if(v.getRevolucionarioData() == revolucionarioData){
                throw new VoteAlreadySentException();
            }
        });
    }
}
