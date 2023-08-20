package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.gobernador.*;
import com.megajuegos.independencia.dto.response.GobernadorResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyGobernadorResponse;
import com.megajuegos.independencia.entities.Building;
import com.megajuegos.independencia.entities.City;
import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.entities.data.GobernadorData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.BuildingTypeEnum;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.enums.RegionEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.CityRepository;
import com.megajuegos.independencia.repository.GameRegionRepository;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import com.megajuegos.independencia.repository.data.CapitanDataRepository;
import com.megajuegos.independencia.repository.data.GobernadorDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.GobernadorService;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.PaymentRequestUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.USER_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class GobernadorServiceImpl implements GobernadorService {

    private final GameRegionRepository gameRegionRepository;
    private final PaymentService paymentService;
    private final GobernadorDataRepository gobernadorRepository;
    private final UserUtil userUtil;
    private final PlayerDataRepository playerDataRepository;
    private final CapitanDataRepository capitanDataRepository;
    private final CityRepository cityRepository;

    @Override
    public GobernadorResponse getData() {
        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        GameRegion gameRegion = gameRegionRepository
                .findByRegionEnum(RegionEnum.containingSubRegion(gobernadorData.getCity().getSubRegion().getSubRegionEnum()))
                .orElseThrow(() -> new RegionNotFoundException());

        return GobernadorResponse.toDtoResponse(gobernadorData, gameRegion);
    }

    @Override
    public GameDataTinyGobernadorResponse getGameData() {
        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        return GameDataTinyGobernadorResponse.toDtoResponse(gobernadorData.getGameData());
    }

    @Override
    @Transactional
    public void sellMarketPlace(MarketPlaceSellRequest request) {

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Card card = null;
        for(Card c : gobernadorData.getCards()){
            if(Objects.equals(c.getId(), request.getIdMarketCard())){
                card = c;
                break;
            }
        }
        if(card==null) throw new CardNotFoundException();

        PlayerData they = playerDataRepository.findById(request.getIdJugadorDestino())
                .orElseThrow(() -> new PlayerNotFoundException());

        they.getCards().add(card);
        gobernadorData.getCards().remove(card);
        gobernadorRepository.save(gobernadorData);
        playerDataRepository.save(they);
    }

    @Override
    public void changeTaxes(TaxesRequest request) {

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer nivelImpositivo = gobernadorData.getCity().getTaxesLevel();
        Integer opinionPublica = gobernadorData.getCity().getPublicOpinion();

        if(request.isAumentar()){
            gobernadorData.getCity().setTaxesLevel(nivelImpositivo+1);
            gobernadorData.getCity().setPublicOpinion(opinionPublica-1);
        }
        if(request.isDisminuir()){
            gobernadorData.getCity().setTaxesLevel(nivelImpositivo-1);
            gobernadorData.getCity().setPublicOpinion(opinionPublica+1);
        }
        gobernadorRepository.save(gobernadorData);
    }

    @Override
    public void buildNewBuilding(UpgradeBuildingRequest request) throws InstanceNotFoundException {

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        PersonalPricesEnum priceEnum = gobernadorData.getPrices().stream()
                .filter(p -> Objects.equals(p.getId(), request.getPriceId()))
                .map(PersonalPrice::getName)
                .findAny()
                .orElseThrow(() -> new PriceNotFoundException());

        BuildingTypeEnum buildingType = BuildingTypeEnum.valueOf(priceEnum.name());

        if (maxTypeOfBuildingReached(buildingType.getId(), gobernadorData)) throw new MaxNumberOfBuildingTypeReachedException();
        if (!paymentService.succesfulPay(gobernadorData, request.getPayment(), priceEnum)) throw new PaymentNotPossibleException();

        gobernadorData.getCity().getBuildings().add(
                Building.builder()
                .buildingType(buildingType)
                .build());
        gobernadorRepository.save(gobernadorData);
    }

    @Override
    public void upgradeMarketplace(PaymentRequestUtil request) {

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(gobernadorData, request, PersonalPricesEnum.MARKET)) throw new PaymentNotPossibleException();

        gobernadorData.getCity().setMarketLevel(gobernadorData.getCity().getMarketLevel()+1);

        gobernadorRepository.save(gobernadorData);
    }

    @Override
    public void giveRepresentationRequest(RepresentationRequest request) {

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Card card = null;
        for(Card c : gobernadorData.getCards()){
            if(Objects.equals(c.getId(), request.getIdRepresentationCard())){
                card = c;
                break;
            }
        }
        if(card==null) throw new CardNotFoundException();

        PlayerData they = playerDataRepository.findById(request.getIdJugadorDestino())
                .orElseThrow(() -> new PlayerNotFoundException());

        they.getCards().add(card);
        gobernadorData.getCards().remove(card);
        gobernadorData.getCity().setDiputado(they.getUsername());

        City city = gobernadorData.getCity();
        city.setDiputado(they.getUsername());

        cityRepository.save(city);
        gobernadorRepository.save(gobernadorData);
        playerDataRepository.save(they);
    }

    @Override
    public void promoteCorruption(CorruptionRequest request) {
        /**
         * TODO
         * */
    }

    @Override
    public void recruitMilitia(PaymentRequestUtil request) {

        /** CANTIDAD MILICIA */

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(gobernadorData, request, PersonalPricesEnum.MILICIA)) throw new PaymentNotPossibleException();

        gobernadorData.setMilicia(gobernadorData.getMilicia()+1);

        gobernadorRepository.save(gobernadorData);

    }

    @Override
    public void assignMilitia(AssignMilitiaRequest request) {

        GobernadorData gobernadorData = gobernadorRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if(gobernadorData.getMilicia() < request.getCantidadMilicias()) throw new InsufficientMilitiaException();

        CapitanData they = capitanDataRepository.findById(request.getIdJugadorDestino())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        gobernadorData.setMilicia(gobernadorData.getMilicia() - request.getCantidadMilicias());
        they.setReserva(they.getReserva() + request.getCantidadMilicias());

        gobernadorRepository.save(gobernadorData);
        capitanDataRepository.save(they);
    }

    @Override
    public void financeCongress(FinanceCongressRequest request) {

    }

    private boolean maxTypeOfBuildingReached(Integer buildingTypeId, GobernadorData gobernadorData) throws InstanceNotFoundException {
        return gobernadorData.getCity().getBuildings()
                .stream().filter(b -> {
                    try {
                        return b.getBuildingType().equals(BuildingTypeEnum.fromId(buildingTypeId));
                    } catch (InstanceNotFoundException e) {
                        e.printStackTrace();
                    }
                    return false;
                }).count() > BuildingTypeEnum.fromId(buildingTypeId).getMax();
    }
}
