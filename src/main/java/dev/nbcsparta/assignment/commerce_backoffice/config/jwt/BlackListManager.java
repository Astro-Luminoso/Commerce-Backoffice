package dev.nbcsparta.assignment.commerce_backoffice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class BlackListManager {

    private final Set<Long> managerIdBlackList;
    private final Map<String, Date> blackList;
    private final ScheduledExecutorService scheduler;

    public BlackListManager() {
        this.managerIdBlackList = new HashSet<>();
        this.blackList = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void registerManagerId(Long managerId) {
        managerIdBlackList.add(managerId);
    }

    public void registerTokenDetail(Map<String, Date> blackListDetail) {
        blackList.putAll(blackListDetail);
    }

    public void checkBlackListed(Claims claims) {
        Long managerId = claims.get("managerId", Long.class);
        String uuid = claims.getId();

        if (managerIdBlackList.contains(managerId)) {
            Date exp = claims.getExpiration();
            registerTokenDetail(Map.of(uuid, exp));

            managerIdBlackList.remove(managerId);
            throw new JwtException("블랙리스트에 등록된 사용자입니다. 토큰 정보가 블랙리스트에 등록됩니다.");
        }

        if (blackList.containsKey(uuid)) {
            throw new JwtException("블랙리스트에 등록된 토큰입니다.");
        }
    }

    @PostConstruct
    public void evictExpiredTokens() {
        Date now = new Date();
        Runnable removeOutdatedTokenDetail = () -> {
            blackList.entrySet().removeIf(entry -> entry.getValue().before(now));
        };

        scheduler.scheduleAtFixedRate(removeOutdatedTokenDetail, 0, 1, TimeUnit.DAYS);
    }
}
