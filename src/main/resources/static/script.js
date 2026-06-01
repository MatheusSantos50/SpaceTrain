// Modal logic
const modal = document.getElementById('add-image-modal');
const btnOpen = document.getElementById('btn-add-image');
const btnClose = document.getElementById('btn-close-modal');
const form = document.getElementById('add-image-form');

if (btnOpen && modal && btnClose) {
  // Open modal
  btnOpen.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.add('active');
  });

  // Close modal via button
  btnClose.addEventListener('click', () => {
    modal.classList.remove('active');
  });

  // Close modal by clicking outside
  window.addEventListener('click', (e) => {
    if (e.target === modal) {
      modal.classList.remove('active');
    }
  });
}

if (form) {
  form.addEventListener('submit', (e) => {
    e.preventDefault();
    // Simulate upload
    alert('Imagem adicionada com sucesso! (Isto é apenas uma simulação)');
    modal.classList.remove('active');
    form.reset();
  });
}
