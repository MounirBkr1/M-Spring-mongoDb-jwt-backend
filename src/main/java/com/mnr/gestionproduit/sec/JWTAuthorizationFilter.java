package com.mnr.gestionproduit.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //authorise all page to send me queries
        response.addHeader("Access-Control-Allow-Origin", "*");
        //authorize user to send thoses properties
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
        //expose this properties  to read by users
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        response.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,PATCH");


        if (request.getMethod().equals("OPTIONS")) {
            //s'il envoie une requette avec options, je l'autorise
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            //System.out.println(new Date(System.currentTimeMillis()));
            //System.out.println(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION));

            //***********recuperer token jwt, jwtToken= Bearer +Jwt ********************
            String jwtToken=request.getHeader(SecurityParams.JWT_HEADER_NAME);

            if(jwtToken==null || !jwtToken.startsWith(SecurityParams.HEADER_PREFIX)){
                //ne rien faire ==return
                filterChain.doFilter(request,response);
                return;
            }

            //verifier la signature de l algorithm,substring:prendre à partir de...
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();

            //decoder en enlevant "Bearer "
            String jwt = jwtToken.substring(SecurityParams.HEADER_PREFIX.length());
            DecodedJWT decodedJWT = verifier.verify(jwt);
            System.out.println("JWT=" + jwt);

            //recuperer les  informations
            String username = decodedJWT.getSubject();
            List<String> roles=decodedJWT.getClaims().get("roles").asList(String.class);
            //System.out.println("username=" + username);
            //System.out.println("roles=" + roles);

            Collection<GrantedAuthority> authorities=new ArrayList<>();


            roles.forEach(rn->{
                authorities.add(new SimpleGrantedAuthority(rn));
            });

            //authentifier l'user qui est porté par jwt, mot de passe null
            UsernamePasswordAuthenticationToken user=
                    new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(user);

            //jai authentifié l'user porté par jwt et je passe au filtre suivant
            filterChain.doFilter(request,response);

        }
    }
}