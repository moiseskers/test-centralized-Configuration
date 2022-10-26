package com.gingapay.templateapplication.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Objects;

@Slf4j
public class TranslateHelper {

    @Autowired AcceptHeaderLocaleResolver acceptHeaderLocaleResolver;
    @Autowired ResourceBundleMessageSource source;

    /**
     * @param code
     * @param args example of an arg (args) : new Object[]{PaymentSolutionName.PAGSEGURO.name(), storeIdentity}
     * @return
     */
    public String get(String code, @Nullable Object[] args) {
        return source.getMessage(code, args, Objects.requireNonNull(acceptHeaderLocaleResolver.getDefaultLocale()));
    }

}
