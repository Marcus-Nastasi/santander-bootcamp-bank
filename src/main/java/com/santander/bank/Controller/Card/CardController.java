package com.santander.bank.Controller.Card;

import com.google.gson.Gson;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Card.CardService;
import com.santander.bank.Services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/card")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Gson gson;

    @PostMapping(value = "/debit")
    public ResponseEntity<String> payDebitCard(@RequestBody String data, @RequestHeader Map<String, String> header) {
        var jsonParsed = gson.fromJson(data, Map.class);

        String token = header.get("authorization").replace("Bearer ", "");
        User u = userRepo.findByCardId((String) jsonParsed.get("id"));
        BigDecimal vl = BigDecimal.valueOf(Double.parseDouble((String) jsonParsed.get("value")));
        String cpf = tokenService.validate(token);

        if (u == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null user");
        if (!cpf.equals(u.getCpf())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not the same user");

        String done = cardService.payOnDebit(u.getAccount_id(), vl, token);

        return ResponseEntity.accepted().body(done);
    }
}




