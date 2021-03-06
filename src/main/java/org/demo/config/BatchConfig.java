package org.demo.config;

import org.demo.domain.Report;
import org.demo.repository.ReportRepository;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.List;

@Configuration
public class BatchConfig {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;


    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Report.class);
        return jaxb2Marshaller;
    }

    /**
     * Read XML file. File is being read from classpath. Therefore we use
     * {@link ClassPathResource}. Normally, you will use {@link org.springframework.core.io.FileSystemResource}
     **/
    @Bean
    public StaxEventItemReader<Report> staxEventItemReader(Jaxb2Marshaller jaxb2Marshaller) {
        StaxEventItemReader<Report> staxEventItemReader = new StaxEventItemReader<>();
        staxEventItemReader.setFragmentRootElementName("record");
        staxEventItemReader.setResource(new ClassPathResource("report.xml"));
        staxEventItemReader.setUnmarshaller(jaxb2Marshaller);
        return staxEventItemReader;
    }


    /**
     * After reading save data to H2 in memory database via Spring Data JPA repository
     **/
    @Bean
    public ItemWriter<Report> itemWriter() {
        return new ItemWriter<Report>() {

            @Override
            public void write(List<? extends Report> list) throws Exception {
                reportRepository.save(list);
            }
        };
    }

    /**
     * Let's use write listener to confirm that data is actually written in database.
     * We'll simply print data.
     **/
    @Bean
    public ItemWriteListener<Report> itemWriteListener() {
        return new ItemWriteListener<Report>() {
            @Override
            public void beforeWrite(List<? extends Report> list) {

            }

            @Override
            public void afterWrite(List<? extends Report> list) {
                List<Report> reportOutputs = reportRepository.findAll();
                for (Report reportOutput : reportOutputs) {
                    System.out.println(reportOutput);
                }
            }

            @Override
            public void onWriteError(Exception e, List<? extends Report> list) {

            }
        };
    }

    @Bean
    public Step step(StaxEventItemReader<Report> staxEventItemReader, ItemWriteListener<Report> itemWriteListener,
                     ItemWriter<Report> itemWriter) {

        return steps.get("step").<Report, Report>chunk(10)
                .reader(staxEventItemReader)
                .writer(itemWriter)
                .listener(itemWriteListener)
                .build();
    }

    @Bean
    public Job job(Step step) {
        return jobs.get("job").start(step).build();
    }

}
