# ♻️ Recicla+ — Sistema de Gestão de Reciclagem

**Transformando resíduos em oportunidades por meio da tecnologia.**

## 📌 Sobre o projeto

O **Recicla+** é uma aplicação desenvolvida em equipe durante a Residência Full Stack 5.0, do Instituto Eldorado em parceria com a Petrobras. O projeto tem como objetivo incentivar a reciclagem por meio de uma plataforma que permite registrar entregas de materiais recicláveis, calcular seus valores e conceder pontos aos usuários.

A proposta também contempla um website para consultar pontos de coleta, materiais aceitos e benefícios disponíveis, conectando sustentabilidade, tecnologia e cidadania.

## 🎯 Objetivos

- Incentivar o descarte correto de materiais recicláveis.
- Facilitar o gerenciamento e o registro de entregas.
- Calcular valores com base no peso e no tipo de material.
- Recompensar os usuários por meio de um sistema de pontuação.
- Disponibilizar informações sobre pontos de coleta e materiais aceitos.
- Promover a sustentabilidade e a conscientização ambiental.

## ⚙️ Funcionalidades do MVP

- [ ] Cadastro e consulta de usuários.
- [ ] Cadastro de materiais recicláveis e seus valores por quilograma.
- [ ] Registro de entregas com peso, data e tipo de material.
- [ ] Cálculo do valor correspondente a cada entrega.
- [ ] Atribuição e consulta de pontos acumulados.
- [ ] Geração de comprovantes de entrega.
- [ ] Consulta ao histórico de entregas.
- [ ] Visualização dos pontos de coleta e materiais aceitos.
- [ ] Consulta de benefícios e descontos disponíveis.

> **Status:** Em desenvolvimento.

## 🔄 Fluxo da aplicação

1. **Identificação:** identificação do usuário no sistema.
2. **Registro:** cadastro da entrega com o tipo e o peso do material.
3. **Cálculo:** cálculo do valor correspondente e dos pontos conforme as regras de negócio.
4. **Comprovante:** geração de um comprovante com as informações da entrega.
5. **Acompanhamento:** consulta do saldo de pontos e do histórico de entregas.
6. **Benefícios:** consulta das opções de descontos disponíveis e dos pontos necessários para utilizá-las.

## 🛠️ Tecnologias

- **Java** — implementação da lógica de negócio.
- **Git e GitHub** — versionamento e colaboração em equipe.
- **Banco de dados** — persistência das informações da aplicação.
- **HTML e CSS** — desenvolvimento da interface web.

*As tecnologias serão atualizadas conforme a arquitetura e as ferramentas forem definidas pela equipe.*

## 🗄️ Banco de dados local

O projeto usa PostgreSQL e Spring Data JPA. O arquivo [`database/schema.sql`](database/schema.sql) documenta a estrutura compartilhada do banco, sem registros de usuários.

Para executar a aplicação localmente, configure estas variáveis de ambiente na configuração de execução da IDE:

- `DB_URL` — URL JDBC do PostgreSQL. Se omitida, usa `jdbc:postgresql://localhost:5433/reciclagem`.
- `DB_USERNAME` — usuário do PostgreSQL.
- `DB_PASSWORD` — senha local do PostgreSQL.

O Hibernate está configurado para apenas validar o mapeamento (`ddl-auto=validate`); a aplicação não cria nem altera tabelas automaticamente. O esquema inicial ainda precisa existir no banco antes de iniciar o Java.

O esquema atual não possui tabela de locais de coleta. A entidade `LocalColeta` não é persistida até o grupo decidir se adicionará essa tabela.

### Responsabilidades Java e site

- **Programa Java no ponto de coleta:** abre uma janela desktop com telas para cadastrar participantes e materiais e registrar coletas. A janela usa a API Java, que grava no banco PostgreSQL.
- **Site:** permite somente consultar os dados do participante e o histórico das coletas. Ele não oferece formulário nem ação de cadastro de coleta.

### Rotas iniciais da API

Com a aplicação em execução na porta `8080`, estão disponíveis:

- `GET /api/materiais` — lista materiais ativos.
- `POST /api/materiais` — cadastra um material usando um tipo já cadastrado.
- `GET /api/tipos-materiais` — lista tipos ativos de material.
- `POST /api/tipos-materiais` — cadastra um tipo de material.
- `POST /api/usuarios` — cadastra um usuário; a senha é armazenada como hash.
- `POST /api/coletas` — registra uma coleta e lança os pontos no extrato do usuário.
- `POST /api/usuarios/identificar` — consulta usuário por CPF e retorna nome, CPF mascarado e saldo de pontos.
- `GET /api/usuarios/{usuarioId}/coletas` — consulta o histórico de coletas.
- `GET /api/usuarios/{usuarioId}/pontos` — consulta o saldo, calculado pela soma do extrato.

O site usa as rotas de identificação e histórico. O registro de coletas fica na API Java (`POST /api/coletas`), para ser chamado pelo programa Java responsável pelo lançamento.

Há exemplos encadeados de cadastro e registro no arquivo [`reciclamais.http`](reciclamais.http), que pode ser executado pelo cliente HTTP do IntelliJ. Os pedidos de criação alteram o banco quando executados.

Ao executar `ReciclaMaisApplication` no IntelliJ em um computador com interface gráfica, o programa abre a janela desktop de operação do ponto de coleta. O servidor local e o banco PostgreSQL precisam estar disponíveis. Para iniciar o servidor sem abrir a janela, defina `reciclamais.desktop.enabled=false`.

Exemplo de corpo para registrar uma coleta:

```json
{
  "usuarioId": 1,
  "materialId": 1,
  "pesoKg": 2.50
}
```

O usuário e o material informados precisam existir no banco; o material também precisa estar ativo. As rotas ainda não implementam autenticação e servem para desenvolvimento local.

## 🌱 Impacto esperado

O Recicla+ busca contribuir para uma gestão mais organizada da reciclagem, incentivando a participação da população por meio de recompensas e facilitando o acesso a informações sobre o descarte correto de resíduos.

## 👥 Desenvolvimento

Projeto acadêmico desenvolvido em equipe durante a **Residência Full Stack 5.0**, do Instituto Eldorado em parceria com a Petrobras.

## 📄 Licença

A licença será definida pela equipe.

---

**Recicla+ — Recicle, acumule pontos e transforme o futuro.** ♻️
