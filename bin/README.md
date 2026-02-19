# javaee-mysql-demo
Jakarta EE demo for database(mysql) access comparing JPA and JDBC 

Java EE 8 (Jakarta EE 8) と MySQL を使用した Web アプリケーションのデモプロジェクトです。

## プロジェクト構成
* **Java:** 17
* **Framework:** Jakarta EE 8 (Servlet 4.0, JSP 2.3)
* **Build Tool:** Maven 3.x
* **Database:** MySQL 8.0

## 実行手順

### 1. ビルド
プロジェクトをコンパイルし、デプロイ可能な `war` ファイルを生成します。
```bash
mvn clean package
```

### 2. サーバーの起動
Mavenプラグインを使用して、Payara Microのダウンロードとアプリケーションのデプロイを自動で行います。
```bash
mvn payara-micro:start
```

### 3. ブラウザで動作確認
サーバーの起動完了後、コンソールに `Payara Micro ... ready` と表示されたら、以下のURLにアクセスしてください。

* **Hello World サーブレット:** [http://localhost:8080/javaee-mysql-demo/hello](http://localhost:8080/javaee-mysql-demo/hello)
* **デフォルト JSP:** [http://localhost:8080/javaee-mysql-demo/](http://localhost:8080/javaee-mysql-demo/)


