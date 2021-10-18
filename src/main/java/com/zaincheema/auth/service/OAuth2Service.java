package com.zaincheema.auth.service;

import com.zaincheema.auth.auth.UserPrincipal;
import com.zaincheema.auth.model.User;
import com.zaincheema.auth.repository.UserRepository;
import com.zaincheema.auth.security.oauth2.OAuth2UserInfo;
import com.zaincheema.auth.security.oauth2.OAuth2UserInfoFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo info = new OAuth2UserInfoFactory(registrationId, oAuth2User.getAttributes()).create();

        processUserInfo(info);

        User user = userRepository.findByProviderId(info.getId())
                .orElseThrow(() -> new OAuth2AuthenticationException("User could not be saved in the database."));
        UserPrincipal principal = UserPrincipal.create(user);
        return principal;
    }

    /**
     * @param info
     * 
     * - Save the user in [UserRepository] only if it has a unique providerId and email.
     * - This ensures that the repository only contains unique users.
     * - If user logs in with google and github referencing same email,
     *   they will be taken to the same profile
     * - If email of either provider is null, it will be considered unique
     *   and reference a different profile
     */
    private void processUserInfo(OAuth2UserInfo info) {
        boolean userExists = userRepository.existsByProviderId(info.getId());
        String email = info.getEmail();
        boolean existsByEmail = StringUtils.hasText(email) && userRepository.existsByEmail(email);
        if (!userExists && !existsByEmail) registerNewUser(info);
    }

    private void registerNewUser(OAuth2UserInfo info) {
        User user = new User();
        user.setProviderId(info.getId());
        user.setEmail(info.getEmail());
        user.setName(info.getName());
        user.setImageUrl(info.getImageUrl());

        userRepository.save(user);
    }
}
