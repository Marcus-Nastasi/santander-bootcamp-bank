package com.santander.bank.Controller.Card;

import com.google.gson.Gson;
import com.santander.bank.DTO.Card.PayInvoiceDTO;
import com.santander.bank.DTO.Card.PayOnDebitDTO;
import com.santander.bank.DTO.Card.PaymentCreditDTO;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Card.CardService;
import com.santander.bank.Services.Token.TokenService;
import jakarta.validation.Valid;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
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
    public ResponseEntity<String> payDebitCard(@RequestBody @Valid PayOnDebitDTO data, @RequestHeader Map<String, String> header) {
        String token = header.get("authorization").replace("Bearer ", "");
        User u = userRepo.findByCardId(String.valueOf(data.id()));
        String cpf = tokenService.validate(token);

        if (u == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null user");
        if (!cpf.equals(u.getCpf())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not the same user");

        return ResponseEntity.accepted().body(cardService.payOnDebit(u.getAccount_id(), data.value(), token));
    }

    @PostMapping(value = "/credit")
    public ResponseEntity<String> credit(@RequestBody @Valid PaymentCreditDTO data, @RequestHeader Map<String, String> header) {
        String token = header.get("authorization").replace("Bearer ", "");
        User u = userRepo.findByCardId(String.valueOf(data.id()));
        String cpf = tokenService.validate(token);

        if (u == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null user");
        if (!cpf.equals(u.getCpf())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not the same user");

        String resp = cardService.payOnCredit(data.id(), data.value(), token);

        return (resp != null) ? ResponseEntity.accepted().body(resp) : ResponseEntity.badRequest().body("null payment");
    }

    @PostMapping(value = "/invoice/pay")
    public ResponseEntity<String> payInvoice(@RequestBody @Valid PayInvoiceDTO data, @RequestHeader Map<String, String> header) {
        String token = header.get("authorization").replace("Bearer ", "");
        User u = userRepo.findByCardId(String.valueOf(data.id()));
        String cpf = tokenService.validate(token);

        if (u == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null user");
        if (!cpf.equals(u.getCpf())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not the same user");

        String resp = cardService.payInvoice(data.id(), data.account(), token);

        return (resp != null) ? ResponseEntity.accepted().body(resp) : ResponseEntity.badRequest().body("null payment");
    }
}




