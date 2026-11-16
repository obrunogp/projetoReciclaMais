#!/bin/zsh

PROJECT_DIR="${0:A:h}"
cd "$PROJECT_DIR" || exit 1
URL="http://localhost:8080/"

if [ ! -f ".env.local" ]; then
  echo "Não encontrei as configurações locais do banco (.env.local)."
  echo "Copie .env.example para .env.local e configure DB_URL, DB_USERNAME e DB_PASSWORD."
  read "?Pressione Enter para fechar esta janela..."
  exit 1
fi

set -a
source ".env.local"
set +a

if curl --max-time 2 -fsS "$URL" >/dev/null 2>&1; then
  echo "O ReciclaMais já está rodando. Abrindo o site..."
  open "$URL"
  exit 0
fi

if /usr/sbin/lsof -nP -iTCP:8080 -sTCP:LISTEN >/dev/null 2>&1; then
  echo "A porta 8080 está ocupada por outro processo. Feche-o e tente novamente."
  read "?Pressione Enter para fechar esta janela..."
  exit 1
fi

if [ -x "${JAVA_HOME:-}/bin/java" ]; then
  JAVA="${JAVA_HOME}/bin/java"
elif command -v java >/dev/null 2>&1; then
  JAVA="$(command -v java)"
else
  echo "Java não foi encontrado. Instale o Java 21 para iniciar o ReciclaMais."
  read "?Pressione Enter para fechar esta janela..."
  exit 1
fi

if [ ! -f "target/reciclamais-0.0.1-SNAPSHOT.jar" ]; then
  echo "O JAR não foi encontrado. Execute ./mvnw -DskipTests package primeiro."
  read "?Pressione Enter para fechar esta janela..."
  exit 1
fi

echo "Iniciando ReciclaMais..."
"$JAVA" -Dreciclamais.desktop.enabled=false -jar target/reciclamais-0.0.1-SNAPSHOT.jar &
APP_PID=$!

for attempt in {1..45}; do
  if curl --max-time 2 -fsS "$URL" >/dev/null 2>&1; then
    echo "O ReciclaMais está pronto. Abrindo o site..."
    open "$URL"
    wait "$APP_PID"
    exit $?
  fi
  if ! kill -0 "$APP_PID" 2>/dev/null; then
    wait "$APP_PID"
    exit $?
  fi
  sleep 1
done

echo "O ReciclaMais ainda não respondeu. Confira os erros acima."
wait "$APP_PID"
