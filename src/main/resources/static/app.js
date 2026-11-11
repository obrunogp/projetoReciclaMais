const root = document.documentElement;
const themeButton = document.querySelector(".theme-toggle");
const themeIcon = document.querySelector(".theme-icon");

function applyTheme(theme) {
  root.dataset.theme = theme;
  const dark = theme === "dark";
  themeIcon.textContent = dark ? "☀" : "☾";
  themeButton.setAttribute("aria-label", dark ? "Ativar modo claro" : "Ativar modo escuro");
  document.querySelector('meta[name="theme-color"]')?.setAttribute("content", dark ? "#171d19" : "#eef1e8");
}

const savedTheme = localStorage.getItem("recicla-theme");
applyTheme(savedTheme || (window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light"));

themeButton.addEventListener("click", () => {
  const nextTheme = root.dataset.theme === "dark" ? "light" : "dark";
  applyTheme(nextTheme);
  localStorage.setItem("recicla-theme", nextTheme);
});

const modalData = {
  materials: {
    kicker: "GUIA DE RECICLAGEM · 01",
    title: "O que pode ser reciclado?",
    html: `
      <div class="modal-columns">
        <div class="modal-group can"><h3><span>✓</span> Geralmente pode</h3><ul>
          <li><strong>Plástico:</strong> garrafas PET, frascos de produtos de limpeza, potes e embalagens limpas.</li>
          <li><strong>Papel:</strong> folhas, jornais, revistas, caixas e papelão secos.</li>
          <li><strong>Vidro:</strong> garrafas e potes de alimentos, sem resíduos.</li>
          <li><strong>Metal:</strong> latinhas, tampas e embalagens de alumínio ou aço.</li>
        </ul></div>
        <div class="modal-group cannot"><h3><span>×</span> Evite colocar junto</h3><ul>
          <li>Papel higiênico, guardanapos engordurados e papel sujo.</li>
          <li>Embalagens com restos de comida ou líquidos.</li>
          <li>Espelhos, porcelana, cerâmica e lâmpadas no vidro comum.</li>
          <li>Fraldas, absorventes e resíduos orgânicos.</li>
        </ul></div>
      </div>
      <div class="modal-single" style="margin-top:14px"><strong>Dica:</strong> esvazie as embalagens e retire o excesso de resíduos. Não é necessário gastar muita água: uma pequena limpeza já pode ajudar na triagem.</div>`
  },
  destination: {
    kicker: "GUIA DE RECICLAGEM · 02",
    title: "Prepare e encaminhe",
    html: `
      <div class="modal-single">
        <p><strong>1. Separe por material.</strong> Deixe papel, plástico, metal e vidro separados sempre que possível.</p>
        <p><strong>2. Cuide da segurança.</strong> Vidros quebrados devem ser embalados e identificados para evitar acidentes. Confirme se o local recebe esse tipo de material.</p>
        <p><strong>3. Procure um ponto de coleta.</strong> Cooperativas, ecopontos e pontos de entrega voluntária podem receber recicláveis.</p>
        <p><strong>4. Registre sua contribuição.</strong> Nos pontos participantes do Recicla+, o operador poderá registrar o material e o peso entregue.</p>
      </div>`
  },
  points: {
    kicker: "GUIA DE RECICLAGEM · 03",
    title: "Como funcionam os pontos?",
    html: `
      <div class="modal-single">
        <p>Ao entregar seus materiais em um ponto participante, a equipe responsável pode conferir o tipo de resíduo e registrar o peso recebido.</p>
        <p>Com o registro, sua conta poderá receber pontos de acordo com as regras do projeto. Depois, você acompanha suas contribuições e seu histórico na área do usuário.</p>
        <p><strong>Importante:</strong> a pontuação depende das regras de cada ponto participante. Nem todo local de coleta faz parte do sistema de recompensas.</p>
        <a class="button button-lime" style="margin-top:8px" href="#consulta-pontos" data-consult-points>Consultar meus pontos ↗</a>
      </div>`
  }
};

const backdrop = document.querySelector("#modal-backdrop");
const modalTitle = document.querySelector("#modal-title");
const modalKicker = document.querySelector("#modal-kicker");
const modalContent = document.querySelector("#modal-content");
const closeButton = document.querySelector(".modal-close");
let lastFocusedElement = null;

function openModal(key) {
  const data = modalData[key];
  if (!data) return;
  lastFocusedElement = document.activeElement;
  modalKicker.textContent = data.kicker;
  modalTitle.textContent = data.title;
  modalContent.innerHTML = data.html;
  backdrop.hidden = false;
  document.body.style.overflow = "hidden";
  closeButton.focus();
}
function closeModal() {
  backdrop.hidden = true;
  document.body.style.overflow = "";
  lastFocusedElement?.focus();
}

if (backdrop && closeButton) {
  document.querySelectorAll("[data-modal]").forEach(card => {
    card.addEventListener("click", () => openModal(card.dataset.modal));
  });
  closeButton.addEventListener("click", closeModal);
  backdrop.addEventListener("click", event => {
    if (event.target === backdrop) closeModal();
  });
  document.addEventListener("keydown", event => {
    if (event.key === "Escape" && !backdrop.hidden) closeModal();
  });
}

const menuButton = document.querySelector(".menu-toggle");
const nav = document.querySelector(".main-nav");
menuButton.addEventListener("click", () => {
  const open = nav.classList.toggle("open");
  menuButton.setAttribute("aria-expanded", String(open));
  menuButton.textContent = open ? "×" : "☰";
});
nav.querySelectorAll("a").forEach(link => link.addEventListener("click", () => {
  nav.classList.remove("open");
  menuButton.setAttribute("aria-expanded", "false");
  menuButton.setAttribute("aria-label", "Abrir menu");
  menuButton.textContent = "☰";
}));

menuButton.addEventListener("click", () => {
  const open = menuButton.getAttribute("aria-expanded") === "true";
  menuButton.setAttribute("aria-label", open ? "Fechar menu" : "Abrir menu");
});

document.addEventListener("click", event => {
  const link = event.target.closest("[data-consult-points]");
  if (!link) return;
  event.preventDefault();
  closeModal();
  document.querySelector("#consulta-pontos")?.scrollIntoView({ behavior: "smooth" });
});

const lookupForm = document.querySelector("#lookup-form");
const cpfInput = document.querySelector("#cpf");
const lookupButton = document.querySelector("#lookup-button");
const statusMessage = document.querySelector("#status-message");
const participantPanel = document.querySelector("#participant-panel");
const participantName = document.querySelector("#participant-name");
const participantCpf = document.querySelector("#participant-cpf");
const participantBalance = document.querySelector("#participant-balance");
const historyPanel = document.querySelector("#history-panel");
const historyBody = document.querySelector("#history-body");
const historyCount = document.querySelector("#history-count");

async function api(path, options = {}) {
  let response;
  try {
    response = await fetch(path, {
      ...options,
      headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    });
  } catch {
    throw new Error("Não foi possível conectar ao Recicla+. Tente novamente em instantes.");
  }

  const body = await response.json().catch(() => ({}));
  if (!response.ok) throw new Error(body.erro || `A consulta falhou (${response.status}).`);
  return body;
}

function setQueryStatus(message, kind = "") {
  statusMessage.textContent = message;
  statusMessage.classList.toggle("error", kind === "error");
  statusMessage.classList.toggle("success", kind === "success");
}

function formatCpf(value) {
  const digits = value.replace(/\D/g, "").slice(0, 11);
  if (digits.length > 9) return digits.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4");
  if (digits.length > 6) return digits.replace(/(\d{3})(\d{3})(\d{1,3})/, "$1.$2.$3");
  if (digits.length > 3) return digits.replace(/(\d{3})(\d{1,3})/, "$1.$2");
  return digits;
}

function showEmptyHistory(message) {
  historyBody.replaceChildren();
  const row = document.createElement("tr");
  row.className = "empty-row";
  const cell = document.createElement("td");
  cell.colSpan = 4;
  cell.textContent = message;
  row.append(cell);
  historyBody.append(row);
}

function displayHistory(items) {
  historyBody.replaceChildren();
  if (!items.length) {
    showEmptyHistory("Ainda não há coletas registradas para este participante.");
    historyCount.textContent = "0 coletas";
    return;
  }

  for (const item of items) {
    const row = document.createElement("tr");
    const date = document.createElement("td");
    const material = document.createElement("td");
    const weight = document.createElement("td");
    const points = document.createElement("td");
    date.textContent = item.dataHora ? new Date(item.dataHora).toLocaleString("pt-BR") : "—";
    material.textContent = item.materialNome || "—";
    weight.textContent = new Intl.NumberFormat("pt-BR", { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(Number(item.pesoKg));
    points.textContent = `${item.pontosGerados ?? 0} pts`;
    points.className = "points-cell";
    row.append(date, material, weight, points);
    historyBody.append(row);
  }
  historyCount.textContent = `${items.length} ${items.length === 1 ? "coleta" : "coletas"}`;
}

cpfInput.addEventListener("input", () => {
  const formatted = formatCpf(cpfInput.value);
  if (formatted !== cpfInput.value) cpfInput.value = formatted;
});

lookupForm.addEventListener("submit", async event => {
  event.preventDefault();
  const cpf = cpfInput.value.replace(/\D/g, "");
  if (cpf.length !== 11) {
    setQueryStatus("Digite um CPF com 11 números.", "error");
    cpfInput.focus();
    return;
  }

  lookupButton.disabled = true;
  participantPanel.hidden = true;
  historyPanel.hidden = true;
  setQueryStatus("Consultando seu saldo de pontos…");

  try {
    const participant = await api("/api/usuarios/identificar", {
      method: "POST",
      body: JSON.stringify({ cpf }),
    });

    participantName.textContent = participant.nome;
    participantCpf.textContent = participant.cpfMascarado;
    participantBalance.textContent = new Intl.NumberFormat("pt-BR").format(participant.saldoPontos ?? 0);
    participantPanel.hidden = false;
    historyPanel.hidden = false;
    historyCount.textContent = "Consultando…";
    showEmptyHistory("Buscando o histórico de coletas…");
    setQueryStatus(`Consulta concluída para ${participant.nome}.`, "success");

    try {
      const collections = await api(`/api/usuarios/${participant.usuarioId}/coletas`);
      displayHistory(collections);
    } catch (error) {
      historyCount.textContent = "Histórico indisponível";
      showEmptyHistory(error.message);
    }
  } catch (error) {
    setQueryStatus(error.message, "error");
  } finally {
    lookupButton.disabled = false;
  }
});
