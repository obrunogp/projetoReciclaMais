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
        <a class="button button-lime" style="margin-top:8px" href="painel.html">Conhecer meu painel ↗</a>
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
  menuButton.textContent = "☰";
}));
