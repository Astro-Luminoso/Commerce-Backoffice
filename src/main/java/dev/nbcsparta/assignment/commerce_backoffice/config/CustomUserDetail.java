package dev.nbcsparta.assignment.commerce_backoffice.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomUserDetail extends User {

    private final Long managerId;
    private final String jwtUuid;
    private final Date exp;

    public CustomUserDetail(
            String username,
            Long managerId,
            String jwtUuid,
            Collection<? extends GrantedAuthority> authorities,
            Date exp
    ) {
        super(username, "", authorities);
        this.managerId = managerId;
        this.jwtUuid = jwtUuid;
        this.exp = exp;

    }

    public long getManagerId() {
        return this.managerId;
    }

    public Map<String, Date> getBlackListDetail() {
        Map<String, Date> detail = new ConcurrentHashMap<>();
        detail.put(jwtUuid, exp);
        return detail;
    }
}
