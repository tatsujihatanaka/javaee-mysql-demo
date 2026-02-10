@echo off
setlocal enabledelayedexpansion

rem 1. .envから環境変数を読み込む
for /f "usebackq tokens=*" %%i in (".env") do (
    set %%i
)

rem 2. 読み込み確認（デバッグ用）
echo --- Check Environment Variables ---
echo AI_SENSEI_DB_URL: "%AI_SENSEI_DB_URL%"
echo AI_SENSEI_DB_USER: %AI_SENSEI_DB_USER%
echo AI_SENSEI_DB_PASS: %AI_SENSEI_DB_PASS%
echo ---------------------------------

rem 3. ビルドしてPayaraを起動
mvn clean package payara-micro:start > test_mvn.txt 2>&1