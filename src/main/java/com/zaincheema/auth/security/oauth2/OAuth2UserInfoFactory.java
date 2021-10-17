package com.zaincheema.auth.security.oauth2;

import java.util.Map;

public class OAuth2UserInfoFactory {
    private static final String PROVIDER_GOOGLE = "google";
    private static final String PROVIDER_GITHUB = "github";

    private final String registrationId;
    private final Map<String, Object> attributes;

    public OAuth2UserInfoFactory(String registrationId, Map<String, Object> attributes) {
        this.registrationId = registrationId.toLowerCase();
        this.attributes = attributes;
    }

    public OAuth2UserInfo create() {
        OAuth2UserInfo info;
        switch (registrationId) {
            case PROVIDER_GOOGLE:
                info = new GoogleOAuth2UserInfo(attributes);
                break;
            case PROVIDER_GITHUB:
                info = new GithubOAuth2UserInfo(attributes);
                break;
            default:
                throw new IllegalStateException("Invalid oauth2 provider: " + registrationId);
        }
        return info;
    }
}
