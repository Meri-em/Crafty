package com.crafty.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

public class JwtClaims {
    
    public static final String SUBJECT = "sub";
    public static final String ROLES = "roles";
    public static final String LOGGED_IN_USER_ID = "userId";
    public static final String MEMBER_ID = "memberId";
    public static final String AUTHOR_ID = "authorId";
    public static final String SCOPE = "scope";
    public static final String LAST_LOGOUT_DATE = "lastLogoutDate";
    
    private final String subject;
    private final Set<String> roles;
    private final String loggedInUserId;
    private final String memberId;
    private final String authorId;
    private final String scope;
    private final String lastLogoutDate;
    
    static JwtClaims fromToken(
        String token,
        BiFunction<String, String, String> getStringClaimValue,
        BiFunction<String, String, Object> getRawClaimValue
    ) {
        return new JwtClaims(
            getStringClaimValue.apply(token, JwtClaims.SUBJECT),
            new HashSet<>((List<String>) getRawClaimValue.apply(token, JwtClaims.ROLES)),
            getStringClaimValue.apply(token, JwtClaims.LOGGED_IN_USER_ID),
            getStringClaimValue.apply(token, JwtClaims.MEMBER_ID),
            getStringClaimValue.apply(token, JwtClaims.AUTHOR_ID),
            getStringClaimValue.apply(token, JwtClaims.SCOPE),
            getStringClaimValue.apply(token, JwtClaims.LAST_LOGOUT_DATE)
        );
    }
    
    private JwtClaims(
        String subject,
        Set<String> roles,
        String loggedInUserId,
        String memberId,
        String authorId,
        String scope,
        String lastLogoutDate
    ) {
        this.subject = subject;
        this.roles = roles;
        this.loggedInUserId = loggedInUserId;
        this.memberId = memberId;
        this.authorId = authorId;
        this.scope = scope;
        this.lastLogoutDate = lastLogoutDate;
    }

    public String getSubject() {
        return subject;
    }
    
    public Set<String> getRoles() {
        return roles;
    }
    
    public String getLoggedInUserId() {
        return loggedInUserId;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public String getAuthorId() {
        return authorId;
    }
    
    public String getScope() {
		return scope;
	}
    
    public String getLastLogoutDate() {
    	return lastLogoutDate;
    }

	public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {
            { put(SUBJECT, subject); }
            { put(ROLES, roles); }
            { put(LOGGED_IN_USER_ID, loggedInUserId); }
            { put(MEMBER_ID, memberId); }
            { put(AUTHOR_ID, authorId); }
            { put(SCOPE, scope); }
            { put(LAST_LOGOUT_DATE, lastLogoutDate); }
        };
    }
    
    private Optional<String> optionalIfEmpty(String value) {
        return StringUtils.isEmpty(value) ? Optional.empty() : Optional.of(value);
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + ((loggedInUserId == null) ? 0 : loggedInUserId.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtClaims other = (JwtClaims) obj;
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (loggedInUserId == null) {
			if (other.loggedInUserId != null)
				return false;
		} else if (!loggedInUserId.equals(other.loggedInUserId))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "JwtClaims [subject=" + subject + ", roles=" + roles + ", loggedInUserId=" + loggedInUserId
                + ", memberId=" + memberId + ", authorId=" + authorId +  "]";
    }

    public static class Builder {
        private final String subject;
        private final Set<String> roles;
        private final String loggedInUserId;
        
        private String memberId;
        private String authorId;
        private String scope;
        private String lastLogoutDate;
        
        public Builder(JwtUser principal) {
            this.subject = principal.getUsername();
            this.roles = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            this.loggedInUserId = principal.getId();
            this.memberId = principal.getMemberId();
            this.authorId = principal.getAuthorId();
            this.lastLogoutDate = principal.getLastLogoutDate() != null ? principal.getLastLogoutDate().toString(): null;
        }
               
        public Builder addScope(String scope) {
        	this.scope = scope;
        	return this;
        }
        
        public JwtClaims build() {
            return new JwtClaims(
                subject,
                roles,
                loggedInUserId,
                memberId,
                authorId,
                scope,
                lastLogoutDate
            );
        }
    }
}

