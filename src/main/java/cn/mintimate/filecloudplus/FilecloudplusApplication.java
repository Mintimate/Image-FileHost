package cn.mintimate.filecloudplus;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("cn.mintimate.filecloudplus.dao")
public class FilecloudplusApplication {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
        scannerConfigurer.setBasePackage("cn.mintimate.filecloudplus.dao");
        return scannerConfigurer;
    }
    public static void main(String[] args) {
        SpringApplication.run(FilecloudplusApplication.class, args);
    }

}
