package xxxxxx.yyyyyy.zzzzzz.domain.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordEncoderFactoryBean implements FactoryBean<PasswordEncoder>{

    private PasswordEncoder passwordencoder;

    public PasswordEncoderFactoryBean(){
        String encodingId = "pbkdf2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new Pbkdf2PasswordEncoder());
        passwordencoder = new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Override
    public PasswordEncoder getObject() throws Exception {
        return passwordencoder;
    }

    @Override
    public Class<?> getObjectType() {
        return PasswordEncoder.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
