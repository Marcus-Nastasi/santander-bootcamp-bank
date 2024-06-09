package com.santander.bank.Controller.Card;

import com.google.gson.Gson;
import com.santander.bank.DTO.Card.LimitGrowDTO;
import com.santander.bank.DTO.Card.PayInvoiceDTO;
import com.santander.bank.DTO.Card.PayOnDebitDTO;
import com.santander.bank.DTO.Card.PaymentCreditDTO;
import com.santander.bank.DTO.User.FindCpfDTO;
import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Cards.Card;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @GetMapping(value = "/get")
    public ResponseEntity<String> getByUserCpf(@RequestBody @Valid FindCpfDTO data, @RequestHeader Map<String, String> headers) {
        User u = userRepo.findByUserCpf(data.cpf());
        String token = headers.get("authorization").replace("Bearer ", "");
        String cpf = tokenService.validate(token);

        if (u == null) return ResponseEntity.badRequest().build();
        if (!cpf.equals(u.getCpf())) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(gson.toJson(u.getCard_id(), String.class));
    }

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

    @PostMapping(value = "/limit/grow")
    public ResponseEntity<String> growLimit(@RequestBody @Valid LimitGrowDTO data, @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization").replace("Bearer ", "");
        String resp = cardService.growLimit(data.id(), token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
    }
}




