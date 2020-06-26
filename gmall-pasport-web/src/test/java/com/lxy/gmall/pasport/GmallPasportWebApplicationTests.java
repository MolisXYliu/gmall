package com.lxy.gmall.pasport;

import com.lxy.gmall.pasport.config.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class GmallPasportWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testJwt(){
        String key="lxy";
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 1001);
        map.put("nickName", "admin");
        String salt="192.168.119.133";
        String token = JwtUtil.encode(key, map, salt);

        System.out.println(token);

        //解密token
        Map<String, Object> maps = JwtUtil.decode(token, key, salt);
        System.out.println(maps);
    }

}
