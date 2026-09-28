/**
 * Smart Disaster Relief Resource Allocation & Inventory Tracker - Frontend Logic
 */

document.addEventListener('DOMContentLoaded', () => {
    checkAuthSession();
    setupNavigationHighlight();
});

// User Session helper
function checkAuthSession() {
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');
    const name = localStorage.getItem('name');

    const userProfileEl = document.getElementById('userProfileDisplay');
    if (userProfileEl && username) {
        userProfileEl.innerHTML = `
            <span class="user-badge">${role || 'USER'}</span>
            <span style="font-weight:600; font-size:0.9rem;">${name || username}</span>
            <button onclick="logoutUser()" class="btn btn-secondary" style="padding: 0.35rem 0.75rem; font-size:0.8rem;">Logout</button>
        `;
    }
}

function logoutUser() {
    localStorage.clear();
    window.location.href = '/login';
}

function setupNavigationHighlight() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-links a');
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
}

// Modal Helpers
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
    }
}

// Generic API Fetch Helper
async function apiFetch(url, options = {}) {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({ message: 'Server error' }));
            throw new Error(errorData.message || `HTTP error ${response.status}`);
        }

        if (response.status === 24 || response.status === 204) {
            return null;
        }

        return await response.json();
    } catch (err) {
        console.error('API Error:', err);
        throw err;
    }
}

// Toast / Notification Helper
function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `alert-box`;
    toast.style.position = 'fixed';
    toast.style.bottom = '20px';
    toast.style.right = '20px';
    toast.style.zIndex = '9999';
    toast.style.minWidth = '300px';
    toast.style.boxShadow = '0 10px 25px rgba(0,0,0,0.5)';
    
    if (type === 'success') {
        toast.style.background = 'rgba(16, 185, 129, 0.9)';
        toast.style.borderColor = '#10b981';
        toast.style.color = '#ffffff';
    } else {
        toast.style.background = 'rgba(239, 68, 68, 0.9)';
        toast.style.borderColor = '#ef4444';
        toast.style.color = '#ffffff';
    }

    toast.innerHTML = `<strong>${type.toUpperCase()}:</strong> ${message}`;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 4000);
}
