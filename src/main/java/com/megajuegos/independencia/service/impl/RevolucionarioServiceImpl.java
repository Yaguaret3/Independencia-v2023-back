package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.revolucionario.CloseVotationRequest;
import com.megajuegos.independencia.dto.request.revolucionario.VoteRequest;
import com.megajuegos.independencia.dto.response.CongresoResponse;
import com.megajuegos.independencia.dto.response.RevolucionarioResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.Votation;
import com.megajuegos.independencia.entities.Vote;
import com.megajuegos.independencia.entities.card.RepresentationCard;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import com.megajuegos.independencia.enums.VoteTypeEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.CongresoRepository;
import com.megajuegos.independencia.repository.VotationRepository;
import com.megajuegos.independencia.repository.VoteRepository;
import com.megajuegos.independencia.repository.data.RevolucionarioRepository;
import com.megajuegos.independencia.service.RevolucionarioService;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
public class RevolucionarioServiceImpl implements RevolucionarioService {

    private final VoteRepository voteRepository;
    private final VotationRepository votationRepository;
    private final UserUtil userUtil;
    private final RevolucionarioRepository revolucionarioRepository;
    private final CongresoRepository congresoRepository;

    @Override
    public RevolucionarioResponse getData() {
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        return RevolucionarioResponse.toDtoResponse(revolucionarioData);
    }

    @Override
    public GameDataTinyResponse getGameData() {
        RevolucionarioData data = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        return GameDataTinyResponse.toTinyResponse(data.getGameData());
    }

    @Override
    public List<CongresoResponse> getCongresosData() {
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        // TODO Estps tienen que ser tiny congresos

        return congresoRepository.findAll().stream()
                .map(CongresoResponse::toDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String vote(VoteRequest request) {

        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
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
                .build();
        voteRepository.save(vote);

        votation.getVotes().add(vote);
        votationRepository.save(votation);

        return VOTED;
    }

    @Override
    public String propose(String proposal) {

        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Congreso congreso = revolucionarioData.getCongreso();

        validatePresidente(congreso, revolucionarioData);
        validateNoActiveVotations(congreso);

        Votation newVotation = Votation.builder()
                .propuesta(proposal)
                .active(true)
                .congreso(congreso)
                .build();

        votationRepository.save(newVotation);

        congreso.getVotations().add(newVotation);

        congresoRepository.save(congreso);

        return PROPOSAL_CREATED;

    }

    @Override
    public String closeVotation() {
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Congreso congreso = revolucionarioData.getCongreso();

        validatePresidente(congreso, revolucionarioData);
        Votation votation = validateActiveVotation(congreso);

        votation.setActive(false);
        votationRepository.save(votation);

        return VOTATION_CLOSED;
    }

    /*---------------------------------------------------------------------------
                    Métodos privados
     ---------------------------------------------------------------------------*/

    private void validatePresidente(Congreso congreso, RevolucionarioData revolucionarioData){
        if(congreso.getPresidente() != revolucionarioData){
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
