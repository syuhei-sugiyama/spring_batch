package com.example.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class HelloTasklet implements Tasklet {
	/*
	 * Taskletインタフェースを実装することでTaskletの処理を作成できる
	 */
	/*
	 * TaskletとChunkは、BeanとしてDIコンテナに登録する必要があるため
	 * クラスに@Componentを付ける
	 */

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Hello World");
		return RepeatStatus.FINISHED;
	}

}
