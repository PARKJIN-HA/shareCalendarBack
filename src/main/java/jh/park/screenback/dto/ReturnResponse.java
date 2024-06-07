package jh.park.screenback.dto;

import jh.park.screenback.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnResponse {
    private String jwtToken;
    private User user;
}
