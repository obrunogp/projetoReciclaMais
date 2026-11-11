package br.com.reciclamais.desktop;

import tools.jackson.databind.JsonNode;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

final class JanelaOperacao extends JFrame {

    private static final Color VERDE = new Color(23, 107, 80);
    private final ReciclaMaisApiClient api = new ReciclaMaisApiClient();
    private final JLabel status = new JLabel("Pronto para atender.");

    private final JTextField cpfColeta = new JTextField(20);
    private final JComboBox<Item> materialColeta = new JComboBox<>();
    private final JTextField pesoColeta = new JTextField(20);
    private final JLabel participanteColeta = new JLabel("Nenhum participante localizado.");
    private final JTextArea comprovante = new JTextArea();
    private JsonNode usuarioAtual;

    private final JTextField novoNome = new JTextField(24);
    private final JTextField novoCpf = new JTextField(24);
    private final JTextField novoEmail = new JTextField(24);
    private final JPasswordField novaSenha = new JPasswordField(24);
    private final JTextField novoEndereco = new JTextField(24);
    private final JTextField novoTelefone = new JTextField(24);

    private final JTextField materialNome = new JTextField(24);
    private final JComboBox<Item> tipoMaterial = new JComboBox<>();
    private final JTextField pontosPorKg = new JTextField("2,00", 24);

    JanelaOperacao() {
        super("Recicla+ | Operação do ponto de coleta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(850, 650));
        setSize(980, 720);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBorder(BorderFactory.createEmptyBorder(22, 26, 16, 26));
        root.setBackground(new Color(244, 247, 245));

        JLabel brand = new JLabel("RECICLA+", SwingConstants.LEFT);
        brand.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        brand.setForeground(VERDE);
        JLabel subtitle = new JLabel("Atendimento no ponto de coleta");
        subtitle.setForeground(new Color(84, 99, 93));
        JPanel heading = new JPanel(new BorderLayout(0, 5));
        heading.setOpaque(false);
        heading.add(brand, BorderLayout.NORTH);
        heading.add(subtitle, BorderLayout.SOUTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Registrar coleta", criarPainelColeta());
        tabs.addTab("Cadastrar participante", criarPainelUsuario());
        tabs.addTab("Cadastrar material", criarPainelMaterial());

        status.setBorder(BorderFactory.createEmptyBorder(8, 2, 0, 2));
        status.setForeground(new Color(84, 99, 93));
        root.add(heading, BorderLayout.NORTH);
        root.add(tabs, BorderLayout.CENTER);
        root.add(status, BorderLayout.SOUTH);
        setContentPane(root);

        carregarMateriais();
        carregarTipos();
    }

    private JPanel criarPainelColeta() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);
        GridBagConstraints c = constraints();

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 226, 221)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        GridBagConstraints f = constraints();
        f.fill = GridBagConstraints.HORIZONTAL;
        f.weightx = 1;
        addHeading(form, f, "Registrar coleta", "Identifique o participante e informe o material recebido.");
        addField(form, f, 2, "CPF do participante", cpfColeta);
        JButton localizar = new JButton("Localizar participante");
        localizar.addActionListener(event -> localizarParticipante());
        addRow(form, f, 4, localizar);
        participanteColeta.setForeground(VERDE);
        addRow(form, f, 5, participanteColeta);
        addField(form, f, 6, "Material recebido", materialColeta);
        addField(form, f, 8, "Peso recebido (kg)", pesoColeta);
        JButton registrar = new JButton("Registrar coleta e emitir comprovante");
        registrar.setForeground(Color.BLACK);
        registrar.addActionListener(event -> registrarColeta());
        addRow(form, f, 10, registrar);

        comprovante.setEditable(false);
        comprovante.setLineWrap(true);
        comprovante.setWrapStyleWord(false);
        comprovante.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        comprovante.setText("RECICLA+ — COMPROVANTE\n\nLocalize um participante, escolha o material e informe o peso.\nO comprovante aparecerá aqui após o registro.");
        JScrollPane receiptScroll = new JScrollPane(comprovante);
        receiptScroll.setBorder(BorderFactory.createTitledBorder("Comprovante de coleta"));

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.48;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(8, 8, 8, 8);
        page.add(form, c);
        c.gridx = 1;
        c.weightx = 0.52;
        page.add(receiptScroll, c);
        return page;
    }

    private JPanel criarPainelUsuario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 226, 221)),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)));
        GridBagConstraints c = constraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        addHeading(form, c, "Novo participante", "Os campos com dados pessoais serão gravados no banco local.");
        addField(form, c, 2, "Nome completo", novoNome);
        addField(form, c, 4, "CPF", novoCpf);
        addField(form, c, 6, "E-mail", novoEmail);
        addField(form, c, 8, "Senha (mínimo 8 caracteres)", novaSenha);
        addField(form, c, 10, "Endereço (opcional)", novoEndereco);
        addField(form, c, 12, "Telefone (opcional)", novoTelefone);
        JButton cadastrar = new JButton("Cadastrar participante");
        cadastrar.setForeground(Color.BLACK);
        cadastrar.addActionListener(event -> cadastrarUsuario());
        addRow(form, c, 14, cadastrar);

        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);
        GridBagConstraints p = constraints();
        p.fill = GridBagConstraints.HORIZONTAL;
        p.weightx = 1;
        p.anchor = GridBagConstraints.NORTH;
        page.add(form, p);
        return page;
    }

    private JPanel criarPainelMaterial() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 226, 221)),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)));
        GridBagConstraints c = constraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        addHeading(form, c, "Novo material", "Selecione um tipo e configure a pontuação por quilo.");
        addField(form, c, 2, "Nome do material", materialNome);
        addField(form, c, 4, "Tipo de material", tipoMaterial);
        JButton novoTipo = new JButton("Cadastrar novo tipo");
        novoTipo.addActionListener(event -> cadastrarTipo());
        addRow(form, c, 5, novoTipo);
        addField(form, c, 7, "Pontos por kg", pontosPorKg);
        JButton cadastrar = new JButton("Cadastrar material");
        cadastrar.setForeground(Color.BLACK);
        cadastrar.addActionListener(event -> cadastrarMaterial());
        addRow(form, c, 9, cadastrar);

        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);
        GridBagConstraints p = constraints();
        p.fill = GridBagConstraints.HORIZONTAL;
        p.weightx = 1;
        p.anchor = GridBagConstraints.NORTH;
        page.add(form, p);
        return page;
    }

    private void localizarParticipante() {
        try {
            usuarioAtual = api.post("/api/usuarios/identificar", Map.of("cpf", cpfColeta.getText().trim()));
            participanteColeta.setText(usuarioAtual.path("nome").asText()
                    + " · CPF " + usuarioAtual.path("cpfMascarado").asText()
                    + " · saldo " + usuarioAtual.path("saldoPontos").asText("0") + " pts");
            setStatus("Participante localizado.", VERDE);
        } catch (Exception exception) {
            usuarioAtual = null;
            participanteColeta.setText("Nenhum participante localizado.");
            mostrarErro(exception);
        }
    }

    private void registrarColeta() {
        try {
            if (usuarioAtual == null) {
                throw new IllegalArgumentException("Localize o participante pelo CPF antes de registrar a coleta.");
            }
            Item material = (Item) materialColeta.getSelectedItem();
            if (material == null) throw new IllegalArgumentException("Cadastre ou selecione um material ativo.");
            BigDecimal peso = decimal(pesoColeta.getText(), "Informe o peso recebido.");
            if (peso.signum() <= 0) throw new IllegalArgumentException("O peso deve ser maior que zero.");

            JsonNode coleta = api.post("/api/coletas", Map.of(
                    "usuarioId", usuarioAtual.path("usuarioId").intValue(),
                    "materialId", material.id(),
                    "pesoKg", peso));
            JsonNode saldo = api.get("/api/usuarios/" + usuarioAtual.path("usuarioId").asInt() + "/pontos");
            comprovante.setText("================================================\n"
                    + "             RECICLA+ — COMPROVANTE\n"
                    + "================================================\n"
                    + "Participante: " + usuarioAtual.path("nome").asText() + "\n"
                    + "CPF:          " + usuarioAtual.path("cpfMascarado").asText() + "\n"
                    + "Data/hora:    " + coleta.path("dataHora").asText() + "\n"
                    + "Material:     " + coleta.path("materialNome").asText(material.nome()) + "\n"
                    + "Peso recebido: " + coleta.path("pesoKg").asText() + " kg\n"
                    + "Pontos:       " + coleta.path("pontosGerados").asText() + "\n"
                    + "Saldo atual:  " + saldo.path("saldoPontos").asText("0") + " pontos\n"
                    + "================================================\n"
                    + "Protótipo demonstrativo — não fiscal\n"
                    + "================================================");
            setStatus("Coleta registrada no banco e comprovante emitido.", VERDE);
            pesoColeta.setText("");
            localizarParticipante();
        } catch (Exception exception) {
            mostrarErro(exception);
        }
    }

    private void cadastrarUsuario() {
        try {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("nome", novoNome.getText().trim());
            body.put("cpf", novoCpf.getText().trim());
            body.put("email", novoEmail.getText().trim());
            body.put("senha", new String(novaSenha.getPassword()));
            body.put("endereco", novoEndereco.getText().trim());
            body.put("telefone", novoTelefone.getText().trim());
            JsonNode usuario = api.post("/api/usuarios", body);
            setStatus("Participante cadastrado com sucesso. ID " + usuario.path("id").asText() + ".", VERDE);
            JOptionPane.showMessageDialog(this,
                    "Cadastro concluído para " + usuario.path("nome").asText() + ".",
                    "Participante cadastrado", JOptionPane.INFORMATION_MESSAGE);
            cpfColeta.setText(usuario.path("cpf").asText());
            limparCamposUsuario();
        } catch (Exception exception) {
            mostrarErro(exception);
        }
    }

    private void cadastrarTipo() {
        JTextField nome = new JTextField();
        JTextField descricao = new JTextField();
        JPanel fields = new JPanel(new GridBagLayout());
        GridBagConstraints c = constraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        addField(fields, c, 0, "Nome do tipo", nome);
        addField(fields, c, 2, "Descrição (opcional)", descricao);
        int result = JOptionPane.showConfirmDialog(this, fields, "Cadastrar tipo de material",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;
        try {
            JsonNode created = api.post("/api/tipos-materiais", Map.of(
                    "nome", nome.getText().trim(), "descricao", descricao.getText().trim()));
            carregarTipos();
            selecionarItem(tipoMaterial, created.path("id").asInt());
            setStatus("Tipo de material cadastrado.", VERDE);
        } catch (Exception exception) {
            mostrarErro(exception);
        }
    }

    private void cadastrarMaterial() {
        try {
            Item tipo = (Item) tipoMaterial.getSelectedItem();
            if (tipo == null) throw new IllegalArgumentException("Cadastre ou selecione um tipo de material.");
            BigDecimal pontos = decimal(pontosPorKg.getText(), "Informe os pontos por kg.");
            if (pontos.signum() < 0) throw new IllegalArgumentException("Os pontos por kg não podem ser negativos.");
            JsonNode material = api.post("/api/materiais", Map.of(
                    "nome", materialNome.getText().trim(),
                    "tipoMaterialId", tipo.id(),
                    "pontosPorKg", pontos));
            setStatus("Material cadastrado com sucesso. ID " + material.path("id").asText() + ".", VERDE);
            materialNome.setText("");
            carregarMateriais();
            JOptionPane.showMessageDialog(this,
                    material.path("nome").asText() + " foi cadastrado com "
                            + material.path("pontosPorKg").asText() + " pontos/kg.",
                    "Material cadastrado", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            mostrarErro(exception);
        }
    }

    private void carregarMateriais() {
        try {
            materialColeta.removeAllItems();
            for (JsonNode material : api.get("/api/materiais")) {
                materialColeta.addItem(new Item(material.path("id").asInt(),
                        material.path("nome").asText() + " · " + material.path("tipo").asText()
                                + " · " + material.path("pontosPorKg").asText() + " pts/kg"));
            }
        } catch (Exception exception) {
            setStatus("Não foi possível carregar os materiais: " + exception.getMessage(), new Color(163, 61, 54));
        }
    }

    private void carregarTipos() {
        try {
            tipoMaterial.removeAllItems();
            for (JsonNode tipo : api.get("/api/tipos-materiais")) {
                tipoMaterial.addItem(new Item(tipo.path("id").asInt(), tipo.path("nome").asText()));
            }
        } catch (Exception exception) {
            setStatus("Não foi possível carregar os tipos: " + exception.getMessage(), new Color(163, 61, 54));
        }
    }

    private void limparCamposUsuario() {
        novoNome.setText("");
        novoCpf.setText("");
        novoEmail.setText("");
        novaSenha.setText("");
        novoEndereco.setText("");
        novoTelefone.setText("");
    }

    private void mostrarErro(Exception exception) {
        String message = exception.getMessage() == null ? "Erro ao comunicar com o sistema." : exception.getMessage();
        setStatus(message, new Color(163, 61, 54));
        JOptionPane.showMessageDialog(this, message, "Não foi possível concluir", JOptionPane.ERROR_MESSAGE);
    }

    private void setStatus(String message, Color color) {
        status.setText(message);
        status.setForeground(color);
    }

    private static BigDecimal decimal(String value, String missingMessage) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(missingMessage);
        try {
            return new BigDecimal(value.trim().replace(',', '.'));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Digite um número válido, usando vírgula ou ponto nos decimais.");
        }
    }

    private static void selecionarItem(JComboBox<Item> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).id() == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private static void addHeading(JPanel panel, GridBagConstraints c, String title, String description) {
        JLabel heading = new JLabel(title);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        heading.setForeground(new Color(39, 51, 46));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(4, 4, 4, 4);
        panel.add(heading, c);
        JLabel note = new JLabel(description);
        note.setForeground(new Color(108, 120, 115));
        c.gridy = 1;
        c.insets = new Insets(0, 4, 18, 4);
        panel.add(note, c);
        c.gridwidth = 1;
    }

    private static void addField(JPanel panel, GridBagConstraints c, int row, String label, java.awt.Component input) {
        JLabel caption = new JLabel(label);
        caption.setFont(caption.getFont().deriveFont(Font.BOLD));
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(9, 4, 5, 10);
        panel.add(caption, c);
        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(5, 4, 5, 4);
        panel.add(input, c);
    }

    private static void addRow(JPanel panel, GridBagConstraints c, int row, java.awt.Component component) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 4, 8, 4);
        panel.add(component, c);
        c.gridwidth = 1;
    }

    private static GridBagConstraints constraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 4, 4, 4);
        return c;
    }

    private record Item(int id, String nome) {
        @Override
        public String toString() { return nome; }
    }
}
