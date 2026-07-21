document.addEventListener('DOMContentLoaded', () => {
    const clock = document.querySelector('[data-live-clock]');
    const updateClock = () => {
        if (clock) clock.textContent = new Intl.DateTimeFormat('es-PE', { hour: '2-digit', minute: '2-digit', second: '2-digit' }).format(new Date());
    };
    updateClock();
    window.setInterval(updateClock, 1000);

    const toggle = document.querySelector('.ops-menu-toggle');
    const navigation = document.querySelector('#ops-navigation');
    if (toggle && navigation) {
        toggle.addEventListener('click', () => {
            const open = toggle.getAttribute('aria-expanded') === 'true';
            toggle.setAttribute('aria-expanded', String(!open));
            navigation.classList.toggle('open', !open);
        });
    }

    document.querySelectorAll('.toast').forEach((toast) => window.setTimeout(() => toast.remove(), 3200));
});
