package com.example.demo.common.auth.resolver;

import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasCurrentUserAnnotation =
            parameter.getParameterAnnotation(CurrentUser.class) != null;
        boolean isUserClass = User.class.equals(parameter.getParameterType());
        return hasCurrentUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        
        Long userId = Long.parseLong(authentication.getName());

        return userRepository.findById(userId)
            .orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + userId));
    }
}