package com.sanglt.aggregator.services.impl;

import com.sanglt.aggregator.clients.UserClient;
import com.sanglt.aggregator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSeverviceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public void chat() {
        userClient.chat();
    }
}
