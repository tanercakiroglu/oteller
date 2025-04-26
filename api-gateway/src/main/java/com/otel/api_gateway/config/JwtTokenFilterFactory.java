package com.otel.api_gateway.config;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtTokenFilterFactory extends
    AbstractGatewayFilterFactory<JwtTokenFilterFactory.Config> {

  private final ReactiveJwtDecoder jwtDecoder;

  public JwtTokenFilterFactory(ReactiveJwtDecoder jwtDecoder) {
    super(Config.class);
    this.jwtDecoder = jwtDecoder;
  }

  @Override
  public GatewayFilter apply(Config config) {

    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      if (!request.getHeaders().containsKey("Authorization")) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      String authHeader = request.getHeaders().getFirst("Authorization");
      if (authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7); // "Bearer " kısmını atla
        Mono<Jwt> decodedToken = jwtDecoder.decode(token);
        decodedToken.map(jwt -> {
          request.getHeaders().add("X-User-Name", jwt.getSubject());
          return jwt;
        }).subscribe();
      }
      return chain.filter(exchange);
    };
  }


  public static class Config {
    // Filtre için yapılandırma özellikleri (gerekirse)
  }
}
