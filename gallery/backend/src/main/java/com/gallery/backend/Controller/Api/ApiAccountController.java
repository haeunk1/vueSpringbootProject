package com.gallery.backend.Controller.Api;

import com.gallery.backend.entity.Item;
import com.gallery.backend.entity.Member;
import com.gallery.backend.repository.ItemRepository;
import com.gallery.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class ApiAccountController {
    @Autowired

    MemberRepository memberRepository;

    @PostMapping("/api/account/login")
    public int login(@RequestBody Map<String, String> params) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
        if(member != null){
            return member.getId();
        }

        //로그인 실패
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
