@echo off
setlocal

:: --- 設定エリア ---
set LOG_FILE="test_boot.log"

:: --- 実行セクション ---
echo ======================================================
echo [INFO] ログ監視を開始しました: %LOG_FILE%
echo [INFO] 監視を終了するには、このウィンドウを閉じるか
echo        Ctrl + C を押してください。
echo ======================================================
echo.

:: ログファイルが存在しない場合に備えてチェック
if not exist %LOG_FILE% (
    echo [WARN] %LOG_FILE% がまだ見つかりません。
    echo        サーバーが起動してログが生成されるのを待機します...
)

:: PowerShellを使用して tail -f 相当の動作を実現
:: -Tail 10 を付けることで、起動時に直近10行だけ表示して開始します
powershell -Command "Get-Content %LOG_FILE% -Wait -Tail 10"

endlocal