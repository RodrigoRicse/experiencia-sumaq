document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.querySelector('.menu-toggle');
    const navigation = document.querySelector('#main-navigation');

    if (toggle && navigation) {
        toggle.addEventListener('click', () => {
            const open = toggle.getAttribute('aria-expanded') === 'true';
            toggle.setAttribute('aria-expanded', String(!open));
            navigation.classList.toggle('open', !open);
        });
    }

    document.querySelectorAll('[data-current-year]').forEach((element) => {
        element.textContent = String(new Date().getFullYear());
    });

    const toast = document.querySelector('.toast');
    document.querySelectorAll('.js-cart-preview').forEach((button) => {
        button.addEventListener('click', () => {
            if (!toast) return;
            toast.textContent = `${button.dataset.product}: carrito disponible en el siguiente incremento.`;
            toast.hidden = false;
            window.clearTimeout(window.sumaqToastTimer);
            window.sumaqToastTimer = window.setTimeout(() => {
                toast.hidden = true;
            }, 2800);
        });
    });

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) entry.target.classList.add('visible');
        });
    }, { threshold: 0.08 });

    document.querySelectorAll('.reveal').forEach((element) => observer.observe(element));
});
