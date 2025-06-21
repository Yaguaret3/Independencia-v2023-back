package com.megajuegos.independencia.config.socket.security;

import com.megajuegos.independencia.config.socket.WebSocketSessionTrackingService;
import com.megajuegos.independencia.util.ConstantsUtil;
import com.megajuegos.independencia.util.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.megajuegos.independencia.util.Messages.TOO_MANY_SOCKET_CONNECTIONS;


@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketInterceptor implements ChannelInterceptor {

    private final WebSocketAuthenticatorService webSocketAuthenticatorService;
    private final WebSocketSessionTrackingService sessionTrackingService;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String authHeader = accessor.getFirstNativeHeader(ConstantsUtil.AUTHORIZACION_FOR_HEADER);
            if (authHeader == null || authHeader.trim().isEmpty() || !authHeader.startsWith(ConstantsUtil.STARTS_WITH_BEARER)) {
                throw new AuthenticationCredentialsNotFoundException("Token was null or empty.");
            }
            final String token = authHeader.substring(ConstantsUtil.BEARER_PART_LENGHT);

            final UsernamePasswordAuthenticationToken user = webSocketAuthenticatorService.getAuthenticatedOrFail(token);

            if (!sessionTrackingService.registerSession(user.getName(), accessor.getSessionId())) {
                log.error(TOO_MANY_SOCKET_CONNECTIONS);
                throw new AuthenticationCredentialsNotFoundException(TOO_MANY_SOCKET_CONNECTIONS);
            }
            accessor.setUser(user);
        }
        return message;
    }
}
