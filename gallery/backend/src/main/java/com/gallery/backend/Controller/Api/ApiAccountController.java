package com.gallery.backend.Controller.Api;

import com.gallery.backend.Service.JwtService;
import com.gallery.backend.Service.JwtServiceImpl;
import com.gallery.backend.entity.Item;
import com.gallery.backend.entity.Member;
import com.gallery.backend.repository.ItemRepository;
import com.gallery.backend.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class ApiAccountController {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params,
                                HttpServletResponse res) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
        if (member != null) {
            int id = member.getId();
            String token = jwtService.getToken("id", id);
            //return token;
            //보안상 토큰은 클라이언트가 아닌 서버에서 관리하도록
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);//자바스크립트에서는 접근 안되도록
            cookie.setPath("/");

            res.addCookie(cookie);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        //로그인 실패
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);
        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
