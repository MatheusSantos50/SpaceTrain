// Modal logic — attach after DOM is ready and add lightweight debug logs
(() => {
  function initModal() {
    const modal = document.getElementById('add-image-modal');
    const btnOpen = document.getElementById('btn-add-image');
    const btnClose = document.getElementById('btn-close-modal');

    if (!btnOpen) {
      console.warn('Add Image button not found (id=btn-add-image)');
      return;
    }

    if (!modal) {
      console.warn('Modal element not found (id=add-image-modal)');
      return;
    }

    // Open modal
    btnOpen.addEventListener('click', (e) => {
      e.preventDefault();
      console.debug('Opening add-image modal');
      modal.classList.add('active');
    });

    // Close modal via button (if present)
    if (btnClose) {
      btnClose.addEventListener('click', () => {
        modal.classList.remove('active');
      });
    }

    // Close modal by clicking outside
    window.addEventListener('click', (e) => {
      if (e.target === modal) {
        modal.classList.remove('active');
      }
    });

    // Edit Modal Elements
    const editModal = document.getElementById('edit-image-modal');
    const btnCloseEdit = document.getElementById('btn-close-edit-modal');
    const btnDeleteImage = document.getElementById('btn-delete-image');
    const deleteImageForm = document.getElementById('delete-image-form');
    const deleteImageId = document.getElementById('delete-image-id');

    // Attach click listeners to all Edit buttons in the gallery
    document.querySelectorAll('.btn-edit-image').forEach(btn => {
      btn.addEventListener('click', (e) => {
        e.preventDefault();
        e.stopPropagation();

        const id = btn.getAttribute('data-id');
        const titulo = btn.getAttribute('data-titulo');
        const descricao = btn.getAttribute('data-descricao');
        const categoriasStr = btn.getAttribute('data-categorias') || '';

        // Prefill modal fields
        document.getElementById('edit-image-id').value = id;
        document.getElementById('edit-photo-title').value = titulo || '';
        document.getElementById('edit-photo-description').value = descricao || '';

        // Parse categories, e.g. "[1, 2]" or "1,2"
        const cleanCats = categoriasStr.replace(/[\[\]\s]/g, '').split(',').filter(Boolean).map(Number);

        // Reset and pre-check category checkboxes in the edit modal
        document.querySelectorAll('.edit-category-checkbox').forEach(cb => {
          cb.checked = cleanCats.includes(Number(cb.value));
        });

        // Show edit modal
        if (editModal) {
          editModal.classList.add('active');
        }
      });
    });

    // Close edit modal via close button
    if (btnCloseEdit) {
      btnCloseEdit.addEventListener('click', () => {
        if (editModal) editModal.classList.remove('active');
      });
    }

    // Close edit modal by clicking outside
    window.addEventListener('click', (e) => {
      if (editModal && e.target === editModal) {
        editModal.classList.remove('active');
      }
    });

    // Handle delete action
    if (btnDeleteImage && deleteImageForm && deleteImageId) {
      btnDeleteImage.addEventListener('click', () => {
        if (confirm('Tem certeza de que deseja excluir esta imagem permanentemente?')) {
          deleteImageId.value = document.getElementById('edit-image-id').value;
          deleteImageForm.submit();
        }
      });
    }
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initModal);
  } else {
    // DOMContentLoaded already fired
    initModal();
  }
})();

