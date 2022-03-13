package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.tasklet.HelloTasklet;

/*
 * @Configurationは、各クラスのいんすたをBeanに登録するためのアノテーション
 * 付与したクラス内では、@Beanアノテーションを使ってBeanを登録できる
 */
/*
 * @EnableBatchProcessingは、Springバッチの設定をする為のアノテーション
 * 付与したクラス内で、以下のクラスのインスタンスをDIできるようになる
 * ・JobBuilderFactory：Job生成用クラス
 * ・StepBuilderFactory：Step生成用クラス
 * ・・・etc
 * @EnableBatchProcessingを使用すると、他にもDIできるインスタンスはあるが
 * 上記の2つ以外ほぼ使わない
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	// JobBuilderのFactoryクラス
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	// StepBuilderのFactoryクラス
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	// HelloTasklet
	@Autowired
	private HelloTasklet helloTasklet;

	// TaskletのStepを生成
	@Bean
	public Step taskletStep1() {
		/*
		 * Stepを作成するために、StepBuilderFactoryを使う
		 * ・get(Step名)
		 *   引数にStepの名前を設定。Stepの名前はDBに登録される。
		 *   どのStepがいつ実行されたかDBで確認できる
		 * ・tasklet(Tasklet)
		 *   StepをTaskletに設定する
		 *   Taskletインタフェースを実装したクラスを引数に指定
		 * ・build()
		 *   Stepを作成する。Step作成のメソッドチェーンの最後に呼び出す
		 */
		return
				stepBuilderFactory.get("HelloTaskletStep1") //Builderの取得
				.tasklet(helloTasklet) //Taskletのセット
				.build(); //Stepの生成
	}

	// Jobを生成
	@Bean
	public Job taskletJob() throws Exception {
		/*
		 * Jobを作成する為に、JobBuilderFactoryを使う
		 * ・get(Job名)
		 *   引数にJobの名前を設定。Jobの名前はDBに登録される。
		 *   どのJobがいつ実行されたかDBで確認できる
		 * ・incrementer
		 *   JobのIDをインクリメントさせるクラス(インクリメンター)を指定する
		 *   JobIDはDBに登録され、JobIDはテーブルの主キーになっている。
		 *   その為、JobIDを重複させない為にインクリメンターが必要
		 *   Springバッチが用意しているRunIdIncrementerは、JobIDを1ずつ増加させる
		 * ・start(Step)
		 *   最初に実行するStepを指定する
		 * ・build
		 *   Jobを作成する。Job作成のメソッドチェーンの最後に呼び出す
		 */
		return
				jobBuilderFactory.get("HelloWorldTaskletJob") //Builderの取得
				.incrementer(new RunIdIncrementer()) //IDのインクリメント
				.start(taskletStep1()) //最初のStep
				.build(); //Jobの生成
	}
}
