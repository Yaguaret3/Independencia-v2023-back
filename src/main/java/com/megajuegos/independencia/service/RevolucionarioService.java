package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.revolucionario.CloseVotationRequest;
import com.megajuegos.independencia.dto.request.revolucionario.VoteRequest;
import com.megajuegos.independencia.dto.response.CongresoResponse;
import com.megajuegos.independencia.dto.response.RevolucionarioResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface RevolucionarioService {

    RevolucionarioResponse getData();
    GameDataTinyResponse getGameData();
    List<CongresoResponse> getCongresosData();
    void vote(VoteRequest request);
    void propose(String proposal);
    void closeVotation();
}
