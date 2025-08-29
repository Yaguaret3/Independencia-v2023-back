package com.megajuegos.independencia.util;

public class Messages {

    private Messages(){
        throw new IllegalStateException("Utility class");
    }

    public static final String USERNAME_ALREADY_EXISTS = "El nombre de usuario ya existe. Por favor, elegí otro.";
    public static final String NO_PLAYER_DATA_FOR_USER = "No hay datos de jugador para el usuario %s";
    public static final String TOO_MANY_SOCKET_CONNECTIONS = "Conexiones en tiempo real permitidas superadas. Esta pantalla no se actualizará automáticamente.";
    public static final String USER_NOT_FOUND_BY_EMAIL = "No existe un usuario con ese email";
    public static final String USER_NOT_FOUND_BY_ID = "No existe un usuario con esa id";
    public static final String PLAYER_DATA_NOT_FOUND_BY_ID = "No existe jugador con esa id";
    public static final String INCORRECT_CREDENTIALS = "Credenciales incorrectas";
    public static final String ORIGINAL_PASS_NOT_MATCHES = "Contraseña original inválida";
    public static final String CONTRASENA_ACTUALIZADA_CON_EXITO = "Contraseña actualizada con éxito";
    public static final String ROLES_ACTUALIZADOS_CON_EXITO = "Roles actualizados con éxito";
    public static final String INIT_COMPLETED = "Inicialización finalizada";
    public static final String CARD_GIVEN = "Carta cedida con éxito";
    public static final String CARD_MOVED = "Carta movida con éxito";
    public static final String CARD_CREATED_GIVEN = "Carta creada y cedida con éxito";
    public static final String CITY_NOT_FOUND_BY_ID = "No existe una ciudad con ese nombre";
    public static final String CITY_ASSIGNED = "Ciudad asignada con éxito";
    public static final String NEW_TURN_STARTED = "Nuevo turno iniciado";
    public static final String NO_PLAYER_ROLE_INSTANCE_EXISTS = "No existe un rol con esa id";
    public static final String CARD_REMOVED = "Carta quitada con éxito";
    public static final String PERSONAL_PRICE_ASSIGNED = "Precios asignados";
    public static final String NEW_GAME_CREATED = "Juego nuevo creado";
    public static final String GAME_DEACTIVATED = "Juego desactivado";
    public static final String GAME_DELETED = "Juego borrado";
    public static final String CONGRESS_PRESIDENT_ASSIGNED = "Presidente del congreso asignado";
    public static final String PROPOSAL_CREATED = "Propuesta nueva creada con éxito";
    public static final String VOTED = "Voto enviado de manera exitosa";
    public static final String VOTATION_CLOSED = "Votación cerrada exitosamente";
    public static final String CITY_UPDATED = "Ciudad actualizada";
    public static final String BUILDING_REMOVED = "Edificio eliminado";
    public static final String BUILDING_CREATED = "Edificio creado";
    public static final String TRADESCORE_ASSIGNED = "Puntaje comercial final asignado";
    public static final String TRADESCORE_UPDATED = "Puntaje comercial actualizado";
    public static final String PRICE_UPDATED = "Precio actualizado";
    public static final String VOTATION_UPDATED = "Votación actualizada";
    public static final String VOTE_ADDED = "Voto creado";
    public static final String VOTE_UPDATED = "Voto actualizado";
    public static final String BATTLE_CREATED = "Batalla creada";
    public static final String BATTLE_SOLVED = "Batalla terminada";
    public static final String BATTLE_VALUES_ASSIGNED = "Valores iniciales asignados";
    public static final String MILITIA_ASSIGNED = "Milicias asignadas";
    public static final String RESERVE_ASSIGNED = "Reserva asignada";
    public static final String ARMY_DELETED = "Ejército destruido";
    public static final String ARMY_CREATED = "Ejército creado";
    public static final String CAMP_MOVED = "Campamento movido";
    public static final String NEW_DIPUTADO_ASSIGNED = "Diputado asignado";
    public static final String PLATA_ASSIGNED = "Plata asignada";
    public static final String CONGRESS_REMOVED = "Congreso borrado";
    public static final String CONGRESS_CREATED = "Congreso creado";
    public static final String CONGRESS_UPDATED = "Congreso actualizado";
    public static final String MOVED_TO_CONGRESS = "Movimiento a congreso exitoso";
    public static final String CONCLUDE_PHASE_REQUESTED = "Solicitud para avanzar de fase realizada";
    public static final String CONCLUDE_PHASE_CANCELLED = "Solicitud para avanzar de fase cancelada";
    public static final String PHASE_CONCLUDED = "Fase concluída";
}
