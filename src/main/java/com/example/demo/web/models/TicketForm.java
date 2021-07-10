package com.example.demo.web.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TicketForm {
    private String clientName;
    private int codePayement;
    private List<Long> listIdTicket = new ArrayList<>();
}
