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
    public static final String LOGGED_IN_USER_FIRST_NAME = "firstName";
    public static final String LOGGED_IN_USER_LAST_NAME = "lastName";
    public static final String MEMBER_ID = "memberId";
    public static final String EMPLOYER_GROUP_ID = "employerGroupId";
    public static final String EMPLOYER_ID = "employerId";
    public static final String CARRIER_ID = "carrierId";
    public static final String IMPERSONATED_BY_USER_ID = "impersonatorUserId";
    public static final String SWITCH_USER_GRANTED_AUTHORITY = "switchUserAuthority";
    public static final String SCOPE = "scope";
    
    private final String subject;
    private final Set<String> roles;
    private final String loggedInUserId;
    private final String loggedInUserFirstName;
    private final String loggedInUserLastName;
    
    private final String memberId;
    private final String employerGroupId;
    private final String employerId;
    private final String carrierId;
    
    private final String impersonatedByUserId;
    private final String switchUserGrantedAuthority;
    private final String scope;
    
    static JwtClaims fromToken(
        String token,
        BiFunction<String, String, String> getStringClaimValue,
        BiFunction<String, String, Object> getRawClaimValue
    ) {
        return new JwtClaims(
            getStringClaimValue.apply(token, JwtClaims.SUBJECT),
            new HashSet<>((List<String>) getRawClaimValue.apply(token, JwtClaims.ROLES)),
            getStringClaimValue.apply(token, JwtClaims.LOGGED_IN_USER_ID),
            getStringClaimValue.apply(token, JwtClaims.LOGGED_IN_USER_FIRST_NAME),
            getStringClaimValue.apply(token, JwtClaims.LOGGED_IN_USER_LAST_NAME),
            getStringClaimValue.apply(token, JwtClaims.MEMBER_ID),
            getStringClaimValue.apply(token, JwtClaims.EMPLOYER_GROUP_ID),
            getStringClaimValue.apply(token, JwtClaims.EMPLOYER_ID),
            getStringClaimValue.apply(token, JwtClaims.CARRIER_ID),
            getStringClaimValue.apply(token, JwtClaims.IMPERSONATED_BY_USER_ID),
            getStringClaimValue.apply(token, JwtClaims.SWITCH_USER_GRANTED_AUTHORITY),
            getStringClaimValue.apply(token, JwtClaims.SCOPE)
        );
    }
    
    private JwtClaims(
        String subject,
        Set<String> roles,
        String loggedInUserId,
        String loggedInUserFirstName,
        String loggedInUserLastName,
        String memberId,
        String employerGroupId,
        String employerId,
        String carrierId,
        String impersonatedByUserId,
        String switchUserGrantedAuthority,
        String scope
    ) {
        this.subject = subject;
        this.roles = roles;
        this.loggedInUserId = loggedInUserId;
        this.loggedInUserFirstName = loggedInUserFirstName;
        this.loggedInUserLastName = loggedInUserLastName;
        this.memberId = memberId;
        this.employerGroupId = employerGroupId;
        this.employerId = employerId;
        this.carrierId = carrierId;
        this.impersonatedByUserId = impersonatedByUserId;
        this.switchUserGrantedAuthority = switchUserGrantedAuthority;
        this.scope = scope;
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
    
    public String getLoggedInUserFirstName() {
    	return loggedInUserFirstName;
    }
    
    public String getLoggedInUserLastName() {
    	return loggedInUserLastName;
    }
    
    public Optional<String> getMemberId() {
        return optionalIfEmpty(memberId);
    }
    
    public Optional<String> getEmployerGroupId() {
        return optionalIfEmpty(employerGroupId);
    }
    
    public Optional<String> getEmployerId() {
        return optionalIfEmpty(employerId);
    }
    
    public Optional<String> getCarrierId() {
        return optionalIfEmpty(carrierId);
    }
    
    public Optional<String> getImpersonatedByUserId() {
        return optionalIfEmpty(impersonatedByUserId);
    }
    
    public String getScope() {
		return scope;
	}

	public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {
            { put(SUBJECT, subject); }
            { put(ROLES, roles); }
            { put(LOGGED_IN_USER_ID, loggedInUserId); }
            { put(LOGGED_IN_USER_FIRST_NAME, loggedInUserFirstName); }
            { put(LOGGED_IN_USER_LAST_NAME, loggedInUserLastName); }
            { put(MEMBER_ID, memberId); }
            { put(EMPLOYER_GROUP_ID, employerGroupId); }
            { put(EMPLOYER_ID, employerId); }
            { put(CARRIER_ID, carrierId); }
            { put(IMPERSONATED_BY_USER_ID, impersonatedByUserId); }
            { put(SWITCH_USER_GRANTED_AUTHORITY, switchUserGrantedAuthority); }
            { put(SCOPE, scope); }
        };
    }
    
    private Optional<String> optionalIfEmpty(String value) {
        return StringUtils.isEmpty(value) ? Optional.empty() : Optional.of(value);
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrierId == null) ? 0 : carrierId.hashCode());
		result = prime * result + ((employerGroupId == null) ? 0 : employerGroupId.hashCode());
		result = prime * result + ((employerId == null) ? 0 : employerId.hashCode());
		result = prime * result + ((impersonatedByUserId == null) ? 0 : impersonatedByUserId.hashCode());
		result = prime * result + ((loggedInUserFirstName == null) ? 0 : loggedInUserFirstName.hashCode());
		result = prime * result + ((loggedInUserId == null) ? 0 : loggedInUserId.hashCode());
		result = prime * result + ((loggedInUserLastName == null) ? 0 : loggedInUserLastName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((switchUserGrantedAuthority == null) ? 0 : switchUserGrantedAuthority.hashCode());
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
		if (carrierId == null) {
			if (other.carrierId != null)
				return false;
		} else if (!carrierId.equals(other.carrierId))
			return false;
		if (employerGroupId == null) {
			if (other.employerGroupId != null)
				return false;
		} else if (!employerGroupId.equals(other.employerGroupId))
			return false;
		if (employerId == null) {
			if (other.employerId != null)
				return false;
		} else if (!employerId.equals(other.employerId))
			return false;
		if (impersonatedByUserId == null) {
			if (other.impersonatedByUserId != null)
				return false;
		} else if (!impersonatedByUserId.equals(other.impersonatedByUserId))
			return false;
		if (loggedInUserFirstName == null) {
			if (other.loggedInUserFirstName != null)
				return false;
		} else if (!loggedInUserFirstName.equals(other.loggedInUserFirstName))
			return false;
		if (loggedInUserId == null) {
			if (other.loggedInUserId != null)
				return false;
		} else if (!loggedInUserId.equals(other.loggedInUserId))
			return false;
		if (loggedInUserLastName == null) {
			if (other.loggedInUserLastName != null)
				return false;
		} else if (!loggedInUserLastName.equals(other.loggedInUserLastName))
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
		if (switchUserGrantedAuthority == null) {
			if (other.switchUserGrantedAuthority != null)
				return false;
		} else if (!switchUserGrantedAuthority.equals(other.switchUserGrantedAuthority))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "JwtClaims [subject=" + subject + ", roles=" + roles + ", loggedInUserId=" + loggedInUserId
                + ", impersonatedByUserId=" + impersonatedByUserId + ", switchUserGrantedAuthority="
                + switchUserGrantedAuthority + ", memberId=" + memberId + ", employerGroupId=" + employerGroupId
                + ", employerId=" + employerId + ", carrierId=" + carrierId + "]";
    }

    public static class Builder {
        private final String subject;
        private final Set<String> roles;
        
        private final String loggedInUserId;
        private final String loggedInUserFirstName;
        private final String loggedInUserLastName;
        
        private String memberId;
        private String employerGroupId;
        private String employerId;
        private String carrierId;
        
        private String impersonatedByUserId;
        private String switchUserGrantedAuthority;
        private String scope;
        
        public Builder(JwtUser principal) {
            this.subject = principal.getUsername();
            this.roles = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            this.loggedInUserId = principal.getId();
            this.loggedInUserFirstName = principal.getFirstName();
            this.loggedInUserLastName = principal.getLastName();
            
            this.memberId = "";
            this.employerGroupId = "";
            this.employerId = "";
            this.carrierId = "";
            
            this.impersonatedByUserId = "";
            this.switchUserGrantedAuthority = "";
            
        }
        
//        public Builder userAssociativeData(JwtAssociativeData associativeData) {
//            this.memberId = associativeData.getMemberId();
//            this.employerGroupId = associativeData.getEmployerGroupId();
//            this.employerId = associativeData.getEmployerId();
//            this.carrierId = associativeData.getCarrierId();
//            
//            return this;
//        }
//        
        public Builder addScope(String scope) {
        	this.scope = scope;
        	return this;
        }
        
        public JwtClaims build() {
            return new JwtClaims(
                subject,
                roles,
                loggedInUserId,
                loggedInUserFirstName,
                loggedInUserLastName,
                
                memberId,
                employerGroupId,
                employerId,
                carrierId,
                
                impersonatedByUserId,
                switchUserGrantedAuthority,
                scope
            );
        }
    }
}

