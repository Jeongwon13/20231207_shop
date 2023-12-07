package com.hy.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.awt.*;
@MapperScan("com.hy.shop.member")
@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {SpringApplication.run(ShopApplication.class, args);}

}
