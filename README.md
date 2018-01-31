//
//	@Bean("reportTransactionItemReaderSoyuztelecom")
//	@StepScope
//	public FlatFileItemReader<ReceiptTransaction> transactionReader(@Value("#{jobParameters['input.file.name']}")
//																			String reportFileName) {
//		LOGGER.info("Обрабатываем транзакции из файла отчета: " + reportFileName + " Читаем отчет...");
//		FlatFileItemReader<ReceiptTransaction> reader = new FlatFileItemReader<>();
//		DefaultLineMapper<ReceiptTransaction> lineMapper = new DefaultLineMapper<>();
//		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//		tokenizer.setDelimiter(";");
//		tokenizer.setQuoteCharacter('"');
//		tokenizer.setNames(new String[]{"transactionId", "orderId", "order_date", "amount", "phone", "order_number", "operator", "service"}); //TODO проверить как правильно: orderId и order_id
//		DefaultFieldSetFactory fieldSetFactory = new DefaultFieldSetFactory();
//		fieldSetFactory.setDateFormat(ORDER_DATE_INPUT_FORMAT);
//		fieldSetFactory.setNumberFormat(AMOUNT_INPUT_FORMAT);
//		tokenizer.setFieldSetFactory(fieldSetFactory);
//		lineMapper.setLineTokenizer(tokenizer);
//		BeanWrapperFieldSetMapper<ReceiptTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<ReceiptTransaction>() {
//			@Override
//			protected void initBinder(DataBinder binder) {
//				binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
//					@Override
//					public void setAsText(String text) {
//						if (StringUtils.isNotEmpty(text)) {
//							try {
//								setValue(ORDER_DATE_INPUT_FORMAT.parse(text));
//							} catch (ParseException e) {
//								LOGGER.error(e.toString(), e);
//							}
//						} else {
//							setValue(null);
//						}
//					}
//
//					@Override
//					public String getAsText() {
//						Date date = (Date) getValue();
//						if (date != null) {
//							return ORDER_DATE_INPUT_FORMAT.format(date);
//						} else {
//							return "";
//						}
//					}
//				});
//				binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
//					@Override
//					public void setAsText(String text) throws IllegalArgumentException {
//						if (StringUtils.isNotEmpty(text)) {
//							try {
//								if (!text.matches(AMOUNT_REGEX)) {
//									throw new IllegalArgumentException("'" + text + "' not match regexp '" + AMOUNT_REGEX + "'");
//								}
//								double value = AMOUNT_INPUT_FORMAT.parse(text).doubleValue();
//								setValue(value * 100); //рубли в копейки
//							} catch (ParseException e) {
//								LOGGER.error(e.toString(), e);
//							}
//						} else {
//							setValue(null);
//						}
//					}
//
//					@Override
//					public String getAsText() {
//						Long value = (Long) getValue();
//						if (value != null) {
//							return AMOUNT_INPUT_FORMAT.format((double) value / 100.); //копейки в рубли
//						} else {
//							return "";
//						}
//					}
//				});
//			}
//
//			@Override
//			public ReceiptTransaction mapFieldSet(final FieldSet fs) throws BindException {
//				String msg = "reportFile=[" + reportFileName + "] fs=[" + fs + "] ";
//				String duplicateErrorMessage = msg + "receiptTransactionRepository.isExistByTransactionId: constraint uc_reports_transactions_receipt unique (trn_id, channel_id)";
//				try {
//					ReceiptTransaction transaction = super.mapFieldSet(fs);
//					transaction.setChannel(channel);
//
//					if (receiptTransactionRepository.isExistByTransactionIdAndChannel(transaction.getTransactionId(), channel)) {
//						LOGGER.warn(duplicateErrorMessage);
//						//return null; //reader завершается весь!!! org.springframework.batch.item.ItemReader
//						throw duplicateKeyExceptionClass.getConstructor(String.class).newInstance(duplicateErrorMessage);
//					} else {
//						LOGGER.info(msg + " Распарсили: " + transaction);
//						return transaction;
//					}
//				} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
//					LOGGER.error(duplicateErrorMessage + " " + e.toString(), e);
//					throw new RuntimeException(duplicateErrorMessage, e); //попробуем просто упасть
//				} catch (Throwable e) {
//					if (!(duplicateKeyExceptionClass.isInstance(e))) { //duplicateKeyExceptionClass пропускаем, ибо уже залогировали строчку, а дальше тихо пропустит элемент
//						LOGGER.error(msg + e.toString(), e);
//					}
//					throw e;
//				}
//			}
//		};
