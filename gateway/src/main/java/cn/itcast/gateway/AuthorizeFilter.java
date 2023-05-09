package cn.itcast.gateway;

import org.apache.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @className: AuthorizeFilter
 * @author: crowgzy
 * @date: 2023/5/9
 **/
@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        //获取参数中的Authorization参数
        String auth = queryParams.getFirst("authorization");
        //判断参数值是否等于admin
        if ("admin".equals(auth)) {
           return chain.filter(exchange);
        }
        exchange.getResponse().setRawStatusCode(HttpStatus.SC_UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
