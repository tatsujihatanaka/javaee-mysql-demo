@echo off
setlocal

:: --- ビルドセクションを追加 ---
echo [INFO] プロジェクトをビルド中...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo [ERROR] ビルドに失敗しました。
    pause
    exit /b
)

:: --- 設定エリア ---
set PAYARA_JAR="C:/Users/hatanaka2/.m2/repository/fish/payara/extras/payara-micro/5.2022.5/payara-micro-5.2022.5.jar"
set MYSQL_DRIVER=".\lib\mysql-connector-j-8.0.33.jar"
set WAR_FILE=".\target\javaee-mysql-demo.war"
set POST_BOOT="post-boot.txt"

:: --- 実行セクション ---
echo [INFO] Payara Micro を起動しています...
echo [INFO] 使用するWAR: %WAR_FILE%

java -jar %PAYARA_JAR% ^
  --noCluster ^
  --addLibs %MYSQL_DRIVER% ^
  --deploy %WAR_FILE% ^
  --postbootcommandfile %POST_BOOT% > test_boot.log 2>&1

if %errorlevel% neq 0 (
    echo [ERROR] 起動に失敗しました。ログを確認してください。
    pause
)

endlocal