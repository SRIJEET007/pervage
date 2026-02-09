document.addEventListener('DOMContentLoaded', () => {
    // Elements
    const grid = document.getElementById('image-grid');
    const uploadBtn = document.getElementById('upload-btn');
    const uploadModal = document.getElementById('upload-modal');
    const closeModalBtn = document.getElementById('close-modal');
    const fileInput = document.getElementById('file-input');
    const dateFilter = document.getElementById('date-filter');
    const lightbox = document.getElementById('lightbox');
    const lightboxImg = document.getElementById('lightbox-img');
    const lightboxClose = document.getElementById('lightbox-close');

    // State
    let images = [];

    // --- API Interactions ---

    async function fetchImages(date = null) {
        try {
            let url = '/pervage/images';
            if (date) {
                url = `/pervage/images/date?date=${date}`;
            }

            const response = await fetch(url);

            if (response.status === 401 || response.status === 403) {
                window.location.href = '/login'; // Redirect to login if unauthorized
                return;
            }

            if (!response.ok) throw new Error('Failed to fetch images');

            images = await response.json();
            renderImages(images);
        } catch (err) {
            console.error(err);
            // Optionally show error toast
        }
    }

    async function uploadImage(file) {
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch('/pervage/upload', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) throw new Error('Upload failed');

            // Refresh grid
            closeModal();
            fetchImages(dateFilter.value || null);
        } catch (err) {
            console.error(err);
            alert('Upload failed: ' + err.message);
        }
    }

    async function deleteImage(id) {
        if (!confirm('Are you sure you want to delete this image?')) return;

        try {
            const response = await fetch(`/pervage/delete/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) throw new Error('Delete failed');

            // Remove from UI immediately for snappy feel
            const card = document.querySelector(`[data-id="${id}"]`);
            if (card) card.remove();
        } catch (err) {
            console.error(err);
            alert('Delete failed');
        }
    }

    // --- Rendering ---

    function renderImages(imageList) {
        grid.innerHTML = '';

        if (imageList.length === 0) {
            grid.innerHTML = `<div style="grid-column: 1/-1; text-align: center; padding: 4rem; color: var(--text-secondary);">
                <h3>No images found</h3>
                <p>Upload some memories or try a different date.</p>
            </div>`;
            return;
        }

        imageList.forEach(img => {
            const card = document.createElement('div');
            card.className = 'image-card';
            card.dataset.id = img.id;

            // Format date
            const date = new Date(img.uploadedAt).toLocaleDateString();

            card.innerHTML = `
                <div class="image-wrapper">
                    <img src="/pervage/images/${img.id}" alt="${img.fileName}" loading="lazy">
                    <div class="card-overlay">
                        <div class="meta-info">
                            <h3>${img.fileName}</h3>
                            <p>${date}</p>
                        </div>
                        <button class="delete-btn" title="Delete">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <polyline points="3 6 5 6 21 6"></polyline>
                                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                            </svg>
                        </button>
                    </div>
                </div>
            `;

            // Click image to open lightbox
            card.querySelector('.image-wrapper').addEventListener('click', (e) => {
                if (!e.target.closest('.delete-btn')) {
                    openLightbox(img.id);
                }
            });

            // Click delete
            card.querySelector('.delete-btn').addEventListener('click', (e) => {
                e.stopPropagation();
                deleteImage(img.id);
            });

            grid.appendChild(card);
        });
    }

    function openLightbox(id) {
        lightboxImg.src = `/pervage/images/${id}`;
        lightbox.style.display = 'flex';
    }

    function closeLightbox() {
        lightbox.style.display = 'none';
        lightboxImg.src = '';
    }

    function openModal() {
        uploadModal.style.display = 'flex';
    }

    function closeModal() {
        uploadModal.style.display = 'none';
        fileInput.value = ''; // Reset input
    }

    // --- Event Listeners ---

    // Initial Load
    fetchImages();

    // Controls
    dateFilter.addEventListener('change', (e) => {
        fetchImages(e.target.value);
    });

    // Upload Modal
    uploadBtn.addEventListener('click', openModal);
    closeModalBtn.addEventListener('click', closeModal);
    uploadModal.addEventListener('click', (e) => {
        if (e.target === uploadModal) closeModal();
    });

    // File Input
    fileInput.addEventListener('change', (e) => {
        if (e.target.files.length > 0) {
            uploadImage(e.target.files[0]);
        }
    });

    // Lightbox
    lightboxClose.addEventListener('click', closeLightbox);
    lightbox.addEventListener('click', (e) => {
        if (e.target === lightbox) closeLightbox();
    });

    // Keyboard support
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            closeModal();
            closeLightbox();
        }
    });
});
