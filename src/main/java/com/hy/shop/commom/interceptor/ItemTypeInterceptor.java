package com.hy.shop.commom.interceptor;

import com.hy.shop.item.model.service.ItemService;
import com.hy.shop.item.model.vo.ItemType;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemTypeInterceptor implements HandlerInterceptor {

    private final ItemService itemService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("==========================================================");
        log.debug("====================INTERCEPTOR BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }




}