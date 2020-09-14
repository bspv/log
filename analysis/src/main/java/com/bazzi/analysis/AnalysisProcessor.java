package com.bazzi.analysis;

import com.bazzi.analysis.config.DefinitionProperties;
import com.bazzi.analysis.topology.AnalysisTopology;
import com.bazzi.analysis.util.SpringContainer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@EnableAsync
@EnableKafka
@MapperScan(basePackages = {"com.bazzi.analysis.mapper"})
@EnableConfigurationProperties({DefinitionProperties.class})
@SpringBootApplication
public class AnalysisProcessor {
    public static void main(String[] args) {
        SpringContainer.run(args);
        AnalysisTopology analysisTopology = SpringContainer.getBean(AnalysisTopology.class);
        analysisTopology.runTopology(args);
    }

}
