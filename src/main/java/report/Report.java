/* ---------------------------------------------------------------------------------------------------------------------
Name    : Report
Version : 23.09.2017
Author  : AORA-K
--------------------------------------------------------------------------------------------------------------------- */



package report;



import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import report.layoutControllers.root.RootLayoutController;
import report.models_view.nodes.node_helpers.StageCreator;
import report.usage_strings.PathStrings;


public class Report extends Application {

/*!******************************************************************************************************************
*                                                                                                               ENUMS
********************************************************************************************************************/
    public  enum ScreenSize {
         width , height; 
         
        private ScreenSize() {  }
        
        public void setValue() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            switch(this){
                case width:  value = screenSize.getWidth(); break;
                case height: value = screenSize.getHeight(); break;
            }
            
        }
        
        public double getValue(){return value;}
        
        private double value;
    }

/*!******************************************************************************************************************
*                                                                                                              FIELDS
********************************************************************************************************************/

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene scene;
    private RootLayoutController rootController;
    private Object centerController;
    private StageCreator stageCreator;

/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public RootLayoutController getRLController() {
        return rootController;
    }

    public <T> T getCenterController() {
        return (T)centerController;
    }

    public <T> void  setCenterController(T controller) {
        centerController = controller;
    }


/*!******************************************************************************************************************
*                                                                                                               INIT
********************************************************************************************************************/

//Init ROOT Layout
    private void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Report.class.getResource(PathStrings.Layout.ROOT));
            rootLayout = (BorderPane)loader.load();
            
            scene = new Scene(rootLayout,ScreenSize.width.getValue() - 100,ScreenSize.height.getValue() - 100);
//            scene = new Scene(rootlayout,1800,900);
            primaryStage.setScene(scene);
            rootController = loader.getController(); //set controller
            rootController.setReportApp(this);



        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //init Intro Layout
    public StageCreator initIntroLayout(){
        StageCreator.setReportMain(this);
        StageCreator center = new StageCreator(PathStrings.Layout.INTRO,"").loadIntoRootBorderPaneCenter();
        Object centerController = center.getController();
        return center;
    }


/*!******************************************************************************************************************
*                                                                                                              START
********************************************************************************************************************/
    @Override
    public void start(Stage stage){
        ScreenSize.width.setValue();
        ScreenSize.height.setValue();
        
        this.primaryStage = stage;
        this.primaryStage.setTitle("App");

        //init ROOT
        this.initRootLayout();
        this.primaryStage.sizeToScene();
        primaryStage.show();

        //Init Intro
        this.initIntroLayout();
        
    }

/*!******************************************************************************************************************
*                                                                              public static void main(String[] args)
********************************************************************************************************************/
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        launch(args);
    }
    
}





@Bean("reportTransactionItemReaderSoyuztelecom")
	@StepScope
	public FlatFileItemReader<ReceiptTransaction> transactionReader(@Value("#{jobParameters['input.file.name']}")
		                                                                String reportFileName){
		LOGGER.info("Обрабатываем транзакции из файла отчета: " + reportFileName + " Читаем отчет...");
		FlatFileItemReader<ReceiptTransaction> reader = new FlatFileItemReader<>();
		DefaultLineMapper<ReceiptTransaction> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(";");
		tokenizer.setQuoteCharacter('"');
		tokenizer.setNames(new String[]{"transactionId", "orderId", "order_date", "amount", "phone", "order_number", "operator", "service"}); //TODO проверить как правильно: orderId и order_id
		DefaultFieldSetFactory fieldSetFactory = new DefaultFieldSetFactory();
		fieldSetFactory.setDateFormat(ORDER_DATE_INPUT_FORMAT);
		fieldSetFactory.setNumberFormat(AMOUNT_INPUT_FORMAT);
		tokenizer.setFieldSetFactory(fieldSetFactory);
		lineMapper.setLineTokenizer(tokenizer);
		BeanWrapperFieldSetMapper<ReceiptTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<ReceiptTransaction>() {
			@Override
			protected void initBinder(DataBinder binder){
				binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
					@Override
					public void setAsText(String text){
						if(StringUtils.isNotEmpty(text)){
							try{
								setValue(ORDER_DATE_INPUT_FORMAT.parse(text));
							} catch(ParseException e){
								LOGGER.error(e.toString(), e);
							}
						} else{
							setValue(null);
						}
					}

					@Override
					public String getAsText(){
						Date date = (Date)getValue();
						if(date != null){
							return ORDER_DATE_INPUT_FORMAT.format(date);
						} else{
							return "";
						}
					}
				});
				binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
					@Override
					public void setAsText(String text) throws IllegalArgumentException{
						if(StringUtils.isNotEmpty(text)){
							try{
								if(!text.matches(AMOUNT_REGEX)){
									throw new IllegalArgumentException("'" + text + "' not match regexp '" + AMOUNT_REGEX + "'");
								}
								double value = AMOUNT_INPUT_FORMAT.parse(text).doubleValue();
								setValue(value * 100); //рубли в копейки
							} catch(ParseException e){
								LOGGER.error(e.toString(), e);
							}
						} else{
							setValue(null);
						}
					}

					@Override
					public String getAsText(){
						Long value = (Long)getValue();
						if(value != null){
							return AMOUNT_INPUT_FORMAT.format((double)value / 100.); //копейки в рубли
						} else{
							return "";
						}
					}
				});
			}

			@Override
			public ReceiptTransaction mapFieldSet(final FieldSet fs) throws BindException{
				String msg = "reportFile=[" + reportFileName + "] fs=[" + fs + "] ";
				String duplicateErrorMessage = msg + "receiptTransactionRepository.isExistByTransactionId: constraint uc_reports_transactions_receipt unique (trn_id, channel_id)";
				try{
					ReceiptTransaction transaction = super.mapFieldSet(fs);
					transaction.setChannel(channel);

					if(receiptTransactionRepository.isExistByTransactionIdAndChannel(transaction.getTransactionId(), channel)){
						LOGGER.warn(duplicateErrorMessage);
						//return null; //reader завершается весь!!! org.springframework.batch.item.ItemReader
						throw duplicateKeyExceptionClass.getConstructor(String.class).newInstance(duplicateErrorMessage);
					} else{
						LOGGER.info(msg + " Распарсили: " + transaction);
						return transaction;
					}
				} catch(IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
					LOGGER.error(duplicateErrorMessage + " " + e.toString(), e);
					throw new RuntimeException(duplicateErrorMessage, e); //попробуем просто упасть
				} catch(Throwable e){
					if(!(duplicateKeyExceptionClass.isInstance(e))){ //duplicateKeyExceptionClass пропускаем, ибо уже залогировали строчку, а дальше тихо пропустит элемент
						LOGGER.error(msg + e.toString(), e);
					}
					throw e;
				}
			}
		};

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;

import org.springframework.batch.item.file.transform.LineAggregator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;


import java.io.IOException;
import java.util.List;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:my.properties")
public class BatchConfiguration {
    @Value("${application.cycle_period.fixed-rate}")
    private String PERIOD;
     /**
     * Reader
     * @return ItemReader<Student>
     */
    @Bean
    public ItemReader<String> reader() {
        FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
        reader.setResource(new ClassPathResource("student-data.csv"));
        reader.setLineMapper(new LineMapper<String>() {
            @Override
            public String mapLine(String line, int lineNumber) throws Exception {
                System.err.println(lineNumber+ " line number");
                return line;
            }
        });
        return reader;
    }
    /**
     * Processor
     * @return ItemProcessor<Student, Marksheet>
     */
    @Bean
    public ItemProcessor<String, String> processor() {
        return student -> {
            System.err.println("student id:"+student);
            return student + PERIOD;
        };
    }
    /**
     * Writer
     * @return ItemWriter<Marksheet>
     */
    @Bean
    public ItemWriter<String> writer() throws IOException {
        //writer
    	FlatFileItemWriter<String> writer = new FlatFileItemWriter<String>(){
            @Override
            public void write(List<? extends String> items) throws Exception {
                System.out.println("--------------> "+ items);
                super.write(items);
            }
        };
//    	writer.setResource(new ClassPathResource("student-marksheet.csv"));
    	writer.setResource(new FileSystemResource("student-marksheet.csv"));
        writer.setShouldDeleteIfEmpty(true);
    	writer.setLineAggregator(new LineAggregator<String>() {
            @Override
            public String aggregate(String item) {
                return item;
            }

        });
        writer.setAppendAllowed(true);
        return writer;
    }
    /**
     * Job
     * @return Job
     */
    @Bean
    public Job createMarkSheet(JobBuilderFactory jobs, @Qualifier("step") Step step) {
        return jobs.get("createMarkSheet")
                .flow(step)
                .end()
                .build();
    }
    /**
     * Step
     * @return Step
     */
    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<String> reader,
            ItemWriter<String> writer, ItemProcessor<String, String> processor) {
        return stepBuilderFactory.get("step")
                .<String, String> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
 
}
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class)
                .web(false)
                .run();


    }
}
		   
		compile("org.springframework.boot:spring-boot-starter-batch")
//    compile group: 'org.springframework.integration', name: 'spring-integration-core', version: '5.0.1.RELEASE'
//    compile group: 'org.springframework.integration', name: 'spring-integration-file', version: '5.0.1.RELEASE'
