#!/bin/zsh

cd "/Users/brunopiovezan/Documents/Codex/2026-11-09/cha/projetoReciclaMais" || exit 1

if [ ! -f ".env.local" ]; then
  echo "Não encontrei as configurações locais do banco (.env.local)."
  echo "Abra o ReciclaMais pelo IntelliJ uma vez para configurar o ambiente."
  read "?Pressione Enter para fechar esta janela..."
  exit 1
fi

set -a
source ".env.local"
set +a

if /usr/sbin/lsof -nP -iTCP:8080 -sTCP:LISTEN >/dev/null 2>&1; then
  echo "O ReciclaMais já está em execução (porta 8080 ocupada)."
  echo "Feche a execução atual no IntelliJ ou encerre o processo antes de abrir novamente."
  read "?Pressione Enter para fechar esta janela..."
  exit 0
fi

export JAVA_HOME="/Users/brunopiovezan/Library/Java/JavaVirtualMachines/ms-21.0.13.0.2/Contents/Home"
export PATH="$JAVA_HOME/bin:/usr/bin:/bin:/usr/sbin:/sbin"

echo "Iniciando ReciclaMais. A janela de registros aparecerá quando o sistema estiver pronto."
"$JAVA_HOME/bin/java" -jar target/reciclamais-0.0.1-SNAPSHOT.jar

app_exit_code=$?
if [ "$app_exit_code" -ne 0 ]; then
  echo "O ReciclaMais encerrou com erro (código $app_exit_code). Confira as mensagens acima."
  read "?Pressione Enter para fechar esta janela..."
fi
exit "$app_exit_code"
